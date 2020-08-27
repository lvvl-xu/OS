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

	static Vector<PCB> newPcb = new Vector<PCB>();// 新建进程队列
	static Vector<PCB> runPcb = new Vector<PCB>();// 执行进程队列
	static Vector<PCB> readyPcb1 = new Vector<PCB>();// 就绪进程队列1
	static Vector<PCB> readyPcb2 = new Vector<PCB>();// 就绪进程队列2
	static Vector<PCB> waitPcb = new Vector<PCB>();// 等待进程队列

	private ButtonGroup buttonGroup = new ButtonGroup();// 单选按钮组
	static JRadioButton fcfsRadioB;// 单选按钮 先来先服务
	static JRadioButton sjrRadioB;// 单选按钮 短作业优先
	static JRadioButton staticPriorityRadioB;// 单选按钮 静态优先级
	static JRadioButton rrRadioB;// 单选按钮 时间片轮转
	static JRadioButton mulrrRadioB;// 单选按钮 多级反馈轮转

	// Table，显示队列信息
	static JTable runTable;// 执行进程Table，显示执行进程信息
	static JTable readyTable1;// 就绪进程Table1，显示就绪进程队列1信息
	static JTable readyTable2;// 就绪进程Table2，显示就绪进程队列2信息
	static JTable waitTable;// 等待进程Table，显示等待进程信息

	// 和Table相对应的Table模式，用于更新Table中的内容
	static DefaultTableModel runMode;
	static DefaultTableModel readyMode1;
	static DefaultTableModel readyMode2;
	static DefaultTableModel waitMode;

	static JLabel timeLabel = new JLabel();// 数字计时器

	public static int isFirstCreatePcb = 1;// 1---表示第一次创建进程，则自动开始运行第一次创建的进程，以后创建的进程由CPU调度

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
		setTitle("模拟进程调度管理");
		getContentPane().setLayout(null);
		setBounds(100, 25, 752, 666);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//菜单条
		final JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		setJMenuBar(menuBar);
		
		//菜单 操作
		final JMenu operateMenu = new JMenu();
		operateMenu.setText("操作");
		menuBar.add(operateMenu);
		//菜单项 创建进程
		final JMenuItem createItem = new JMenuItem();
		createItem.setText("创建进程");
		operateMenu.add(createItem);
		createItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PCBFrame pcbFrame = new PCBFrame();
				pcbFrame.setVisible(true);
			}
		});
		//菜单项 运行进程
		final JMenuItem startItem = new JMenuItem();
		startItem.setText("运行进程");
		operateMenu.add(startItem);
		startItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CpuColockTimer.pauseCountTime = false;
			}
		});
		//菜单项 重置进程
		final JMenuItem resetItem = new JMenuItem();
		resetItem.setText("重置进程");
		operateMenu.add(resetItem);
		resetItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetOs();
			}
		});
		
//		//菜单 帮助
//		final JMenu helpMenu = new JMenu();
//		helpMenu.setText("帮助");
//		menuBar.add(helpMenu);
//		//菜单项 关于
//		final JMenuItem aboutItem = new JMenuItem();
//		aboutItem.setText("关于");
//		helpMenu.add(aboutItem);
//		aboutItem.addActionListener(new ActionListener() {
//			public void actionPerformed(final ActionEvent e) {
//				AboutFrame aboutFrame = new AboutFrame();
//				aboutFrame.setVisible(true);
//			}
//		});

		//单选框 先来先服务
		fcfsRadioB = new JRadioButton();
		fcfsRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(fcfsRadioB);
		fcfsRadioB.setText("先来先服务");
		fcfsRadioB.setBounds(10, 10, 90, 26);
		getContentPane().add(fcfsRadioB);
		fcfsRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// 重置系统
			}
		});
		fcfsRadioB.setSelected(true);

		//单选框 短作业优先
		sjrRadioB = new JRadioButton();
		sjrRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(sjrRadioB);
		sjrRadioB.setText("短作业优先");
		sjrRadioB.setBounds(102, 10, 90, 26);
		getContentPane().add(sjrRadioB);
		sjrRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// 重置系统
			}
		});

		//单选框 静态优先级
		staticPriorityRadioB = new JRadioButton();
		staticPriorityRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(staticPriorityRadioB);
		staticPriorityRadioB.setText("静态优先级");
		staticPriorityRadioB.setBounds(194, 10, 90, 26);
		getContentPane().add(staticPriorityRadioB);
		staticPriorityRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// 重置系统
			}
		});

		//单选框 时间片轮转
		rrRadioB = new JRadioButton();
		rrRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(rrRadioB);
		rrRadioB.setText("时间片轮转");
		rrRadioB.setBounds(286, 10, 90, 26);
		getContentPane().add(rrRadioB);
		rrRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// 重置系统
			}
		});

		//单选框 多级反馈队列
		mulrrRadioB = new JRadioButton();
		mulrrRadioB.setBackground(Color.LIGHT_GRAY);
		buttonGroup.add(mulrrRadioB);
		mulrrRadioB.setText("多级反馈队列");
		mulrrRadioB.setBounds(378, 10, 103, 26);
		getContentPane().add(mulrrRadioB);
		mulrrRadioB.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				resetOs();// 重置系统
			}
		});
		
		//数字计时器
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(new Font("", Font.BOLD, 42));
		timeLabel.setText("0   s");
		timeLabel.setBounds(624, 10, 103, 40);
		getContentPane().add(timeLabel);
		
		//标签 时间片
		final JLabel timeSliceLabel = new JLabel();
		timeSliceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeSliceLabel.setText("时间片:");
		timeSliceLabel.setBounds(558, 10, 54, 18);
		getContentPane().add(timeSliceLabel);
		//下拉列表 选择时间片长度
		timeSliceComboBox = new JComboBox();
		timeSliceComboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
		timeSliceComboBox.setSelectedIndex(4);
		timeSliceComboBox.setSelectedItem(4);
		timeSliceComboBox.setBounds(558, 30, 54, 27);
		getContentPane().add(timeSliceComboBox);
		// 监听时间片组合框内容改变事件
		timeSliceComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				int i = timeSliceComboBox.getSelectedIndex() + 1;
				CpuColockTimer.timeSlice = i;// 更新时间片的值
			}
		});
		
		//按钮 暂停
		final JButton pauseButton = new JButton();
		pauseButton.setText("暂停");
		pauseButton.setBounds(80, 43, 78, 18);
		getContentPane().add(pauseButton);
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				CpuColockTimer.pauseCountTime = true;// 暂停计时器计时

			}
		});

		//按钮 继续
		final JButton continueButton = new JButton();
		continueButton.setText("继续");
		continueButton.setBounds(164, 43, 78, 18);
		getContentPane().add(continueButton);
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				CpuColockTimer.pauseCountTime = false;// 计时器继续计时
			}
		});

		//按钮 重置
		final JButton resetButton = new JButton();
		resetButton.setText("重置");
		resetButton.setBounds(335, 43, 78, 18);
		getContentPane().add(resetButton);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				resetOs();// 重置系统
			}
		});

		//按钮 创建
		final JButton createButton = new JButton();
		createButton.setText("创建");
		createButton.setBounds(251, 43, 78, 18);
		getContentPane().add(createButton);
		// 监听点击创建按钮事件
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				PCBFrame pcbFrame = new PCBFrame();// 新建一个进程窗体
				pcbFrame.setVisible(true);
			}
		});
		 
		//和Table相对应的Table模式，用于更新Table中的内容
		
		final Object[] columnNames = { "进程标识号", "优先级", "当前状态", "提交时间", "持续时间", "已执行时间", "完成时间" };
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
		
		// Table，显示队列信息
		
		//标签 执行队列
		final JLabel runLabel = new JLabel();
		runLabel.setText("执行队列：");
		runLabel.setBounds(20, 43, 66, 18);
		getContentPane().add(runLabel);
		//滚动条
		final JScrollPane runPane = new JScrollPane();
		runPane.setBounds(20, 67, 705, 121);
		getContentPane().add(runPane);
		//执行进程Table，显示执行进程信息
		runTable = new JTable(runMode);
		runPane.setViewportView(runTable);
		//弹出菜单
		final JPopupMenu runPopupMenu = new JPopupMenu();
		addPopup(runTable, runPopupMenu);
		//菜单元素 阻塞
		final JMenuItem runBreakItem = new JMenuItem();
		runBreakItem.setText("阻塞");
		runPopupMenu.add(runBreakItem);
		runBreakItem.addActionListener(new ActionListener() {
			// 监听点击阻塞进程菜单事件
			public void actionPerformed(final ActionEvent e) {
				int i = runTable.getSelectedRow();
				if (i >= 0) {
					PCB p = runPcb.get(i);
					if (p.getStatus() == "执行态") {
						p.setStatus("等待态");
						waitPcb.add(p);
						addWaitTable(p);// 加入等待进程队列中

						runPcb.remove(i);
						runMode.removeRow(i);// 从执行进程队列中移除被阻塞的进程

						startRunPcb(); // 开始运行下个就绪进程
					}
				}
			}
		});
		//菜单元素 删除
		final JMenuItem runDelItem = new JMenuItem();
		runDelItem.setText("删除");
		runPopupMenu.add(runDelItem);
		// 监听点击删除菜单事件
		runDelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				int i = runTable.getSelectedRow();
				if (i >= 0) {
					runPcb.remove(i);
					runMode.removeRow(i);
				}
			}
		});

		//标签 就绪队列1
		final JLabel readyLabel1 = new JLabel();
		readyLabel1.setText("就绪队列1:");
		readyLabel1.setBounds(20, 194, 66, 18);
		getContentPane().add(readyLabel1);
		//滚动条
		final JScrollPane readyPane1 = new JScrollPane();
		readyPane1.setBounds(22, 218, 703, 108);
		getContentPane().add(readyPane1);
		//就绪进程Table1，显示就绪进程队列1信息
		readyTable1 = new JTable(readyMode1);
		readyPane1.setViewportView(readyTable1);
		//弹出菜单
		final JPopupMenu readyPopupMenu1 = new JPopupMenu();
		addPopup(readyTable1, readyPopupMenu1);
		//菜单元素 删除
		final JMenuItem ready1DelItem = new JMenuItem();
		ready1DelItem.setText("删除");
		readyPopupMenu1.add(ready1DelItem);
		// 监听点击删除菜单事件
		ready1DelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				int i = readyTable1.getSelectedRow();
				if (i >= 0) {
					readyPcb1.remove(i);
					readyMode1.removeRow(i);
				}
			}
		});
		
		//标签 就绪队列2
		final JLabel readyLabel2 = new JLabel();
		readyLabel2.setText("就绪队列2：");
		readyLabel2.setBounds(20, 330, 78, 18);
		getContentPane().add(readyLabel2);
		//滚动条
		final JScrollPane readyPane2 = new JScrollPane();
		readyPane2.setBounds(20, 354, 705, 108);
		getContentPane().add(readyPane2);
		//就绪进程Table2，显示就绪进程队列2信息
		readyTable2 = new JTable(readyMode2);
		readyPane2.setViewportView(readyTable2);
		//弹出菜单
		final JPopupMenu readyPopupMenu2 = new JPopupMenu();
		addPopup(readyTable2, readyPopupMenu2);
		//菜单元素 删除
		final JMenuItem ready2DelItem = new JMenuItem();
		ready2DelItem.setText("删除");
		readyPopupMenu2.add(ready2DelItem);
		// 监听点击删除菜单事件
		ready2DelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				int i = readyTable2.getSelectedRow();
				if (i >= 0) {
					readyPcb2.remove(i);
					readyMode2.removeRow(i);
				}
			}
		});

		//标签 等待队列
		final JLabel waitLabel = new JLabel();
		waitLabel.setText("等待队列：");
		waitLabel.setBounds(20, 465, 66, 18);
		getContentPane().add(waitLabel);
		//滚动条
		final JScrollPane waitPane = new JScrollPane();
		waitPane.setBounds(20, 487, 705, 108);
		getContentPane().add(waitPane);
		//等待进程Table，显示等待进程信息
		waitTable = new JTable(waitMode);
		waitPane.setViewportView(waitTable);
		//弹出菜单
		final JPopupMenu waitPopupMenu = new JPopupMenu();
		addPopup(waitTable, waitPopupMenu);
		//菜单元素 删除
		final JMenuItem waitDelItem = new JMenuItem();
		waitDelItem.setText("删除");
		waitPopupMenu.add(waitDelItem);
		// 监听点击删除菜单事件
		waitDelItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				int i = waitTable.getSelectedRow();
				if (i >= 0) {
					waitPcb.remove(i);
					waitMode.removeRow(i);
				}
			}
		});




		CCT.start();// 计时器开始计时

	}

	public static void startRunPcb() {
		// 开始运行下个进程，有顺序的判断各个进程队列是否为空

		int isHavePcb = 0;// 判断是否还有进程要执行
		PCB p = new PCB();
		if (readyPcb1.size() > 0)// 就绪进程队列1不为空
		{
			isHavePcb = 1;
			p = readyPcb1.get(0);

			readyMode1.removeRow(0);
			readyPcb1.remove(0);
		} else if (readyPcb2.size() > 0)// 就绪进程队列2不为空
		{
			isHavePcb = 1;
			p = readyPcb2.get(0);

			readyMode2.removeRow(0);
			readyPcb2.remove(0);
		} else if (waitPcb.size() > 0)// 等待进程队列不为空
		{
			isHavePcb = 1;
			p = waitPcb.get(0);

			waitMode.removeRow(0);
			waitPcb.remove(0);
		}

		if (isHavePcb == 1)// 仍有进程要执行
		{
			p.setStatus("执行态");
			runPcb.add(p);
			addRunTable(p);
		}
		// else
		// CpuColockTimer.timer.cancel();

	}

	protected void resetOs() {// 重置系统
		runMode.setRowCount(0);
		readyMode1.setRowCount(0);
		readyMode2.setRowCount(0);
		waitMode.setRowCount(0);// 清空Table

		newPcb.clear();
		runPcb.clear();
		readyPcb1.clear();
		readyPcb2.clear();
		waitPcb.clear();// 清空所以进程队列

		CpuColockTimer.time = 0;
		timeLabel.setText("0   s");
		CpuColockTimer.pauseCountTime = false;// 计时器归零，重新计时

		isFirstCreatePcb = 1;// 第一次创建进程标记置为1

		PCBFrame.pidCount = 1;// 进程标识号置为1
		PCBFrame.pidTextField.setText("1");
	}

	public static void addPriorityPcb(Vector<PCB> readyPcb1, PCB pcb) {
		// 根据进程优先级有序地插入到就绪队列1中，末尾的进程优先级最小
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
		// 根据进程持续时间有序地插入到就绪队列1中，末尾的进程持续时间最长
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
		// 将进程加入就绪进程队列中
		Vector<String> v = createVector(p);
		if (type == 1)
			readyMode1.addRow(v);
		else if (type == 2)
			readyMode2.addRow(v);
	}

	public static void addRunTable(PCB p) {
		// 将进程加入执行进程队列中
		Vector<String> v = createVector(p);
		runMode.addRow(v);
	}

	public static void addWaitTable(PCB p) {
		// 将进程加入等待进程队列中
		Vector<String> v = createVector(p);
		waitMode.addRow(v);
	}

	public static void wakeWaitPcb() {
		// 唤醒等待进程队列到就绪进程队列
		PCB p = waitPcb.get(0);
		waitMode.removeRow(0);
		waitPcb.remove(0);
		p.setStatus("就绪态");
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
		// 更新就绪进程Table，先清空Table，再将有序的就绪进程队列加入Table中
		readyMode1.setRowCount(0);
		for (int i = 0; i < readyPcb1.size(); i++) {
			Vector<String> v = createVector(readyPcb1.get(i));
			readyMode1.addRow(v);
		}
	}
	public static void updateLastTimeTable() {
		// 更新就绪进程Table，先清空Table，再将有序的就绪进程队列加入Table中
		readyMode1.setRowCount(0);
		for (int i = 0; i < readyPcb1.size(); i++) {
			Vector<String> v = createVector(readyPcb1.get(i));
			readyMode1.addRow(v);
		}
	}

	public static Vector<String> createVector(PCB p) {
		// 将进程p 所有属性加入Vector向量v中，返回v
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
