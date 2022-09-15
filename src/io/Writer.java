package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import main.Referential;
import tools.Constants;


public class Writer {

	/** constructor
	 * 
	 * @param pathFileName
	 */
	public Writer() {
		super();
	}
	
	 /** write report on the check of an instance
	  * 
	  * @param teamId
	  * @param pathFilename
	  * @throws IOException
	  */
	public void writeReport(Referential ref, String pathFilename) throws IOException {

		File lFile = new File(pathFilename);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(lFile), StandardCharsets.ISO_8859_1);
		BufferedWriter br = new BufferedWriter(outputStreamWriter);

		try {	
			if (ref.getRefAnos().getAll().length > 0) {
				br.write("scope");
				br.write(Constants.SEP_CSV);
				br.write("anomaly");
				br.write(Constants.SEP_CSV);
				br.write("data");
				br.write(Constants.SEP_CSV);
				
				br.write(Constants.LINE_FEED);
	
				for(String ano : ref.getRefAnos().getAll()) {
					br.write(ano);
					br.write(Constants.LINE_FEED);
				}
			} 
			
			if (ref.getRefAnos().getAll().length == 0) {
				br.write("transportation cost");
				br.write(Constants.SEP_CSV);
				br.write("inventory cost");
				br.write(Constants.SEP_CSV);
				br.write("objective function");
				br.write(Constants.SEP_CSV);

				br.write(Constants.LINE_FEED);


				br.write(String.format("%1$.2f", ref.getRefOutputTrucks().getTransportationCost()));
				br.write(Constants.SEP_CSV);
				br.write(String.format("%1$.2f", ref.getRefOutputItems().getInventoryCost()));
				br.write(Constants.SEP_CSV);

				double objectiveFunction = ref.getCoeffTransportationCost()*ref.getRefOutputTrucks().getTransportationCost() 
						+ ref.getCoeffInventoryCost()*ref.getRefOutputItems().getInventoryCost();
				br.write(String.format("%1$.2f", objectiveFunction));
				br.write(Constants.LINE_FEED);
			}
			
			br.flush();

		} finally {
			if (br != null) {
				br.close();
			}
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		}		
	}	
	
}
