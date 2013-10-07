import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Log;

public class Util {

	public static void printCloudletList(List<Cloudlet> list, String[] users,
			ArrayList<ArrayList<Integer>> idMap) {
		int size = list.size();
		Cloudlet cloudlet;

		String row = "%10s\t%10d\t%10s\t%10d\t%4d\t%10.2f\t%10.2f\t%10.2f";
		String header = row.replaceAll("d", "s").replaceAll(".2f", "s")
				.replaceAll("f", "s");

		Log.printLine(String.format(header, "User", "Cloudlet", "Status",
				"Datacenter", "VM", "Time", "Start", "Finish"));
		Log.printLine(String.format(header, "====", "========", "======",
				"==========", "==", "====", "=====", "======"));

		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			int userId = cloudlet.getUserId();
			String username = "N/A";
			for(int j=0; j < users.length; j++){
				if ( idMap.get(j).contains(userId)){
					username = users[j];
					break;
				}
			}
			
			Log.printLine(String.format(row, username,
					cloudlet.getCloudletId(),
					Cloudlet.getStatusString(cloudlet.getStatus()),
					cloudlet.getResourceId(), cloudlet.getVmId(),
					cloudlet.getActualCPUTime(), cloudlet.getExecStartTime(),
					cloudlet.getFinishTime()));
		}
	}
}
