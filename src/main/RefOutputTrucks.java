package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.OutputTruck;
import tools.Constants;

public class RefOutputTrucks {

	private Map<String, OutputTruck> hmIdentTruck;
	private double transportationCost;

	/** constructor
	 * 
	 */
	public RefOutputTrucks() {
		this.hmIdentTruck = new HashMap<String, OutputTruck>();
	}

	/** add a new truck
	 * 
	 * @param stack
	 */
	public void add(OutputTruck truck) {
		this.hmIdentTruck.put(truck.getId(), truck);
	}
	
	/** calculate the fixed cost of used trucks (planned and extra)
	 * 
	 */
	public void calculateTransportationCost(Referential ref) {
		this.transportationCost = 0.0;
		
		for(OutputTruck truck : this.hmIdentTruck.values()) {
			this.transportationCost += (this.isExtraTruckId(truck.getId()) ?truck.getInputTruck().getCost()*(1.0 + ref.getCoeffCostExtraTruck())
										:truck.getInputTruck().getCost());
		}
	}
	
	
	/**
	 * @return the transportationCost
	 */
	public double getTransportationCost() {
		return transportationCost;
	}

	/** checks wether a truckId is from extra trucks
	 * 
	 * @param truckId
	 * @return
	 */
	public boolean isExtraTruckId(String truckId) {
		return truckId.charAt(0) == Constants.PREFIX_EXTRA_TRUCK_ID;
	}
	
	/** get a truck from its id
	 * 
	 * @param truckId
	 * @return
	 */
	public OutputTruck get(String truckId) {
		return this.hmIdentTruck.get(truckId);
	}
	
	/** get all the trucks
	 * 
	 * @return
	 */
	public Collection<OutputTruck> getAll() {
		return this.hmIdentTruck.values();
	}

	/** get the number of output trucks
	 * 
	 * @return
	 */
	public int size() {
		return this.hmIdentTruck.size();
	}
}
