
public class PCB {
	int pid=0; //���̱�ʶ��
	int priority=0;//���ȼ�
	String status="";//��ǰ״̬
	int lastTime=0;//����ʱ��
	int inputTime=0;//�ύʱ��
	int endTime=0;//���ʱ��
	int executeTime=0;//��ִ��ʱ��

	//��ý��̱�ʶ��
	public int getPid() {
		return pid;
	}
	//���ý��̱�ʶ��
	public void setPid(int pid) {
		this.pid = pid;
	}
	//������ȼ�
	public int getPriority() {
		return priority;
	}
	//�������ȼ�
	public void setPriority(int priority) {
		this.priority = priority;
	}
	//��ó���ʱ��
	public int getLastTime() {
		return lastTime;
	}
	//���ó���ʱ��
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}
	//����ύʱ��
	public int getInputTime() {
		return inputTime;
	}
	//�����ύʱ��
	public void setInputTime(int inputTime) {
		this.inputTime = inputTime;
	}
	//������ʱ��
	public int getEndTime() {
		return endTime;
	}
	//�������ʱ��
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	//�����ִ��ʱ��
	public int getExecuteTime() {
		return executeTime;
	}

	// ������ִ��ʱ��
	public void setExecuteTime(int executeTime) {
		this.executeTime = executeTime;
	}
	//��õ�ǰ״̬
	public String getStatus() {
		return status;
	}
	//���õ�ǰ״̬
	public void setStatus(String status) {
		this.status = status;
	}
}
