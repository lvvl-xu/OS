package os_process;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {

	private JComboBox timeSliceComboBox;
	CpuColockTimer CCT = new CpuColockTimer();

	static Vector<PCB> newPcb = new Vector<PCB>();// �½����̶���
	static Vector<PCB> runPcb = new Vector<PCB>();// ִ�н��̶���
	static Vector<PCB> readyPcb1 = new Vector<PCB>();// �������̶���1
	static Vector<PCB> readyPcb2 = new Vector<PCB>();// �������̶���2
	static Vector<PCB> waitPcb = new Vector<PCB>();// �ȴ����̶���

	private ButtonGroup buttonGroup = new ButtonGroup();// ��ѡ��ť��
	static JRadioButton fcfsRadioB;// ��ѡ��ť �����ȷ���
	static JRadioButton sjrRadioB;// ��ѡ��ť ����ҵ����
	static JRadioButton staticPriorityRadioB;// ��ѡ��ť ��̬���ȼ�
	static JRadioButton rrRadioB;// ��ѡ��ť ʱ��Ƭ��ת
	static JRadioButton mulrrRadioB;// ��ѡ��ť �༶������ת

	// Table����ʾ������Ϣ
	static JTable runTable;// ִ�н���Table����ʾִ�н�����Ϣ
	static JTable readyTable1;// ��������Table1����ʾ�������̶���1��Ϣ
	static JTable readyTable2;// ��������Table2����ʾ�������̶���2��Ϣ
	static JTable waitTable;// �ȴ�����Table����ʾ�ȴ�������Ϣ

	// ��Table���Ӧ��Tableģʽ�����ڸ���Table�е�����
	static DefaultTableModel runMode;
	static DefaultTableModel readyMode1;
	static DefaultTableModel readyMode2;
	static DefaultTableModel waitMode;

	static JLabel timeLabel = new JLabel();// ���ּ�ʱ��

	public static int isFirstCreatePcb = 1;// 1---��ʾ��һ�δ������̣����Զ���ʼ���е�һ�δ����Ľ��̣��Ժ󴴽��Ľ�����CPU����

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/os_process/res/mainFrameTitle.png")));
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setTitle("ģ����̵��ȹ���");
		getContentPane().setLayout(null);
		setBounds(100, 25, 752, 666);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//�˵���
		final JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		setJMenuBar(menuBar);
		
		//�˵� ����
		final JMenu operateMenu = new JMenu();
		operateMenu.setText("����");
		menuBar.add(operateMenu);
		//�˵��� ��������
		final JMenuItem createItem = new JMenuItem();
		createItem.setText("��������");
		operateMenu.add(createItem);
		createItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PCBFrame pcbFrame = new PCBFrame();
				pcbFrame.setVisible(true);
			}
		});
		//�˵��� ���н���
		final JMenuItem startItem = new JMenuItem();
		startItem.setText("���н���");
		operateMenu.add(startItem);
		startItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CpuColockTimer.pauseCountTime = false;
			}
		});
		//�˵��� ���ý���
		final JMenuItem resetItem = new JMenuItem();
		resetItem.setText("���ý���");
		operateMenu.add(resetItem);
		resetItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetOs();
			}
		});
		
//		//�˵� ����
//		final JMenu helpMenu = new JMenu();
//		helpMenu.setText("����");
//		menuBar.add(helpMenu);
//		//�˵��� ����
//		final JMenuItem aboutItem = new JMenuItem();
//		aboutItem.setText("����");
//		helpMenu.add(aboutItem);
//		aboutItem.addActionListener(new ActionListener() {
//			public void actionPerformed(final ActionEvent e) {
//				AboutFrame aboutFrame = new AboutFrame();
//				aboutFrame.setVisible(true);
//			}
//		});

		//��ѡ�� �����ȷ���
		fcfsRadioB = new JRadioButton();
		fcfsRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(fcfsRadioB);
		fcfsRadioB.setText("�����ȷ���");
		fcfsRadioB.setBounds(10, 10, 90, 26);
		getContentPane().add(fcfsRadioB);
		fcfsRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// ����ϵͳ
			}
		});
		fcfsRadioB.setSelected(true);

		//��ѡ�� ����ҵ����
		sjrRadioB = new JRadioButton();
		sjrRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(sjrRadioB);
		sjrRadioB.setText("����ҵ����");
		sjrRadioB.setBounds(102, 10, 90, 26);
		getContentPane().add(sjrRadioB);
		sjrRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// ����ϵͳ
			}
		});

		//��ѡ�� ��̬���ȼ�
		staticPriorityRadioB = new JRadioButton();
		staticPriorityRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(staticPriorityRadioB);
		staticPriorityRadioB.setText("��̬���ȼ�");
		staticPriorityRadioB.setBounds(194, 10, 90, 26);
		getContentPane().add(staticPriorityRadioB);
		staticPriorityRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// ����ϵͳ
			}
		});

		//��ѡ�� ʱ��Ƭ��ת
		rrRadioB = new JRadioButton();
		rrRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rrRadioB);
		rrRadioB.setText("ʱ��Ƭ��ת");
		rrRadioB.setBounds(286, 10, 90, 26);
		getContentPane().add(rrRadioB);
		rrRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// ����ϵͳ
			}
		});

		//��ѡ�� �༶��������
		mulrrRadioB = new JRadioButton();
		mulrrRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(mulrrRadioB);
		mulrrRadioB.setText("�༶��������");
		mulrrRadioB.setBounds(378, 10, 103, 26);
		getContentPane().add(mulrrRadioB);
		mulrrRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// ����ϵͳ
			}
		});
		
		//���ּ�ʱ��
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(new Font("", Font.BOLD, 42));
		timeLabel.setText("0   s");
		timeLabel.setBounds(624, 10, 103, 40);
		getContentPane().add(timeLabel);
		
		//��ǩ ʱ��Ƭ
		final JLabel timeSliceLabel = new JLabel();
		timeSliceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeSliceLabel.setText("ʱ��Ƭ:");
		timeSliceLabel.setBounds(558, 10, 54, 18);
		getContentPane().add(timeSliceLabel);
		//�����б� ѡ��ʱ��Ƭ����
		timeSliceComboBox = new JComboBox();
		timeSliceComboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		timeSliceComboBox.setSelectedIndex(4);
		timeSliceComboBox.setSelectedItem(4);
		timeSliceComboBox.setBounds(558, 30, 54, 27);
		getContentPane().add(timeSliceComboBox);
		// ����ʱ��Ƭ��Ͽ����ݸı��¼�
		timeSliceComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				int i = timeSliceComboBox.getSelectedIndex() + 1;
				CpuColockTimer.timeSlice = i;// ����ʱ��Ƭ��ֵ
			}
		});
		
		//��ť ��ͣ
		final JButton pauseButton = new JButton();
		pauseButton.setText("��ͣ");
		pauseButton.setBounds(80, 43, 78, 18);
		getContentPane().add(pauseButton);
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				CpuColockTimer.pauseCountTime = true;// ��ͣ��ʱ����ʱ

			}
		});

		//��ť ����
		final JButton continueButton = new JButton();
		continueButton.setText("����");
		continueButton.setBounds(164, 43, 78, 18);
		getContentPane().add(continueButton);
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				CpuColockTimer.pauseCountTime = false;// ��ʱ��������ʱ
			}
		});

		//��ť ����
		final JButton resetButton = new JButton();
		resetButton.setText("����");
		resetButton.setBounds(335, 43, 78, 18);
		getContentPane().add(resetButton);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				resetOs();// ����ϵͳ
			}
		});

		//��ť ����
		final JButton createButton = new JButton();
		createButton.setText("����");
		createButton.setBounds(251, 43, 78, 18);
		getContentPane().add(createButton);
		// �������������ť�¼�
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				PCBFrame pcbFrame = new PCBFrame();// �½�һ�����̴���
				pcbFrame.setVisible(true);
			}
		});
		 
		//��Table���Ӧ��Tableģʽ�����ڸ���Table�е�����
		
		final Object[] columnNames = { "���̱�ʶ��", "���ȼ�", "��ǰ״̬", "�ύʱ��", "����ʱ��", "��ִ��ʱ��", "���ʱ��" };
		runMode = new DefaultTableModel(null, columnNames) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int ColUmnIndex) {
				return false;
			}
		};
		readyMode1 = new DefaultTableModel(null, columnNames) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int ColUmnIndex) {
				return false;
			}
		};	
		readyMode2 = new DefaultTableModel(null, columnNames) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int ColUmnIndex) {
				return false;
			}
		};	
		waitMode = new DefaultTableModel(null, columnNames) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int ColUmnIndex) {
				return false;
			}
		};
		
		// Table����ʾ������Ϣ
		
		//��ǩ ִ�ж���
		final JLabel runLabel = new JLabel();
		runLabel.setText("ִ�ж��У�");
		runLabel.setBounds(20, 43, 66, 18);
		getContentPane().add(runLabel);
		//������
		final JScrollPane runPane = new JScrollPane();
		runPane.setBounds(20, 67, 705, 121);
		getContentPane().add(runPane);
		//ִ�н���Table����ʾִ�н�����Ϣ
		runTable = new JTable(runMode);
		runPane.setViewportView(runTable);
		//�����˵�
		final JPopupMenu runPopupMenu = new JPopupMenu();
		addPopup(runTable, runPopupMenu);
		//�˵�Ԫ�� ����
		final JMenuItem runBreakItem = new JMenuItem();
		runBreakItem.setText("����");
		runPopupMenu.add(runBreakItem);
		runBreakItem.addActionListener(new ActionListener() {
			// ��������������̲˵��¼�
			public void actionPerformed(final ActionEvent e) {
				int i = runTable.getSelectedRow();
				if (i >= 0) {
					PCB p = runPcb.get(i);
					if (p.getStatus() == "ִ��̬") {
						p.setStatus("�ȴ�̬");
						waitPcb.add(p);
						addWaitTable(p);// ����ȴ����̶�����

						runPcb.remove(i);
						runMode.removeRow(i);// ��ִ�н��̶������Ƴ��������Ľ���

						startRunPcb(); // ��ʼ�����¸���������
					}
				}
			}
		});
		//�˵�Ԫ�� ɾ��
		final JMenuItem runDelItem = new JMenuItem();
		runDelItem.setText("ɾ��");
		runPopupMenu.add(runDelItem);
		// �������ɾ���˵��¼�
		runDelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				int i = runTable.getSelectedRow();
				if (i >= 0) {
					runPcb.remove(i);
					runMode.removeRow(i);
				}
			}
		});

		//��ǩ ��������1
		final JLabel readyLabel1 = new JLabel();
		readyLabel1.setText("��������1:");
		readyLabel1.setBounds(20, 194, 66, 18);
		getContentPane().add(readyLabel1);
		//������
		final JScrollPane readyPane1 = new JScrollPane();
		readyPane1.setBounds(22, 218, 703, 108);
		getContentPane().add(readyPane1);
		//��������Table1����ʾ�������̶���1��Ϣ
		readyTable1 = new JTable(readyMode1);
		readyPane1.setViewportView(readyTable1);
		//�����˵�
		final JPopupMenu readyPopupMenu1 = new JPopupMenu();
		addPopup(readyTable1, readyPopupMenu1);
		//�˵�Ԫ�� ɾ��
		final JMenuItem ready1DelItem = new JMenuItem();
		ready1DelItem.setText("ɾ��");
		readyPopupMenu1.add(ready1DelItem);
		// �������ɾ���˵��¼�
		ready1DelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				int i = readyTable1.getSelectedRow();
				if (i >= 0) {
					readyPcb1.remove(i);
					readyMode1.removeRow(i);
				}
			}
		});
		
		//��ǩ ��������2
		final JLabel readyLabel2 = new JLabel();
		readyLabel2.setText("��������2��");
		readyLabel2.setBounds(20, 330, 78, 18);
		getContentPane().add(readyLabel2);
		//������
		final JScrollPane readyPane2 = new JScrollPane();
		readyPane2.setBounds(20, 354, 705, 108);
		getContentPane().add(readyPane2);
		//��������Table2����ʾ�������̶���2��Ϣ
		readyTable2 = new JTable(readyMode2);
		readyPane2.setViewportView(readyTable2);
		//�����˵�
		final JPopupMenu readyPopupMenu2 = new JPopupMenu();
		addPopup(readyTable2, readyPopupMenu2);
		//�˵�Ԫ�� ɾ��
		final JMenuItem ready2DelItem = new JMenuItem();
		ready2DelItem.setText("ɾ��");
		readyPopupMenu2.add(ready2DelItem);
		// �������ɾ���˵��¼�
		ready2DelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				int i = readyTable2.getSelectedRow();
				if (i >= 0) {
					readyPcb2.remove(i);
					readyMode2.removeRow(i);
				}
			}
		});

		//��ǩ �ȴ�����
		final JLabel waitLabel = new JLabel();
		waitLabel.setText("�ȴ����У�");
		waitLabel.setBounds(20, 465, 66, 18);
		getContentPane().add(waitLabel);
		//������
		final JScrollPane waitPane = new JScrollPane();
		waitPane.setBounds(20, 487, 705, 108);
		getContentPane().add(waitPane);
		//�ȴ�����Table����ʾ�ȴ�������Ϣ
		waitTable = new JTable(waitMode);
		waitPane.setViewportView(waitTable);
		//�����˵�
		final JPopupMenu waitPopupMenu = new JPopupMenu();
		addPopup(waitTable, waitPopupMenu);
		//�˵�Ԫ�� ɾ��
		final JMenuItem waitDelItem = new JMenuItem();
		waitDelItem.setText("ɾ��");
		waitPopupMenu.add(waitDelItem);
		// �������ɾ���˵��¼�
		waitDelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				int i = waitTable.getSelectedRow();
				if (i >= 0) {
					waitPcb.remove(i);
					waitMode.removeRow(i);
				}
			}
		});




		CCT.start();// ��ʱ����ʼ��ʱ

	}

	public static void startRunPcb() {
		// ��ʼ�����¸����̣���˳����жϸ������̶����Ƿ�Ϊ��

		int isHavePcb = 0;// �ж��Ƿ��н���Ҫִ��
		PCB p = new PCB();
		if (readyPcb1.size() > 0)// �������̶���1��Ϊ��
		{
			isHavePcb = 1;
			p = readyPcb1.get(0);

			readyMode1.removeRow(0);
			readyPcb1.remove(0);
		} else if (readyPcb2.size() > 0)// �������̶���2��Ϊ��
		{
			isHavePcb = 1;
			p = readyPcb2.get(0);

			readyMode2.removeRow(0);
			readyPcb2.remove(0);
		} else if (waitPcb.size() > 0)// �ȴ����̶��в�Ϊ��
		{
			isHavePcb = 1;
			p = waitPcb.get(0);

			waitMode.removeRow(0);
			waitPcb.remove(0);
		}

		if (isHavePcb == 1)// ���н���Ҫִ��
		{
			p.setStatus("ִ��̬");
			runPcb.add(p);
			addRunTable(p);
		}
		// else
		// CpuColockTimer.timer.cancel();

	}

	protected void resetOs() {// ����ϵͳ
		runMode.setRowCount(0);
		readyMode1.setRowCount(0);
		readyMode2.setRowCount(0);
		waitMode.setRowCount(0);// ���Table

		newPcb.clear();
		runPcb.clear();
		readyPcb1.clear();
		readyPcb2.clear();
		waitPcb.clear();// ������Խ��̶���

		CpuColockTimer.time = 0;
		timeLabel.setText("0   s");
		CpuColockTimer.pauseCountTime = false;// ��ʱ�����㣬���¼�ʱ

		isFirstCreatePcb = 1;// ��һ�δ������̱����Ϊ1

		PCBFrame.pidCount = 1;// ���̱�ʶ����Ϊ1
		PCBFrame.pidTextField.setText("1");
	}

	public static void addPriorityPcb(Vector<PCB> readyPcb1, PCB pcb) {
		// ���ݽ������ȼ�����ز��뵽��������1�У�ĩβ�Ľ������ȼ���С
		if (readyPcb1.size() == 0)
			readyPcb1.add(pcb);
		else {
			readyPcb1.setSize(readyPcb1.size() + 1);
			int i;
			for (i = readyPcb1.size() - 2; i >= 0; i--) {
				PCB rPcb = readyPcb1.get(i);
				if (rPcb.getPriority() < pcb.getPriority())
					readyPcb1.set(i + 1, rPcb);
				else
					break;
			}
			readyPcb1.set(i + 1, pcb);
		}
	}

	public static void addLastTimePcb(Vector<PCB> readyPcb1, PCB pcb) {
		// ���ݽ��̳���ʱ������ز��뵽��������1�У�ĩβ�Ľ��̳���ʱ���
		if (readyPcb1.size() == 0)
			readyPcb1.add(pcb);
		else {
			readyPcb1.setSize(readyPcb1.size() + 1);
			int i;
			for (i = readyPcb1.size() - 2; i >= 0; i--) {
				PCB rPcb = readyPcb1.get(i);
				if (rPcb.getLastTime() > pcb.getLastTime())
					readyPcb1.set(i + 1, rPcb);
				else
					break;
			}
			readyPcb1.set(i + 1, pcb);
		}
	}

	public static void addReadyTable(PCB p, int type) {
		// �����̼���������̶�����
		Vector<String> v = createVector(p);
		if (type == 1)
			readyMode1.addRow(v);
		else if (type == 2)
			readyMode2.addRow(v);
	}

	public static void addRunTable(PCB p) {
		// �����̼���ִ�н��̶�����
		Vector<String> v = createVector(p);
		runMode.addRow(v);
	}

	public static void addWaitTable(PCB p) {
		// �����̼���ȴ����̶�����
		Vector<String> v = createVector(p);
		waitMode.addRow(v);
	}

	public static void wakeWaitPcb() {
		// ���ѵȴ����̶��е��������̶���
		PCB p = waitPcb.get(0);
		waitMode.removeRow(0);
		waitPcb.remove(0);
		p.setStatus("����̬");
		readyPcb1.add(p);
		addReadyTable(p, 1);
	}

	/**
	 * WindowBuilder generated method.<br>
	 * Please don't remove this method or its invocations.<br>
	 * It used by WindowBuilder to associate the {@link javax.swing.JPopupMenu} with
	 * parent.
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					showMenu(e);
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public static void updatePriorityTable() {
		// ���¾�������Table�������Table���ٽ�����ľ������̶��м���Table��
		readyMode1.setRowCount(0);
		for (int i = 0; i < readyPcb1.size(); i++) {
			Vector<String> v = createVector(readyPcb1.get(i));
			readyMode1.addRow(v);
		}
	}
	public static void updateLastTimeTable() {
		// ���¾�������Table�������Table���ٽ�����ľ������̶��м���Table��
		readyMode1.setRowCount(0);
		for (int i = 0; i < readyPcb1.size(); i++) {
			Vector<String> v = createVector(readyPcb1.get(i));
			readyMode1.addRow(v);
		}
	}

	public static Vector<String> createVector(PCB p) {
		// ������p �������Լ���Vector����v�У�����v
		Vector<String> v = new Vector<String>();
		v.addElement(new Integer(p.getPid()).toString());
		v.addElement(new Integer(p.getPriority()).toString());
		v.addElement(p.getStatus());
		v.addElement(new Integer(p.getInputTime()).toString());
		v.addElement(new Integer(p.getLastTime()).toString());
		v.addElement(new Integer(p.getExecuteTime()).toString());
		v.addElement(new Integer(p.getEndTime()).toString());
		return v;
	}
}
