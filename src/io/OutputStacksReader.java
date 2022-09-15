package io;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.Referential;
import model.OutputStack;
import model.OutputTruck;
import tools.Constants;

public class OutputStacksReader {

	private static Logger LOGGER = LoggerFactory.getLogger(OutputStacksReader.class);
	 
	private static final int IND_COL_TRUCK_ID = 0;
	private static final int IND_COL_STACK_ID = 1;
	private static final int IND_COL_STACK_CODE = 2;
	private static final int IND_COL_X_ORIGIN = 3;
	private static final int IND_COL_Y_ORIGIN = 4;
	private static final int IND_COL_Z_ORIGIN = 5;
	private static final int IND_COL_X_EXTREMITY = 6;
	private static final int IND_COL_Y_EXTREMITY = 7;
	private static final int IND_COL_Z_EXTREMITY = 8;


	/** constructor
	 *
	 */
	public OutputStacksReader() {
		super();
	}
	
	/**
	 * read output stacks csv file
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
					 * format = "Id truck;Id stack;Stack code;X origin;Y origin;Z origin;X extremity;Y extremity;Z extremity"
					 * example = "P152050201;P152050201_4;D;1010;1206;0;2020;2412;2555"
					 * 
					 */
					String truckId = tabStrs[IND_COL_TRUCK_ID].replaceAll(" ", "");
					OutputTruck truck = ref.getRefOutputTrucks().get(truckId);
					if (truck == null)  {
							ref.getRefAnos().add("output stacks file; truck id unknown in the outputs trucks; truck id = " + truckId);
						continue;
					}
					
					OutputStack stack = new OutputStack();
					stack.setIdStack(tabStrs[IND_COL_STACK_ID].replaceAll(" ", ""));
					stack.setStackCodeDisplay(tabStrs[IND_COL_STACK_CODE].replaceAll(" ", ""));
					stack.setxOrigin(Integer.parseInt(tabStrs[IND_COL_X_ORIGIN].replaceAll(" ", "")));
					stack.setyOrigin(Integer.parseInt(tabStrs[IND_COL_Y_ORIGIN].replaceAll(" ", "")));
					stack.setzOrigin(Integer.parseInt(tabStrs[IND_COL_Z_ORIGIN].replaceAll(" ", "")));
					stack.setxExtremity(Integer.parseInt(tabStrs[IND_COL_X_EXTREMITY].replaceAll(" ", "")));
					stack.setyExtremity(Integer.parseInt(tabStrs[IND_COL_Y_EXTREMITY].replaceAll(" ", "")));
					stack.setzExtremity(Integer.parseInt(tabStrs[IND_COL_Z_EXTREMITY].replaceAll(" ", "")));

					stack.setOutputTruck(truck);

					boolean ano = false;
					if (stack.getxExtremity() <= stack.getxOrigin()) {
						ref.getRefAnos().add("output stacks file; xExtremity <= xOrigin : case forbidden; stack id = " + stack.getId() 
							+ ";xExtremity = " + stack.getxExtremity()
							+ ";xOrigin = " + stack.getxOrigin());
						ano = true;
					}
					if (stack.getxExtremity() <= stack.getxOrigin()) {
						ref.getRefAnos().add("output stacks file; yExtremity < yOrigin : case forbidden; stack id = " + stack.getId() 
							+ ";yExtremity = " + stack.getyExtremity()
							+ ";yOrigin = " + stack.getyOrigin());
						ano = true;
					}
					if (stack.getzExtremity() <= stack.getzOrigin()) {
						ref.getRefAnos().add("output stacks file; zExtremity <= zOrigin : case forbidden; stack id = " + stack.getId() 
							+ ";zExtremity = " + stack.getzExtremity()
							+ ";zOrigin = " + stack.getzOrigin());
						ano = true;
					}
					if (stack.getzOrigin() != 0) {
						ref.getRefAnos().add("output stacks file; zOrigin != 0 : case forbidden; stack id = " + stack.getId() 
							+ ";zOrigin = " + stack.getzOrigin());
						ano = true;
					}
					if (!ano) ref.getRefOutputStacks().add(stack);
				}
			}
			
		LOGGER.info("Read of output stacks file : " + ref.getRefOutputStacks().size() + " stacks");
	}
}
