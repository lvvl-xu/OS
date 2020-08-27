/***
 * 菜单栏
 */
package os_philosopher;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
	JRadioButtonMenuItem choice[];
	interface1 m0 = new interface1();// 最多允许4哲学家同时进餐
	interface2 m1 = new interface2();// 奇左偶右
	interface3 m2 = new interface3();// AND信号量机制
	interface4 m4 = new interface4();// 记录型型号量
	Eat eat = new Eat("手动--允许死锁");
	Eat2 eat2 = new Eat2("手动--两只筷子同时取");

	public Menu() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/os_philosopher/res/choose.png")));
		setTitle("\u54F2\u5B66\u5BB6\u8FDB\u9910\u95EE\u9898");
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 25, 464, 258);

		JLabel jlabel1 = new JLabel("\u54F2\u5B66\u5BB6\u8FDB\u9910\u6A21\u62DF");
		jlabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jlabel1.setBounds(76, 0, 289, 47);
		getContentPane().add(jlabel1);
		jlabel1.setFont(new Font("楷体", Font.PLAIN, 40));
		jlabel1.setForeground(Color.BLACK);

		JButton itf1Button = new JButton("\u6700\u591A\u5141\u8BB84\u54F2\u5B66\u5BB6\u540C\u65F6\u8FDB\u9910");
		itf1Button.setBounds(227, 81, 190, 30);
		getContentPane().add(itf1Button);

		JButton itf2Button = new JButton("\u5947\u5DE6\u5076\u53F3");
		itf2Button.setBounds(26, 179, 190, 30);
		getContentPane().add(itf2Button);

		JButton itf3Button = new JButton("AND\u4FE1\u53F7\u91CF\u673A\u5236");
		itf3Button.setBounds(26, 129, 190, 30);
		getContentPane().add(itf3Button);

		JButton itf4Button = new JButton("\u8BB0\u5F55\u578B\u578B\u53F7\u91CF");
		itf4Button.setBounds(26, 81, 190, 30);
		getContentPane().add(itf4Button);

		JButton eatButton = new JButton("\u624B\u52A8--\u5141\u8BB8\u6B7B\u9501");
		eatButton.setBounds(227, 129, 190, 30);
		getContentPane().add(eatButton);

		JButton eat2Button = new JButton("\u624B\u52A8--\u4E24\u53EA\u7B77\u5B50\u540C\u65F6\u53D6");
		eat2Button.setBounds(227, 179, 190, 30);
		getContentPane().add(eat2Button);
		itf4Button.addActionListener(new ActionListener()// 设置监听器
		{
			public void actionPerformed(ActionEvent e) {
				m4.setSize(900, 700);
				m4.setVisible(true);
			}
		});
		itf3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m2.setSize(900, 700);
				m2.setVisible(true);
			}
		});
		itf2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m1.setSize(900, 700);
				m1.setVisible(true);
			}
		});
		itf1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m0.setSize(900, 700);
				m0.setVisible(true);
			}
		});
		eatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eat.setSize(900, 700);
				eat.setVisible(true);
			}
		});
		eat2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eat2.setSize(900, 700);// 设置界面大小
				eat2.setVisible(true);
			}
		});
	}

	public static void main(String args[]) {
		Menu s = new Menu();
		s.setLocationRelativeTo(null);
		s.setVisible(true);
	}
}
