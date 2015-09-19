package com.myselia.sandbox.runtime.network;

import com.myselia.javacommon.topology.MyseliaUUID;

public class VirtualSlave {
	private char slaveid;
	private MyseliaUUID muuid = new MyseliaUUID();
	
	public VirtualSlave(char slaveid){
		this.slaveid = slaveid;
	}
	
	public void assignMyseliaUUID(MyseliaUUID muuid){
		this.muuid = muuid;
	}
	
	public MyseliaUUID getMyseliaUUID(){
		return muuid;
	}

}
