package code;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class chopsticks  {
	 int i;
	JLabel cl;
	
	ImageIcon empty=new ImageIcon("empty.jpg"),cp;
	 int semaphore=1;
	public chopsticks(int i,JLabel cl, ImageIcon cp){
	this.i=i;
	this.cl=cl;this.cp=cp;
	}
    public String toString(){
		return"筷子"+i;
		}
	public synchronized void P()//对象级别锁   取筷子，信号量限制互斥
	{
		semaphore--;
		if(semaphore<0)
		{
				try{
						wait();
					}catch(InterruptedException e){}
					
			
		}
		cl.setIcon(empty);
	}
	public synchronized void V()//释放筷子，唤醒其他线程
	{//对象级别锁
		semaphore++;
		if(semaphore<=0){
			
			notify();
		}
		cl.setIcon(cp);
	}
	}

