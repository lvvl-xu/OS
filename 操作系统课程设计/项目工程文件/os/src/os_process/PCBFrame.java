package os_process;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PCBFrame extends JFrame {

	public static JTextField pidTextField = new JTextField("1");
	public static int pidCount = 1;// ���̱�ʶ�ţ�ÿ������һ�����̣��Զ���1

	private JLabel priorityLabel;// �������ȼ�
	private JComboBox priorityComboBox;// �������ȼ�
	private JTextField lastTimeTextField;// ��������ʱ��

	/**
	 * Create the frame
	 */
	public PCBFrame() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(PCBFrame.class.getResource("/os_process/res/pcbFrameTitle.png")));
		getContentPane().setBackground(Color.WHITE);
		setBackground(new Color(51, 102, 102));
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(848, 25, 249, 199);
		setTitle("�½�����");

		addWindowListener(new WindowAdapter()// �����࣬�رմ���
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		// ��ǩ ���̱�ʶ��
		final JLabel pidLabel = new JLabel();
		pidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pidLabel.setText("���̱�ʶ�ţ�");
		pidLabel.setBounds(26, 10, 98, 18);
		getContentPane().add(pidLabel);
		// ����� ���̱�ʶ��
		pidTextField.setBounds(130, 10, 96, 22);
		getContentPane().add(pidTextField);

		// ��ǩ �������ȼ�
		priorityLabel = new JLabel();
		priorityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priorityLabel.setText("�������ȼ���");
		priorityLabel.setBounds(26, 50, 98, 18);
		getContentPane().add(priorityLabel);
		// �����б� �������ȼ�
		priorityComboBox = new JComboBox();
		priorityComboBox.setSelectedItem(0);
		priorityComboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		priorityComboBox.setBounds(130, 50, 96, 22);
		getContentPane().add(priorityComboBox);

		// ��ǩ ��������ʱ��
		final JLabel lastTimeLabel = new JLabel();
		lastTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastTimeLabel.setText("��������ʱ�䣺");
		lastTimeLabel.setBounds(26, 90, 98, 18);
		getContentPane().add(lastTimeLabel);
		// ����� ��������ʱ��
		lastTimeTextField = new JTextField();
		lastTimeTextField.setText("10");
		lastTimeTextField.setBounds(130, 90, 96, 22);
		getContentPane().add(lastTimeTextField);

		// ��ť ȷ��
		final JButton createButton = new JButton();
		createButton.setText(" ȷ��");
		createButton.setBounds(36, 122, 72, 28);
		getContentPane().add(createButton);
		// �������������ť�¼�
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCB pcb = new PCB();
				pcb.setPid(Integer.parseInt(pidTextField.getText()));// ���̱�ʶ��
				pcb.setPriority(Integer.parseInt(priorityComboBox.getSelectedItem().toString()));// �������ȼ�
				pcb.setLastTime(Integer.parseInt(lastTimeTextField.getText()));// ���ó���ʱ��
				pcb.setExecuteTime(0);// ������ִ��ʱ��
				if (!notInNewPcb(MainFrame.newPcb, pcb.getPid()))
					showMsg(0);// ���̱�ʶ���Ѵ���
				else if (pcb.getLastTime() == 0)
					showMsg(1);// ���̳���ʱ�䲻��Ϊ0
				else // ���㴴������������������̶��к;���Table
				{
					pcb.setInputTime(CpuColockTimer.time);
					pcb.setStatus("����̬");
					MainFrame.newPcb.add(pcb); // �����½���newPcb
					if (MainFrame.staticPriorityRadioB.isSelected())// ����ǰ����ȼ������㷨��������½��Ľ�������Ĳ��뵽����������
					{
						MainFrame.addPriorityPcb(MainFrame.readyPcb1, pcb);
						MainFrame.updatePriorityTable();
					}else if(MainFrame.sjrRadioB.isSelected()) {
						MainFrame.addLastTimePcb(MainFrame.readyPcb1, pcb);
						MainFrame.updateLastTimeTable();
					} else // ���������㷨����ͳһ�����������1��
					{
						MainFrame.readyPcb1.add(pcb);
						MainFrame.addReadyTable(pcb, 1);
					}

					if (MainFrame.isFirstCreatePcb == 1)// ��һ�δ������̣����Զ���ʼ���е�һ�δ����Ľ��̣��Ժ󴴽��Ľ�����CPU����
					{
						MainFrame.startRunPcb();
						MainFrame.isFirstCreatePcb = 0;
					}

					pidCount++;// ���̱�ʶ���Զ���1
					pidTextField.setText(new Integer(pidCount).toString());
					dispose();
				}
			}
		});
		// ��ť �ر�
		final JButton resetButton = new JButton();
		resetButton.setText("�ر�");
		resetButton.setBounds(140, 122, 72, 28);
		getContentPane().add(resetButton);
		// ��������رհ�ť�¼�
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				dispose();
			}
		});
	}

	private boolean notInNewPcb(Vector<PCB> NewPcb, int pid) {
		for (int i = 0; i < NewPcb.size(); i++)
			if (NewPcb.get(i).getPid() == pid)
				return false;
		return true;
	}

	private void showMsg(int type) {
		switch (type) {
		case 0:
			JOptionPane.showMessageDialog(this, "��������ʧ��O_O\n���̱�ʶ���Ѵ��ڣ�", "ʧ��", JOptionPane.PLAIN_MESSAGE);
			break;
		case 1:
			JOptionPane.showMessageDialog(this, "��������ʧ��O_O\n���̳���ʱ�䲻��0(s) ��", "ʧ��", JOptionPane.PLAIN_MESSAGE);
			break;
		}
	}
}
