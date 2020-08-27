/*
  为了预防死锁的产生，我们添加一条竞争规则：所有哲学家先竞争奇数号筷子，获得后才能去竞争偶数号筷子（由于5号哲学家左右都是奇数号筷子，
  在本文中规定他先竞争5号筷子）。这样的话就总会有一名哲学家可以顺利获得两支筷子开始进餐。此方法的本质是通过附加的规则，让哲学家按照一
  定的顺序请求临界资源——筷子。这样的话，在资源分配图中就不会出现环路，破坏了死锁产生的必要条件之一：“环路等待”条件，从而有效地预防了死
 锁的产生。接下来我们用 Java 语言来实现该刚才描述的策略。在实现代码中用五个线程表示五个哲学家的活动， 用一个逻辑型数组表示筷子的状
 态。 在此问题中，筷子是临界资源，必须互斥地进行访问。我们为筷子定义一个类，其中包含了表示筷子状态的逻辑。
 */
class Chopsticks{
/* 用 used[1]至 used[5]五个数组元素分别代表编号 1 至 5 的五支筷子的状态 */
/* false 表示未被占用，true 表示已经被占用。 used[0]元素在程序中未使用 */
    private boolean used[]={true,false,false,false,false,false};
/* 拿起筷子的操作 */
    public synchronized void takeChopstick(){
/* 取得当前线程的名称并转化为整型,用此整数来判断该哲学家应该用哪两支筷子 */
/* i 为左手边筷子编号，j 为右手边筷子编号 */
        String name=Thread.currentThread().getName();
        int i=Integer.parseInt(name);
/* 1~4 号哲学家使用的筷子编号是 i 和 i+1,5 号哲学家使用
的筷子编号是 5 和 1 */
        int j=i==5?1:i+1;
/* 将两边筷子的编号按奇偶顺序赋给 odd,even 两个变量 */
        int odd,even;
        if(i%2==0){
        	even=i;odd=j;
        }
        else {
        	odd=i;even=j;
        }
/* 首先竞争奇数号筷子 */
        while(used[odd]){
            try{
            	wait();
               }
        catch(InterruptedException e){}
/* 中断函数  */
        }
        used[odd]=true;
/* 然后竞争偶数号筷子 */
        while(used[even]){
            try{
            	wait();            //"睡眠"
            	
               }
        catch(InterruptedException e){}
        }
        used[even]=true;
    }/*放下筷子的操作 */
    public synchronized void putChopstick(){
        String name=Thread.currentThread().getName();
        int i=Integer.parseInt(name);
        int j=i==5?1:i+1;
/* 将相应筷子的标志置为 fasle 表示使用完毕， 并且通知其
他等待线程来竞争 */
        used[i]=false;
        used[j]=false;
        notifyAll();               //唤醒，与wait（）对应
    }
}


/*
当某一哲学家线程执行取得筷子方法时， 程序会根据该线程的名称来确定该线程需要使用哪两支筷子，并且分辨出哪支筷子编号是奇数，按照先奇后偶的
顺序来试图取得这两支筷子。 如果这两支筷子都未被使用(即对应的数组元素值为 false)，该哲学家线程即可先后取得这两支筷子进餐，否则会在竞
争某支筷子失 败后执行 wait()操作进入 Chopsticks 类实例的等待区， 直到其他的哲学家线程进餐完毕放下筷子时用 notifyAll()将
其唤醒。当某一哲学家线程放下筷子时， 程序会将放下的筷子对应的数组元素值置为 false，并用 notifyAll()唤醒在等待区里的其他线程。 
接下来定义出哲学家类
 */
class Philosopher extends Thread{
    Chopsticks chopsticks;
    public Philosopher(String name,Chopsticks chopsticks){
/* 在构造实例时将 name 参数传给 Thread 的构造函数作为线程的名称 */
        super(name);
/* 所有哲学家线程共享同一个筷子类的实例 */
        this.chopsticks=chopsticks;
    }
    public void run(){
/* 交替地思考、拿起筷子、进餐、放下筷子 */
        while(true){
             thinking();
             chopsticks.takeChopstick();
             eating();
             chopsticks.putChopstick();
        }
    }
    public void thinking(){
/* 显示字符串输出正在思考的哲学家，用线程休眠1秒钟来模拟思考时间 */
        System.out.println ("Philosopher " +Thread.currentThread ().getName()+" is thinking.");
        try{
        	Thread.sleep(2000);
        }
        catch(InterruptedException e){}
    }
    public void eating(){
/* 显示字符串输出正在进餐的哲学家，并用线程休眠 1 秒钟来模拟进餐时间 */
        System.out.println ("Philosopher " +Thread.currentThread ().getName()+" is eating.");
        try{
        	Thread.sleep(2000);
        }
        catch(InterruptedException e){}
    }
} 


/*
在运行时，用Philosopher 类产生五个线程模拟五个哲学家，每个线程不停地重复执行思考、拿起筷子、进餐、放下筷子的过程。 线程的名称依次为
“1”， “2”，“3”，“4”，“5”(字符串类型) 
主程序如下
 */
public class Mainz{
    public static void main(String[] args){
/* 产生筷子类的实例 chopsticks */
        Chopsticks chopsticks=new Chopsticks();
/* 用筷子类的实例作为参数， 产生五个哲学家线程并启动*/
/* 五个哲学家线程的名称为 1~5 */
        new Philosopher("1",chopsticks).start();
        new Philosopher("2",chopsticks).start();
        new Philosopher("3",chopsticks).start();
        new Philosopher("4",chopsticks).start();
        new Philosopher("5",chopsticks).start();
    }
}