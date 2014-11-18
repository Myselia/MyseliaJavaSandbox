package com.mycelia.sandbox.runtime.local;

import com.mycelia.sandbox.communication.bean.Transmission;

public class VirtualStem
{
	private static VirtualStem instance;
	
	public static VirtualStem getInstance()
	{
		if(instance==null)
		{
			instance=new VirtualStem();
		}
		
		return instance;
	}
	
	private VirtualStem()
	{
		//Do nothing
	}
	
	public void routeTransmission(Transmission transmission)
	{
		//This needs to be thread safe
	}
}
