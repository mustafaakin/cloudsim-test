import java.util.Calendar;

import org.cloudbus.cloudsim.core.CloudSim;

public class TestCloudSim {

	public static void main(String[] args) throws Exception {
		int num_user = 1;
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false;

		CloudSim.init(num_user, calendar, trace_flag);

		HostParams h1 = new HostParams();
		h1.cores = 4;
		h1.ram = 8192;
		h1.speed = 5000;

		HostParams h2 = new HostParams();
		h2.cores = 1;
		h2.ram = 512;
		h2.speed = 500;

		HostParams h3 = new HostParams();
		h3.cores = 16;
		h3.ram = 32768;
		h3.speed = 15000;

		Provider.getDatacenter("Ankara", h1, h1);
		Provider.getDatacenter("Istanbul", h2);
		Provider.getDatacenter("Izmır", h3, h3);

		VmParams v1 = new VmParams();
		v1.cores = 2;
		v1.ram = 512;
		v1.speed = 100;
		v1.work = 50000;

		VmParams v2 = new VmParams();
		v2.cores = 4;
		v2.ram = 4096;
		v2.speed = 400;
		v2.work = 1000000;

		User u1 = new User("mustafa");
		u1.addRequest(new Request(0, v1, 4));
		u1.addRequest(new Request(100, v1, 5));

		User u2 = new User("cem");
		u2.addRequest(new Request(10, v2, 1));
		u2.addRequest(new Request(500, v2, 1));

		User u3 = new User("ibrahim");
		u3.addRequest(new Request(83, v2, 10));

		CloudSim.startSimulation();

		// Get cloudlet list for each user
		Util.printHeader();
		Util.printCloudletList(u1);
		Util.printCloudletList(u2);
		Util.printCloudletList(u3);
		
		

		CloudSim.stopSimulation();
	}
}
