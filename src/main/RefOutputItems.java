package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.renault.dates.Day;

import model.OutputItem;

public class RefOutputItems {

	private Map<String, Integer> hmIdentItems; // key = item id - value = nb items
	private List<OutputItem> items;
	
	private double inventoryCost;
	
	/** constructor
	 * 
	 */
	public RefOutputItems() {
		this.hmIdentItems = new HashMap<String, Integer>();
		this.items = new ArrayList<OutputItem>();
	}

	/** add a new output item
	 * 
	 * @param item
	 */
	public void add(OutputItem item) {
		Integer nbItems = this.hmIdentItems.get(item.getId());
		if (nbItems == null) nbItems = 0;
		nbItems++;
		this.hmIdentItems.put(item.getId(), nbItems);
		
		this.items.add(item);
	}
	
	/** get the number of items for an item id
	 * 
	 * @return
	 */
	public int getNbOutputItems(String ident) {
		Integer nbItems = this.hmIdentItems.get(ident);
		return (nbItems == null) ?0 :nbItems;
	}
	
	/** get all the items
	 * 
	 * @return
	 */
	public List<OutputItem> getAll() {
		return this.items;
	}
	
	/** get the number of output items
	 * 
	 * @return
	 */
	public int size() {
		return this.items.size();
	}

	
	/**
	 * @return the inventoryCost
	 */
	public double getInventoryCost() {
		return inventoryCost;
	}

	/** calculate the inventory cost
	 * 
	 * @param ref
	 */
	public void calculateInventoryCost(Referential ref) {
		this.inventoryCost = 0.0;
		
		for(OutputItem item : this.items) {
			Day arrivalTime = new Day(item.getOutputTruck().getInputTruck().getArrivalTime().substring(0, 8));
			Day latestArrivalTime = new Day(item.getInputItem().getLatestArrivalTime().substring(0, 8));
			int diffDays = arrivalTime.nbUnits(latestArrivalTime) - 1;
			this.inventoryCost += item.getInputItem().getInventoryCost()*diffDays;
		}
	}
}


