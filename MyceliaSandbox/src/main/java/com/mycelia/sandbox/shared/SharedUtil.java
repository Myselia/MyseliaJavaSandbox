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
				return;
			}
			catch(InterruptedException e)
			{
				//Do nothing
			}
		}
	}
}
