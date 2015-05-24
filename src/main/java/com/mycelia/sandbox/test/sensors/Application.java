package com.mycelia.sandbox.test.sensors;

import com.mycelia.sandbox.runtime.templates.MyceliaApplication;

public class Application {
	
	public static void main(String[] args){
		MyceliaApplication<Master, Slave> app = new MyceliaApplication<Master, Slave>(Master.class, Slave.class, args);	
		
		System.out.println("Myselia Sensor App");
		
		app.start();
		try {
			Thread.sleep(60*60*1000);
		} catch (Exception e) {
			System.err.println("application thread error");
		}
		app.stop();
		
		System.out.println("App Stopped --------------------------------");
	}

}
