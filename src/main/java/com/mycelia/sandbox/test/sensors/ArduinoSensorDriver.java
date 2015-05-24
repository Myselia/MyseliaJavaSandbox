package com.mycelia.sandbox.test.sensors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;

public class ArduinoSensorDriver implements SerialPortEventListener {

	public static volatile String bro = "";

	static SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1",
		"/dev/ttyACM0", // Raspberry Pi
		"/dev/ttyACM1", // Raspberry Pi
		"/dev/ttyACM2", // Raspberry Pi
		"/dev/ttyUSB0", // Linux
		"/dev/ttyUSB1", // Linux
		"/dev/ttyUSB2", // Linux
		"/dev/ttyUSB3", // Linux
		"/dev/ttyUSB4", // Linux
		"/dev/ttyUSB5", // Linux
		"COM3", // Windows
		"COM4", // Windows
		"COM5", // Windows
		"COM6", // Windows
		"COM7", // Windows
		"COM8", // Windows
		"COM9", // Windows
		"/dev/ttyS80" //symbolic
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private static BufferedReader input;
	/** The output stream to the port */
	private static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
		System.out.println("INITIALIZING...");
		// the next line is for Raspberry Pi and
		// gets us into the while loop and was suggested here was suggested
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		  System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
		try {
			portId = CommPortIdentifier.getPortIdentifier(PORT_NAMES[1]);
			System.out.println("BRO");
		} catch (NoSuchPortException e1) {
			System.out.println("BRAH");
		}
		
		System.out.println("VERSION NUMBER : 01 : " + portEnum);
		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			
			System.out.println("CHECKING ENUMERATION ELEMENTS : " + currPortId.getName());
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				} else {
					System.out.println("Checking port : " + portName);
				}
			}
		}
		
		if (portId == null) {
			System.err.println("Could not find serial port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public static synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				System.out.println("Recieved from Arduino: ||" + inputLine
						+ "||");
				bro = inputLine;
				System.out.println("Forwarded.");
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	public void roll() {
		System.out.println("ROLLING...");

		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for n seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				int n = 300;
				try {
					Thread.sleep(n * 1000);
				} catch (InterruptedException ie) {
				}
				close();
			}
		};
		t.start();

		System.out.println("Driver Started");
	}

}
