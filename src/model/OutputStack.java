package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OutputStack {

	private OutputTruck outputTruck;
	private String idStack; // stack key
	private String stackCodeDisplay;
	private int xOrigin;
	private int yOrigin;
	private int zOrigin;
	private int xExtremity;
	private int yExtremity; 
	private int zExtremity;
	
	private List<OutputItem> items;
	private Map<String, Integer> hmPlantDockLoadingOrder;
	private double weight;
	private double volume;
	
	/** constructor
	 * 
	 */
	public OutputStack() {
		this.items = new ArrayList<OutputItem>();
		this.hmPlantDockLoadingOrder = new HashMap<String, Integer>();
	}

	/** add a new item
	 * 
	 * @param item
	 */
	public void addItem(OutputItem item) {
		this.items.add(item);
		this.weight += item.getInputItem().getWeight();
		this.volume += item.getInputItem().getVolume();
		this.hmPlantDockLoadingOrder.put(item.getInputItem().getPlantDock(), 
				this.outputTruck.getInputTruck().getPlantDockLoadingOrder(item.getInputItem().getPlantDock()));
	}
	
	/** get the plant docks of the items of the stack
	 * 
	 * @return
	 */
	public Set<String> getPlantDocks() {
		return this.hmPlantDockLoadingOrder.keySet();
	}
	
	/** get the loading order of a plant dock
	 * 
	 * @param plantDock
	 * @return
	 */
	public int getPlantDockLoadingOrder(String plantDock) {
		return this.hmPlantDockLoadingOrder.get(plantDock);
	}
	
	/** get the supplier of the stack (all items share the same supplier)
	 * 
	 */
	public String getSupplier() {
		if (this.getItems().size() == 0) return "stack empty";
		
		return this.getItems().get(0).getInputItem().getSupplier();
	}
	
	/** get the supplier dock of the stack (all items share the same supplier)
	 * 
	 */
	public String getSupplierDock() {
		if (this.getItems().size() == 0) return "";
		
		return this.getItems().get(0).getInputItem().getSupplierDock();
	}
	
	/** get the loading order of the supplier dock
	 * 
	 * @return
	 */
	public int getSupplierDockLoadingOrder() {
		return this.outputTruck.getInputTruck().getSupplierDockLoadingOrder(this.getSupplierDock());
	}
	
	
	/**
	 * @return the outputTruck
	 */
	public OutputTruck getOutputTruck() {
		return outputTruck;
	}

	/**
	 * @param outputTruck the outputTruck to set
	 */
	public void setOutputTruck(OutputTruck outputTruck) {
		this.outputTruck = outputTruck;
		outputTruck.addStack(this);
	}

	/**
	 * @return the idStack
	 */
	public String getId() {
		return idStack;
	}

	/**
	 * @param idStack the idStack to set
	 */
	public void setIdStack(String idStack) {
		this.idStack = idStack;
	}

	/**
	 * @return the stackCodeDisplay
	 */
	public String getStackCodeDisplay() {
		return stackCodeDisplay;
	}

	/**
	 * @param stackCodeDisplay the stackCodeDisplay to set
	 */
	public void setStackCodeDisplay(String stackCodeDisplay) {
		this.stackCodeDisplay = stackCodeDisplay;
	}

	/**
	 * @return the xOrigin
	 */
	public int getxOrigin() {
		return xOrigin;
	}

	/**
	 * @param xOrigin the xOrigin to set
	 */
	public void setxOrigin(int xOrigin) {
		this.xOrigin = xOrigin;
	}

	/**
	 * @return the yOrigin
	 */
	public int getyOrigin() {
		return yOrigin;
	}

	/**
	 * @param yOrigin the yOrigin to set
	 */
	public void setyOrigin(int yOrigin) {
		this.yOrigin = yOrigin;
	}

	/**
	 * @return the zOrigin
	 */
	public int getzOrigin() {
		return zOrigin;
	}

	/**
	 * @param zOrigin the zOrigin to set
	 */
	public void setzOrigin(int zOrigin) {
		this.zOrigin = zOrigin;
	}

	/**
	 * @return the xExtremity
	 */
	public int getxExtremity() {
		return xExtremity;
	}

	/**
	 * @param xExtremity the xExtremity to set
	 */
	public void setxExtremity(int xExtremity) {
		this.xExtremity = xExtremity;
	}

	/**
	 * @return the yExtremity
	 */
	public int getyExtremity() {
		return yExtremity;
	}

	/**
	 * @param yExtremity the yExtremity to set
	 */
	public void setyExtremity(int yExtremity) {
		this.yExtremity = yExtremity;
	}

	/**
	 * @return the zExtremity
	 */
	public int getzExtremity() {
		return zExtremity;
	}

	/**
	 * @param zExtremity the zExtremity to set
	 */
	public void setzExtremity(int zExtremity) {
		this.zExtremity = zExtremity;
	}

	/**
	 * @return the items
	 */
	public List<OutputItem> getItems() {
		return items;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return the volume
	 */
	public double getVolume() {
		return volume;
	}

	/** get the x coordinate of the center of gravity of the stack
	 * 
	 * @return
	 */
	public double getxGravityCenter() {
		return (double) this.xOrigin + ((double) this.xExtremity - this.xOrigin) / 2.0;
	}
	
	/** get the y coordinate of the center of gravity of the stack
	 * 
	 * @return
	 */
	public double getyGravityCenter() {
		return (double) this.yOrigin + ((double) this.yExtremity - this.yOrigin) / 2.0;
	}

}
