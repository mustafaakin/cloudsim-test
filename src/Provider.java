import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class Provider {
	public static Map<Integer, String> datacenterNames = new HashMap<Integer,String>();
	
	public static String getDatacenterName(int id) {
		return datacenterNames.get(id);
	}
	
	public static List<Cloudlet> createCloudlet(Request req, int userId, int cloudlets,
			int idShift) {
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
		long length = req.getParams().work;
		long fileSize = 0;
		long outputSize = 0;
		int pesNumber = req.getParams().cores;
		UtilizationModel utilizationModel = new UtilizationModelFull();
		Cloudlet[] cloudlet = new Cloudlet[cloudlets];
		for (int i = 0; i < cloudlets; i++) {
			cloudlet[i] = new Cloudlet(idShift + i, length, pesNumber,
					fileSize, outputSize, utilizationModel, utilizationModel,
					utilizationModel);
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
		}
		return list;
	}


	public static Datacenter getDatacenter(String name, HostParams... hostParamss) {
		LinkedList<Host> hostList = new LinkedList<Host>();
		int i = 0;
		for (HostParams h : hostParamss) {			
			hostList.add(getHost(i++, h));
		}

		String arch = "x86";
		String os = "Linux";
		String vmm = "Xen";
		double time_zone = 10.0;
		double cost = 3.0;
		double costPerMem = 0.05;
		double costPerStorage = 0.1;
		double costPerBw = 0.1;
		LinkedList<Storage> storageList = new LinkedList<Storage>();

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch, os, vmm, hostList, time_zone, cost, costPerMem,
				costPerStorage, costPerBw);

		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics,
					new VmAllocationPolicySimple(hostList), storageList, 0);
			datacenterNames.put(datacenter.getId(),name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	public static Host getHost(int hostId, HostParams params) {
		List<Pe> peList1 = new ArrayList<Pe>();

		int mips = params.speed;

		for (int i = 0; i < params.cores; i++) {
			peList1.add(new Pe(i, new PeProvisionerSimple(mips)));
		}

		long storage = 100000000;
		int bw = 1000000;

		Host h = (new Host(hostId, new RamProvisionerSimple(params.ram),
				new BwProvisionerSimple(bw), storage, peList1,
				new VmSchedulerTimeShared(peList1)));
		return h;
	}
}
