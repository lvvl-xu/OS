import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import os.com.swtdesigner.SwingResourceManager;

import java.awt.Font;

public class AboutFrame extends JFrame {

	private JTextArea teamTextArea;
	private JTextArea taskTextArea;

	/**
	 * Create the frame
	 */
	public AboutFrame() {
		super();
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutFrame.class.getResource("/about.png")));
		setBackground(UIManager.getColor("Button.shadow"));
		getContentPane().setLayout(null);
		setBounds(250, 100, 500, 313);
		setTitle("关于本系统");
		addWindowListener(new WindowAdapter()// 匿名类，关闭窗口
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		final JPanel taskPanel = new JPanel();
		taskPanel.setLayout(null);
		taskPanel.setBounds(242, 10, 240, 147);
		taskPanel.setBorder(BorderFactory.createTitledBorder("任务简介"));
		getContentPane().add(taskPanel);

		taskTextArea = new JTextArea();
		taskTextArea.setFont(new Font("黑体", Font.PLAIN, 10));
		taskTextArea.setBackground(SystemColor.text);
		taskTextArea.setText(
				"               操作系统课程设计\n\n--------进程调度\n            先来先服务、短作业优先、\n            优先级调度、时间片轮转、\n            多级反馈队列\n--------进程同步\n           读者写者问题、哲学家问题");
		taskTextArea.setBounds(10, 23, 217, 114);
		taskTextArea.setEditable(false);
		taskPanel.add(taskTextArea);

		final JPanel teamPanel = new JPanel();
		teamPanel.setLayout(null);
		teamPanel.setBounds(241, 158, 241, 91);
		teamPanel.setBorder(BorderFactory.createTitledBorder("作者简介"));
		getContentPane().add(teamPanel);

		teamTextArea = new JTextArea();
		teamTextArea.setFont(new Font("黑体", Font.PLAIN, 10));
		teamTextArea.setBackground(SystemColor.text);
		teamTextArea.setText("学校：安徽大学\n专业：软件工程\n作者：汪鹏 & 许东明 & 袁兴武 & 方俊翔 \n完成时间：2020-3-4");
		teamTextArea.setBounds(10, 23, 221, 58);
		teamTextArea.setEditable(false);
		teamPanel.add(teamTextArea);

		final JLabel osLabel = new JLabel();
		osLabel.setIcon(SwingResourceManager.getIcon(AboutFrame.class, "/AHU.jpg"));
		osLabel.setBounds(10, 10, 227, 249);
		getContentPane().add(osLabel);
	}
}
