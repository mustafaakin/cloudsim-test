import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;

public class User extends SimEntity {
	private static final int NEW_REQ = 0;
	private ArrayList<Request> reqs = new ArrayList<Request>();
	private ArrayList<DatacenterBroker> brokers = new ArrayList<DatacenterBroker>();

	private LinkedList<Vm> vmList = new LinkedList<Vm>();
	private LinkedList<Cloudlet> cloudletList = new LinkedList<Cloudlet>();

	private ArrayList<Integer> ids = new ArrayList<Integer>();
	
	public User(String name) {
		super(name);
	}

	public void addRequest(Request r) {
		reqs.add(r);
	}
	
	public ArrayList<Integer> getIds() {
		return ids;
	}
	@Override
	public void processEvent(SimEvent ev) {
		switch (ev.getTag()) {
		case NEW_REQ:
			int idx = (Integer) ev.getData();
			Request r = reqs.get(idx);

			try {
				DatacenterBroker broker = null;
				broker = new DatacenterBroker(getName() + "-broker-" + idx);
				ids.add(broker.getId());
				
				List<Vm> vmList = Provider.createVM(broker.getId(),
						r.getVmCount(), idx * 100);
				List<Cloudlet> cloudletList = Provider.createCloudlet(
						broker.getId(), r.getCloudletCount(), idx * 100);

				broker.submitCloudletList(cloudletList);
				broker.submitVmList(vmList);

				this.vmList.addAll(vmList);
				this.cloudletList.addAll(cloudletList);

				brokers.add(idx, broker);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:

		}
		CloudSim.resumeSimulation();
	}

	@Override
	public void startEntity() {
		for (int i = 0; i < reqs.size(); i++) {
			Request r = reqs.get(i);
			schedule(getId(), r.getTime(), NEW_REQ, i);
		}
	}

	@Override
	public void shutdownEntity() {
		// TODO Auto-generated method stub
	}

	public List<Cloudlet> getCloudletList() {
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
		for (DatacenterBroker b : brokers) {
			list.addAll(b.getCloudletSubmittedList());
		}
		return list;
	}

	public List<Vm> getVmList() {
		return vmList;
	}
}
