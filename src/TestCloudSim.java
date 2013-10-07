import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.core.CloudSim;

public class TestCloudSim {

	public static void main(String[] args) throws Exception {
		int num_user = 1;
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false;

		CloudSim.init(num_user, calendar, trace_flag);

		Provider.getDatacenter("Center1", Provider.getHost(4, 0, 4096));
		Provider.getDatacenter("Center2", Provider.getHost(4, 0, 4096));
		
		User u1 = new User("mustafa");
		u1.addRequest(new Request(100, 5, 4));
		u1.addRequest(new Request(200, 1, 5));
		u1.addRequest(new Request(300, 2, 8));
		u1.addRequest(new Request(350, 4, 10));

		User u2 = new User("cem");
		u2.addRequest(new Request(10, 1, 2));
		u2.addRequest(new Request(500, 1, 2));

		User u3 = new User("ibrahim");
		u3.addRequest(new Request(1000, 10, 20));

		CloudSim.startSimulation();
		
		// Get cloudlet list for each user
		List<Cloudlet> list = new LinkedList<Cloudlet>();
		list.addAll(u1.getCloudletList());
		list.addAll(u2.getCloudletList());
		list.addAll(u3.getCloudletList());

		
		// Get the Broker ID mappings from each user
		ArrayList<ArrayList<Integer>> idMap = new ArrayList<ArrayList<Integer>>();
		idMap.add(0, u1.getIds());
		idMap.add(1, u2.getIds());
		idMap.add(2, u3.getIds());
		
				
		
		CloudSim.stopSimulation();

		Util.printCloudletList(list,new String[]{"mustafa","cem","ibrahim"},idMap);
	}
}
