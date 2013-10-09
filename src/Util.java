import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;

public class Util {
	private final static String row = "%10s\t%10s\t%10s\t%4d\t%10.2f\t%10.2f\t%10.2f";
	private final static String header = row.replaceAll("d", "s")
			.replaceAll(".2f", "s").replaceAll("f", "s");

	public static void printHeader() {
		Log.printLine(String.format(header, "User", "Status", "Datacenter",
				"VM", "Time", "Start", "Finish"));
		Log.printLine(String.format(header, "====", "======", "==========",
				"==", "====", "=====", "======"));

	}

	public static void printCloudletList(User u) {
		List<Cloudlet> list = u.getCloudletList();
		int size = list.size();
		Cloudlet cloudlet;

		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);

			String datacenterName = Provider.getDatacenterName(cloudlet
					.getResourceId());

			Log.printLine(String.format(row, u.getName(),
					Cloudlet.getStatusString(cloudlet.getStatus()),
					datacenterName, cloudlet.getVmId(),
					cloudlet.getActualCPUTime(), cloudlet.getExecStartTime(),
					cloudlet.getFinishTime()));
		}
	}
}
