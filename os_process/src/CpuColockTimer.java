
import java.util.Timer;
import java.util.TimerTask;

public class CpuColockTimer {

	public static Timer timer = new Timer(); // ��ʱ
	public static int time = 0;
	public static int timeSlice = 5;// cpu ʱ��Ƭ��Ĭ��Ϊ5s
	public static boolean pauseCountTime = false;// ���ڿ��Ƽ�ʱ���Ƿ��ʱ��false���ʱ��true ��ֹͣ��ʱ

	public CpuColockTimer() {
	}

	public void start() {
		timer.schedule(new TimerTask() {
			public void run() {
				count(); // ʱ��ÿ�μ�1s
				if (time > 600)
					timer.cancel();
			}

			private void count() {
				if (pauseCountTime == false) {
					if (MainFrame.fcfsRadioB.isSelected()) {
						DispatchPcb(1);// �����ȷ�������㷨
					} else if (MainFrame.rrRadioB.isSelected()) {
						DispatchPcb(2);// ʱ��Ƭ��ת�����㷨
					} else if (MainFrame.mulrrRadioB.isSelected()) {
						DispatchPcb(3);// �༶�������е����㷨
					} else if (MainFrame.staticPriorityRadioB.isSelected()) {
						DispatchPcb(4);// ��̬���ȼ������㷨
					} else if (MainFrame.sjrRadioB.isSelected()) {
						DispatchPcb(5);// �����ҵ���ȵ����㷨
					}
				}
			}

			private void DispatchPcb(int type) {
				time++;
				MainFrame.timeLabel.setText(new String(new Integer(time).toString() + "  s"));
				int size = MainFrame.runPcb.size();
				if (size > 0 && MainFrame.runPcb.get(size - 1).getStatus() == "ִ��̬")
				// ��ִ�ж��в�Ϊ���������̴���ִ��̬������ִ��ʱ���1s
				{
					PCB pcb = MainFrame.runPcb.get(size - 1);
					int exeTime = pcb.getExecuteTime();
					exeTime += 1;// ��������ִ��ʱ����1s
					pcb.setExecuteTime(exeTime);

					MainFrame.runMode.setValueAt(exeTime, MainFrame.runMode.getRowCount() - 1, 5);// ����ִ��Table�У�����ִ��̬���̵���ִ��ʱ��

					if (type == 2 || type == 3)// ��Ϊʱ��Ƭ��ת����༶������ת��ʱ
					{
						if (exeTime % timeSlice == 0 && exeTime < pcb.getLastTime())// ʱ��Ƭ�����ҽ��̻�û��������
						{
							pcb.setStatus("����̬");
							MainFrame.runMode.removeRow(size - 1);
							MainFrame.runPcb.remove(pcb);// ��ִ�ж������Ƴ�ʱ��Ƭ����Ľ���

							if (type == 2)// Ϊʱ��Ƭ��ת��ʱ�������������1��
							{
								MainFrame.readyPcb1.add(pcb);
								MainFrame.addReadyTable(pcb, 1);
							} else // Ϊ�༶������ת��ʱ�������������2��
							{
								MainFrame.readyPcb2.add(pcb);
								MainFrame.addReadyTable(pcb, 2);
							}

							MainFrame.startRunPcb();// ��ʼ������һ���������̣���û�о������̣������еȴ�����
						}
					}
					if (exeTime == pcb.getLastTime())// �����������
					{
						pcb.setEndTime(time);
						pcb.setStatus("���̬");
						MainFrame.runMode.removeRow(size - 1);
						MainFrame.addRunTable(pcb);// ���½���״̬Ϊ���̬���ȴ�ִ��Table���Ƴ��������¼���ִ��Table��

						if (MainFrame.waitPcb.size() > 0)// ���ȴ����в�Ϊ�գ����ѵȴ����е�����������
							MainFrame.wakeWaitPcb();

						MainFrame.startRunPcb();// ��ʼ������һ���������̣���û�о������̣������еȴ�����
					}
				} else if (size > 0)// ִ�ж����н��̶���������ɣ����ѵȴ������ҵ����¸�������������
				{
					if (MainFrame.waitPcb.size() > 0)// ���ȴ����в�Ϊ�գ����ѵȴ����е�����������
						MainFrame.wakeWaitPcb();

					MainFrame.startRunPcb();// ��ʼ������һ���������̣���û�о������̣������еȴ�����
				}
			}
		}, 1, 1000);// 1---��ʾ��ʱ��1ms��ʼ��1000---��ʾÿ��1s��һ��ʱ��
	}
}
