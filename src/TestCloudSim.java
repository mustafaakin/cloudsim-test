import java.util.Calendar;

import org.cloudbus.cloudsim.core.CloudSim;

public class TestCloudSim {

	public static void main(String[] args) throws Exception {
		int num_user = 1;
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false;

		CloudSim.init(num_user, calendar, trace_flag);

		Provider.getDatacenter("C0", Provider.getHost(4, 0, 4096));
		Provider.getDatacenter("C1", Provider.getHost(8, 1, 4096));
		Provider.getDatacenter("C2", Provider.getHost(16, 2, 16 * 4096));
		
		User u1 = new User("mustafa");
		u1.addRequest(new Request(100, 5));
		u1.addRequest(new Request(200, 1));
		u1.addRequest(new Request(300, 2));
		u1.addRequest(new Request(350, 4));

		User u2 = new User("cem");
		u2.addRequest(new Request(10, 1));
		u2.addRequest(new Request(500, 1));

		User u3 = new User("ibrahim");
		u3.addRequest(new Request(1000, 10));

		CloudSim.startSimulation();
		
		// Get cloudlet list for each user

		Util.printHeader();
		Util.printCloudletList(u1);
		Util.printCloudletList(u2);
		Util.printCloudletList(u3);
		
		CloudSim.stopSimulation();


	}
}
