/**
 * ��������ĸ���ѧ��ͬʱ����
 */
package code;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
public class philoDemo1 extends Thread{
	int No;
	static JTextArea area=new JTextArea(60,30);
	private static int count=4;
	private Random rand=new Random();
	chopsticks left,right;
	int wait[]={5,0,4,0,3};
	JLabel phl,ll,rl,hll,hrl;//phl ��ѧ��ͼ��,ll ��ߵĿ���ͼ��,rl �ұߵĿ���ͼ��,lp ��߿���ͼƬ ,rp �ұ߿���ͼƬ,hll ��߿�,hrl �ұ߿�
	
	ImageIcon lp,rp;
	ImageIcon tp=new ImageIcon("thinking1.jpg");
	ImageIcon sp=new ImageIcon("starving1.jpg");
	ImageIcon ep=new ImageIcon("eating1.jpg");
	ImageIcon empty=new ImageIcon("empty.jpg");
	int priority=Thread.NORM_PRIORITY;
	
	philoDemo1(int num,JLabel pl,chopsticks left, JLabel ll,chopsticks right,JLabel rl,
			 ImageIcon lp,ImageIcon rp, JLabel hll, JLabel hrl,/*int priority,*/JTextArea area ){
	    this.No=num;				this.phl=pl;
		this.left=left;     		this.ll=ll;
		this.right=right;			this.rl=rl;
		this.hll=hll;				this.hrl=hrl;
		this.lp=lp;					this.rp=rp;
		//this.priority=priority;
		this.area=area;
	}
	public String toString(){
		return"��ѧ��"+No;
		}
	public static synchronized void P(){//�༶����,P����
		count--;
		if(count<0){
		try{
			area.append(Thread.currentThread().getName()+"�ڵ�����"+"\n");
			System.out.println(Thread.currentThread().getName()+"�ڵ�����");
			philoDemo1.class.wait();
		
		}catch(InterruptedException e){}
		}
		}
		public static synchronized void V(){//�༶����,V����
		count++;
		if(count<=0){
		philoDemo1.class.notify();
		}
		}
		public void think(){//˼��
			phl.setIcon(tp);
		    try{
		    		Thread.sleep(/*rand.nextInt(ponder)*/4000);
		    	}catch(InterruptedException e){}
		    	System.out.println(this+"��˼��");
		    	area.append(this+"��˼��"+"\n");
		}
		//�������� �Է�
		public void eat(){
			
		philoDemo1.P();
		phl.setIcon(sp);
		System.out.println(this+"�Ҷ��ˣ�");
		area.append(this+"�Ҷ��ˣ�"+"\n");
		//System.out.println(this+"����");
		int n=rand.nextInt(5);
		if(n%2==0){
			
				left.P();
				hll.setIcon(lp);
				System.out.println(this+"���ȼ���"+priority+"����"+left+"׼������"+right);
				System.out.println(this+"���ȼ���"+priority+"����"+left+"׼������"+right);
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){}
		
				right.P();
				hrl.setIcon(rp);
				phl.setIcon(ep);
				area.append(this+"����"+right+"�Է�"+"\n");
				System.out.println(this+"����"+right+"�Է�");
			
			philoDemo1.V();
			try{
					sleep(8000);
			}catch(InterruptedException e){}
			
			left.V();
			hll.setIcon(empty);
			right.V();
			hrl.setIcon(empty);
		}
		else{
			
				right.P();
				hrl.setIcon(rp);
				area.append(this+"���ȼ���"+priority+"����"+right+",׼������"+left+"\n");
				System.out.println(this+"���ȼ���"+priority+"����"+right+",׼������"+left);
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){}
			
			
				left.P();
				area.append(this+"����"+left+",�Է�"+"\n");
				System.out.println(this+"����"+left+",�Է�");
				hll.setIcon(lp);
				phl.setIcon(ep);
			
		try{
			sleep(8000);
		}catch(InterruptedException e){}
		philoDemo1.V();
		
		right.V();
		hrl.setIcon(empty);
		left.V();
		hll.setIcon(empty);
		}
		
		setPriority(priority--);//�������ȼ�
		if(priority==Thread.MIN_PRIORITY)
		{
			priority=Thread.MAX_PRIORITY;
		}
		}
		public void run(){//�����߳�
		while(true){
			
			think();
			eat();
			
			}
		}
		
	
}
