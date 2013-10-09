import java.util.LinkedList;

import org.cloudbus.cloudsim.Vm;

public class Request {
	private double time;

	private int vmCount;
	private VmParams params;

	public Request(double time, VmParams params, int vmCount) {
		this.time = time;
		this.vmCount = vmCount;
		this.params = params;
	}

	public VmParams getParams() {
		return params;
	}

	public double getTime() {
		return time;
	}

	public int getVmCount() {
		return vmCount;
	}
	
	public LinkedList<Vm> getVms(int vmId, int userId){
		LinkedList<Vm> vms = new LinkedList<Vm>();
		for(int i = 0; i < vmCount; i++){
			vms.add( params.getVm(vmId + i, userId));
		}
		return vms;
	}
}
