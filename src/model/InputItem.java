package model;

import java.util.Objects;

public class InputItem {

	/* item key
	 * 
	 */
	private String ident;
    private String supplier;
    private String plant;
    private String product;
    private String earliestArrivalTime;
    private String latestArrivalTime;
	
    
    private String supplierDock;
   private String plantDock;
    private String packaging;
    private int nbItems;
    private int length;
    private int width;
    private int height;
    private double weight;
    private int nestingHeight;
    private String stackabilityCode;
    private String forcedOrientation;
    private int inventoryCost;
    private int maxStackability;
    
    private double volume = 0.0;
    
    /** constructor
     * 
     */
	public InputItem() {
		super();
	}

	/** get the volume of the item
	 * 
	 * @return
	 */
	public double getVolume() {
		if (this.volume == 0.0)
			this.volume =  ((double)this.width * (double)this.length * (double)this.height) / (1000.0 * 1000.0 * 1000.0);	
		return this.volume;
	}
	
	/**
	 * @return the ident
	 */
	public String getId() {
		return ident;
	}


	/**
	 * @param ident the ident to set
	 */
	public void setIdent(String ident) {
		this.ident = ident;
	}


	/**
	 * @return the supplier
	 */
	public String getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier the supplier to set
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return the supplierDock
	 */
	public String getSupplierDock() {
		return supplierDock;
	}

	/**
	 * @param supplierDock the supplierDock to set
	 */
	public void setSupplierDock(String supplierDock) {
		this.supplierDock = supplierDock;
	}

	/**
	 * @return the plant
	 */
	public String getPlant() {
		return plant;
	}

	/**
	 * @param plant the plant to set
	 */
	public void setPlant(String plant) {
		this.plant = plant;
	}

	/**
	 * @return the plantDock
	 */
	public String getPlantDock() {
		return plantDock;
	}

	/**
	 * @param plantDock the plantDock to set
	 */
	public void setPlantDock(String plantDock) {
		this.plantDock = plantDock;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the packaging
	 */
	public String getPackaging() {
		return packaging;
	}

	/**
	 * @param packaging the packaging to set
	 */
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	/**
	 * @return the nbItems
	 */
	public int getNbItems() {
		return nbItems;
	}

	/**
	 * @param nbItems the nbItems to set
	 */
	public void setNbItems(int nbItems) {
		this.nbItems = nbItems;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the nestingHeight
	 */
	public int getNestingHeight() {
		return nestingHeight;
	}

	/**
	 * @param nestingHeight the nestingHeight to set
	 */
	public void setNestingHeight(int nestingHeight) {
		this.nestingHeight = nestingHeight;
	}

	/**
	 * @return the stackabilityCode
	 */
	public String getStackabilityCode() {
		return stackabilityCode;
	}

	/**
	 * @param stackabilityCode the stackabilityCode to set
	 */
	public void setStackabilityCode(String stackabilityCode) {
		this.stackabilityCode = stackabilityCode;
	}

	/**
	 * @return the forcedOrientation
	 */
	public String getForcedOrientation() {
		return forcedOrientation;
	}

	/**
	 * @param forcedOrientation the forcedOrientation to set
	 */
	public void setForcedOrientation(String forcedOrientation) {
		this.forcedOrientation = forcedOrientation;
	}

	/**
	 * @return the earliestArrivalTime
	 */
	public String getEarliestArrivalTime() {
		return earliestArrivalTime;
	}

	/**
	 * @param earliestArrivalTime the earliestArrivalTime to set
	 */
	public void setEarliestArrivalTime(String earliestArrivalTime) {
		this.earliestArrivalTime = earliestArrivalTime;
	}

	/**
	 * @return the latestArrivalTime
	 */
	public String getLatestArrivalTime() {
		return latestArrivalTime;
	}

	/**
	 * @param latestArrivalTime the latestArrivalTime to set
	 */
	public void setLatestArrivalTime(String latestArrivalTime) {
		this.latestArrivalTime = latestArrivalTime;
	}

	/**
	 * @return the inventoryCost
	 */
	public int getInventoryCost() {
		return inventoryCost;
	}

	/**
	 * @param inventoryCost the inventoryCost to set
	 */
	public void setInventoryCost(int inventoryCost) {
		this.inventoryCost = inventoryCost;
	}

	/**
	 * @return the maxStackability
	 */
	public int getMaxStackability() {
		return maxStackability;
	}

	/**
	 * @param maxStackability the maxStackability to set
	 */
	public void setMaxStackability(int maxStackability) {
		this.maxStackability = maxStackability;
	}

	@Override
	public int hashCode() {
		return Objects.hash(earliestArrivalTime, latestArrivalTime, plant, product, supplier);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InputItem other = (InputItem) obj;
		return earliestArrivalTime == other.earliestArrivalTime && latestArrivalTime == other.latestArrivalTime
				&& Objects.equals(plant, other.plant) && Objects.equals(product, other.product)
				&& Objects.equals(supplier, other.supplier);
	}

	
}
