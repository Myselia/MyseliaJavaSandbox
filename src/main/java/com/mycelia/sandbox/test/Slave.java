package com.mycelia.sandbox.test;

import com.mycelia.sandbox.runtime.templates.MyceliaSlaveModule;

public class Slave extends MyceliaSlaveModule{
	
	public Slave(){
		System.out.println("SLAVE CREATED");
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void tick() {
		// TODO Auto-generated method stub
		
	}

}
