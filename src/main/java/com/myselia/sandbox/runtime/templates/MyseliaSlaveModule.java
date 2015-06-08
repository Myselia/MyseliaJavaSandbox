package com.myselia.sandbox.runtime.templates;

import com.myselia.sandbox.constants.MyseliaModuleType;

public abstract class MyseliaSlaveModule extends MyseliaModule{
	
	public MyseliaSlaveModule(){
		super(MyseliaModuleType.SLAVE);
	}

}
