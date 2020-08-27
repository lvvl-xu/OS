package code;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class philoDemo4 extends Thread{
	int No;
	chopsticks left,right;
JLabel phl,ll,rl,hll,hrl;//phl 哲学家图标,ll 左边的筷子图标,rl 右边的筷子图标,lp 左边筷子图片 ,rp 右边筷子图片,hll 左边空,hrl 右边空
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
		return"哲学家"+No;
		}
	public void think(){//思考
		phl.setIcon(tp);
	    try{
	    		Thread.sleep(/*rand.nextInt(ponder)*/4000);
	    	}catch(InterruptedException e){}
	    	System.out.println(this+"在思考");
	    	area.append(this+"在思考"+"\n");
	}
	//
	public void eat()
	{  
		phl.setIcon(sp);
		System.out.println(this+"我饿了！");
		area.append(this+"我饿了！"+"\n");
		left.P();
		hll.setIcon(lp);
		area.append(this+"优先级："+priority+"拿起"+left+"准备拿起"+right+"\n");
		System.out.println(this+"拿起"+left+"准备拿起"+right);
		right.P();
		area.append(this+"拿起"+right+"吃饭"+"\n");
		System.out.println(this+"拿起"+right+"吃饭");
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
