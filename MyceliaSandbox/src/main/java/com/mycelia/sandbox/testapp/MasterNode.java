package com.mycelia.sandbox.testapp;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycelia.sandbox.framework.MyceliaMasterNode;
import com.mycelia.sandbox.framework.Task;
import com.mycelia.sandbox.shared.SharedUtil;

public class MasterNode extends MyceliaMasterNode
{
	/**
	 * Time to wait between assigning new task.
	 */
	private static int WAIT_TIME=1000;
	
	private static Logger logger=LoggerFactory.getLogger(MasterNode.class);
	
	private class DoWorkThread extends Thread
	{
		@Override
		public void run()
		{
			Random random=new Random();
			int base;
			int exponent;
			
			try
			{
				while(true)
				{
					if(isInterrupted())
						throw new InterruptedException();
					
					base=random.nextInt(1000);
					exponent=random.nextInt(1000);
					
					logger.info("Starting new task calculate with base "+base+" and exponent "+exponent);
					startTaskOnAnyNode(new Task("calculate"), base, exponent);
					
					sleep(WAIT_TIME);
				}
			}
			catch(InterruptedException e)
			{
				//Let the thread finish if we receive interrupt.
			}
		}
	}
	
	private DoWorkThread thread;
	
	@Override
	public void start()
	{
		createSlaveNodes(5);
		
		thread=new DoWorkThread();
		thread.start();
	}

	@Override
	public void stop()
	{
		thread.interrupt();
		
		SharedUtil.joindIgnoreInterrupts(thread);
	}
}
