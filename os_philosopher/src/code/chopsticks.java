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
		return"����"+i;
		}
	public synchronized void P()//���󼶱���   ȡ���ӣ��ź������ƻ���
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
	public synchronized void V()//�ͷſ��ӣ����������߳�
	{//���󼶱���
		semaphore++;
		if(semaphore<=0){
			
			notify();
		}
		cl.setIcon(cp);
	}
	}

