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
import model.OutputItem;
import model.OutputStack;
import model.OutputTruck;
import tools.Constants;

public class OutputItemsReader {

	private static Logger LOGGER = LoggerFactory.getLogger(OutputItemsReader.class);
	 
	private static final int IND_COL_ITEM_ID = 0;
	private static final int IND_COL_TRUCK_ID = 1;
	private static final int IND_COL_STACK_ID = 2;
	private static final int IND_COL_ITEM_CODE = 3;
	private static final int IND_COL_X_ORIGIN = 4;
	private static final int IND_COL_Y_ORIGIN = 5;
	private static final int IND_COL_Z_ORIGIN = 6;
	private static final int IND_COL_X_EXTREMITY = 7;
	private static final int IND_COL_Y_EXTREMITY = 8;
	private static final int IND_COL_Z_EXTREMITY = 9;


	/** constructor
	 *
	 */
	public OutputItemsReader() {
		super();
	}
	
	/**
	 * read output items csv file
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
					 * format = "Item ident;Id truck;Id stack;Item code;X origin;Y origin;Z origin;X extremity;Y extremity;Z extremity"
					 * example = "0090017100_31052022023765;P152050201;P152050201_1;A4;0;0;955;1010;1206;1340"
					 * 
					 */
					String itemId = tabStrs[IND_COL_ITEM_ID].replaceAll(" ", "");
					InputItem inputItem = ref.getRefInputItems().get(itemId);
					if (inputItem == null)  {
						String ano = "output items file; item id unknown in the inputs items; item id = " + itemId;
						ref.getRefAnos().add(ano);
						continue;
					}
					
					String truckId = tabStrs[IND_COL_TRUCK_ID].replaceAll(" ", "");
					OutputTruck truck = ref.getRefOutputTrucks().get(truckId);
					if (truck == null)  {
						String ano = "output items file; truck id unknown in the outputs trucks; truck id = " + truckId;
						ref.getRefAnos().add(ano);
						continue;
					}
					
					String stackId = tabStrs[IND_COL_STACK_ID].replaceAll(" ", "");
					OutputStack stack = ref.getRefOutputStacks().get(stackId);
					if (stack == null)  {
						String ano = "output items file; stack id unknown in the outputs stacks; stack id = " + stackId;
						ref.getRefAnos().add(ano);
						continue;
					}
							
					OutputItem item = new OutputItem();
					item.setInputItem(inputItem);
					item.setOutputTruck(truck);
					item.setOutputStack(stack);
					
					item.setItemCodeDisplay(tabStrs[IND_COL_ITEM_CODE].replaceAll(" ", ""));
					item.setxOrigin(Integer.parseInt(tabStrs[IND_COL_X_ORIGIN].replaceAll(" ", "")));
					item.setyOrigin(Integer.parseInt(tabStrs[IND_COL_Y_ORIGIN].replaceAll(" ", "")));
					item.setzOrigin(Integer.parseInt(tabStrs[IND_COL_Z_ORIGIN].replaceAll(" ", "")));
					item.setxExtremity(Integer.parseInt(tabStrs[IND_COL_X_EXTREMITY].replaceAll(" ", "")));
					item.setyExtremity(Integer.parseInt(tabStrs[IND_COL_Y_EXTREMITY].replaceAll(" ", "")));
					item.setzExtremity(Integer.parseInt(tabStrs[IND_COL_Z_EXTREMITY].replaceAll(" ", "")));

					truck.addSupplier(inputItem.getSupplier());
					
					/* check on item x,y,z coordinates
					 * 
					 */
					boolean ano = false;
					if (item.getxExtremity() != item.getxOrigin() + inputItem.getWidth() && item.getxExtremity() != item.getxOrigin() + inputItem.getLength()) {
						ref.getRefAnos().add("output items file;xExtremity != xOrigin + width &&  xExtremity != xOrigin + length : case forbidden; item id = " + item.getId() 
							+ ";xExtremity = " + Integer.toString(item.getxExtremity())
							+ ";xOrigin = " + Integer.toString(item.getxOrigin())
							+ ";width = " + Integer.toString(inputItem.getWidth()));
						ano = true;
					}
					if (item.getyExtremity() != item.getyOrigin() + inputItem.getWidth() && item.getyExtremity() != item.getyOrigin() + inputItem.getLength()) {
						ref.getRefAnos().add("output items file;yExtremity != yOrigin + width &&  yExtremity != yOrigin + length : case forbidden; item id = " + item.getId() 
							+ ";yExtremity = " + Integer.toString(item.getyExtremity())
							+ ";yOrigin = " + Integer.toString(item.getyOrigin())
							+ ";width = " + Integer.toString(inputItem.getWidth()));
						ano = true;
					}
					if (item.getzOrigin() == 0 && item.getzExtremity() != inputItem.getHeight()) {
						ref.getRefAnos().add("output items file;zExtremity != height when zOrigin=0 : case forbidden; item id = " + item.getId() 
							+ ";zExtremity = " + Integer.toString(item.getzExtremity())
							+ ";zOrigin = " + Integer.toString(item.getzOrigin())
							+ ";height = " + Integer.toString(inputItem.getHeight()));
						ano = true;
					}
					if (item.getzOrigin() != 0 && item.getzExtremity() != item.getzOrigin() + inputItem.getHeight() - inputItem.getNestingHeight()) {
						ref.getRefAnos().add("output items file;zExtremity != zOrigin + height - nestingHeight when zOrigin != 0 : case forbidden; item id = " + item.getId() 
							+ ";zExtremity = " + Integer.toString(item.getzExtremity())
							+ ";zOrigin = " + Integer.toString(item.getzOrigin())
							+ ";height = " + Integer.toString(inputItem.getHeight()));
						ano = true;
					}
					
					/* check on identity between item x,y coordinates and stack x,y coordinates
					 * 
					 */
					if (item.getxOrigin() != stack.getxOrigin()) {
						ref.getRefAnos().add("output items file;item xOrigin != stack xOrigin : case forbidden;item id = " + item.getId() 
						+ ";item xOrigin = " + Integer.toString(item.getxOrigin())
						+ ";stack id = " + stack.getId()
						+ ";stack xOrigin = " + Integer.toString(stack.getxOrigin()));
						ano = true;
					}
					
					if (item.getxExtremity() != stack.getxExtremity()) {
						ref.getRefAnos().add("output items file;item xExtremity != stack xExtremity : case forbidden;item id = " + item.getId() 
						+ ";item xExtremity = " + Integer.toString(item.getxExtremity())
						+ ";stack id = " + stack.getId()
						+ ";stack xExtremity = " + Integer.toString(stack.getxExtremity()));
						ano = true;
					}
					
					if (item.getyOrigin() != stack.getyOrigin()) {
						ref.getRefAnos().add("output items file;item yOrigin != stack yOrigin : case forbidden;item id = " + item.getId() 
						+ ";item yOrigin = " + Integer.toString(item.getyOrigin())
						+ ";stack id = " + stack.getId()
						+ ";stack yOrigin = " + Integer.toString(stack.getyOrigin()));
						ano = true;
					}
					
					if (item.getyExtremity() != stack.getyExtremity()) {
						ref.getRefAnos().add("output items file;item yExtremity != stack yExtremity : case forbidden;item id = " + item.getId() 
						+ ";item yExtremity = " + Integer.toString(item.getyExtremity())
						+ ";stack id = " + stack.getId()
						+ ";stack yExtremity = " + Integer.toString(stack.getyExtremity()));
						ano = true;
					}
					
					if (!ano) 
						ref.getRefOutputItems().add(item);
				}
			}
			
		LOGGER.info("Read of output items file : " + ref.getRefOutputItems().size() + " items");
	}

}
