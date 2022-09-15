/**
 * 
 */
package main;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.InputItemsReader;
import io.InputParametersReader;
import io.InputTrucksReader;
import io.InstancesReader;
import io.OutputItemsReader;
import io.OutputStacksReader;
import io.OutputTrucksReader;
import io.Writer;
import model.Instance;

/**
 * @author a193857
 *
 */
public class Main {

	private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

	// pour interdire l'instanciation avec le constructeur par défaut
	private Main() {
		super();
	}

	/**
	 * main
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// System.out.println("Matias test\n");

		if (args.length < 1)
			throw new IllegalArgumentException("usage : exe pathFileName_instances");

		try {
			LOGGER.info("read file of instances");

			InstancesReader reader = new InstancesReader();
			List<Instance> instances = reader.readFile(args[0]);

			Writer writer = new Writer();

			for (Instance instance : instances) {
				Referential ref = new Referential();

				LOGGER.info("read instance " + Integer.toString(instance.getIdent()));

				LOGGER.info("read parameters file " + instance.getInputParametersPathFilename());
				InputParametersReader inputParametersReader = new InputParametersReader();
				inputParametersReader.readFile(instance.getInputParametersPathFilename(), ref);

				LOGGER.info("read input items file " + instance.getInputItemsPathFilename());
				InputItemsReader inputItemsReader = new InputItemsReader();
				inputItemsReader.readFile(instance.getInputItemsPathFilename(), ref);

				LOGGER.info("read input trucks file " + instance.getInputTrucksPathFilename());
				InputTrucksReader inputTrucksReader = new InputTrucksReader();
				inputTrucksReader.readFile(instance.getInputTrucksPathFilename(), ref);

				LOGGER.info("read output trucks file " + instance.getOutputTrucksPathFilename());
				OutputTrucksReader outputTrucksReader = new OutputTrucksReader();
				outputTrucksReader.readFile(instance.getOutputTrucksPathFilename(), ref);

				LOGGER.info("read output stacks file " + instance.getOutputStacksPathFilename());
				OutputStacksReader outputStacksReader = new OutputStacksReader();
				outputStacksReader.readFile(instance.getOutputStacksPathFilename(), ref);

				LOGGER.info("read output items file " + instance.getOutputItemsPathFilename());
				OutputItemsReader outputItemsReader = new OutputItemsReader();
				outputItemsReader.readFile(instance.getOutputItemsPathFilename(), ref);

				LOGGER.info("check all constraints");
				ref.getChecker().checkAllConstraints();

				if (ref.getRefAnos().getAll().length == 0) {
					LOGGER.info("calculation of transportation costs");
					ref.getRefOutputTrucks().calculateTransportationCost(ref);

					LOGGER.info("calculation of inventory costs");
					ref.getRefOutputItems().calculateInventoryCost(ref);
				}

				LOGGER.info("write report on instance " + Integer.toString(instance.getIdent()));
				writer.writeReport(ref, instance.getReportPathFilename());
			}

			LOGGER.info("end of program");

		} catch (Exception e1) {

			LOGGER.error("Crash !!", e1);

			for (int i = 0; i < e1.getStackTrace().length; i++) {
				String msgErr = e1.getStackTrace()[i].toString();
				LOGGER.error(msgErr);
			}
		}

	}
}
