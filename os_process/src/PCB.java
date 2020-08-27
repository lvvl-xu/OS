
public class PCB {
	int pid=0; //进程标识号
	int priority=0;//优先级
	String status="";//当前状态
	int lastTime=0;//持续时间
	int inputTime=0;//提交时间
	int endTime=0;//完成时间
	int executeTime=0;//已执行时间

	//获得进程标识号
	public int getPid() {
		return pid;
	}
	//设置进程标识号
	public void setPid(int pid) {
		this.pid = pid;
	}
	//获得优先级
	public int getPriority() {
		return priority;
	}
	//设置优先级
	public void setPriority(int priority) {
		this.priority = priority;
	}
	//获得持续时间
	public int getLastTime() {
		return lastTime;
	}
	//设置持续时间
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}
	//获得提交时间
	public int getInputTime() {
		return inputTime;
	}
	//设置提交时间
	public void setInputTime(int inputTime) {
		this.inputTime = inputTime;
	}
	//获得完成时间
	public int getEndTime() {
		return endTime;
	}
	//设置完成时间
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	//获得已执行时间
	public int getExecuteTime() {
		return executeTime;
	}

	// 设置已执行时间
	public void setExecuteTime(int executeTime) {
		this.executeTime = executeTime;
	}
	//获得当前状态
	public String getStatus() {
		return status;
	}
	//设置当前状态
	public void setStatus(String status) {
		this.status = status;
	}
}
