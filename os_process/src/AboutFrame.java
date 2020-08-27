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
import com.swtdesigner.SwingResourceManager;

public class AboutFrame extends JFrame {

	private JTextArea teamTextArea;
	private JTextArea taskTextArea;

	/**
	 * Create the frame
	 */
	public AboutFrame() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutFrame.class.getResource("/res/about.png")));
		setBackground(UIManager.getColor("Button.shadow"));
		getContentPane().setLayout(null);
		setBounds(250, 100, 500, 316);
		setTitle("���ڱ�ϵͳ");
		addWindowListener(new WindowAdapter()// �����࣬�رմ���
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		final JPanel taskPanel = new JPanel();
		taskPanel.setLayout(null);
		taskPanel.setBounds(242, 10, 240, 123);
		taskPanel.setBorder(BorderFactory.createTitledBorder("������"));
		getContentPane().add(taskPanel);

		taskTextArea = new JTextArea();
		taskTextArea.setBackground(SystemColor.text);
		taskTextArea.setText(
				"                 ����ϵͳ�γ����\n                          --------ģ����̵����㷨\n   �����ȷ�����ȡ�����ҵ���ȵ��ȡ�\n   ���ȼ������㷨��ʱ��Ƭ��ת�㷨��\n   �༶���������㷨\n");
		taskTextArea.setBounds(10, 23, 217, 90);
		taskTextArea.setEditable(false);
		taskPanel.add(taskTextArea);

		final JPanel teamPanel = new JPanel();
		teamPanel.setLayout(null);
		teamPanel.setBounds(241, 155, 241, 123);
		teamPanel.setBorder(BorderFactory.createTitledBorder("���߼��"));
		getContentPane().add(teamPanel);

		teamTextArea = new JTextArea();
		teamTextArea.setBackground(SystemColor.text);
		teamTextArea.setText("ѧУ�����մ�ѧ\nרҵ���������\n���ߣ����� & ���� & Ԭ���� & ������ \n���ʱ�䣺2020-2-26");
		teamTextArea.setBounds(10, 19, 221, 92);
		teamTextArea.setEditable(false);
		teamPanel.add(teamTextArea);

		final JLabel osLabel = new JLabel();
		osLabel.setIcon(SwingResourceManager.getIcon(AboutFrame.class, "res/AHU.jpg"));
		osLabel.setBounds(10, 10, 227, 268);
		getContentPane().add(osLabel);
	}
}
