/***
 * 奇数号先拿左边筷子，偶数号先拿右边筷子
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
	JLabel phl, ll, rl, hll, hrl;// phl 哲学家图标,ll 左边的筷子图标,rl 右边的筷子图标,lp 左边筷子图片 ,rp 右边筷子图片,hll 左边空,hrl 右边空
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
		return "哲学家" + No;
	}

	public void think() {// 思考
		phl.setIcon(tp);
		try {
			Thread.sleep(/* rand.nextInt(ponder) */4000);
		} catch (InterruptedException e) {
		}
		System.out.println(this + "在思考");
		area.append(this + "在思考" + "\n");
	}

	// 满足条件 吃饭 当哲学家号位奇数时先取左边筷子，再取右边筷子，反之相反
	public void eat() {

		phl.setIcon(sp);
		area.append(this + "我饿了！" + "\n");
		System.out.println(this + "我饿了！");
		if (No % 2 != 0) {

			left.P();// 取左边筷子
			hll.setIcon(lp);
			area.append(this + "优先级：" + priority + "拿起" + left + "准备拿起" + right + "\n");
			System.out.println(this + "优先级：" + priority + "拿起" + left + "准备拿起" + right);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

			right.P();// 取右边筷子
			hrl.setIcon(rp);
			phl.setIcon(ep);
			System.out.println(this + "拿起" + right + "吃饭");
			area.append(this + "拿起" + right + "吃饭" + "\n");
			try {
				sleep(5000);
			} catch (InterruptedException e) {
			}
			left.V();// 放下左边筷子
			hll.setIcon(empty);
			right.V();// 放下右边筷子
			hrl.setIcon(empty);
		} else {

			right.P();
			hrl.setIcon(rp);
			System.out.println(this + "优先级：" + priority + "拿起" + right + ",准备拿起" + left);
			area.append(this + "优先级：" + priority + "拿起" + right + ",准备拿起" + left + "\n");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

			left.P();
			hll.setIcon(lp);
			phl.setIcon(ep);
			System.out.println(this + "拿起" + left + ",吃饭");
			area.append(this + "拿起" + left + ",吃饭" + "\n");
			try {
				sleep(5000);
			} catch (InterruptedException e) {
			}

			right.V();
			hrl.setIcon(empty);
			left.V();
			hll.setIcon(empty);
			setPriority(priority--);// 设置优先级
			if (priority == Thread.MIN_PRIORITY) {
				priority = Thread.MAX_PRIORITY;
			}
		}
	}

	public void run() {// 运行线程
		while (true) {

			think();
			eat();
		}
	}

}
