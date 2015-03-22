package com.mycelia.sandbox.test;

import com.mycelia.common.communication.units.TransmissionBuilder;
import com.mycelia.common.constants.opcode.ActionType;
import com.mycelia.common.constants.opcode.ComponentType;
import com.mycelia.common.constants.opcode.OpcodeAccessor;
import com.mycelia.sandbox.runtime.templates.MyceliaMasterModule;

public class Master extends MyceliaMasterModule {
	int count = 0;
	TransmissionBuilder tb = new TransmissionBuilder();

	public Master() {
		System.out.println("MASTER CREATED");
	}

	protected void tick() {
		System.out.print("...setting up transmission : ");
		tb.newTransmission(
				OpcodeAccessor.make(ComponentType.SANDBOXMASTER, ActionType.RUNTIME, "DATA"), 
				OpcodeAccessor.make(ComponentType.LENS, ActionType.RUNTIME, "DATA"));
		tb.addAtom("Count", "int", Integer.toString(count));
		mailbox.putInOutQueue(tb.getTransmission());
		System.out.print("done...");
		System.out.println(mailbox.getOutQueueSize());
		count++;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setup() {

	}

}
