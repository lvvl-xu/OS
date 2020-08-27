import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

public class StartFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton SchedulingBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartFrame.class.getResource("/icon.png")));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 411, 303);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//菜单 帮助
		final JMenu helpMenu = new JMenu();
		helpMenu.setText("帮助");
		menuBar.add(helpMenu);
		//菜单项 关于
		final JMenuItem aboutItem = new JMenuItem();
		aboutItem.setText("关于");
		helpMenu.add(aboutItem);
		getContentPane().setLayout(null);
		
		SchedulingBtn = new JButton("\u8FDB\u7A0B\u8C03\u5EA6");
		SchedulingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				os_process.MainFrame mainFrame = new os_process.MainFrame();
				mainFrame.setVisible(true);
			}
		});
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(new Color(240, 240, 240));
		textField.setFont(new Font("华文行楷", Font.PLAIN, 48));
		textField.setText("\u64CD\u4F5C\u7CFB\u7EDF\u8BFE\u7A0B\u8BBE\u8BA1");
		textField.setBounds(0, 0, 402, 59);
		getContentPane().add(textField);
		SchedulingBtn.setFont(new Font("华文新魏", Font.PLAIN, 23));
		SchedulingBtn.setBounds(94, 69, 195, 42);
		getContentPane().add(SchedulingBtn);
		
		JButton RandWBtn = new JButton("\u8BFB\u8005\u5199\u8005\u95EE\u9898");
		RandWBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				os_rw.MainJFrame mainjFrame = new os_rw.MainJFrame();
				mainjFrame.setVisible(true);
			}
		});
		RandWBtn.setFont(new Font("华文新魏", Font.PLAIN, 20));
		RandWBtn.setBounds(94, 121, 195, 42);
		getContentPane().add(RandWBtn);
		
		JButton philosopherBtn = new JButton("\u54F2\u5B66\u5BB6\u5C31\u9910\u95EE\u9898");
		philosopherBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				os_philosopher.Menu menu = new os_philosopher.Menu();
				menu.setVisible(true);
			}
		});
		philosopherBtn.setFont(new Font("华文新魏", Font.PLAIN, 20));
		philosopherBtn.setBounds(94, 173, 195, 42);
		getContentPane().add(philosopherBtn);
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AboutFrame aboutFrame = new AboutFrame();
				aboutFrame.setVisible(true);
			}
		});
	}
}
