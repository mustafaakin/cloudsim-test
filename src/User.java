import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;

public class User extends SimEntity {
	public static final int CLOUDLET_SHIFT_COUNT = 1000;

	private static final int NEW_REQ = 0;

	private ArrayList<DatacenterBroker> brokers = new ArrayList<DatacenterBroker>();
	private LinkedList<Cloudlet> cloudletList = new LinkedList<Cloudlet>();

	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private ArrayList<Request> reqs = new ArrayList<Request>();

	private LinkedList<Vm> vmList = new LinkedList<Vm>();

	private int lastId = getId() * CLOUDLET_SHIFT_COUNT;

	public User(String name) {
		super(name);
	}

	public void addRequest(Request r) {
		reqs.add(r);
	}

	public List<Cloudlet> getCloudletList() {
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();
		for (DatacenterBroker b : brokers) {
			list.addAll(b.getCloudletSubmittedList());
		}
		return list;
	}

	public ArrayList<Integer> getIds() {
		return ids;
	}

	public List<Vm> getVmList() {
		return vmList;
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

				List<Vm> vmList = r.getVms(lastId, broker.getId());
				List<Cloudlet> cloudletList = Provider.createCloudlet(
						broker.getId(), r.getVmCount(), lastId);

				broker.submitCloudletList(cloudletList);
				broker.submitVmList(vmList);

				this.vmList.addAll(vmList);
				this.cloudletList.addAll(cloudletList);

				brokers.add(idx, broker);

				lastId = lastId + r.getVmCount();

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			break;
		default:

		}
		CloudSim.resumeSimulation();
	}

	@Override
	public void shutdownEntity() {
		// TODO Auto-generated method stub
	}

	@Override
	public void startEntity() {
		for (int i = 0; i < reqs.size(); i++) {
			Request r = reqs.get(i);
			schedule(getId(), r.getTime(), NEW_REQ, i);
		}
	}
}
