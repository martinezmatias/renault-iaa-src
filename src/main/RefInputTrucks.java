package main;

import java.util.HashMap;
import java.util.Map;


import model.InputTruck;
import tools.Constants;

public class RefInputTrucks {

	private Map<String, InputTruck> hmIdentTruck;
	
	
	/* constructor
	 * 
	 */
	public RefInputTrucks() {
		this.hmIdentTruck = new HashMap<String, InputTruck>();
	}
	
	/** add a new truck
	 * 
	 * @param truck
	 */
	public void add(InputTruck truck) {
		this.hmIdentTruck.put(truck.getId(), truck);
	}
	
	/** get a truck from its ident
	 * 
	 * @param ident
	 * @return
	 */
	public InputTruck get(Referential ref, String ident) {
		// ident = Pxxxxxxx or Qxxxxxxx_n
		String identInitial = ident;
		if (ref.getRefOutputTrucks().isExtraTruckId(ident)) {
			identInitial = Constants.PREFIX_PLANNED_TRUCK_ID + ident.substring(1, ident.indexOf("_"));
		}
		return this.hmIdentTruck.get(identInitial);
	}
	
	/** get the number of trucks
	 * 
	 * @return
	 */
	public int size() {
		return this.hmIdentTruck.size();
	}
}
