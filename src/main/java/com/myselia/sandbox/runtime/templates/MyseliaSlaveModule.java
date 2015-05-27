package com.myselia.sandbox.runtime.templates;

import com.myselia.javacommon.communication.units.Message;
import com.myselia.sandbox.constants.MyseliaModuleType;

public abstract class MyseliaSlaveModule extends MyseliaModule{
	
	public MyseliaSlaveModule(){
		super(MyseliaModuleType.SLAVE);
	}
	
	public Message checkMail(){
		return null;
	}

}
