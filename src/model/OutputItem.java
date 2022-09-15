package model;

public class OutputItem {

	private InputItem inputItem;
    
	private OutputTruck outputTruck;
	private OutputStack outputStack; 
	private String itemCodeDisplay;
	private int xOrigin;
	private int yOrigin;
	private int zOrigin;
	private int xExtremity;
	private int yExtremity; 
	private int zExtremity;
	
	/** constructor
	 * 
	 */
	public OutputItem() {
		super();
	}


	/**
	 * @return the inputItem
	 */
	public InputItem getInputItem() {
		return inputItem;
	}


	/**
	 * @param inputItem the inputItem to set
	 */
	public void setInputItem(InputItem inputItem) {
		this.inputItem = inputItem;
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
	}

	/**
	 * @return the itemCodeDisplay
	 */
	public String getItemCodeDisplay() {
		return itemCodeDisplay;
	}

	/**
	 * @param itemCodeDisplay the itemCodeDisplay to set
	 */
	public void setItemCodeDisplay(String itemCodeDisplay) {
		this.itemCodeDisplay = itemCodeDisplay;
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
	 * @return the outputStack
	 */
	public OutputStack getOutputStack() {
		return outputStack;
	}


	/**
	 * @param outputStack the outputStack to set
	 */
	public void setOutputStack(OutputStack outputStack) {
		this.outputStack = outputStack;
		outputStack.addItem(this);
	}

	/** get the ident of the item
	 * 
	 * @return
	 */
	public String getId() {
		return this.inputItem.getId();
	}

	
}
