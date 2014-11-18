package com.mycelia.sandbox.constants;

public class Constants
{
	/**
	 * Prefix used for Transmission opcodes related to user transmissions.
	 */
	public static final String USER_OPCODE_PREFIX="2";
	
	/**
	 * Default user opcode for when the user does not specify it.
	 */
	public static final String DEFAULT_USER_OPCODE_PREFIX=USER_OPCODE_PREFIX+"00";
	
	/**
	 * Prefix used for Transmission opcodes related to the sandbox.
	 */
	public static final String SANDBOX_OPCODE_PREFIX="9";
	
	/**
	 * Opcode to start a node.
	 */
	public static final String START_SANDBOX_OPCODE=SANDBOX_OPCODE_PREFIX+"01";
	
	/**
	 * Opcode to stop a node.
	 */
	public static final String STOP_SANDBOX_OPCODE=SANDBOX_OPCODE_PREFIX+"02";
	
	public static final String MASTER_NODE_THREAD_NAME="Master Node Deamon Thread";
	
	public static final String ANY_SLAVE_NODE="ANYSLAVE";
}
