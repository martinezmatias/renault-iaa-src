package io;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.Referential;
import model.InputTruck;
import tools.Constants;

public class InputTrucksReader {

	private static Logger LOGGER = LoggerFactory.getLogger(InputTrucksReader.class);
	 
	private static final int IND_COL_SUPPLIER = 0;
	private static final int IND_COL_SUPPLIER_LOADING_ORDER = 1;
	private static final int IND_COL_SUPPLIER_DOCK = 2;
	private static final int IND_COL_SUPPLIER_DOCK_LOADING_ORDER = 3;
	private static final int IND_COL_PLANT = 4;
	private static final int IND_COL_PLANT_DOCK = 5;
	private static final int IND_COL_PLANT_DOCK_LOADING_ORDER = 6;
	private static final int IND_COL_PRODUCT = 7;
	private static final int IND_COL_ARRIVAL_TIME = 8;
	private static final int IND_COL_IDENT = 9;	
	private static final int IND_COL_LENGTH = 10;
	private static final int IND_COL_WIDTH = 11;
	private static final int IND_COL_HEIGHT = 12;
	private static final int IND_COL_MAX_WEIGHT = 13;
	private static final int IND_COL_FLAG_STACK_MULTIPLE_DOCKS = 14;
	private static final int IND_COL_MAX_STACK_DENSITY = 15;
	private static final int IND_COL_MAX_WEIGHT_ON_BOTTOM_ITEM = 16;
	private static final int IND_COL_COST = 17;
	private static final int IND_COL_MIDDLE_AXLE_MAX_WEIGHT = 18;
	private static final int IND_COL_REAR_AXLE_MAX_WEIGHT = 19;
	private static final int IND_COL_WEIGHT_TRACTOR = 20;
	private static final int IND_COL_DISTANCE_BETWEEN_FRONT_MIDDLE_AXLES = 21;
	private static final int IND_COL_DISTANCE_BETWEEN_FRONT_AXLE_CENTER_GRAVITY_TRACTOR = 22;
	private static final int IND_COL_DISTANCE_BETWEEN_FRONT_AXLE_HARNESS_TRACTOR = 23;
	private static final int IND_COL_WEIGHT_EMPTY_TRAILER = 24;
	private static final int IND_COL_DISTANCE_BETWEEN_HARNESS_REAR_AXLE_TRAILER = 25;
	private static final int IND_COL_DISTANCE_BETWEEN_CENTER_GRAVITY_TRAILER_REAR_AXLE = 26;
	private static final int IND_COL_DISTANCE_BETWEEN_END_TRAILER_HARNESS = 27;
	
	/** constructor
	 *
	 */
	public InputTrucksReader() {
		super();
	}
	
	/**
	 * read input trucks csv file
	 * @param filePathName
	 * @param ref
	 * @throws IOException
	 */
	public void readFile(String filePathName, Referential ref) throws IOException {
		String line = null;	
		int numLine = 0;

		BufferedReader reader = newBufferedReader(get(filePathName), Charset.forName("ISO-8859-1"));

			while ((line = reader.readLine()) != null) {
				if (line.indexOf(Constants.SEP_CSV)!=  (-1)) {

					String[] tabStrs = line.split(Constants.SEP_CSV, -1);

					numLine++;
					if (numLine == 1) continue; // skip the headers line

					if (tabStrs[0].isEmpty()) continue; // skip empty line

					/*
					 * format = ""
					 * example = ""
					 * 
					 */
					String ident = tabStrs[IND_COL_IDENT].trim();
					String product = tabStrs[IND_COL_PRODUCT].replaceAll(" ", "");
					
					InputTruck truck = ref.getRefInputTrucks().get(ref, ident);
					if (truck == null) {
						truck = new InputTruck(ident);
						ref.getRefInputTrucks().add(truck);
						
						/* characteristics of the truck
						 * 
						 */
						String plant = String.format("%1$10s", tabStrs[IND_COL_PLANT].trim()).replace(' ', '0').intern(); 
						truck.setPlant(plant);
						
						truck.setArrivalTime(tabStrs[IND_COL_ARRIVAL_TIME].replaceAll(" ", ""));
						truck.setLength(Integer.parseInt(tabStrs[IND_COL_LENGTH].replaceAll(" ", "")));
						truck.setWidth(Integer.parseInt(tabStrs[IND_COL_WIDTH].replaceAll(" ", "")));
						truck.setHeight(Integer.parseInt(tabStrs[IND_COL_HEIGHT].replaceAll(" ", "")));
						truck.setMaxWeight(Integer.parseInt(tabStrs[IND_COL_MAX_WEIGHT].replaceAll(" ", "")));
						truck.setStackWithMultipleDocks(tabStrs[IND_COL_FLAG_STACK_MULTIPLE_DOCKS].replaceAll(" ", "").compareTo("1")==0);
						truck.setMaxStackDensity(Integer.parseInt(tabStrs[IND_COL_MAX_STACK_DENSITY].replaceAll(" ", "")));												
						truck.setCost(Integer.parseInt(tabStrs[IND_COL_COST].replaceAll(" ", "")));
						
						truck.setMiddleAxleMaxWeight(Double.parseDouble(tabStrs[IND_COL_MIDDLE_AXLE_MAX_WEIGHT].replaceAll(",", "")));
						truck.setRearAxleMaxWeight(Double.parseDouble(tabStrs[IND_COL_REAR_AXLE_MAX_WEIGHT].replaceAll(",", ".")));
						truck.setWeightTractor(Double.parseDouble(tabStrs[IND_COL_WEIGHT_TRACTOR].replaceAll(",", ".")));
						truck.setDistanceFrontMidleAxles(Integer.parseInt(tabStrs[IND_COL_DISTANCE_BETWEEN_FRONT_MIDDLE_AXLES]));				
						truck.setDistanceFrontAxleCenterGravityTractor(Integer.parseInt(tabStrs[IND_COL_DISTANCE_BETWEEN_FRONT_AXLE_CENTER_GRAVITY_TRACTOR]));
						truck.setDistanceFrontAxleHarnessTractor(Integer.parseInt(tabStrs[IND_COL_DISTANCE_BETWEEN_FRONT_AXLE_HARNESS_TRACTOR]));
						truck.setWeightEmptyTrailer(Double.parseDouble(tabStrs[IND_COL_WEIGHT_EMPTY_TRAILER].replaceAll(",", ".")));
						truck.setDistanceHarnessRearAxleTrailer(Integer.parseInt(tabStrs[IND_COL_DISTANCE_BETWEEN_HARNESS_REAR_AXLE_TRAILER]));
						truck.setDistanceTrailerCenterGravityRearAxle(Integer.parseInt(tabStrs[IND_COL_DISTANCE_BETWEEN_CENTER_GRAVITY_TRAILER_REAR_AXLE]));
						truck.setDistanceEndTrailerHarness(Integer.parseInt(tabStrs[IND_COL_DISTANCE_BETWEEN_END_TRAILER_HARNESS]));			
					}
					
					/* updates suppliers, docks and products
					 * 
					 */
					String supplier = String.format("%1$10s", tabStrs[IND_COL_SUPPLIER].trim()).replace(' ', '0').intern(); 
					truck.addSupplierAndLoadingOrder(supplier, Integer.parseInt(tabStrs[IND_COL_SUPPLIER_LOADING_ORDER].replaceAll(" ", "")));
					
					truck.addSupplierDockAndLoadingOrder(tabStrs[IND_COL_SUPPLIER_DOCK].replaceAll(" ", ""), Integer.parseInt(tabStrs[IND_COL_SUPPLIER_DOCK_LOADING_ORDER].replaceAll(" ", "")));
					truck.addPlantDockAndLoadingOrder(tabStrs[IND_COL_PLANT_DOCK].replaceAll(" ", ""), Integer.parseInt(tabStrs[IND_COL_PLANT_DOCK_LOADING_ORDER].replaceAll(" ", "")));
					truck.addProduct(product);
					
					truck.setMaxWeightAboveBottomItemInStacks(product, Integer.parseInt(tabStrs[IND_COL_MAX_WEIGHT_ON_BOTTOM_ITEM].replaceAll(" ", "")));
				}
			}
			
		LOGGER.info("Read of input trucks file : " + ref.getRefInputTrucks().size() + " planned trucks");
	}

}
