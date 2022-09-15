package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InputTruck {

	private String ident;
	private int length;
	private int width;
	private int height;
	private int maxWeight;
	private int cost;
	private String arrivalTime;
	
    private boolean stackWithMultipleDocks;
    private int maxStackDensity;
    
    private Map<String, Integer> hmProductMaxWeightAboveBottomItemInStacks; // key = product code, value = max weigth above the bottom item if bottom item contains the product code

	private double middleAxleMaxWeight;
	private double rearAxleMaxWeight;
	private double weightTractor;
	private int distanceFrontMidleAxlesTractor;
	private int distanceFrontAxleCenterGravityTractor;
	private int distanceFrontAxleHarnessTractor;
	private double weightEmptyTrailer;
	private int distanceHarnessRearAxleTrailer;
	private int distanceTrailerCenterGravityRearAxle;
	private int distanceEndTrailerHarness;
	
	private Map<String, Integer> hmSupplierLoadingOrder;
	private Map<String, Integer> hmSupplierDockLoadingOrder;
	private Map<String, Integer> hmPlantDockLoadingOrder;
	
	private String plant;
	private Set<String> hsProducts;
	
	
	/** constructor
	 * 
	 */
	public InputTruck(String ident) {
		this.ident = ident;
		
		this.hmSupplierLoadingOrder = new HashMap<String, Integer>();
		this.hmSupplierDockLoadingOrder = new HashMap<String, Integer>();
		this.hmPlantDockLoadingOrder = new HashMap<String, Integer>();
		
		this.hsProducts = new HashSet<String>();
		this.hmProductMaxWeightAboveBottomItemInStacks = new HashMap<String, Integer>();
	}
	
	/** add a new product picked up by the truck.
	 * 
	 * @param product
	 */
	public void addProduct(String product) {
		this.hsProducts.add(product);
	}
	
	/** add a new supplier and its loading order
	 * 
	 * @param supplier
	 * @param loadingOrder
	 */
	public void addSupplierAndLoadingOrder(String supplier, int loadingOrder) {
		this.hmSupplierLoadingOrder.put(supplier, loadingOrder);
	}
	
	/** add a new supplier dock and its loading order
	 * 
	 * @param supplierDock
	 * @param loadingOrder
	 */
	public void addSupplierDockAndLoadingOrder(String supplierDock, int loadingOrder) {
		this.hmSupplierDockLoadingOrder.put(supplierDock, loadingOrder);
	}
	
	/** add a new plant dock and its loading order
	 * 
	 * @param plantDock
	 * @param loadingOrder
	 */
	public void addPlantDockAndLoadingOrder(String plantDock, int loadingOrder) {
		this.hmPlantDockLoadingOrder.put(plantDock, loadingOrder);
	}
	
	/** get the products picked up
	 * 
	 * @return
	 */
	public Set<String> getProducts() {
		return this.hsProducts;
	}
	
	/** get the suppliers picked up
	 * 
	 * @return
	 */
	public Set<String> getSuppliers() {
		return this.hmSupplierLoadingOrder.keySet();
	}
	
	/** checks wether the truck picks-up the product
	 * 
	 * @param product
	 * @return
	 */
	public boolean pickupProduct(String product) {
		return this.hsProducts.contains(product);
	}
	
	/** checks wether the truck picks-up a supplier
	 * 
	 * @param supplier
	 * @return
	 */
	public boolean pickupSupplier(String supplier) {
		return this.hmSupplierLoadingOrder.containsKey(supplier);
	}
	
	/** checks wether the truck picks-up a supplier dock
	 * 
	 * @param supplierDock
	 * @return
	 */
	public boolean pickupSupplierDock(String supplierDock) {
		return this.hmSupplierDockLoadingOrder.containsKey(supplierDock);
	}
	
	/** checks wether the truck delivers a plant dock
	 * 
	 * @param plantDock
	 * @return
	 */
	public boolean deliverPlantDock(String plantDock) {
		return this.hmPlantDockLoadingOrder.containsKey(plantDock);
	}
	
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @param middleAxleMaxWeight the middleAxleMaxWeight to set
	 */
	public void setMiddleAxleMaxWeight(double middleAxleMaxWeight) {
		this.middleAxleMaxWeight = middleAxleMaxWeight;
	}

	/**
	 * @param rearAxleMaxWeight the rearAxleMaxWeight to set
	 */
	public void setRearAxleMaxWeight(double rearAxleMaxWeight) {
		this.rearAxleMaxWeight = rearAxleMaxWeight;
	}

	/**
	 * @param weightTractor the weightTractor to set
	 */
	public void setWeightTractor(double weightTractor) {
		this.weightTractor = weightTractor;
	}

	/**
	 * @param distanceFrontMidleAxles the distanceFrontMidleAxles to set
	 */
	public void setDistanceFrontMidleAxles(int distanceFrontMidleAxles) {
		this.distanceFrontMidleAxlesTractor = distanceFrontMidleAxles;
	}

	/**
	 * @param distanceFrontAxleCenterGravityTractor the distanceFrontAxleCenterGravityTractor to set
	 */
	public void setDistanceFrontAxleCenterGravityTractor(int distanceFrontAxleCenterGravityTractor) {
		this.distanceFrontAxleCenterGravityTractor = distanceFrontAxleCenterGravityTractor;
	}

	/**
	 * @param distanceFrontAxleHarnessTractor the distanceFrontAxleHarnessTractor to set
	 */
	public void setDistanceFrontAxleHarnessTractor(int distanceFrontAxleHarnessTractor) {
		this.distanceFrontAxleHarnessTractor = distanceFrontAxleHarnessTractor;
	}

	/**
	 * @param weightEmptyTrailer the weightEmptyTrailer to set
	 */
	public void setWeightEmptyTrailer(double weightEmptyTrailer) {
		this.weightEmptyTrailer = weightEmptyTrailer;
	}

	/**
	 * @param distanceHarnessRearAxleTrailer the distanceHarnessRearAxleTrailer to set
	 */
	public void setDistanceHarnessRearAxleTrailer(int distanceHarnessRearAxleTrailer) {
		this.distanceHarnessRearAxleTrailer = distanceHarnessRearAxleTrailer;
	}

	/**
	 * @param distanceTrailerCenterGravityRearAxle the distanceTrailerCenterGravityRearAxle to set
	 */
	public void setDistanceTrailerCenterGravityRearAxle(int distanceTrailerCenterGravityRearAxle) {
		this.distanceTrailerCenterGravityRearAxle = distanceTrailerCenterGravityRearAxle;
	}

	/**
	 * @param distanceEndTrailerHarness the distanceEndTrailerHarness to set
	 */
	public void setDistanceEndTrailerHarness(int distanceEndTrailerHarness) {
		this.distanceEndTrailerHarness = distanceEndTrailerHarness;
	}

	/**
	 * @param plant the plant to set
	 */
	public void setPlant(String plant) {
		this.plant = plant;
	}

	/** gets the loading order of a supplier, NULL if the supplier is not picked-up by the truck
	 * 
	 * @param supplier
	 * @return
	 */
	public int getSupplierLoadingOrder(String supplier) {
		return this.hmSupplierLoadingOrder.get(supplier);
	}

	/** gets the loading order of a supplier dock, NULL if the supplier dock is not picked-up by the truck
	 * 
	 * @param supplierDock
	 * @return
	 */
	public int getSupplierDockLoadingOrder(String supplierDock) {
		return this.hmSupplierDockLoadingOrder.get(supplierDock);
	}
	
	/** gets the loading order of a plant dock, NULL if the plant dock is not delivered by the truck
	 * 
	 * @param plantDock
	 * @return
	 */
	public int getPlantDockLoadingOrder(String plantDock) {
		return this.hmPlantDockLoadingOrder.get(plantDock);
	}
	
	/**
	 * @return the ident
	 */
	public String getId() {
		return ident;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the maxWeight
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @return the middleAxleMaxWeight
	 */
	public double getMiddleAxleMaxWeight() {
		return middleAxleMaxWeight;
	}

	/**
	 * @return the rearAxleMaxWeight
	 */
	public double getRearAxleMaxWeight() {
		return rearAxleMaxWeight;
	}

	/**
	 * @return the weightTractor
	 */
	public double getWeightTractor() {
		return weightTractor;
	}

	/**
	 * @return the distanceFrontMidleAxles
	 */
	public int getDistanceFrontMidleAxlesTractor() {
		return distanceFrontMidleAxlesTractor;
	}

	/**
	 * @return the distanceFrontAxleCenterGravityTractor
	 */
	public int getDistanceFrontAxleCenterGravityTractor() {
		return distanceFrontAxleCenterGravityTractor;
	}

	/**
	 * @return the distanceFrontAxleHarnessTractor
	 */
	public int getDistanceFrontAxleHarnessTractor() {
		return distanceFrontAxleHarnessTractor;
	}

	/**
	 * @return the weightEmptyTrailer
	 */
	public double getWeightEmptyTrailer() {
		return weightEmptyTrailer;
	}

	/**
	 * @return the distanceHarnessRearAxleTrailer
	 */
	public int getDistanceHarnessRearAxleTrailer() {
		return distanceHarnessRearAxleTrailer;
	}

	/**
	 * @return the distanceTrailerCenterGravityRearAxle
	 */
	public int getDistanceTrailerCenterGravityRearAxle() {
		return distanceTrailerCenterGravityRearAxle;
	}

	/**
	 * @return the distanceEndTrailerHarness
	 */
	public int getDistanceEndTrailerHarness() {
		return distanceEndTrailerHarness;
	}

	/**
	 * @return the plant
	 */
	public String getPlant() {
		return plant;
	}

	/**
	 * @return the stackWithMultipleDocks
	 */
	public boolean isStackWithMultipleDocks() {
		return stackWithMultipleDocks;
	}

	/**
	 * @param stackWithMultipleDocks the stackWithMultipleDocks to set
	 */
	public void setStackWithMultipleDocks(boolean stackWithMultipleDocks) {
		this.stackWithMultipleDocks = stackWithMultipleDocks;
	}

	/**
	 * @return the maxStackDensity
	 */
	public int getMaxStackDensity() {
		return maxStackDensity;
	}

	/**
	 * @param maxStackDensity the maxStackDensity to set
	 */
	public void setMaxStackDensity(int maxStackDensity) {
		this.maxStackDensity = maxStackDensity;
	}

	/**
	 * @return the maxWeightAboveBottomItemInStacks
	 */
	public Integer getMaxWeightAboveBottomItemInStacks(String productCode) {
		return this.hmProductMaxWeightAboveBottomItemInStacks.get(productCode);
	}

	/**
	 * @param maxWeightAboveBottomItemInStacks the maxWeightAboveBottomItemInStacks to set
	 */
	public void setMaxWeightAboveBottomItemInStacks(String productCode, int maxWeightAboveBottomItemInStacks) {
		this.hmProductMaxWeightAboveBottomItemInStacks.put(productCode, maxWeightAboveBottomItemInStacks);
	}
	
	
}
