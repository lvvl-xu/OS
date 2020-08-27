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
		setTitle("���ڱ�ϵͳ");
		addWindowListener(new WindowAdapter()// �����࣬�رմ���
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		final JPanel taskPanel = new JPanel();
		taskPanel.setLayout(null);
		taskPanel.setBounds(242, 10, 240, 147);
		taskPanel.setBorder(BorderFactory.createTitledBorder("������"));
		getContentPane().add(taskPanel);

		taskTextArea = new JTextArea();
		taskTextArea.setFont(new Font("����", Font.PLAIN, 10));
		taskTextArea.setBackground(SystemColor.text);
		taskTextArea.setText(
				"               ����ϵͳ�γ����\n\n--------���̵���\n            �����ȷ��񡢶���ҵ���ȡ�\n            ���ȼ����ȡ�ʱ��Ƭ��ת��\n            �༶��������\n--------����ͬ��\n           ����д�����⡢��ѧ������");
		taskTextArea.setBounds(10, 23, 217, 114);
		taskTextArea.setEditable(false);
		taskPanel.add(taskTextArea);

		final JPanel teamPanel = new JPanel();
		teamPanel.setLayout(null);
		teamPanel.setBounds(241, 158, 241, 91);
		teamPanel.setBorder(BorderFactory.createTitledBorder("���߼��"));
		getContentPane().add(teamPanel);

		teamTextArea = new JTextArea();
		teamTextArea.setFont(new Font("����", Font.PLAIN, 10));
		teamTextArea.setBackground(SystemColor.text);
		teamTextArea.setText("ѧУ�����մ�ѧ\nרҵ���������\n���ߣ����� & ���� & Ԭ���� & ������ \n���ʱ�䣺2020-3-4");
		teamTextArea.setBounds(10, 23, 221, 58);
		teamTextArea.setEditable(false);
		teamPanel.add(teamTextArea);

		final JLabel osLabel = new JLabel();
		osLabel.setIcon(SwingResourceManager.getIcon(AboutFrame.class, "/AHU.jpg"));
		osLabel.setBounds(10, 10, 227, 249);
		getContentPane().add(osLabel);
	}
}
