package code;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class philoDemo4 extends Thread{
	int No;
	chopsticks left,right;
JLabel phl,ll,rl,hll,hrl;//phl ��ѧ��ͼ��,ll ��ߵĿ���ͼ��,rl �ұߵĿ���ͼ��,lp ��߿���ͼƬ ,rp �ұ߿���ͼƬ,hll ��߿�,hrl �ұ߿�
static JTextArea area=new JTextArea(60,30);
	ImageIcon lp,rp;
	ImageIcon tp=new ImageIcon("thinking1.jpg");
	ImageIcon sp=new ImageIcon("starving1.jpg");
	ImageIcon ep=new ImageIcon("eating1.jpg");
	ImageIcon empty=new ImageIcon("empty.jpg");
	int priority=Thread.NORM_PRIORITY;
	
	philoDemo4(int num,JLabel pl,chopsticks left, JLabel ll,chopsticks right,JLabel rl,
			 ImageIcon lp,ImageIcon rp, JLabel hll, JLabel hrl/*,int priority*/,JTextArea area ){
	    this.No=num;				this.phl=pl;
		this.left=left;     		this.ll=ll;
		this.right=right;			this.rl=rl;
		this.hll=hll;				this.hrl=hrl;
		this.lp=lp;					this.rp=rp;
		this.priority=priority;
		this.area=area;
		
	}
	public String toString(){
		return"��ѧ��"+No;
		}
	public void think(){//˼��
		phl.setIcon(tp);
	    try{
	    		Thread.sleep(/*rand.nextInt(ponder)*/4000);
	    	}catch(InterruptedException e){}
	    	System.out.println(this+"��˼��");
	    	area.append(this+"��˼��"+"\n");
	}
	//
	public void eat()
	{  
		phl.setIcon(sp);
		System.out.println(this+"�Ҷ��ˣ�");
		area.append(this+"�Ҷ��ˣ�"+"\n");
		left.P();
		hll.setIcon(lp);
		area.append(this+"���ȼ���"+priority+"����"+left+"׼������"+right+"\n");
		System.out.println(this+"����"+left+"׼������"+right);
		right.P();
		area.append(this+"����"+right+"�Է�"+"\n");
		System.out.println(this+"����"+right+"�Է�");
		hrl.setIcon(rp);
	    phl.setIcon(ep);
	    try{
			sleep(5000);
		}catch(InterruptedException e){}
		left.V();
		hll.setIcon(empty);
		right.V();
		hrl.setIcon(empty);
	}
	public void run(){
		while(true){
			
			think();
			
			eat();
		}
		}
}
