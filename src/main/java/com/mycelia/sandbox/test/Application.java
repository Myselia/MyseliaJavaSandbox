package com.mycelia.sandbox.test;

import com.mycelia.sandbox.constants.MyceliaModuleType;
import com.mycelia.sandbox.constants.MyceliaRuntimeType;
import com.mycelia.sandbox.runtime.templates.MyceliaApplication;

public class Application {
	
	public static void main(String[] args){
		
		System.out.println("setup began");
		
		MyceliaApplication<Master, Slave> app = new MyceliaApplication<Master, Slave>(Master.class, Slave.class);
		
		app.setRuntimeType(MyceliaRuntimeType.NETWORK);
		app.setSlaveCount(5);
		app.setModuleType(MyceliaModuleType.MASTER);
		
		System.out.println("app started --------------------------------");
		
		app.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.err.println("application thread error");
		}
		app.stop();
		
		System.out.println("app stopped --------------------------------");
	}

}