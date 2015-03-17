package com.mycelia.sandbox.runtime.templates;

import com.mycelia.sandbox.constants.MyceliaModuleType;

/**
 * A Mycelia Module capable of processing part of a Mycelia Application
 * and communicate with other Mycelia Modules
 *
 */
public abstract class MyceliaModule implements Runnable{
	
	private String nodeId;
	private MyceliaModuleType moduleType;
	
	public MyceliaModule(MyceliaModuleType moduleType){
		this.moduleType = moduleType;
	}
	
	public abstract void setup();

}
