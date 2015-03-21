package com.mycelia.sandbox.runtime.templates;

import com.mycelia.common.communication.units.Message;
import com.mycelia.sandbox.constants.MyceliaModuleType;

public abstract class MyceliaSlaveModule extends MyceliaModule{
	
	public MyceliaSlaveModule(){
		super(MyceliaModuleType.SLAVE);
	}
	
	public Message checkMail(){
		return null;
	}

}
