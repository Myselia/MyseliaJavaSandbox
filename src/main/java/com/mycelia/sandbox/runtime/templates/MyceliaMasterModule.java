package com.mycelia.sandbox.runtime.templates;

import com.mycelia.common.communication.units.Message;
import com.mycelia.sandbox.constants.MyceliaModuleType;

public abstract class MyceliaMasterModule extends MyceliaModule{
	
	public MyceliaMasterModule(){
		super(MyceliaModuleType.MASTER);
	}
	
	private Message checkMail(){
		//TODO : BUILD
		return null;
	}
	
}
