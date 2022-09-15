package io;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.Referential;
import model.InputItem;
import tools.Constants;


public class InputItemsReader {

	private static Logger LOGGER = LoggerFactory.getLogger(InputItemsReader.class);
	 
	private static final int IND_COL_ID = 0;
	private static final int IND_COL_SUPPLIER = 1;
	private static final int IND_COL_SUPPLIER_DOCK = 2;
	private static final int IND_COL_PLANT = 3;
	private static final int IND_COL_PLANT_DOCK = 4;
	private static final int IND_COL_PRODUCT = 5;
	private static final int IND_COL_PACKAGE = 6;
	private static final int IND_COL_NB_ITEMS = 7;
	private static final int IND_COL_LENGTH = 8;
	private static final int IND_COL_WIDTH = 9;
	private static final int IND_COL_HEIGHT = 10;
	private static final int IND_COL_WEIGHT = 11;
	private static final int IND_COL_NESTING_HEIGHT = 12;
	private static final int IND_COL_STACKABILITY_CODE = 13;
	private static final int IND_COL_FORCED_ORIENTATION = 14;
	private static final int IND_COL_EARLIEST_ARRIVAL_TIME = 15;
	private static final int IND_COL_LATEST_ARRIVAL_TIME = 16;
	private static final int IND_COL_INVENTORY_COST = 17;
	private static final int IND_COL_MAX_STACKABILITY = 18;

	/** constructor
	 *
	 */
	public InputItemsReader() {
		super();
	}
	
	/**
	 * read input items csv file
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
					InputItem item = new InputItem();
					
					String itemId = String.format("%1$15s", tabStrs[IND_COL_ID].trim()).replace(' ', '0').intern(); 
					item.setIdent(itemId);
					
					String supplier = String.format("%1$10s", tabStrs[IND_COL_SUPPLIER].trim()).replace(' ', '0').intern(); 
					item.setSupplier(supplier);
			
					item.setSupplierDock(tabStrs[IND_COL_SUPPLIER_DOCK].replaceAll(" ", ""));
					
					String plant = String.format("%1$10s", tabStrs[IND_COL_PLANT].trim()).replace(' ', '0').intern(); 
					item.setPlant(plant);
					
					item.setPlantDock(tabStrs[IND_COL_PLANT_DOCK].replaceAll(" ", ""));
					item.setProduct(tabStrs[IND_COL_PRODUCT].replaceAll(" ", ""));
					item.setPackaging(tabStrs[IND_COL_PACKAGE].replaceAll(" ", ""));
					item.setNbItems(Integer.parseInt(tabStrs[IND_COL_NB_ITEMS].replaceAll(" ", "")));
					item.setLength(Integer.parseInt(tabStrs[IND_COL_LENGTH].replaceAll(" ", "")));
					item.setWidth(Integer.parseInt(tabStrs[IND_COL_WIDTH].replaceAll(" ", "")));
					item.setHeight(Integer.parseInt(tabStrs[IND_COL_HEIGHT].replaceAll(" ", "")));
					item.setWeight(Double.parseDouble(tabStrs[IND_COL_WEIGHT].replaceAll(" ", "").replace(',', '.')));
					item.setNestingHeight(Integer.parseInt(tabStrs[IND_COL_NESTING_HEIGHT].replaceAll(" ", "")));
					item.setStackabilityCode(tabStrs[IND_COL_STACKABILITY_CODE].replaceAll(" ", ""));
					item.setForcedOrientation(tabStrs[IND_COL_FORCED_ORIENTATION].replaceAll(" ", "").toUpperCase());
					item.setEarliestArrivalTime(tabStrs[IND_COL_EARLIEST_ARRIVAL_TIME].replaceAll(" ", ""));
					item.setLatestArrivalTime(tabStrs[IND_COL_LATEST_ARRIVAL_TIME].replaceAll(" ", ""));
					item.setInventoryCost(Integer.parseInt(tabStrs[IND_COL_INVENTORY_COST].replaceAll(" ", "")));
					item.setMaxStackability(Integer.parseInt(tabStrs[IND_COL_MAX_STACKABILITY].replaceAll(" ", "")));
			
					ref.getRefInputItems().add(item);
				}
			}
			
		LOGGER.info("Read of input items file : " + ref.getRefInputItems().size() + " items");
	}

}
