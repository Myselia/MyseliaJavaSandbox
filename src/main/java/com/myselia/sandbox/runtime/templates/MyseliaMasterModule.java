package com.myselia.sandbox.runtime.templates;

import com.myselia.sandbox.constants.MyseliaModuleType;
import com.myselia.sandbox.runtime.network.VirtualSlave;

public abstract class MyseliaMasterModule extends MyseliaModule{
	
	public VirtualSlave[] slaves;
	
	public MyseliaMasterModule(){
		super(MyseliaModuleType.MASTER);
	}
	
	public VirtualSlave[] allocateVirtualSlaves(int count, char[] uid){
		if(uid.length != count){
			System.err.println("Wrong uid count for VirtualSlave[] allocation");
			return null;
		}
		
		VirtualSlave[] slaves = new VirtualSlave[count];
		for(int i = 0; i < count; i++){
			slaves[i] = new VirtualSlave(uid[i]);
		}
		
		return slaves;
		
	}

}
