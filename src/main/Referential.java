package main;

import model.Checker;

public class Referential {

	private RefInputItems refInputItems;
	private RefInputTrucks refInputTrucks;
	
	private RefOutputItems refOutputItems;
	private RefOutputStacks refOutputStacks;
	private RefOutputTrucks refOutputTrucks;
	
	private RefAnos refAnos;

	private Checker checker;
	
	// parameters
	private double coeffInventoryCost;
	private double coeffTransportationCost;
	private double coeffCostExtraTruck;
	
	/** constructor
	 * 
	 */
	public Referential() {
		this.refInputItems = new RefInputItems();
		this.refInputTrucks = new RefInputTrucks();
		
		this.refOutputItems = new RefOutputItems();
		this.refOutputStacks = new RefOutputStacks();
		this.refOutputTrucks = new RefOutputTrucks();
		
		this.refAnos = new RefAnos();
		
		this.checker = new Checker(this);
	}
	
	/**
	 * @return the coeffInventoryCost
	 */
	public double getCoeffInventoryCost() {
		return coeffInventoryCost;
	}

	/**
	 * @param coeffInventoryCost the coeffInventoryCost to set
	 */
	public void setCoeffInventoryCost(double coeffInventoryCost) {
		this.coeffInventoryCost = coeffInventoryCost;
	}

	/**
	 * @return the coeffTransportationCost
	 */
	public double getCoeffTransportationCost() {
		return coeffTransportationCost;
	}

	/**
	 * @param coeffTransportationCost the coeffTransportationCost to set
	 */
	public void setCoeffTransportationCost(double coeffTransportationCost) {
		this.coeffTransportationCost = coeffTransportationCost;
	}

	/**
	 * @return the coeffCostExtraTruck
	 */
	public double getCoeffCostExtraTruck() {
		return coeffCostExtraTruck;
	}

	/**
	 * @param coeffCostExtraTruck the coeffCostExtraTruck to set
	 */
	public void setCoeffCostExtraTruck(double coeffCostExtraTruck) {
		this.coeffCostExtraTruck = coeffCostExtraTruck;
	}

	
	/**
	 * @return the checker
	 */
	public Checker getChecker() {
		return checker;
	}

	/**
	 * @return the refInputItems
	 */
	public RefInputItems getRefInputItems() {
		return refInputItems;
	}

	/**
	 * @return the refInputTrucks
	 */
	public RefInputTrucks getRefInputTrucks() {
		return refInputTrucks;
	}

	/**
	 * @return the refOutputItems
	 */
	public RefOutputItems getRefOutputItems() {
		return refOutputItems;
	}

	/**
	 * @return the refOutputStacks
	 */
	public RefOutputStacks getRefOutputStacks() {
		return refOutputStacks;
	}

	/**
	 * @return the refOutputTrucks
	 */
	public RefOutputTrucks getRefOutputTrucks() {
		return refOutputTrucks;
	}

	/**
	 * @return the refAnos
	 */
	public RefAnos getRefAnos() {
		return refAnos;
	}

}
