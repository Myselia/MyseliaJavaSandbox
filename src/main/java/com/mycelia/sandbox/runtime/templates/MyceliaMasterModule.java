package com.mycelia.sandbox.runtime.templates;

import com.mycelia.common.framework.TaskInstance;
import com.mycelia.common.framework.communication.Message;
import com.mycelia.sandbox.constants.MyceliaModuleType;

public abstract class MyceliaMasterModule extends MyceliaModule implements Runnable{
	
	public MyceliaMasterModule(){
		super(MyceliaModuleType.MASTER);
	}
	
	public void sendToNode(String nodeId, TaskInstance task){
		//TODO : BUILD
	}
	
	public void sendtoNode(String nodeId, Message message){
		//TODO : BUILD
	}
	
	public Message checkMail(){
		//TODO : BUILD
		return null;
	}
	
}
