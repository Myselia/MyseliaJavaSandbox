package com.mycelia.sandbox.constants;

public class SandboxOpcodes
{
	//General
	
	/**
	 * General error opcode.
	 */
	public static final String GENERIC_ERROR=
			OpcodePrefix.SANDBOX_MASTER+OpcodePrefix.ERROR+"0";
	
	/**
	 * Opcode to start a node.
	 */
	public static final String START=
			OpcodePrefix.SANDBOX_MASTER+OpcodePrefix.RUNTIME+"1";
	/**
	 * Opcode to stop a node.
	 */
	public static final String STOP=
			OpcodePrefix.SANDBOX_MASTER+OpcodePrefix.RUNTIME+"2";
	
	//Slave node codes
	
	/**
	 * Opcode to request "start task" on a slave node.
	 */
	public static final String START_TASK_REQUEST_SLAVE=
			OpcodePrefix.SANDBOX_SLAVE+OpcodePrefix.RUNTIME+"3";
	
	/**
	 * Opcode for "start task" answer on a slave node.
	 */
	public static final String START_TASK_ANSWER_SLAVE=
			OpcodePrefix.SANDBOX_SLAVE+OpcodePrefix.RUNTIME+"4";
	
	/**
	 * Opcode to request "get task instance result" on a slave node.
	 */
	public static final String GET_RESULT_REQUEST_SLAVE=
			OpcodePrefix.SANDBOX_SLAVE+OpcodePrefix.RUNTIME+"5";
	
	/**
	 * Opcode for "get task instance result" answer on a slave node.
	 */
	public static final String GET_RESULT_ANSWER_SLAVE=
			OpcodePrefix.SANDBOX_SLAVE+OpcodePrefix.RUNTIME+"6";
	
	/**
	 * Opcode to request "is task instance done" on a slave node.
	 */
	public static final String TASK_STATUS_REQUEST_SLAVE=
			OpcodePrefix.SANDBOX_SLAVE+OpcodePrefix.RUNTIME+"7";
	
	/**
	 * Opcode for "is task instance done" answer on a slave node.
	 */
	public static final String TASK_STATUS_ANSWER_SLAVE=
			OpcodePrefix.SANDBOX_SLAVE+OpcodePrefix.RUNTIME+"8";
}
