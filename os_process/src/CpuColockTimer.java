
import java.util.Timer;
import java.util.TimerTask;

public class CpuColockTimer {

	public static Timer timer = new Timer(); // 计时
	public static int time = 0;
	public static int timeSlice = 5;// cpu 时间片，默认为5s
	public static boolean pauseCountTime = false;// 用于控制计时器是否计时，false则计时，true 则停止计时

	public CpuColockTimer() {
	}

	public void start() {
		timer.schedule(new TimerTask() {
			public void run() {
				count(); // 时间每次加1s
				if (time > 600)
					timer.cancel();
			}

			private void count() {
				if (pauseCountTime == false) {
					if (MainFrame.fcfsRadioB.isSelected()) {
						DispatchPcb(1);// 先来先服务调度算法
					} else if (MainFrame.rrRadioB.isSelected()) {
						DispatchPcb(2);// 时间片轮转调度算法
					} else if (MainFrame.mulrrRadioB.isSelected()) {
						DispatchPcb(3);// 多级反馈队列调度算法
					} else if (MainFrame.staticPriorityRadioB.isSelected()) {
						DispatchPcb(4);// 静态优先级调度算法
					} else if (MainFrame.sjrRadioB.isSelected()) {
						DispatchPcb(5);// 最短作业优先调度算法
					}
				}
			}

			private void DispatchPcb(int type) {
				time++;
				MainFrame.timeLabel.setText(new String(new Integer(time).toString() + "  s"));
				int size = MainFrame.runPcb.size();
				if (size > 0 && MainFrame.runPcb.get(size - 1).getStatus() == "执行态")
				// 若执行队列不为空且最后进程处在执行态则将其已执行时间加1s
				{
					PCB pcb = MainFrame.runPcb.get(size - 1);
					int exeTime = pcb.getExecuteTime();
					exeTime += 1;// 将进程已执行时间增1s
					pcb.setExecuteTime(exeTime);

					MainFrame.runMode.setValueAt(exeTime, MainFrame.runMode.getRowCount() - 1, 5);// 更新执行Table中，处在执行态进程的已执行时间

					if (type == 2 || type == 3)// 当为时间片轮转法或多级反馈轮转法时
					{
						if (exeTime % timeSlice == 0 && exeTime < pcb.getLastTime())// 时间片用完且进程还没有运行完
						{
							pcb.setStatus("就绪态");
							MainFrame.runMode.removeRow(size - 1);
							MainFrame.runPcb.remove(pcb);// 从执行队列中移除时间片用完的进程

							if (type == 2)// 为时间片轮转法时，加入就绪队列1中
							{
								MainFrame.readyPcb1.add(pcb);
								MainFrame.addReadyTable(pcb, 1);
							} else // 为多级反馈轮转法时，加入就绪队列2中
							{
								MainFrame.readyPcb2.add(pcb);
								MainFrame.addReadyTable(pcb, 2);
							}

							MainFrame.startRunPcb();// 开始运行下一个就绪进程，若没有就绪进程，则运行等待进程
						}
					}
					if (exeTime == pcb.getLastTime())// 进程运行完成
					{
						pcb.setEndTime(time);
						pcb.setStatus("完成态");
						MainFrame.runMode.removeRow(size - 1);
						MainFrame.addRunTable(pcb);// 更新进程状态为完成态。先从执行Table中移除，再重新加入执行Table中

						if (MainFrame.waitPcb.size() > 0)// 若等待队列不为空，则唤醒等待队列到就绪队列中
							MainFrame.wakeWaitPcb();

						MainFrame.startRunPcb();// 开始运行下一个就绪进程，若没有就绪进程，则运行等待进程
					}
				} else if (size > 0)// 执行队列中进程都已运行完成，则唤醒等待进程且调入下个就绪进程运行
				{
					if (MainFrame.waitPcb.size() > 0)// 若等待队列不为空，则唤醒等待队列到就绪队列中
						MainFrame.wakeWaitPcb();

					MainFrame.startRunPcb();// 开始运行下一个就绪进程，若没有就绪进程，则运行等待进程
				}
			}
		}, 1, 1000);// 1---表示计时从1ms开始，1000---表示每隔1s计一次时间
	}
}
