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
import model.OutputTruck;
import tools.Constants;

public class OutputTrucksReader {

	private static Logger LOGGER = LoggerFactory.getLogger(OutputTrucksReader.class);
	 
	private static final int IND_COL_TRUCK_ID = 0;
	private static final int IND_COL_LENGTH = 1;
	private static final int IND_COL_WEIGHT = 2;
	private static final int IND_COL_VOLUME = 3;
	private static final int IND_COL_WEIGHT_MIDDLE_AXLE = 4;
	private static final int IND_COL_WEIGHT_REAR_AXLE = 5;


	/** constructor
	 *
	 */
	public OutputTrucksReader() {
		super();
	}
	
	/**
	 * read output trucks csv file
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
					 * format = "Id truck;Loaded length;Weight of loaded items;Volume of loaded items;EMm;EMr"
					 * example = "P206050205;10460;22854,000;52,524;16379,491;13901,224"
					 * 
					 */
					String truckId = tabStrs[IND_COL_TRUCK_ID].replaceAll(" ", "");
					InputTruck inputTruck = ref.getRefInputTrucks().get(ref, truckId);
					if (inputTruck == null)  {
						String ano = "output trucks file; truck id unknown in the input trucks; truck id = " + truckId;
						ref.getRefAnos().add(ano);
						continue;
					}
					
					OutputTruck truck = new OutputTruck();
					truck.setId(truckId);
					truck.setInputTruck(inputTruck);
					truck.setExtraTruck(ref.getRefOutputTrucks().isExtraTruckId(truckId));

					truck.setLoadedLength(Integer.parseInt(tabStrs[IND_COL_LENGTH]));
					truck.setLoadedWeight(Double.parseDouble(tabStrs[IND_COL_WEIGHT].replaceAll(",", ".")));
					truck.setLoadedVolume(Double.parseDouble(tabStrs[IND_COL_VOLUME].replaceAll(",", ".")));
					truck.setWeightMiddleAxleTrailer(Double.parseDouble(tabStrs[IND_COL_WEIGHT_MIDDLE_AXLE].replaceAll(",", ".")));
					truck.setWeightRearAxleTrailer(Double.parseDouble(tabStrs[IND_COL_WEIGHT_REAR_AXLE].replaceAll(",", ".")));

					ref.getRefOutputTrucks().add(truck);
				}
			}
			
		LOGGER.info("Read of output trucks file : " + ref.getRefOutputTrucks().size() + " trucks");
	}

}
