package code;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class interface4  extends JFrame implements ActionListener{
	static JTextArea area=new JTextArea(50,28);
	JButton startBt,endBt;
	JLabel cl[];
	JLabel h[];
	JLabel pl[];
	chopsticks c[];
	philoDemo4 p[];
	ImageIcon cp[],empty,tp,sp,ep;
	interface4() {
	   super("哲学家就餐问题模拟--记录型型号量（允许死锁）");
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   Container ct=this.getContentPane();
	   ct.setBackground(java.awt.Color.YELLOW);
	   tp=new ImageIcon("thinking1.jpg");
	   sp=new ImageIcon("starving1.jpg");
	   ep=new ImageIcon("eating1.jpg");
	   empty=new ImageIcon("empty.jpg");
	   JPanel panel=new JPanel();
	   JPanel panel1=new JPanel();
	   JPanel panel2=new JPanel();
	   JPanel panel3=new JPanel();
	   JPanel panel4=new JPanel();
	   JPanel panel5=new JPanel();
	   panel1.setLayout(new FlowLayout());
	   panel1.add(new JLabel(tp));
	   panel1.add(new JLabel("思考中                  "));
	   panel1.add(new JLabel(sp));
	   panel1.add(new JLabel("饥饿中                  "));
	   panel1.add(new JLabel(ep));
	   panel1.add(new JLabel("吃饭中                  "));
	   pl=new JLabel[6];
	   for(int i=1;i<=5;i++) {
	    pl[i]=new JLabel();
	    pl[i].setIcon(tp);
	   }
	    
	    
	   cp=new ImageIcon[6];
	   cp[1]=new ImageIcon("c1.jpg");
	   cp[2]=new ImageIcon("c2.jpg");
	   cp[3]=new ImageIcon("c3.jpg");
	   cp[4]=new ImageIcon("c4.jpg");
	   cp[5]=new ImageIcon("c5.jpg");
	  
	   cl=new JLabel[6];
	   cl[1]=new JLabel(); cl[1].setIcon(cp[1]);
	   cl[2]=new JLabel(); cl[2].setIcon(cp[2]);
	   cl[3]=new JLabel(); cl[3].setIcon(cp[3]);
	   cl[4]=new JLabel(); cl[4].setIcon(cp[4]);
	   cl[5]=new JLabel(); cl[5].setIcon(cp[5]);
	  
	   c=new chopsticks[6];
	   for(int i=1;i<=5;i++) c[i]=new chopsticks(i,cl[i],cp[i]);
	  
	   h=new JLabel[13];
	   for(int i=1;i<=12;i++) {
	    h[i]=new JLabel();
	    h[i].setIcon(empty);
	   }
	  
	   p=new philoDemo4[6];
	   //建立哲学家对象
	   panel=new JPanel();
	   panel.setLayout(new GridBagLayout());//设置布局
	   GridBagConstraints gbc=new GridBagConstraints();
	   gbc.weightx=1; gbc.weighty=1;
	   gbc.gridwidth=1; gbc.gridheight=1;
	   gbc.gridy=0;
	   gbc.gridx=4; panel.add(h[2],gbc);
	   gbc.gridx=6; panel.add(h[1],gbc);
	   gbc.gridy=1;
	   gbc.gridx=5; panel.add(pl[1],gbc);
	   gbc.gridy=2;
	   gbc.gridx=0; panel.add(h[3],gbc);
	   gbc.gridx=3; panel.add(cl[1],gbc);
	   gbc.gridx=7; panel.add(cl[5],gbc);
	   gbc.gridx=10; panel.add(h[10],gbc);
	   gbc.gridy=3;
	   gbc.gridx=1; panel.add(pl[2],gbc);
	   gbc.gridx=9; panel.add(pl[5],gbc);
	   gbc.gridy=4;
	   gbc.gridx=0; panel.add(h[4],gbc);
	   gbc.gridx=10; panel.add(h[9],gbc);
	   gbc.gridy=5;
	   gbc.gridx=2; panel.add(cl[2],gbc);
	   gbc.gridx=8; panel.add(cl[4],gbc);
	   gbc.gridy=7;
	   gbc.gridx=2; panel.add(h[5],gbc);
	   gbc.gridx=3; panel.add(pl[3],gbc);
	   gbc.gridx=7; panel.add(pl[4],gbc);
	   gbc.gridx=8; panel.add(h[8],gbc);
	   gbc.gridy=8;
	   gbc.gridx=3; panel.add(h[6],gbc);
	   gbc.gridx=5; panel.add(cl[3],gbc);
	   gbc.gridx=7; panel.add(h[7],gbc);
	   ct.add(panel,BorderLayout.CENTER);
	  
	   p[0]=new philoDemo4(0, pl[1], c[5], cl[5],
	     c[1], cl[1], cp[5],   cp[1], h[1], h[2],area);
	   p[1]=new philoDemo4(1, pl[2], c[1], cl[2],
	     c[2], cl[1], cp[2],   cp[1], h[4], h[3],area);
	   p[2]=new philoDemo4(2, pl[3], c[2], cl[2],
	     c[3], cl[3], cp[2],   cp[3], h[5], h[6],area);
	   p[3]=new philoDemo4(3, pl[4], c[3], cl[4],
	     c[4], cl[3], cp[4],   cp[3], h[8], h[7],area);
	   p[4]=new philoDemo4(4, pl[5], c[4], cl[4],
	     c[5], cl[5], cp[4],   cp[5], h[9], h[10],area);
	  
	   startBt=new JButton("开始");
	   startBt.setEnabled(true);
	   startBt.addActionListener(this);
	   endBt=new JButton("关闭");
	   endBt.addActionListener(this);
	   panel2=new JPanel(new FlowLayout());
	   panel2.add(startBt);
	   panel2.add(endBt);
	 
	   panel3.setLayout(new BorderLayout());
	   panel3.add(panel1,"North");
	   panel3.add(panel,"Center");
	   panel3.add(panel2,"South");
	   panel4.add(new JScrollPane(area));
	 
	   ct.add(panel3,"Center");
	   ct.add(panel4,"East");
	}

	public void actionPerformed(ActionEvent e) {
	   if(e.getSource()==startBt) {
	    for(int i=0;i<=4;i++) 
	    {
	    	   p[i].start(); //启动5个哲学家线程
	    	 
	    }
	    
	    startBt.setEnabled(false);
	   }
	   else if(e.getSource()==endBt) {
		   dispose(); 
	    //System.exit(0);
	   }
	}

	public static void main(String[] args) {
		interface4 f=new interface4();
	   f.setSize(900,700);
	   f.setVisible(true);
	}

}
