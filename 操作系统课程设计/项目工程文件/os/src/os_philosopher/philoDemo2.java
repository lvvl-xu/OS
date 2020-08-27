/***
 * ������������߿��ӣ�ż���������ұ߿���
 */
package os_philosopher;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class philoDemo2 extends Thread {
	int No;
	static JTextArea area = new JTextArea(60, 30);
	private Thread eating = null;
	private Random rand = new Random();
	chopsticks left, right;
	JLabel phl, ll, rl, hll, hrl;// phl ��ѧ��ͼ��,ll ��ߵĿ���ͼ��,rl �ұߵĿ���ͼ��,lp ��߿���ͼƬ ,rp �ұ߿���ͼƬ,hll ��߿�,hrl �ұ߿�
	int wait[] = { 0, 0, 0, 0, 0 };
	ImageIcon lp, rp;
	ImageIcon tp = new ImageIcon("src/os_philosopher/res/thinking1.jpg");
	ImageIcon sp = new ImageIcon("src/os_philosopher/res/starving1.jpg");
	ImageIcon ep = new ImageIcon("src/os_philosopher/res/eating1.jpg");
	ImageIcon empty = new ImageIcon("src/os_philosopher/res/empty.jpg");
	int priority = Thread.NORM_PRIORITY;
	boolean Done;

	philoDemo2(int num, JLabel pl, chopsticks left, JLabel ll, chopsticks right, JLabel rl, ImageIcon lp, ImageIcon rp,
			JLabel hll, JLabel hrl, /* int priority, */JTextArea area) {
		this.No = num;
		this.phl = pl;
		this.left = left;
		this.ll = ll;
		this.right = right;
		this.rl = rl;
		this.hll = hll;
		this.hrl = hrl;
		this.lp = lp;
		this.rp = rp;
		this.area = area;
		this.priority = priority;

	}

	public String toString() {
		return "��ѧ��" + No;
	}

	public void think() {// ˼��
		phl.setIcon(tp);
		try {
			Thread.sleep(/* rand.nextInt(ponder) */4000);
		} catch (InterruptedException e) {
		}
		System.out.println(this + "��˼��");
		area.append(this + "��˼��" + "\n");
	}

	// �������� �Է� ����ѧ�Һ�λ����ʱ��ȡ��߿��ӣ���ȡ�ұ߿��ӣ���֮�෴
	public void eat() {

		phl.setIcon(sp);
		area.append(this + "�Ҷ��ˣ�" + "\n");
		System.out.println(this + "�Ҷ��ˣ�");
		if (No % 2 != 0) {

			left.P();// ȡ��߿���
			hll.setIcon(lp);
			area.append(this + "���ȼ���" + priority + "����" + left + "׼������" + right + "\n");
			System.out.println(this + "���ȼ���" + priority + "����" + left + "׼������" + right);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

			right.P();// ȡ�ұ߿���
			hrl.setIcon(rp);
			phl.setIcon(ep);
			System.out.println(this + "����" + right + "�Է�");
			area.append(this + "����" + right + "�Է�" + "\n");
			try {
				sleep(5000);
			} catch (InterruptedException e) {
			}
			left.V();// ������߿���
			hll.setIcon(empty);
			right.V();// �����ұ߿���
			hrl.setIcon(empty);
		} else {

			right.P();
			hrl.setIcon(rp);
			System.out.println(this + "���ȼ���" + priority + "����" + right + ",׼������" + left);
			area.append(this + "���ȼ���" + priority + "����" + right + ",׼������" + left + "\n");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

			left.P();
			hll.setIcon(lp);
			phl.setIcon(ep);
			System.out.println(this + "����" + left + ",�Է�");
			area.append(this + "����" + left + ",�Է�" + "\n");
			try {
				sleep(5000);
			} catch (InterruptedException e) {
			}

			right.V();
			hrl.setIcon(empty);
			left.V();
			hll.setIcon(empty);
			setPriority(priority--);// �������ȼ�
			if (priority == Thread.MIN_PRIORITY) {
				priority = Thread.MAX_PRIORITY;
			}
		}
	}

	public void run() {// �����߳�
		while (true) {

			think();
			eat();
		}
	}

}
