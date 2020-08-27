/**
 * 最多允许四个哲学家同时进餐
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
	JLabel phl,ll,rl,hll,hrl;//phl 哲学家图标,ll 左边的筷子图标,rl 右边的筷子图标,lp 左边筷子图片 ,rp 右边筷子图片,hll 左边空,hrl 右边空
	
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
		return"哲学家"+No;
		}
	public static synchronized void P(){//类级别锁,P操作
		count--;
		if(count<0){
		try{
			area.append(Thread.currentThread().getName()+"在等允许"+"\n");
			System.out.println(Thread.currentThread().getName()+"在等允许");
			philoDemo1.class.wait();
		
		}catch(InterruptedException e){}
		}
		}
		public static synchronized void V(){//类级别锁,V操作
		count++;
		if(count<=0){
		philoDemo1.class.notify();
		}
		}
		public void think(){//思考
			phl.setIcon(tp);
		    try{
		    		Thread.sleep(/*rand.nextInt(ponder)*/4000);
		    	}catch(InterruptedException e){}
		    	System.out.println(this+"在思考");
		    	area.append(this+"在思考"+"\n");
		}
		//满足条件 吃饭
		public void eat(){
			
		philoDemo1.P();
		phl.setIcon(sp);
		System.out.println(this+"我饿了！");
		area.append(this+"我饿了！"+"\n");
		//System.out.println(this+"饥饿");
		int n=rand.nextInt(5);
		if(n%2==0){
			
				left.P();
				hll.setIcon(lp);
				System.out.println(this+"优先级："+priority+"拿起"+left+"准备拿起"+right);
				System.out.println(this+"优先级："+priority+"拿起"+left+"准备拿起"+right);
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){}
		
				right.P();
				hrl.setIcon(rp);
				phl.setIcon(ep);
				area.append(this+"拿起"+right+"吃饭"+"\n");
				System.out.println(this+"拿起"+right+"吃饭");
			
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
				area.append(this+"优先级："+priority+"拿起"+right+",准备拿起"+left+"\n");
				System.out.println(this+"优先级："+priority+"拿起"+right+",准备拿起"+left);
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){}
			
			
				left.P();
				area.append(this+"拿起"+left+",吃饭"+"\n");
				System.out.println(this+"拿起"+left+",吃饭");
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
		
		setPriority(priority--);//设置优先级
		if(priority==Thread.MIN_PRIORITY)
		{
			priority=Thread.MAX_PRIORITY;
		}
		}
		public void run(){//运行线程
		while(true){
			
			think();
			eat();
			
			}
		}
		
	
}
