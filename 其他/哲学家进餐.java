/*
  Ϊ��Ԥ�������Ĳ������������һ����������������ѧ���Ⱦ��������ſ��ӣ���ú����ȥ����ż���ſ��ӣ�����5����ѧ�����Ҷ��������ſ��ӣ�
  �ڱ����й涨���Ⱦ���5�ſ��ӣ��������Ļ����ܻ���һ����ѧ�ҿ���˳�������֧���ӿ�ʼ���͡��˷����ı�����ͨ�����ӵĹ�������ѧ�Ұ���һ
  ����˳�������ٽ���Դ�������ӡ������Ļ�������Դ����ͼ�оͲ�����ֻ�·���ƻ������������ı�Ҫ����֮һ������·�ȴ����������Ӷ���Ч��Ԥ������
 ���Ĳ����������������� Java ������ʵ�ָøղ������Ĳ��ԡ���ʵ�ִ�����������̱߳�ʾ�����ѧ�ҵĻ�� ��һ���߼��������ʾ���ӵ�״
 ̬�� �ڴ������У��������ٽ���Դ�����뻥��ؽ��з��ʡ�����Ϊ���Ӷ���һ���࣬���а����˱�ʾ����״̬���߼���
 */
class Chopsticks{
/* �� used[1]�� used[5]�������Ԫ�طֱ������ 1 �� 5 ����֧���ӵ�״̬ */
/* false ��ʾδ��ռ�ã�true ��ʾ�Ѿ���ռ�á� used[0]Ԫ���ڳ�����δʹ�� */
    private boolean used[]={true,false,false,false,false,false};
/* ������ӵĲ��� */
    public synchronized void takeChopstick(){
/* ȡ�õ�ǰ�̵߳����Ʋ�ת��Ϊ����,�ô��������жϸ���ѧ��Ӧ��������֧���� */
/* i Ϊ���ֱ߿��ӱ�ţ�j Ϊ���ֱ߿��ӱ�� */
        String name=Thread.currentThread().getName();
        int i=Integer.parseInt(name);
/* 1~4 ����ѧ��ʹ�õĿ��ӱ���� i �� i+1,5 ����ѧ��ʹ��
�Ŀ��ӱ���� 5 �� 1 */
        int j=i==5?1:i+1;
/* �����߿��ӵı�Ű���ż˳�򸳸� odd,even �������� */
        int odd,even;
        if(i%2==0){
        	even=i;odd=j;
        }
        else {
        	odd=i;even=j;
        }
/* ���Ⱦ��������ſ��� */
        while(used[odd]){
            try{
            	wait();
               }
        catch(InterruptedException e){}
/* �жϺ���  */
        }
        used[odd]=true;
/* Ȼ����ż���ſ��� */
        while(used[even]){
            try{
            	wait();            //"˯��"
            	
               }
        catch(InterruptedException e){}
        }
        used[even]=true;
    }/*���¿��ӵĲ��� */
    public synchronized void putChopstick(){
        String name=Thread.currentThread().getName();
        int i=Integer.parseInt(name);
        int j=i==5?1:i+1;
/* ����Ӧ���ӵı�־��Ϊ fasle ��ʾʹ����ϣ� ����֪ͨ��
���ȴ��߳������� */
        used[i]=false;
        used[j]=false;
        notifyAll();               //���ѣ���wait������Ӧ
    }
}


/*
��ĳһ��ѧ���߳�ִ��ȡ�ÿ��ӷ���ʱ�� �������ݸ��̵߳�������ȷ�����߳���Ҫʹ������֧���ӣ����ҷֱ����֧���ӱ�������������������ż��
˳������ͼȡ������֧���ӡ� �������֧���Ӷ�δ��ʹ��(����Ӧ������Ԫ��ֵΪ false)������ѧ���̼߳����Ⱥ�ȡ������֧���ӽ��ͣ�������ھ�
��ĳ֧����ʧ �ܺ�ִ�� wait()�������� Chopsticks ��ʵ���ĵȴ����� ֱ����������ѧ���߳̽�����Ϸ��¿���ʱ�� notifyAll()��
�份�ѡ���ĳһ��ѧ���̷߳��¿���ʱ�� ����Ὣ���µĿ��Ӷ�Ӧ������Ԫ��ֵ��Ϊ false������ notifyAll()�����ڵȴ�����������̡߳� 
�������������ѧ����
 */
class Philosopher extends Thread{
    Chopsticks chopsticks;
    public Philosopher(String name,Chopsticks chopsticks){
/* �ڹ���ʵ��ʱ�� name �������� Thread �Ĺ��캯����Ϊ�̵߳����� */
        super(name);
/* ������ѧ���̹߳���ͬһ���������ʵ�� */
        this.chopsticks=chopsticks;
    }
    public void run(){
/* �����˼����������ӡ����͡����¿��� */
        while(true){
             thinking();
             chopsticks.takeChopstick();
             eating();
             chopsticks.putChopstick();
        }
    }
    public void thinking(){
/* ��ʾ�ַ����������˼������ѧ�ң����߳�����1������ģ��˼��ʱ�� */
        System.out.println ("Philosopher " +Thread.currentThread ().getName()+" is thinking.");
        try{
        	Thread.sleep(2000);
        }
        catch(InterruptedException e){}
    }
    public void eating(){
/* ��ʾ�ַ���������ڽ��͵���ѧ�ң������߳����� 1 ������ģ�����ʱ�� */
        System.out.println ("Philosopher " +Thread.currentThread ().getName()+" is eating.");
        try{
        	Thread.sleep(2000);
        }
        catch(InterruptedException e){}
    }
} 


/*
������ʱ����Philosopher ���������߳�ģ�������ѧ�ң�ÿ���̲߳�ͣ���ظ�ִ��˼����������ӡ����͡����¿��ӵĹ��̡� �̵߳���������Ϊ
��1���� ��2������3������4������5��(�ַ�������) 
����������
 */
public class Mainz{
    public static void main(String[] args){
/* �����������ʵ�� chopsticks */
        Chopsticks chopsticks=new Chopsticks();
/* �ÿ������ʵ����Ϊ������ ���������ѧ���̲߳�����*/
/* �����ѧ���̵߳�����Ϊ 1~5 */
        new Philosopher("1",chopsticks).start();
        new Philosopher("2",chopsticks).start();
        new Philosopher("3",chopsticks).start();
        new Philosopher("4",chopsticks).start();
        new Philosopher("5",chopsticks).start();
    }
}