package com.mycelia.sandbox.test;

import com.mycelia.common.communication.structures.Message;
import com.mycelia.sandbox.runtime.templates.MyceliaMasterModule;

public class Master extends MyceliaMasterModule{	
	int count = 0;

	public Master(){
		System.out.println("MASTER CREATED");
	}

	
	protected void tick() {
		System.out.println("Sending stuff ... ");
		Message bob = new Message(new String(count++ + " "));
		messagemailbox.send(bob);
		System.out.println("sent");
	}

	@Override
	public void setup() {
		
	}
	
}
