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
	public static int pidCount = 1;// 进程标识号，每创建完一个进程，自动加1

	private JLabel priorityLabel;// 进程优先级
	private JComboBox priorityComboBox;// 进程优先级
	private JTextField lastTimeTextField;// 进程运行时间

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
		setTitle("新建进程");

		addWindowListener(new WindowAdapter()// 匿名类，关闭窗口
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		// 便签 进程标识号
		final JLabel pidLabel = new JLabel();
		pidLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pidLabel.setText("进程标识号：");
		pidLabel.setBounds(26, 10, 98, 18);
		getContentPane().add(pidLabel);
		// 输入框 进程标识号
		pidTextField.setBounds(130, 10, 96, 22);
		getContentPane().add(pidTextField);

		// 标签 进程优先级
		priorityLabel = new JLabel();
		priorityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priorityLabel.setText("进程优先级：");
		priorityLabel.setBounds(26, 50, 98, 18);
		getContentPane().add(priorityLabel);
		// 下拉列表 进程优先级
		priorityComboBox = new JComboBox();
		priorityComboBox.setSelectedItem(0);
		priorityComboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		priorityComboBox.setBounds(130, 50, 96, 22);
		getContentPane().add(priorityComboBox);

		// 标签 进程运行时间
		final JLabel lastTimeLabel = new JLabel();
		lastTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastTimeLabel.setText("进程运行时间：");
		lastTimeLabel.setBounds(26, 90, 98, 18);
		getContentPane().add(lastTimeLabel);
		// 输入框 进程运行时间
		lastTimeTextField = new JTextField();
		lastTimeTextField.setText("10");
		lastTimeTextField.setBounds(130, 90, 96, 22);
		getContentPane().add(lastTimeTextField);

		// 按钮 确定
		final JButton createButton = new JButton();
		createButton.setText(" 确定");
		createButton.setBounds(36, 122, 72, 28);
		getContentPane().add(createButton);
		// 监听点击创建按钮事件
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PCB pcb = new PCB();
				pcb.setPid(Integer.parseInt(pidTextField.getText()));// 进程标识号
				pcb.setPriority(Integer.parseInt(priorityComboBox.getSelectedItem().toString()));// 设置优先级
				pcb.setLastTime(Integer.parseInt(lastTimeTextField.getText()));// 设置持续时间
				pcb.setExecuteTime(0);// 设置已执行时间
				if (!notInNewPcb(MainFrame.newPcb, pcb.getPid()))
					showMsg(0);// 进程标识号已存在
				else if (pcb.getLastTime() == 0)
					showMsg(1);// 进程持续时间不能为0
				else // 满足创建条件，加入就绪进程队列和就绪Table
				{
					pcb.setInputTime(CpuColockTimer.time);
					pcb.setStatus("就绪态");
					MainFrame.newPcb.add(pcb); // 加入新进程newPcb
					if (MainFrame.staticPriorityRadioB.isSelected())// 如果是按优先级调度算法，则根据新建的进程有序的插入到就绪队列中
					{
						MainFrame.addPriorityPcb(MainFrame.readyPcb1, pcb);
						MainFrame.updatePriorityTable();
					}else if(MainFrame.sjrRadioB.isSelected()) {
						MainFrame.addLastTimePcb(MainFrame.readyPcb1, pcb);
						MainFrame.updateLastTimeTable();
					} else // 其他调度算法，则统一加入就绪队列1中
					{
						MainFrame.readyPcb1.add(pcb);
						MainFrame.addReadyTable(pcb, 1);
					}

					if (MainFrame.isFirstCreatePcb == 1)// 第一次创建进程，则自动开始运行第一次创建的进程，以后创建的进程由CPU调度
					{
						MainFrame.startRunPcb();
						MainFrame.isFirstCreatePcb = 0;
					}

					pidCount++;// 进程标识号自动增1
					pidTextField.setText(new Integer(pidCount).toString());
					dispose();
				}
			}
		});
		// 按钮 关闭
		final JButton resetButton = new JButton();
		resetButton.setText("关闭");
		resetButton.setBounds(140, 122, 72, 28);
		getContentPane().add(resetButton);
		// 监听点击关闭按钮事件
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
			JOptionPane.showMessageDialog(this, "创建进程失败O_O\n进程标识号已存在！", "失败", JOptionPane.PLAIN_MESSAGE);
			break;
		case 1:
			JOptionPane.showMessageDialog(this, "创建进程失败O_O\n进程持续时间不能0(s) ！", "失败", JOptionPane.PLAIN_MESSAGE);
			break;
		}
	}
}
