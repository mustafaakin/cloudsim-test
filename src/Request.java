import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

public class Request {
	private double time;

	private int vmCount, cloudletCount;

	public Request(double time, int vmCount, int cloudletCount) {
		this.time = time;
		this.vmCount = vmCount;
		this.cloudletCount = cloudletCount;
	}

	public double getTime() {
		return time;
	}

	public int getCloudletCount() {
		return cloudletCount;
	}

	public int getVmCount() {
		return vmCount;
	}
}
