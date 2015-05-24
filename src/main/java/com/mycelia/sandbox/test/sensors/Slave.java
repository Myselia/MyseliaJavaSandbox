package com.mycelia.sandbox.test.sensors;

import java.util.Random;

import com.mycelia.common.communication.units.TransmissionBuilder;
import com.mycelia.common.constants.opcode.ActionType;
import com.mycelia.common.constants.opcode.ComponentType;
import com.mycelia.common.constants.opcode.OpcodeAccessor;
import com.mycelia.sandbox.runtime.templates.MyceliaSlaveModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 

import java.util.Enumeration;
import java.util.Scanner;

public class Slave extends MyceliaSlaveModule{
	TransmissionBuilder tb = new TransmissionBuilder();
	ArduinoSensorDriver asd = new ArduinoSensorDriver();
	
	Random random = new Random();
	private int average = 0;
	
	public Slave(){
		System.out.println("BRO SLAVE CREATED");
	}

	@Override
	public void setup() {
		System.out.println("SLAVE SETUP");
		asd.initialize();
		asd.roll();
	}

	@Override
	protected void tick() {
		
		
		tb.newTransmission(
				OpcodeAccessor.make(ComponentType.SANDBOXSLAVE, ActionType.RUNTIME, "DATA"), 
				OpcodeAccessor.make(ComponentType.LENS, ActionType.RUNTIME, "DATA"));	
		
		System.out.println("SLAVE TICK ||" + ArduinoSensorDriver.bro + "||");
		tb.addAtom("Average Value", "int", ArduinoSensorDriver.bro);
		
		mailbox.putInOutQueue(tb.getTransmission());
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}


}
