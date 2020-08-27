package 操作;
import java.util.*;


//读者写者问题——读者优先
class Semaphore{
	int value;
	public Semaphore(int v){//构造
		this.value = v;
	}
	public synchronized void p(){
		value = value-1;
		if(value<0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public synchronized void v(){
		value = value+1;
		if(value<=0){
			this.notifyAll();
		}
	}
}


class Reader implements Runnable{
	int count;
	Semaphore rmutex;
	Semaphore wmutex;
	
	public Reader(int c,Semaphore r,Semaphore w){
		this.count = c;
		this.rmutex = r;
		this.wmutex = w;
	}
	public void run(){
		while(true){
			rmutex.p();
			if(count == 0) wmutex.p();//当第一读进程欲读数据库时，阻止写进程写
			count++;
			rmutex.v();
			System.out.println(count+" begin read data");
			rmutex.p();
			count--;
			System.out.println("1 reader has finished  reading data");
			if(count == 0) wmutex.v();//当最后一个读进程读完数据库时，运行写进程写
			rmutex.v();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}

class Writer implements Runnable{
	Semaphore wmutex;
	int wnumber;
	public Writer(Semaphore w){    
		
		this.wmutex = w;
	}
	public void run(){
		    while(true){
			wmutex.p();
			System.out.println("writer "+"begin write data");//写入数据
			System.out.println("1 writer finish write data");
			wmutex.v();			
			try {
				Thread.sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    }				
	}
}
//读者写者——写者优先
class freader implements Runnable{
	int count;
	int rnumber;
	int z;
	Semaphore rmutex;
	Semaphore wmutex;
	Semaphore zmutex;
	Semaphore rsem;
	public freader(int c,Semaphore r,Semaphore w,Semaphore z,Semaphore z1){
		this.count = c;
		this.rmutex = r;
		this.wmutex = w;
		this.zmutex = z;	
		this.rsem = z1;
		}
	public void run(){
	    while(true){
	    zmutex.p();
	    rsem.p();
		rmutex.p();
		count++;
		if(count == 1) wmutex.p();//当第一读进程欲读数据库时，阻止写进程写 
		wmutex.v();
		rmutex.v();
		rsem.v();
		zmutex.v();
		System.out.println(count+" reader"+" begin read data");//读者消费资源
		rmutex.p();
		count--;
		System.out.println("1 writer has finish read data");
		if(count == 0) wmutex.v();//当最后一个读进程读完数据库时，运行写进程写
		rmutex.v();
		try {
			Thread.sleep((long) (Math.random()*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    }
	    
}
	}
class fwriter implements Runnable{
	int wcount;
	Semaphore rmutex;
	Semaphore wmutex;
	Semaphore ymutex;
	Semaphore rsem;
	int wnumber;
	public fwriter(int c,Semaphore r,Semaphore w,Semaphore y,Semaphore z1){
		this.rmutex = r;   
        this.wmutex = w;
        this.ymutex = y;
        this.rsem = z1;
        
	}
	public void run(){
		    while(true){
		    	ymutex.p();
		    	wcount++;
		    	if(wcount==1) rsem.p();
		    	ymutex.v();
		    	wmutex.p();
		    	System.out.println(wcount+" writer "+"begin write data");///写入
		    	wmutex.v();
		    	ymutex.p();
		    	wcount--;
		    	System.out.println("1 writer has finished write data");
		    	if(wcount==0)
		    	rsem.v();
		    	ymutex.v();
		    	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
			/*try {
				Thread.sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		    }				
	}
}
	

public class ReaderWriter{
	public static void main(String[] args){		
		//**********************************
		while(true){
		Scanner sc = new Scanner(System.in);
		System.out.println("输入读者个数(0-10)");
		int rnum = sc.nextInt();
		
		if(rnum<0|rnum>10){
			System.out.println("输入错误请重新输入");
			continue;
		}
		System.out.println("请输入写者个数(0-10)");
		int wnum = sc.nextInt();
		System.out.println("读者:"+rnum+"写者:"+wnum);
		if(wnum<0|wnum>10){
			System.out.println("输入错误重新输入");	
			continue;
		}
		System.out.println("读者优先请按1 写者优先请按2");
		int first = sc.nextInt();
		if(first<1|first>2){
			System.out.println("输入错误重新输入");	
			continue;
		}
		
		//*************************************
		if(first==1){
		 int count = 0;
		Semaphore rmutex = new Semaphore(1);
		Semaphore wmutex = new Semaphore(1);		
		Reader reader = new Reader( count,rmutex,wmutex);
		Thread []r = new Thread[rnum];
		Writer writer = new Writer(wmutex);
		Thread []w = new Thread[wnum];
		int b[] = new int[wnum];
		for(int i=0;i<rnum;i++){
			int c = 0;		
			r[i] = new Thread(reader);
			r[i].start();
			c++;			
			}	
		for(int dcount=0;dcount<wnum;dcount++){
			int d = 0;
			b[d]=dcount+1;
			w[dcount] = new Thread(writer);
			w[dcount].start();
			d++;
		}
		}	
		if(first==2){
			 int count = 0;
			 int wcount = 0;
			Semaphore rmutex = new Semaphore(1);
			Semaphore wmutex = new Semaphore(1);	
			Semaphore ymutex = new Semaphore(1);
			Semaphore rsem = new Semaphore(1);
			Semaphore zmutex = new Semaphore(1);
			freader reader = new freader( count,rmutex,wmutex,zmutex,rsem);
			Thread []r = new Thread[rnum];
			fwriter writer = new fwriter(wcount,rmutex,wmutex,ymutex,rsem);
			Thread []w = new Thread[wnum];
			int b[] = new int[wnum];
			for(int dcount=0;dcount<wnum;dcount++){
				int d = 0;
				b[d]=dcount+1;
				w[dcount] = new Thread(writer);
				w[dcount].start();
				d++;				
			}	
			for(int i=0;i<rnum;i++){
				int c = 0;		
				r[i] = new Thread(reader);
				r[i].start();
				c++;
				
			}
			
			}	
		
		

		
		
		/*Thread r1 = new Thread(reader);
		
		Thread w1 = new Thread(writer);
		w1.start();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		r1.start();
		break;*/
		}
		
	}
}
