import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

public class VmParams {
	public int cores;
	public int ram;
	public int work;
	public int speed;

	private static int bw = 0;

	public Vm getVm(int vmId, int userId) {
		return new Vm(vmId, userId, speed, cores, ram, bw, work, "Xen",
				new CloudletSchedulerTimeShared());
	}

}
