package com.mycelia.sandbox.shared;

public class SharedUtil
{
	public static void joindIgnoreInterrupts(Thread thread)
	{
		while(true)
		{
			try
			{
				thread.join();
			}
			catch(InterruptedException e)
			{
				//Do nothing
			}
		}
	}
}
