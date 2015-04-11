package com.mycelia.sandbox.test;

import java.util.Random;

import com.mycelia.common.communication.units.TransmissionBuilder;
import com.mycelia.common.constants.opcode.ActionType;
import com.mycelia.common.constants.opcode.ComponentType;
import com.mycelia.common.constants.opcode.OpcodeAccessor;
import com.mycelia.sandbox.runtime.templates.MyceliaSlaveModule;

public class Slave extends MyceliaSlaveModule{
	TransmissionBuilder tb = new TransmissionBuilder();
	
	Random random = new Random();
	private int rand = 0;
	
	public Slave(){
		System.out.println("SLAVE CREATED");
	}

	@Override
	public void setup() {

	}

	@Override
	protected void tick() {
		new_number();
		
		tb.newTransmission(
				OpcodeAccessor.make(ComponentType.SANDBOXSLAVE, ActionType.RUNTIME, "DATA"), 
				OpcodeAccessor.make(ComponentType.LENS, ActionType.RUNTIME, "DATA"));
		tb.addAtom("random", "int", Integer.toString(rand));
		mailbox.putInOutQueue(tb.getTransmission());
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void new_number(){
		this.rand = random.nextInt(9);
	}

}
