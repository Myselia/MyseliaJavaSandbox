package com.myselia.sandbox.runtime.templates;

import com.myselia.javacommon.communication.units.Message;
import com.myselia.sandbox.constants.MyseliaModuleType;

public abstract class MyseliaMasterModule extends MyseliaModule{
	
	public MyseliaMasterModule(){
		super(MyseliaModuleType.MASTER);
	}
	
	private Message checkMail(){
		//TODO : BUILD
		return null;
	}
	
}
