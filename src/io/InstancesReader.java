package io;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Instance;
import tools.Constants;

public class InstancesReader {

	private static Logger LOGGER = LoggerFactory.getLogger(InstancesReader.class);
	 
	private static final int IND_COL_INPUT_ITEMS = 0;
	private static final int IND_COL_INPUT_TRUCKS = 1;
	private static final int IND_COL_INPUT_PARAMS = 2;
	private static final int IND_COL_OUTPUT_ITEMS = 3;
	private static final int IND_COL_OUTPUT_STACKS = 4;
	private static final int IND_COL_OUTPUT_TRUCKS = 5;
	private static final int IND_COL_REPORT = 6;

	/** constructor
	 * 
	 */
	public InstancesReader() {
		super();
	}

	/** read file of instances
	 * 
	 * @param pathFilename
	 * @return
	 * @throws IOException
	 */
	public List<Instance> readFile(String pathFilename) throws IOException {
		List<Instance> instances = new ArrayList<Instance>();

		String line = null;	
		int numLine = 0;
		BufferedReader reader = newBufferedReader(get(pathFilename), Charset.forName("ISO-8859-1"));

		while ((line = reader.readLine()) != null) {
			if (line.indexOf(Constants.SEP_CSV)!=  (-1)) {

				String[] tabStrs = line.split(Constants.SEP_CSV, -1);

				numLine++;
				if (numLine == 1) continue; // skip the headers line

				if (tabStrs[0].isEmpty()) continue; // skip empty line

				Instance instance = new Instance();

				instance.setInputItemsPathFilename(tabStrs[IND_COL_INPUT_ITEMS].trim());
				instance.setInputTrucksPathFilename(tabStrs[IND_COL_INPUT_TRUCKS].trim());
				instance.setInputParametersPathFilename(tabStrs[IND_COL_INPUT_PARAMS].trim());

				instance.setOutputItemsPathFilename(tabStrs[IND_COL_OUTPUT_ITEMS].trim());
				instance.setOutputStacksPathFilename(tabStrs[IND_COL_OUTPUT_STACKS].trim());
				instance.setOutputTrucksPathFilename(tabStrs[IND_COL_OUTPUT_TRUCKS].trim());

				instance.setReportPathFilename(tabStrs[IND_COL_REPORT].trim());

				instances.add(instance);
			}
		}
		LOGGER.info("Read of instances file : " + instances.size() + " instances");
		return instances;
	}
}
