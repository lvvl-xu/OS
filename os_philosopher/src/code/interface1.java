/**
 * 最多允许四个哲学家同时进餐
 */
package code;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class interface1 extends JFrame implements ActionListener {
	JButton startBt, endBt;
	static JTextArea area = new JTextArea(50, 28);
	JLabel cl[];
	JLabel h[];
	JLabel pl[];
	chopsticks c[];
	philoDemo1 p[];
	ImageIcon cp[], empty, tp, sp, ep;//五支筷子的图片组，空的图，思考中的图，饥饿中的图，吃饭中的图

	interface1() {
		super("哲学家就餐问题模拟--最多允许4哲学家同时进餐");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container ct = this.getContentPane();
		ct.setBackground(java.awt.Color.YELLOW);

		cp = new ImageIcon[6];//五支筷子的图片组
		cp[1] = new ImageIcon("c1.jpg");
		cp[2] = new ImageIcon("c2.jpg");
		cp[3] = new ImageIcon("c3.jpg");
		cp[4] = new ImageIcon("c4.jpg");
		cp[5] = new ImageIcon("c5.jpg");
		tp = new ImageIcon("thinking1.jpg");//空的图
		sp = new ImageIcon("starving1.jpg");//思考中的图
		ep = new ImageIcon("eating1.jpg");//饥饿中的图
		empty = new ImageIcon("empty.jpg");//吃饭中的图
		
		JPanel panel = new JPanel();
		
		pl = new JLabel[6];
		for (int i = 1; i <= 5; i++) {
			pl[i] = new JLabel();
			pl[i].setIcon(tp);
		}

		cl = new JLabel[6];
		cl[1] = new JLabel();
		cl[1].setIcon(cp[1]);
		cl[2] = new JLabel();
		cl[2].setIcon(cp[2]);
		cl[3] = new JLabel();
		cl[3].setIcon(cp[3]);
		cl[4] = new JLabel();
		cl[4].setIcon(cp[4]);
		cl[5] = new JLabel();
		cl[5].setIcon(cp[5]);

		c = new chopsticks[6];
		for (int i = 1; i <= 5; i++)
			c[i] = new chopsticks(i, cl[i], cp[i]);

		h = new JLabel[13];
		for (int i = 1; i <= 12; i++) {
			h[i] = new JLabel();
			h[i].setIcon(empty);
		}

		p = new philoDemo1[6];

		panel.setLayout(new GridBagLayout());// 设置布局
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridy = 0;
		gbc.gridx = 4;
		panel.add(h[2], gbc);
		gbc.gridx = 6;
		panel.add(h[1], gbc);
		gbc.gridy = 1;
		gbc.gridx = 5;
		panel.add(pl[1], gbc);
		gbc.gridy = 2;
		gbc.gridx = 0;
		panel.add(h[3], gbc);
		gbc.gridx = 3;
		panel.add(cl[1], gbc);
		gbc.gridx = 7;
		panel.add(cl[5], gbc);
		gbc.gridx = 10;
		panel.add(h[10], gbc);
		gbc.gridy = 3;
		gbc.gridx = 1;
		panel.add(pl[2], gbc);
		gbc.gridx = 9;
		panel.add(pl[5], gbc);
		gbc.gridy = 4;
		gbc.gridx = 0;
		panel.add(h[4], gbc);
		gbc.gridx = 10;
		panel.add(h[9], gbc);
		gbc.gridy = 5;
		gbc.gridx = 2;
		panel.add(cl[2], gbc);
		gbc.gridx = 8;
		panel.add(cl[4], gbc);
		gbc.gridy = 7;
		gbc.gridx = 2;
		panel.add(h[5], gbc);
		gbc.gridx = 3;
		panel.add(pl[3], gbc);
		gbc.gridx = 7;
		panel.add(pl[4], gbc);
		gbc.gridx = 8;
		panel.add(h[8], gbc);
		gbc.gridy = 8;
		gbc.gridx = 3;
		panel.add(h[6], gbc);
		gbc.gridx = 5;
		panel.add(cl[3], gbc);
		gbc.gridx = 7;
		panel.add(h[7], gbc);
		ct.add(panel, BorderLayout.CENTER);
		// 建立哲学家对象
		p[0] = new philoDemo1(0, pl[1], c[5], cl[5], c[1], cl[1], cp[5], cp[1], h[1], h[2], area);
		p[1] = new philoDemo1(1, pl[2], c[1], cl[2], c[2], cl[1], cp[2], cp[1], h[4], h[3], area);
		p[2] = new philoDemo1(2, pl[3], c[2], cl[2], c[3], cl[3], cp[2], cp[3], h[5], h[6], area);
		p[3] = new philoDemo1(3, pl[4], c[3], cl[4], c[4], cl[3], cp[4], cp[3], h[8], h[7], area);
		p[4] = new philoDemo1(4, pl[5], c[4], cl[4], c[5], cl[5], cp[4], cp[5], h[9], h[10], area);

		startBt = new JButton("开始");
		startBt.setEnabled(true);
		startBt.addActionListener(this);
		endBt = new JButton("关闭");
		endBt.addActionListener(this);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(new JLabel(tp));
		panel1.add(new JLabel("思考中                  "));
		panel1.add(new JLabel(sp));
		panel1.add(new JLabel("饥饿中                  "));
		panel1.add(new JLabel(ep));
		panel1.add(new JLabel("吃饭中                  "));
		
		JPanel panel2 = new JPanel();
		panel2 = new JPanel(new FlowLayout());
		panel2.add(startBt);
		panel2.add(endBt);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.add(panel1, "North");
		panel3.add(panel, "Center");
		panel3.add(panel2, "South");
		ct.add(panel3, "Center");
		
		JPanel panel4 = new JPanel();
		panel4.add(new JScrollPane(area));
		ct.add(panel4, "East");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startBt) {
			for (int i = 0; i <= 4; i++) // 启动5个哲学家线程
			{
				p[i].start();

			}
			startBt.setEnabled(false);
		} else if (e.getSource() == endBt) {
			// System.exit(0);
			dispose();
		}
	}

	public static void main(String[] args) {
		interface1 f = new interface1();
		f.setSize(900, 700);
		f.setVisible(true);
	}

}
