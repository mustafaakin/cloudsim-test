
public class Request {
	private double time;

	private int vmCount;

	public Request(double time, int vmCount) {
		this.time = time;
		this.vmCount = vmCount;
	}

	public double getTime() {
		return time;
	}

	public int getVmCount() {
		return vmCount;
	}
}
