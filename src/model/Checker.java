package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import main.Referential;
import tools.Constants;

public class Checker {

	private Referential ref;
	
	/** constructor
	 * 
	 * @param ref
	 */
	public Checker(Referential ref) {
		this.ref = ref;
	}

	/** check all the constraints
	 * 
	 */
	public void checkAllConstraints() {
		/* items constraints
		 * 
		 */
		this.checkConstraintI1();
		this.checkConstraintI2toI5();
		
		/* stacks constraints
		 * 
		 */
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			// sort items by ascending zOrigin
			Collections.sort(stack.getItems(), new Comparator<OutputItem>() {
				@Override
				public int compare(OutputItem item1, OutputItem item2) {
					return item1.getzOrigin() - item2.getzOrigin();
				}				
			});
		}
		
		this.checkStackHeight();
		this.checkStackWithItems();
		
		this.checkConstraintS1();
		this.checkConstraintS2();
		this.checkConstraintS3();
		this.checkConstraintS4();
		this.checkConstraintS5();
		this.checkConstraintS6();
		this.checkConstraintS7();
		
		/* trucks constraints
		 * 
		 */
		this.checkTruckWithStacks();
		this.checkTruckWeightsOnAxles();
		
		/* check truck indicators
		 * 
		 */
		this.checkTruckLoadedWeight();
		this.checkTruckLoadedVolume();
		this.checkTruckLoadedLength();

		
		/* placement constraints
		 * 
		 */
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {	
			// sort stacks by ascending xOrigin	
			Collections.sort(truck.getStacks(), new Comparator<OutputStack>() {
				@Override
				public int compare(OutputStack stack1, OutputStack stack2) {
					return stack1.getxOrigin() - stack2.getxOrigin();
				}				
			});
		}
		
		this.checkConstraintP1();
		this.checkConstraintP2();
		this.checkConstraintP3();
		this.checkConstraintP4();
		
		/* weight constraints
		 * 
		 */
		this.checkConstraintW1();
		this.checkConstraintW2();
	}
	
	
	/** check constraint I1 : All items must be packed into stacks, which must be loaded into trucks (planned or extra)
	 * 
	 */
	private void checkConstraintI1()  {
		// check that all input items are present in the output items, no more, no less
		for(InputItem inputItem : ref.getRefInputItems().getAll()) {
			int nbItemsFound = ref.getRefOutputItems().getNbOutputItems(inputItem.getId());
			if (nbItemsFound != inputItem.getNbItems()) {
				ref.getRefAnos().add("Constraint I1;different qty between inputs and outputs for item ident = " 
						+ inputItem.getId() + ";" + "input item qty = " + inputItem.getNbItems() + ";output item qty = " + nbItemsFound);
			}
		}
	}
	
	/** check constraints
	 *  I2 : An item i can be loaded into a truck t iff t arrives at the item’s plant
	 *  I3 : An item i can be loaded into a truck t iff t can pickup the item’s product
	 *  I4 : An item i can be loaded into a truck t iff t stops by the item’s supplier
	 *  I5 : An item i can be loaded into a truck t iff t arrives at the plant in the item’s time window
	 */
	private void checkConstraintI2toI5()  {
		for(OutputItem outputItem : ref.getRefOutputItems().getAll()) {
			InputTruck inputTruck = outputItem.getOutputTruck().getInputTruck();
			InputItem inputItem = outputItem.getInputItem();
			
			if (inputItem.getPlant().compareToIgnoreCase(inputTruck.getPlant()) != 0) {			
				ref.getRefAnos().add("Constraint I2;an item is loaded into a truck with a different plant;item ident = " 
						+ inputItem.getId() + ";" + "item plant = " + inputItem.getPlant() + ";truck plant = " + inputTruck.getPlant());
			}
			if (!inputTruck.pickupProduct(inputItem.getProduct())) {			
				ref.getRefAnos().add("Constraint I3;an item is loaded into a truck which does not pickup the item's product;item ident = " 
						+ inputItem.getId() + ";" + "item product = " + inputItem.getProduct() + ";truck products = " + inputTruck.getProducts().toString());
			}
			if (!inputTruck.pickupSupplier(inputItem.getSupplier())) {			
				ref.getRefAnos().add("Constraint I4;an item is loaded into a truck which does not pickup the item's supplier;item ident = " 
						+ inputItem.getId()  + ";" + "item supplier = " + inputItem.getSupplier() + ";truck suppliers = " + inputTruck.getSuppliers().toString());
			}
			if (inputTruck.getArrivalTime().compareTo(inputItem.getEarliestArrivalTime()) < 0 || inputTruck.getArrivalTime().compareTo(inputItem.getLatestArrivalTime()) > 0) {			
				ref.getRefAnos().add("Constraint I5;an item is loaded into a truck which does not arrive within the item's time window;item ident = " 
						+ inputItem.getId() + ";"
						+ "item time window = [" + inputItem.getEarliestArrivalTime() + "," + inputItem.getLatestArrivalTime() + "]" 
						+ ";truck arrival time = " + inputTruck.getArrivalTime());
			}
		}
	}
	
	/** check stack height = sum item heigth - item nesting height (except for top item)
	 * 
	 */
	private void checkStackHeight() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0) continue;
			
			// items are sorted by ascending zOrigin
			int itemsHeight = 0;
			OutputItem bottomItem = stack.getItems().get(0);
			for(OutputItem item : stack.getItems()) {
				itemsHeight += item.getInputItem().getHeight();
				if (item != bottomItem)
					itemsHeight -= item.getInputItem().getNestingHeight();
					
			}
			if (itemsHeight != stack.getzExtremity()) {
				ref.getRefAnos().add("stack;the stack height is different from the sum of its items height minus items nesting height;stack id = " + stack.getId()
				+ ";stack height = " + Integer.toString(stack.getzExtremity())
				+ ";sum items height minus items nesting height = " + Integer.toString(itemsHeight));
			}
		}
	}
	
	/** check that all trucks have stacks
	 * 
	 */
	private void checkTruckWithStacks() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			if (truck.getStacks().size() == 0) 
				ref.getRefAnos().add("Output truck;the truck has no stacks;truck id = " + truck.getId());
		}
	}
	
	/** check the loaded weight of trucks
	 * 
	 */
	private void checkTruckLoadedWeight() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			if ((Math.abs(truck.getCalculateLoadedWeight() - truck.getLoadedWeight()) / truck.getCalculateLoadedWeight()) > 0.01)
				ref.getRefAnos().add("Output truck;the loaded weight is incorrect;truck id = " + truck.getId()
								+ ";loaded weight read in output truck file = " + String.format("%1$.2f", truck.getLoadedWeight())
								+ ";total stacks weight = " + String.format("%1$.2f", truck.getCalculateLoadedWeight())
								);
		}
	}

	
	/** check the loaded volume of trucks
	 * 
	 */
	private void checkTruckLoadedVolume() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			if ((Math.abs(truck.getCalculateLoadedVolume() - truck.getLoadedVolume()) / truck.getCalculateLoadedVolume()) > 0.05)
				ref.getRefAnos().add("Output truck;the loaded volume is incorrect;truck id = " + truck.getId()
								+ ";loaded volume read in output truck file = " + String.format("%1$.2f", truck.getLoadedVolume())
								+ ";total stacks volume = " + String.format("%1$.2f", truck.getCalculateLoadedVolume())
								);
		}
	}

	/** check the loaded length of trucks
	 * 
	 */
	private void checkTruckLoadedLength() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			if (truck.getCalculatedLoadedLength() != 0 && (Math.abs(truck.getCalculatedLoadedLength() - truck.getLoadedLength()) / truck.getCalculatedLoadedLength()) > 0.01)
				ref.getRefAnos().add("Output truck;the loaded length is incorrect;truck id = " + truck.getId()
								+ ";loaded length read in output truck file = " + String.format("%1$.2f", truck.getLoadedLength())
								+ ";loaded length due to the stacks loaded in the truck = " + String.format("%1$.2f", truck.getCalculatedLoadedLength())
								);
		}
	}
	
	/** check the weight on axles of trucks
	 * 
	 */
	private void checkTruckWeightsOnAxles() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			truck.calculateDistanceWeightOnAxles(99);
			
			if ((Math.abs(truck.getCalculatedWeightOnMiddleAxleTrailer() - truck.getWeightMiddleAxleTrailer()) / truck.getCalculatedWeightOnMiddleAxleTrailer()) > 0.01)
				ref.getRefAnos().add("Output truck;the weight on middle axle of the trailer is incorrect;truck id = " + truck.getId()
							+ ";weight on middle axle of the trailer read in output truck file = " + String.format("%1$.2f", truck.getWeightMiddleAxleTrailer())
							+ ";recalculated  weight on middle axle of the trailer = " + String.format("%1$.2f", truck.getCalculatedWeightOnMiddleAxleTrailer())
							+ ";total stacks weight = " + String.format("%1$.2f", truck.getCalculateLoadedWeight())
							+ ";distance between center of gravity of stacks and end of the trailer = " + String.format("%1$.2f", truck.getDistanceCenterGravityStacksEndTrailer())
							+ ";distance between center of gravity of stacks and rear axle of the trailer = " + String.format("%1$.2f", truck.getDistanceCenterGravityStacksRearAxleTrailer())
							+ ";weight on the harness of the trailer = " + String.format("%1$.2f", truck.getWeightOnHarnessTrailer())
							);
			if ((Math.abs(truck.getCalculatedWeightOnRearAxleTrailer() - truck.getWeightRearAxleTrailer()) / truck.getCalculatedWeightOnRearAxleTrailer()) > 0.01)
				ref.getRefAnos().add("Output truck;the weight on rear axle of the trailer is incorrect;truck id = " + truck.getId()
							+ ";weight on rear axle of the trailer read in output truck file = " + String.format("%1$.2f", truck.getWeightMiddleAxleTrailer())
							+ ";recalculated  weight on rear axle of the trailer = " + String.format("%1$.2f", truck.getCalculatedWeightOnRearAxleTrailer())
							+ ";total stacks weight = " + String.format("%1$.2f", truck.getCalculateLoadedWeight())
							+ ";distance between center of gravity of stacks and end of the trailer = " + String.format("%1$.2f", truck.getDistanceCenterGravityStacksEndTrailer())
							+ ";distance between center of gravity of stacks and rear axle of the trailer = " + String.format("%1$.2f", truck.getDistanceCenterGravityStacksRearAxleTrailer())
							+ ";weight on the harness of the trailer = " + String.format("%1$.2f", truck.getWeightOnHarnessTrailer())
							);
		}
	}
	
	/** check that all stacks have items
	 * 
	 */
	private void checkStackWithItems() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0)
				ref.getRefAnos().add("stack;the stack has no item;stack id = " + stack.getId());
		}
	}
	
	/** check constraint S1 : All the items packed in a stack must share the same supplier, plant, stackability code, maximal stackability, maximal density and supplier dock
	 * 
	 */
	private void checkConstraintS1() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0) continue;
			
			InputItem firstItem = stack.getItems().get(0).getInputItem();
			
			for(OutputItem outputItem : stack.getItems()) {
				InputItem item = outputItem.getInputItem();
				
				if (item == firstItem) continue;
				
				if (item.getSupplier().compareToIgnoreCase(firstItem.getSupplier()) != 0) {
					ref.getRefAnos().add("Constraint S1;2 items of a stack do not share the same supplier;stack id = " + stack.getId() 
										+ ";item1 supplier  =" + firstItem.getSupplier() 
										+ ";item2 supplier = " + item.getSupplier());
				}
				if (item.getPlant().compareToIgnoreCase(firstItem.getPlant()) != 0) {
					ref.getRefAnos().add("Constraint S1;2 items of a stack do not share the same plant;stack id = " + stack.getId() 
										+ ";item1 plant  =" + firstItem.getPlant() 
										+ ";item2 plant = " + item.getPlant());
				}
				if (item.getStackabilityCode().compareToIgnoreCase(firstItem.getStackabilityCode()) != 0) {
					ref.getRefAnos().add("Constraint S1;2 items of a stack do not share the same stackability code;stack id = " + stack.getId() 
										+ ";item1 plant  =" + firstItem.getStackabilityCode() 
										+ ";item2 plant = " + item.getStackabilityCode());
				}
				if (item.getSupplierDock().compareToIgnoreCase(firstItem.getSupplierDock()) != 0) {
					ref.getRefAnos().add("Constraint S1;2 items of a stack do not share the same supplier dock;stack id = " 
										+ stack.getId() + ";item1 supplier dock  =" + firstItem.getSupplierDock() 
										+ ";item2 supplier dock = " + item.getSupplierDock());
				}
			}
		}
	}
	
	
	/** check constraint S2 : if (flag “stack with multiple docks” = no) for the truck, then all the items of stack s must share the same plant dock
	 * 
	 */
	private void checkConstraintS2() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0) continue;
			
			if (!stack.getOutputTruck().getInputTruck().isStackWithMultipleDocks())  {
				InputItem firstItem = stack.getItems().get(0).getInputItem();
				
				for(OutputItem outputItem : stack.getItems()) {
					InputItem item = outputItem.getInputItem();
					
					if (item == firstItem) continue;
					
					if (firstItem.getPlantDock().compareToIgnoreCase(item.getPlantDock()) != 0) {
						ref.getRefAnos().add("Constraint S2;flag “stack with multiple docks” = no and 2 items of the stack do not share the same plant dock;stack id = " 
								+ stack.getId() + ";item1 plant dock  =" + firstItem.getPlantDock() 
								+ ";item2 plant dock = " + item.getPlantDock());
					}
				}
			}
		}
	}
	
	/** check constraint S3 : If the flag “stack with multiple docks” = yes for the truck, s may contain items with 2 plant docks with consecutive loading orders. 
	 * 		There is a restriction : for every stackability code SC present among the stacks of a truck, only one stack with the stackability code SC can contain items 
	 * 		with 2 plant docks.
	 */
	private void checkConstraintS3() {
		Set<String> hsTruckStackabilityCodeWithStackWith2PlantDocks = new HashSet<String>(); // key = truck id + stackability code which have a stack with 2 plant docks
		
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0) continue;
			
			if (stack.getOutputTruck().getInputTruck().isStackWithMultipleDocks()) {
				Set<String> hsPlantDocks = new HashSet<String>(); // value = plant dock + loading order
				
				for(OutputItem outputItem : stack.getItems()) {
					String plantDock = outputItem.getInputItem().getPlantDock();
					hsPlantDocks.add(plantDock + Constants.SEP_KEY 
								+ Integer.toString(stack.getOutputTruck().getInputTruck().getPlantDockLoadingOrder(plantDock)));
				}
				if (hsPlantDocks.size() > 2) {
					ref.getRefAnos().add("Constraint S3;flag “stack with multiple docks” = yes and there are more than 2 plant docks in the stack;stack id = " + stack.getId()
					+ ";the set of plant dock/loading order = " + hsPlantDocks.toString());
				}
				if (hsPlantDocks.size() == 2) {
					int loadingOrderFirstPlantDock = -1;
					int loadingOrderSecondPlantDock = -1;
					
					Iterator<String> iter = hsPlantDocks.iterator();
					while(iter.hasNext()) {
						if (loadingOrderFirstPlantDock == -1) {
							String plantDockAndLoadingOrder = iter.next();
							loadingOrderFirstPlantDock = Integer.parseInt(plantDockAndLoadingOrder.substring(plantDockAndLoadingOrder.indexOf(Constants.SEP_KEY)+1));
						} else {
							String plantDockAndLoadingOrder = iter.next();
							loadingOrderSecondPlantDock = Integer.parseInt(plantDockAndLoadingOrder.substring(plantDockAndLoadingOrder.indexOf(Constants.SEP_KEY)+1));							
						}
					}
					if (Math.abs(loadingOrderFirstPlantDock - loadingOrderSecondPlantDock) > 1) {
						ref.getRefAnos().add("Constraint S3;flag “stack with multiple docks” = yes and there are 2 plant docks with non consecutive loading order in the stack;stack id = " + stack.getId()
						+ ";the set of plant dock/loading order = " + hsPlantDocks.toString());
					}
					String key = stack.getOutputTruck().getId() + Constants.SEP_KEY + stack.getItems().get(0).getInputItem().getStackabilityCode();
					if (!hsTruckStackabilityCodeWithStackWith2PlantDocks.contains(key)) {
						hsTruckStackabilityCodeWithStackWith2PlantDocks.add(key);
					} else {
						ref.getRefAnos().add("Constraint S3;there are at least 2 stacks with the same stackability code and 2 plant docks in the truck;truck id/stackability code = " + key);
						continue;
					}
				}
			}
		}
	}
	
	/** check constraint S4 : If one item i of a stack s has a forced orientation IO_i then all the items of stack s must share the same orientation (so_s = IO_i).
	 * 
	 */
	private void checkConstraintS4() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			// get the forced orientation on all the items of the stack
			String forcedOrientationStack = Constants.FORCED_ORIENTATION_NONE;

			for(OutputItem outputItem : stack.getItems()) {
				String forcedOrientationItem = outputItem.getInputItem().getForcedOrientation();
				
				if (forcedOrientationStack.compareToIgnoreCase(Constants.FORCED_ORIENTATION_NONE) == 0
						&& forcedOrientationItem.compareToIgnoreCase(forcedOrientationStack) !=  0) {
					forcedOrientationStack = forcedOrientationItem;
				} else if (forcedOrientationStack.compareToIgnoreCase(Constants.FORCED_ORIENTATION_NONE) != 0
						&& forcedOrientationItem.compareToIgnoreCase(Constants.FORCED_ORIENTATION_NONE) != 0
						&& forcedOrientationItem.compareToIgnoreCase(forcedOrientationStack) !=  0) {
					ref.getRefAnos().add("Constraint S4;there are 2 different forced orientations in the stack;stack id = " + stack.getId()
							+ ";first forced orientation = " + forcedOrientationStack + ";second forced orientation = " + forcedOrientationItem);
					continue;
				}
			}
		}
	}
	
	/** check constraint S5 : in a stack s, the total weight of the items packed above the bottom item should not exceed the maximal weight IMM_i
	 * 
	 */
	private void checkConstraintS5() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0) continue;

			// items are already sorted by ascending zOrigin
			OutputItem bottomItem = stack.getItems().get(0);

			boolean ano = false;
			if (bottomItem.getzOrigin() != 0) {
				ref.getRefAnos().add("Constraint S5;there is no item at the bottom of the stack (zOrigin = 0);stack id = " + stack.getId());
				ano = true;
			} else if (stack.getItems().size() > 1 && stack.getItems().get(1).getzOrigin() == 0) {
				ref.getRefAnos().add("Constraint S5;there are 2 items at the bottom of the stack (zOrigin = 0);stack id = " + stack.getId()
					+ ";item1 id = " + bottomItem.getId() + ";item2 id = " + stack.getItems().get(1).getId());
				ano = true;
			}

			if (ano) continue;

			double weightAboveTheBottomItem = 0.0;
			for(OutputItem outputItem : stack.getItems()) {
				if (outputItem == bottomItem) continue;

				weightAboveTheBottomItem += outputItem.getInputItem().getWeight();
			}
			Integer maxWeightAboveBottomItem = stack.getOutputTruck().getInputTruck().getMaxWeightAboveBottomItemInStacks(bottomItem.getInputItem().getProduct());
			if (maxWeightAboveBottomItem != null && weightAboveTheBottomItem > maxWeightAboveBottomItem) {
				ref.getRefAnos().add("Constraint S5;the weigth of the items above the bottom item of the stack exceeds the maximal weight;stack id = " + stack.getId()
				+ ";weight of the items above the bottom item = " + String.format("%1$.2f", weightAboveTheBottomItem) 
				+ ";maximal weight authorized = " + Integer.toString(stack.getOutputTruck().getInputTruck().getMaxWeightAboveBottomItemInStacks(bottomItem.getInputItem().getProduct())));
			}
		}
	}
	
	/** check constraint S6 : the number of items in a stack should not exceed the smallest maximal stackability of the items
	 * 
	 */
	private void checkConstraintS6() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0) continue;
			
			int maximalStackability = stack.getItems().get(0).getInputItem().getMaxStackability();
			for(OutputItem item : stack.getItems()) {
				if (maximalStackability > item.getInputItem().getMaxStackability())
					maximalStackability = item.getInputItem().getMaxStackability();
			}
			if (stack.getItems().size() > maximalStackability) {
				ref.getRefAnos().add("Constraint S6;the number of items exceeds the maximal stackability in the stack;stack id = " + stack.getId() 
					+ ";number of items = " + Integer.toString(stack.getItems().size())
					+ ";maximal stackability = " + Integer.toString(maximalStackability));
			}
		}
	}
	
	/** check constraint S7 : The density of a stack s should not exceed the lowest maximal density of its items (reminder : all the items of a stack share 
	 * 		the same maximal density)
	 * 
	 */
	private void checkConstraintS7() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			if (stack.getItems().size() == 0) continue;
			
			double stackWeight = 0.0;
			for(OutputItem outputItem : stack.getItems()) {
				stackWeight += outputItem.getInputItem().getWeight();
			}
			double stackSurface = ((double)(stack.getxExtremity() - stack.getxOrigin())/1000.0) * ((double)(stack.getyExtremity() - stack.getyOrigin())/1000.0);
			double stackDensity = stackWeight / stackSurface;
			
			if (stackDensity > stack.getOutputTruck().getInputTruck().getMaxStackDensity()) {
				ref.getRefAnos().add("Constraint S7;the density of the stack exceeds the maximal stack density of the truck;stack id = " + stack.getId() 
				+ ";density of the stack = " + String.format("%1$.2f",stackDensity)
				+ ";truck id = " + stack.getOutputTruck().getId()
				+ ";maximal density for the truck = " + Integer.toString(stack.getOutputTruck().getInputTruck().getMaxStackDensity()));
			}
		}
	}
	
	/** check constraint P1 : the placement of a stack s into a truck t should not exceed the truck’s dimensions
	 * 
	 */
	private void checkConstraintP1() {
		for(OutputStack stack : ref.getRefOutputStacks().getAll()) {
			InputTruck truck = stack.getOutputTruck().getInputTruck();
			
			if (stack.getxOrigin() < 0 || stack.getxOrigin() > truck.getLength()) {
				ref.getRefAnos().add("Constraint P1;xOrigin of the stack exceeds the length of the truck;stack id = " + stack.getId() 
									+";xOrigin = " + Integer.toString(stack.getxOrigin()) + "; truck length = " + Integer.toString(truck.getLength()));
			}
			if (stack.getyOrigin() < 0 || stack.getyOrigin() > truck.getWidth()) {
				ref.getRefAnos().add("Constraint P1;yOrigin of the stack exceeds the width of the truck;stack id = " + stack.getId() 
									+";yOrigin = " + Integer.toString(stack.getyOrigin()) + "; truck width = " + Integer.toString(truck.getWidth()));
			}
			
			if (stack.getxExtremity() < 0 || stack.getxExtremity() > truck.getLength()) {
				ref.getRefAnos().add("Constraint P1;xExtremity of the stack exceeds the length of the truck;stack id = " + stack.getId() 
									+";xExtremity = " + Integer.toString(stack.getxExtremity()) + "; truck length = " + Integer.toString(truck.getLength()));
			}
			if (stack.getyExtremity() < 0 || stack.getyExtremity() > truck.getWidth()) {
				ref.getRefAnos().add("Constraint P1;yExtremity of the stack exceeds the width of the truck;stack id = " + stack.getId() 
									+";yExtremity = " + Integer.toString(stack.getyExtremity()) + "; truck width = " + Integer.toString(truck.getWidth()));
			}
			if (stack.getzExtremity() > truck.getHeight()) {
				ref.getRefAnos().add("Constraint P1;zExtremity of the stack exceeds the height of the truck;stack id = " + stack.getId() 
									+";zExtremity = " + Integer.toString(stack.getzExtremity()) + "; truck height = " + Integer.toString(truck.getHeight()));
			}
		}
	}
	
	/** check constraint P2 : the stacks packed into a truck t cannot overlap
	 * 
	 */
	private void checkConstraintP2() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			for(OutputStack stack1 : truck.getStacks()) {
				for(OutputStack stack2 : truck.getStacks()) {
					if (stack2 == stack1) continue;	
					
					if (stack1.getxOrigin() <= stack2.getxOrigin()) {
						// If stack1 and stack2 overlap in the X axis, they cannot overlap in the Y axis
						if (stack2.getxOrigin() < stack1.getxExtremity()) {
							if (!(stack2.getyOrigin() >= stack1.getyExtremity() || stack2.getyExtremity() <= stack1.getyOrigin())) {
								ref.getRefAnos().add("Constraint P2;2 stacks overlap in a truck;truck id = " + truck.getId() 
									+ ";stack1 id = " + stack1.getId() + ";stack2 id = " + stack2.getId()
									+ ";stack1 xOrigin = " + Integer.toString(stack1.getxOrigin()) + ";stack1 xExtremity = " + Integer.toString(stack1.getxExtremity())
									+ ";stack1 yOrigin = " + Integer.toString(stack1.getyOrigin()) + ";stack1 yExtremity = " + Integer.toString(stack1.getyExtremity())
									+ ";stack2 xOrigin = " + Integer.toString(stack2.getxOrigin()) + ";stack2 xExtremity = " + Integer.toString(stack2.getxExtremity())
									+ ";stack2 yOrigin = " + Integer.toString(stack2.getyOrigin()) + ";stack2 yExtremity = " + Integer.toString(stack2.getyExtremity())
									);								
							}
						}
					}
				}
			}
		}
	}
	
	/** check constraint P3 : any stack should be adjacent to another stack on the left on the X axis, or if there is a single stack in the truck, 
	 * the unique stack should be placed at the front of the truck (adjacent to the truck driver)
	 * 
	 */
	private void checkConstraintP3() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			if (truck.getStacks().size() == 0) continue;
			
			if (truck.getStacks().size() == 1) {
				OutputStack stack = truck.getStacks().get(0);
				if (stack.getxOrigin() != 0) {
					ref.getRefAnos().add("Constraint P3;the single stack of a truck is not placed in the front of a truck (xOrigin != 0);truck id = " + truck.getId() 
					+ ";stack id = " + stack.getId() + ";stack xOrigin = " + Integer.toString(stack.getxOrigin()));
				}
			} else {
				// stacks are ordered by ascending xOrigin inside every truck
				for(OutputStack stack1 : truck.getStacks()) {				
					if (stack1.getxOrigin() == 0) continue;
					
					boolean stackAdjacent = false;
					for(OutputStack stack2 : truck.getStacks()) {
						if (stack1 == stack2) continue;
						
						// the xOrigin of stack 1 should be equal to the xExtremity of another stack, with an overlap on the Y axis
						if (stack1.getxOrigin() == stack2.getxExtremity()
							&& ((stack2.getyOrigin() >= stack1.getyOrigin() && stack2.getyOrigin() <= stack1.getyExtremity()) // origin of stack 2 is included in stack 1 segment on Y axis
								|| (stack2.getyExtremity() >= stack1.getyOrigin() && stack2.getyExtremity() <= stack1.getyExtremity()) // extremity of stack 2 is included in stack 1 segment on Y axis
								|| (stack2.getyExtremity() >= stack1.getyExtremity() && stack2.getyOrigin() <= stack1.getyOrigin()) // stack1 is completely included in stack 2 segment on Y axis
								)
							)
							stackAdjacent = true;
					}
					if (!stackAdjacent) 
						ref.getRefAnos().add("Constraint P3;a stack is not adjacent on the left on the X axis to any other stack of the truck"
								+ ";truck id = " + truck.getId() + ";stack id = " + stack1.getId());
				}
			}
		}
	}
	
	/** check constraint P4 : stacks must be placed in an increasing fashion from the front to the rear of the truck, 
	 * 	(1) according to the suppliers’ pickup order, and among the stacks of the same supplier, stacks must be placed in an increasing fashion 
	 *  (2) according to the supplier dock loading order, and among the stacks with the same supplier and supplier dock, 
	 *  stacks must be placed in an increasing fashion (3) according to the plant dock loading order
	 * 
	 */
	private void checkConstraintP4() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			if (truck.getStacks().size() == 0) continue;

			// stacks are ordered by ascending xOrigin inside every truck
			for(OutputStack stack1 : truck.getStacks()) {
				if (stack1.getItems().size() == 0) continue;

				for(OutputStack stack2 : truck.getStacks()) {
					if (stack2.getItems().size() == 0) continue;	
					if (stack1 == stack2 ||  stack1.getxOrigin() > stack2.getxOrigin()) continue;

					String supplier1 = stack1.getSupplier();
					String supplier2 = stack2.getSupplier();
					
					if (!truck.getInputTruck().pickupSupplier(supplier1)) {
						ref.getRefAnos().add("stack;supplier not picked-up by the truck;stack id = " + stack1.getId() + ";truck id = " + truck.getId() + ";supplier = " + supplier1);
						continue;
					}
					if (!truck.getInputTruck().pickupSupplier(supplier2)) {
						ref.getRefAnos().add("stack;supplier not picked-up by the truck;stack id = " + stack2.getId() + ";truck id = " + truck.getId() + ";supplier = " + supplier2);
						continue;
					}
					
					if (supplier1.compareTo(supplier2) == 0) {
						String supplierDock1 = stack1.getSupplierDock();
						String supplierDock2 = stack2.getSupplierDock();

						if (!truck.getInputTruck().pickupSupplierDock(supplierDock1)) {
							ref.getRefAnos().add("stack;supplier dock not picked-up by the truck;stack id = " + stack1.getId() + ";truck id = " + truck.getId() 
							+ ";supplier dock = " + supplierDock1);
							continue;
						}
						if (!truck.getInputTruck().pickupSupplierDock(supplierDock2)) {
							ref.getRefAnos().add("stack;supplier dock not picked-up by the truck;stack id = " + stack2.getId() + ";truck id = " + truck.getId() 
							+ ";supplier dock = " + supplierDock2);
							continue;
						}
						
						if (supplierDock1.compareTo(supplierDock2) == 0) {
							
							for(String plantDock1 : stack1.getPlantDocks()) {
								for(String plantDock2 : stack2.getPlantDocks()) {
									if (plantDock1.compareTo(plantDock2) == 0) continue;
									
									if (!truck.getInputTruck().deliverPlantDock(plantDock1)) {
										ref.getRefAnos().add("stack;plant dock not delivered by the truck;stack id = " + stack1.getId() + ";truck id = " + truck.getId() 
										+ ";plant dock = " + plantDock1);
										continue;
									}
									if (!truck.getInputTruck().deliverPlantDock(plantDock2)) {
										ref.getRefAnos().add("stack;plant dock not delivered by the truck;stack id = " + stack2.getId() + ";truck id = " + truck.getId() 
										+ ";plant dock = " + plantDock2);
										continue;
									}
									
									if (stack1.getxOrigin() < stack2.getxOrigin()
											&& stack1.getPlantDockLoadingOrder(plantDock1) > stack2.getPlantDockLoadingOrder(plantDock2)) {
										ref.getRefAnos().add("Constraint P4;loading order of stack1 plant dock > loading order of stack2 plant dock but stack1 xOrigin < stack2 xOrigin"
													+ ";truck id = " + truck.getId() 
													+ ";stack1 id = " + stack1.getId()
													+ ";stack1 xOrigin = " + stack1.getxOrigin()
													+ ";stack1 supplier = " + supplier1
													+ ";stack1 supplier dock = " + supplierDock1
													+ ";stack1 plant dock = " + plantDock1
													+ ";loading order of stack1 plant dock = " + Integer.toString(stack1.getPlantDockLoadingOrder(plantDock1))
													+ ";stack2 id = " + stack2.getId()
													+ ";stack2 xOrigin = " + stack2.getxOrigin()
													+ ";stack2 supplier = " + supplier2
													+ ";stack2 supplier dock = " + supplierDock2
													+ ";stack2 plant dock = " + plantDock2
													+ ";loading order of stack2 plant dock = " + Integer.toString(stack2.getPlantDockLoadingOrder(plantDock2))
												);
									}								
								}
							}
						} else {
							if (stack1.getxOrigin() < stack2.getxOrigin()
									&& stack1.getSupplierDockLoadingOrder() > stack2.getSupplierDockLoadingOrder()) {
								ref.getRefAnos().add("Constraint P4;loading order of stack1 supplier dock > loading order of stack2 supplier dock but stack1 xOrigin < stack2 xOrigin;truck id = " + truck.getId() 
								+ ";stack1 id = " + stack1.getId()
								+ ";stack1 supplier dock = " + supplierDock1
								+ ";loading order of stack1 supplier dock = " + Integer.toString(stack1.getSupplierDockLoadingOrder())
								+ ";stack2 id = " + stack2.getId()
								+ ";stack2 supplier dock = " + supplierDock2
								+ ";loading order of stack2 lant dock = " + Integer.toString(stack2.getSupplierDockLoadingOrder())
										);
							}
						}
					} else {
						if (stack1.getxOrigin() < stack2.getxOrigin()
								&& truck.getInputTruck().getSupplierLoadingOrder(supplier1) > truck.getInputTruck().getSupplierLoadingOrder(supplier2)) {
							ref.getRefAnos().add("Constraint P4;pickup order of stack1 supplier > pickup order of stack2 supplier but stack1 xOrigin < stack2 xOrigin;truck id = " + truck.getId() 
										+ ";stack1 id = " + stack1.getId()
										+ ";stack1 supplier = " + supplier1
										+ ";pickup order of stack1 supplier = " + Integer.toString(truck.getInputTruck().getSupplierLoadingOrder(supplier1))
										+ ";stack2 id = " + stack2.getId()
										+ ";stack2 supplier = " + supplier2
										+ ";pickup order of stack2 supplier = " + Integer.toString(truck.getInputTruck().getSupplierLoadingOrder(supplier2))
										);
						}
					}
				}
			}
		}
	}
	
	/** The weights of the stacks packed in a truck should not exceed the truck’s maximal loaded weight
	 * 
	 */
	private void checkConstraintW1() {
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			if (truck.getCalculateLoadedWeight() > truck.getInputTruck().getMaxWeight()) 
				ref.getRefAnos().add("Constraint W1;the weight of the stacks exceed the maximal authorized weight of the truck;truck id = " + truck.getId() 
						+ ";stacks weight = " + String.format("%1$.2f",truck.getCalculateLoadedWeight())
						+ ";max authorized weigth of the truck = " + Integer.toString(truck.getInputTruck().getMaxWeight()));
		}
	}
	
	/** The weights on the middle axle and on the rear axle of a truck must not exceed the max weights authorized 
	 * 
	 */
	private void checkConstraintW2() {
		List<String> suppliersChecked = null;
		
		for(OutputTruck truck : ref.getRefOutputTrucks().getAll()) {
			// check for every supplier
			for(int supplierLoadingOrder : truck.getSupplierLoadingOrderInStacks()) {
				truck.calculateDistanceWeightOnAxles(supplierLoadingOrder);
				
				suppliersChecked = truck.getSuppliers(supplierLoadingOrder);
				
				if (truck.getCalculatedWeightOnMiddleAxleTrailer() > truck.getInputTruck().getMiddleAxleMaxWeight())
					ref.getRefAnos().add("Constraint W2;the weight on middle axle of the trailer exceeds max weight;truck id = " + truck.getId()
									+ ";recalculated weight on middle axle of the trailer = " + String.format("%1$.2f", truck.getCalculatedWeightOnMiddleAxleTrailer())
									+ ";max weight on middle axle of the trailer = " + String.format("%1$.2f", truck.getInputTruck().getMiddleAxleMaxWeight())
									+";with suppliers = " + suppliersChecked.toString()
									);
				if (truck.getCalculatedWeightOnRearAxleTrailer() > truck.getInputTruck().getRearAxleMaxWeight())
					ref.getRefAnos().add("Constraint W2;the weight on rear axle of the trailer exceeds max weight;truck id = " + truck.getId()
									+ ";recalculated weight on rear axle of the trailer = " + String.format("%1$.2f", truck.getCalculatedWeightOnRearAxleTrailer())
									+ ";max weight on rear axle of the trailer = " + String.format("%1$.2f", truck.getInputTruck().getRearAxleMaxWeight())
									+";with suppliers = " + suppliersChecked.toString()
									);
			}
		}	
		
	}
}

