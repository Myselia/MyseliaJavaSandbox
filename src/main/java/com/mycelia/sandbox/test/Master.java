package com.mycelia.sandbox.test;

import com.mycelia.sandbox.runtime.templates.MyceliaMasterModule;

public class Master extends MyceliaMasterModule{	
	int count = 0;

	public Master(){
		System.out.println("MASTER CREATED");
	}

	
	protected void tick() {
		System.out.println("Sending stuff ... ");
		
		System.out.println("sent");
	}

	@Override
	public void setup() {
		
	}
	
}
