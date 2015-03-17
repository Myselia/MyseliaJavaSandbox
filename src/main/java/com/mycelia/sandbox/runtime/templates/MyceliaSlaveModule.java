package com.mycelia.sandbox.runtime.templates;

import com.mycelia.common.framework.Task;
import com.mycelia.common.framework.TaskInstance;
import com.mycelia.common.framework.communication.Message;
import com.mycelia.sandbox.constants.MyceliaModuleType;

public abstract class MyceliaSlaveModule extends MyceliaModule{
	
	public MyceliaSlaveModule(){
		super(MyceliaModuleType.SLAVE);
	}
	
	public void sendToMaster(Message message){
		//TODO : BUILD
	}
	
	public Message checkMail(){
		//TODO : BUILD
		return null;
	}
	
	public Task checkTaskList(){
		//TODO : BUILD
		return null;
	}
}
