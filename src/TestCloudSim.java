import java.util.Calendar;

import org.cloudbus.cloudsim.core.CloudSim;

public class TestCloudSim {

	public static void main(String[] args) throws Exception {
		int num_user = 1;
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false;

		CloudSim.init(num_user, calendar, trace_flag);

		Provider.getDatacenter("PC", Provider.getHost(2, 0, 2048));
		Provider.getDatacenter("Server", Provider.getHost(8, 0, 16384));
		Provider.getDatacenter("SuperPC", Provider.getHost(64, 0, 768 * 1024),
				Provider.getHost(64, 1, 768 * 1024));

		VmParams v1 = new VmParams();
		v1.cores = 2;
		v1.ram = 512;
		v1.speed = 10;
		v1.work = 50000;
		
		User u1 = new User("mustafa");
		u1.addRequest(new Request(0, v1, 4));
		u1.addRequest(new Request(100,v1,5));

		User u2 = new User("cem");
		u2.addRequest(new Request(10, v1, 1));
		u2.addRequest(new Request(500,v1, 1));

		User u3 = new User("ibrahim");
		u3.addRequest(new Request(83,v1, 5));

		CloudSim.startSimulation();

		// Get cloudlet list for each user
		Util.printHeader();
		Util.printCloudletList(u1);
		Util.printCloudletList(u2);
		Util.printCloudletList(u3);

		CloudSim.stopSimulation();
	}
}
