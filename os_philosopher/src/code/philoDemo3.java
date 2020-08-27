/**
 * 要么同时拿起，要么不拿
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
	JLabel phl,ll,rl,hll,hrl;//phl 哲学家图标,ll 左边的筷子图标,rl 右边的筷子图标,lp 左边筷子图片 ,rp 右边筷子图片,hll 左边空,hrl 右边空
	
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
		return"哲学家"+No;
		}
	public void think(){//思考
			phl.setIcon(tp);
		    try{
		    		Thread.sleep(/*rand.nextInt(ponder)*/8000);
		    	}catch(InterruptedException e){}
		    	System.out.println(this+"在思考"+"\n");
		    	area.append(this+"在思考"+"\n");
		}
		
	
		public void run(){//运行线程
		while(true){
			
			think();
			phl.setIcon(sp);
			System.out.println(this+"我饿了！");
			area.append(this+"我饿了！"+"\n");
			waiting(No);
			eat();
			putdown(No);
		}
		}
		
	public static synchronized boolean test(int i)//测试左右两侧筷子是否都未被占用
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
		while(!test(i))//被占用，等待可用
		{
			try{
				philoDemo3.class.wait();
				
			}catch(Exception e)
			{
				area.append(Thread.currentThread().getName()+"不能同时拿到两只筷子，请等待"+"\n");
				System.out.println(Thread.currentThread().getName()+"不能同时拿到两只筷子，请等待");
			}
		}
		area.append(Thread.currentThread().getName()+"可以同时获得筷子，可以吃饭"+"\n");
		System.out.println(Thread.currentThread().getName()+"可以同时获得筷子，可以吃饭");
	}
	public static synchronized void putdown(int i)//用完筷子后唤醒其他线程
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
			area.append(this+"优先级："+priority+"拿起"+left+"准备拿起"+right+"开始吃饭"+"\n");
			System.out.println(this+"优先级："+priority+"拿起"+left+"拿起"+right+"开始吃饭");
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
			area.append(this+"优先级："+priority+"拿起"+right+",准备拿起"+left+"开始吃饭"+"\n");
			System.out.println(this+"优先级："+priority+"拿起"+right+"拿起"+left+"开始吃饭");
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