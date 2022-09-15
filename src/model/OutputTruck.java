package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OutputTruck {

	private InputTruck inputTruck;

	private String ident; // ident <> inputTruck.ident if it is an extra truck
	private boolean extraTruck;
	private int loadedLength;
	private double loadedWeight;
	private double loadedVolume;
	private double weightOnMiddleAxleTrailer;
	private double weightOnRearAxleTrailer;	
	
	/* calculations with all suppliers
	 * 
	 */
	private double distanceCenterGravityStacksEndTrailer;
	private double distanceCenterGravityStacksRearAxleTrailer;
	private double weightOnHarnessTrailer;

	private double calculatedLoadedWeight = 0.0;
	private double calculatedLoadedVolume = 0.0;
	private int calculatedLoadedLength = 0;
	
	private double calculatedWeightOnMiddleAxleTrailer;
	private double calculatedWeightOnRearAxleTrailer;	

	private List<OutputStack> stacks;
	private Set<Integer> hsSupplierLoadingOrderInStack;
	private Set<String> hsSuppliersInStack;
	
	/** constructor
	 * 
	 */
	public OutputTruck() {
		this.stacks = new ArrayList<OutputStack>();
		this.hsSupplierLoadingOrderInStack = new TreeSet<Integer>();
		this.hsSuppliersInStack = new HashSet<String>();
	}

	/** add a new stack
	 * 
	 */
	public void addStack(OutputStack stack)  {
		this.stacks.add(stack);
		
		if (stack.getxExtremity() > this.calculatedLoadedLength) {
			this.calculatedLoadedLength = stack.getxExtremity();	
		}
	}
	
	/** add a loaded supplier
	 * 
	 * @param supplier
	 */
	public void addSupplier(String supplier) {
		this.hsSupplierLoadingOrderInStack.add(this.inputTruck.getSupplierLoadingOrder(supplier));
		this.hsSuppliersInStack.add(supplier);
	}
	
	/** get the suppliers loading order present in the truck
	 * 
	 * @return
	 */
	public Set<Integer> getSupplierLoadingOrderInStacks() {
		return this.hsSupplierLoadingOrderInStack;
	}
	
	/** get the stacks of the truck
	 * 
	 * @return
	 */
	public List<OutputStack> getStacks() {
		return this.stacks;
	}
	
	public void setId(String ident) {
		this.ident = ident;
	}
	
	public String getId() {
		return this.ident;
	}
	
	/**
	 * @param inputTruck the inputTruck to set
	 */
	public void setInputTruck(InputTruck inputTruck) {
		this.inputTruck = inputTruck;
	}


	/**
	 * @param loadedLength the loadedLength to set
	 */
	public void setLoadedLength(int loadedLength) {
		this.loadedLength = loadedLength;
	}


	/**
	 * @param loadedWeight the loadedWeight to set
	 */
	public void setLoadedWeight(double loadedWeight) {
		this.loadedWeight = loadedWeight;
	}


	/**
	 * @param loadedVolume the loadedVolume to set
	 */
	public void setLoadedVolume(double loadedVolume) {
		this.loadedVolume = loadedVolume;
	}


	/**
	 * @param weightMiddleAxleTrailer the weightMiddleAxleTrailer to set
	 */
	public void setWeightMiddleAxleTrailer(double weightMiddleAxleTrailer) {
		this.weightOnMiddleAxleTrailer = weightMiddleAxleTrailer;
	}


	/**
	 * @param weightRearAxleTrailer the weightRearAxleTrailer to set
	 */
	public void setWeightRearAxleTrailer(double weightRearAxleTrailer) {
		this.weightOnRearAxleTrailer = weightRearAxleTrailer;
	}


	/**
	 * @return the extraTruck
	 */
	public boolean isExtraTruck() {
		return extraTruck;
	}

	/**
	 * @param extraTruck the extraTruck to set
	 */
	public void setExtraTruck(boolean extraTruck) {
		this.extraTruck = extraTruck;
	}

	/**
	 * @return the inputTruck
	 */
	public InputTruck getInputTruck() {
		return inputTruck;
	}

	/**
	 * @return the loadedLength
	 */
	public int getLoadedLength() {
		return loadedLength;
	}

	/**
	 * @return the loadedWeight
	 */
	public double getLoadedWeight() {
		return loadedWeight;
	}

	/**
	 * @return the loadedVolume
	 */
	public double getLoadedVolume() {
		return loadedVolume;
	}

	/**
	 * @return the weightMiddleAxleTrailer
	 */
	public double getWeightMiddleAxleTrailer() {
		return weightOnMiddleAxleTrailer;
	}

	/**
	 * @return the weightRearAxleTrailer
	 */
	public double getWeightRearAxleTrailer() {
		return weightOnRearAxleTrailer;
	}
	
	/**
	 * @return the calculatedWeightOnMiddleAxleTrailer
	 */
	public double getCalculatedWeightOnMiddleAxleTrailer() {
		return calculatedWeightOnMiddleAxleTrailer;
	}

	/**
	 * @return the calculatedWeightOnRearAxleTrailer
	 */
	public double getCalculatedWeightOnRearAxleTrailer() {
		return calculatedWeightOnRearAxleTrailer;
	}

	/** calculate the weight of the stacks loaded into the truck, for suppliers whose loading order is inferior to a maximal loading order
	 * 
	 */
	public double getCalculateLoadedWeight(int maxSupplierLoadingOrder) {
		double calculatedLoadedWeight = 0.0;
			for(OutputStack stack : this.stacks) {
				if (this.inputTruck.getSupplierLoadingOrder(stack.getSupplier()) > maxSupplierLoadingOrder) continue;
				
				calculatedLoadedWeight += stack.getWeight();
			}
		return calculatedLoadedWeight;
	}
	
	/** calculate the weight of the stacks loaded into the truck
	 * 
	 */
	public double getCalculateLoadedWeight() {
		if (this.calculatedLoadedWeight == 0.0) {
			for(OutputStack stack : this.stacks) {
				this.calculatedLoadedWeight += stack.getWeight();
			}
		}
		return this.calculatedLoadedWeight;
	}
	
	/** calculate the volume of the stacks loaded into the truck
	 * 
	 */
	public double getCalculateLoadedVolume() {
		if (this.calculatedLoadedVolume == 0.0) {
			for(OutputStack stack : this.stacks) 
				this.calculatedLoadedVolume += stack.getVolume();
		}
		return this.calculatedLoadedVolume;
	}
	
	/**
	 * @return the weightOnMiddleAxleTrailer
	 */
	public double getWeightOnMiddleAxleTrailer() {
		return weightOnMiddleAxleTrailer;
	}

	/**
	 * @return the weightOnRearAxleTrailer
	 */
	public double getWeightOnRearAxleTrailer() {
		return weightOnRearAxleTrailer;
	}

	/**
	 * @return the distanceCenterGravityStacksEndTrailer
	 */
	public double getDistanceCenterGravityStacksEndTrailer() {
		return distanceCenterGravityStacksEndTrailer;
	}

	/**
	 * @return the distanceCenterGravityStacksRearAxleTrailer
	 */
	public double getDistanceCenterGravityStacksRearAxleTrailer() {
		return distanceCenterGravityStacksRearAxleTrailer;
	}

	/**
	 * @return the weightOnHarnessTrailer
	 */
	public double getWeightOnHarnessTrailer() {
		return weightOnHarnessTrailer;
	}

	
	/**
	 * @return the calculatedLoadedLength
	 */
	public int getCalculatedLoadedLength() {
		return calculatedLoadedLength;
	}

	/** calculate distances and weights on axles with the suppliers whose loading order <= maxSupplierLoadingOrder
	 * 
	 * @param maxSupplierLoadingOrder
	 */
	public void calculateDistanceWeightOnAxles(int maxSupplierLoadingOrder) {
		this.distanceCenterGravityStacksEndTrailer = 0;
		for(OutputStack stack : this.stacks) {
			if (this.inputTruck.getSupplierLoadingOrder(stack.getSupplier()) > maxSupplierLoadingOrder) continue;
			
			this.distanceCenterGravityStacksEndTrailer += stack.getxGravityCenter() * stack.getWeight();
		}
		this.distanceCenterGravityStacksEndTrailer = this.distanceCenterGravityStacksEndTrailer / this.getCalculateLoadedWeight(maxSupplierLoadingOrder);

		InputTruck truck = this.inputTruck;
		this.distanceCenterGravityStacksRearAxleTrailer = truck.getDistanceEndTrailerHarness() + truck.getDistanceHarnessRearAxleTrailer() 
															- distanceCenterGravityStacksEndTrailer;

		this.weightOnHarnessTrailer = (this.getCalculateLoadedWeight(maxSupplierLoadingOrder) * this.distanceCenterGravityStacksRearAxleTrailer
										+ truck.getWeightEmptyTrailer() * truck.getDistanceTrailerCenterGravityRearAxle()) 
									/ truck.getDistanceHarnessRearAxleTrailer();
		
		this.calculatedWeightOnRearAxleTrailer = this.getCalculateLoadedWeight(maxSupplierLoadingOrder) + truck.getWeightEmptyTrailer() - this.weightOnHarnessTrailer;
		this.calculatedWeightOnMiddleAxleTrailer = (truck.getWeightTractor() * truck.getDistanceFrontAxleCenterGravityTractor() +
				this.weightOnHarnessTrailer * truck.getDistanceFrontAxleHarnessTractor()) / truck.getDistanceFrontMidleAxlesTractor();
	}

	/** get the suppliers present in stacks and whose loading order <= maxSupplierLoadingOrder
	 * 
	 * @param maxSupplierLoadingOrder
	 * @return
	 */
	public List<String> getSuppliers(int maxSupplierLoadingOrder) {
		List<String> suppliers = new ArrayList<String>();
		
		for(String supplier : this.hsSuppliersInStack) {
			if (this.inputTruck.getSupplierLoadingOrder(supplier) <= maxSupplierLoadingOrder)
				suppliers.add(supplier);
		}
		return suppliers;
	}

}
