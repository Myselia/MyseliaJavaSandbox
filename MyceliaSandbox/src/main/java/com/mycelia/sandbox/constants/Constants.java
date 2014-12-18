package com.mycelia.sandbox.constants;

/**
 * Global application constants.
 */
public class Constants
{
	/**
	 * Default user opcode for when the user does not specify it.
	 */
	public static final String DEFAULT_USER_OPCODE_PREFIX=
			OpcodePrefix.USER+OpcodePrefix.DATA+"0";
	
	/**
	 * The name the master node's thread is going to have when using a RuntimeType Local.
	 */
	public static final String MASTER_NODE_THREAD_NAME="Master Node Deamon Thread";
	
	/**
	 * The name prefix for the slave nodes' thread when using a RuntimeType Local.
	 */
	public static final String SLAVE_NODE_THREAD_NAME="Slave Node Deamon Thread";
	
	/**
	 * Node ID representing a transmission to any node.
	 */
	public static final String ANY_SLAVE_NODE="ANYSLAVE";
}
