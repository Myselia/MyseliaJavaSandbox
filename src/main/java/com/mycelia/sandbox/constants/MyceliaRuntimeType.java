package com.mycelia.sandbox.constants;

/**
 * The different Mycelia Runtime types
 *
 * Some Runtimes are more suited for resource-heavy applications or parallel computing
 */
public enum MyceliaRuntimeType {
	
	/**
	 * A Mycelia Runtime running on the local computer where the application is started
	 * 
	 * This Runtime type uses an inner executor to run the module instances.
	 * Transmissions are directly piped from the different module instances.
	 */
	LOCAL,
	
	/**
	 * A Mycelia Runtime spanning a computer network or a node grid.
	 * 
	 * This Runtime type uses a separate computer to execute each module instance.
	 * The computer starting the application is only used as a master module executor.
	 * Network Transmissions are used to communicate from module instances.
	 */
	NETWORK
}
