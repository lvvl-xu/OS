/**
 * Ҫôͬʱ����Ҫô����
 */

package code;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
public class philoDemo3 extends Thread{
	int No;
	static JTextArea area=new JTextArea(60,30);
	private static boolean avail[]={true,true,true,true,true};
	private Random rand=new Random();
	private static int ponder=10;
	chopsticks left,right;
	JLabel phl,ll,rl,hll,hrl;//phl ��ѧ��ͼ��,ll ��ߵĿ���ͼ��,rl �ұߵĿ���ͼ��,lp ��߿���ͼƬ ,rp �ұ߿���ͼƬ,hll ��߿�,hrl �ұ߿�
	
	ImageIcon lp,rp;
	ImageIcon tp=new ImageIcon("thinking1.jpg");
	ImageIcon sp=new ImageIcon("starving1.jpg");
	ImageIcon ep=new ImageIcon("eating1.jpg");
	ImageIcon empty=new ImageIcon("empty.jpg");
	int priority=Thread.NORM_PRIORITY;
	boolean Done;
	philoDemo3(int num,JLabel pl,chopsticks left, JLabel ll,chopsticks right,JLabel rl,
			 ImageIcon lp,ImageIcon rp, JLabel hll, JLabel hrl,/*int priority,*/JTextArea area ){
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
		    		Thread.sleep(/*rand.nextInt(ponder)*/8000);
		    	}catch(InterruptedException e){}
		    	System.out.println(this+"��˼��"+"\n");
		    	area.append(this+"��˼��"+"\n");
		}
		
	
		public void run(){//�����߳�
		while(true){
			
			think();
			phl.setIcon(sp);
			System.out.println(this+"�Ҷ��ˣ�");
			area.append(this+"�Ҷ��ˣ�"+"\n");
			waiting(No);
			eat();
			putdown(No);
		}
		}
		
	public static synchronized boolean test(int i)//����������������Ƿ�δ��ռ��
	{
		if(avail[i]&&avail[(i+1)%5])
		{
			avail[i]=avail[(i+1)%5]=false;	
		    return true;	
		 }
		else 
			return false;
	}
	public static synchronized void waiting(int i)
	{
		while(!test(i))//��ռ�ã��ȴ�����
		{
			try{
				philoDemo3.class.wait();
				
			}catch(Exception e)
			{
				area.append(Thread.currentThread().getName()+"����ͬʱ�õ���ֻ���ӣ���ȴ�"+"\n");
				System.out.println(Thread.currentThread().getName()+"����ͬʱ�õ���ֻ���ӣ���ȴ�");
			}
		}
		area.append(Thread.currentThread().getName()+"����ͬʱ��ÿ��ӣ����ԳԷ�"+"\n");
		System.out.println(Thread.currentThread().getName()+"����ͬʱ��ÿ��ӣ����ԳԷ�");
	}
	public static synchronized void putdown(int i)//������Ӻ��������߳�
	{
		avail[i]=avail[(i+1)%5]=true;
		synchronized(philoDemo3.class){
			philoDemo3.class.notifyAll();
		}
	}
	public void eat(){
		
		if(rand.nextInt(10)%2==0)
		{ 
			left.P();  
			right.P();
			hll.setIcon(lp);
			hrl.setIcon(rp);
			phl.setIcon(ep);
			area.append(this+"���ȼ���"+priority+"����"+left+"׼������"+right+"��ʼ�Է�"+"\n");
			System.out.println(this+"���ȼ���"+priority+"����"+left+"����"+right+"��ʼ�Է�");
			try{
			Thread.sleep(20);
			}catch(InterruptedException e){}
		}
		else 
		{
			right.P();
			left.P();  
			hll.setIcon(lp);
			hrl.setIcon(rp);
			phl.setIcon(ep);
			area.append(this+"���ȼ���"+priority+"����"+right+",׼������"+left+"��ʼ�Է�"+"\n");
			System.out.println(this+"���ȼ���"+priority+"����"+right+"����"+left+"��ʼ�Է�");
			try{
					Thread.sleep(20);
				}catch(InterruptedException e){}
		}
		try{
				sleep(5000);
			}catch(InterruptedException e){}
		left.V();
		hll.setIcon(empty);
		right.V();
		hrl.setIcon(empty);
		setPriority(priority--);
		if(priority==Thread.MIN_PRIORITY)
		{
			priority=Thread.MAX_PRIORITY;
		}
	}
	
}