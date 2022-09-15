package io;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.Referential;
import tools.Constants;

public class InputParametersReader {

	private static Logger LOGGER = LoggerFactory.getLogger(InputParametersReader.class);
	 
	private static final int IND_COL_COEFF_INVENTORY_COST = 0;
	private static final int IND_COL_COEFF_TRANSPORTATION_COST = 1;
	private static final int IND_COL_COEFF_COST_EXTRA_TRUCK = 2;
	

	/** constructor
	 *
	 */
	public InputParametersReader() {
		super();
	}
	
	/**
	 * read input parameters csv file
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
					 * format = "Coefficient inventory cost;Coefficient transportation cost;Coefficient cost extra truck"
					 * example = "10,0;1,0;0,2"
					 * 
					 */
					ref.setCoeffInventoryCost(Double.parseDouble(tabStrs[IND_COL_COEFF_INVENTORY_COST].trim().replace(',', '.')));
					ref.setCoeffTransportationCost(Double.parseDouble(tabStrs[IND_COL_COEFF_TRANSPORTATION_COST].trim().replace(',', '.')));
					ref.setCoeffCostExtraTruck(Double.parseDouble(tabStrs[IND_COL_COEFF_COST_EXTRA_TRUCK].trim().replace(',', '.')));
				}
			}
			
		LOGGER.info("Read of parameters file");
	}


}
