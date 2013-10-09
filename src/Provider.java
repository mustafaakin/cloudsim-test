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
	
	public static List<Cloudlet> createCloudlet(int userId, int cloudlets,
			int idShift) {
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
		long length = 40000;
		long fileSize = 300;
		long outputSize = 300;
		int pesNumber = 1;
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


	public static Datacenter getDatacenter(String name, Host... hosts) {
		LinkedList<Host> hostList = new LinkedList<Host>();
		for (Host h : hosts) {
			hostList.add(h);
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

	public static Host getHost(int cores, int hostId, int ram) {
		List<Pe> peList1 = new ArrayList<Pe>();

		int mips = 1000;

		for (int i = 0; i < cores; i++) {
			peList1.add(new Pe(i, new PeProvisionerSimple(mips)));
		}

		long storage = 1000000;
		int bw = 10000;

		Host h = (new Host(hostId, new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw), storage, peList1,
				new VmSchedulerTimeShared(peList1)));
		return h;
	}
}
