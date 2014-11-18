package com.mycelia.sandbox.exception;

public class NotImplementedException extends RuntimeException
{
	private static final long serialVersionUID=-5792478006700846260L;

	public NotImplementedException()
	{
		//Do nothing.
	}
	
	public NotImplementedException(String message)
	{
		super(message);
	}
	
	public NotImplementedException(Throwable t)
	{
		super(t);
	}
}
