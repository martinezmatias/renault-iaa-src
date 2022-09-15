package model;

public class Instance {

	private static int INDEX = 0;
	
	private int ident;
	
	private String inputItemsPathFilename;
	private String inputTrucksPathFilename;
	private String inputParametersPathFilename;
	
	private String outputItemsPathFilename;
	private String outputStacksPathFilename;
	private String outputTrucksPathFilename;
	
	private String reportPathFilename;
	
	/** constructor
	 * 
	 */
	public Instance() {
		this.ident = INDEX;
		INDEX++;
	}

	
	/**
	 * @param inputItemsPathFilename the inputItemsPathFilename to set
	 */
	public void setInputItemsPathFilename(String inputItemsPathFilename) {
		this.inputItemsPathFilename = inputItemsPathFilename;
	}


	/**
	 * @param inputTrucksPathFilename the inputTrucksPathFilename to set
	 */
	public void setInputTrucksPathFilename(String inputTrucksPathFilename) {
		this.inputTrucksPathFilename = inputTrucksPathFilename;
	}


	/**
	 * @param inputParametersPathFilename the inputParametersPathFilename to set
	 */
	public void setInputParametersPathFilename(String inputParametersPathFilename) {
		this.inputParametersPathFilename = inputParametersPathFilename;
	}


	/**
	 * @param outputItemsPathFilename the outputItemsPathFilename to set
	 */
	public void setOutputItemsPathFilename(String outputItemsPathFilename) {
		this.outputItemsPathFilename = outputItemsPathFilename;
	}


	/**
	 * @param outputStacksPathFilename the outputStacksPathFilename to set
	 */
	public void setOutputStacksPathFilename(String outputStacksPathFilename) {
		this.outputStacksPathFilename = outputStacksPathFilename;
	}


	/**
	 * @param outputTrucksPathFilename the outputTrucksPathFilename to set
	 */
	public void setOutputTrucksPathFilename(String outputTrucksPathFilename) {
		this.outputTrucksPathFilename = outputTrucksPathFilename;
	}


	/**
	 * @param reportPathFilename the reportPathFilename to set
	 */
	public void setReportPathFilename(String reportPathFilename) {
		this.reportPathFilename = reportPathFilename;
	}


	/**
	 * @return the ident
	 */
	public int getIdent() {
		return ident;
	}

	/**
	 * @return the inputItemsPathFilename
	 */
	public String getInputItemsPathFilename() {
		return inputItemsPathFilename;
	}

	/**
	 * @return the inputTrucksPathFilename
	 */
	public String getInputTrucksPathFilename() {
		return inputTrucksPathFilename;
	}

	/**
	 * @return the inputParametersPathFilename
	 */
	public String getInputParametersPathFilename() {
		return inputParametersPathFilename;
	}

	/**
	 * @return the outputItemsPathFilename
	 */
	public String getOutputItemsPathFilename() {
		return outputItemsPathFilename;
	}

	/**
	 * @return the outputStacksPathFilename
	 */
	public String getOutputStacksPathFilename() {
		return outputStacksPathFilename;
	}

	/**
	 * @return the outputTrucksPathFilename
	 */
	public String getOutputTrucksPathFilename() {
		return outputTrucksPathFilename;
	}

	/**
	 * @return the reportPathFilename
	 */
	public String getReportPathFilename() {
		return reportPathFilename;
	}

}
