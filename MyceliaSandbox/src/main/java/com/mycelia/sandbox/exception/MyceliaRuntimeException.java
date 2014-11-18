package com.mycelia.sandbox.exception;

public class MyceliaRuntimeException extends RuntimeException
{
	private static final long serialVersionUID=7693921292465325121L;

	public MyceliaRuntimeException()
	{
		//Do nothing.
	}
	
	public MyceliaRuntimeException(String message)
	{
		super(message);
	}
	
	public MyceliaRuntimeException(Throwable t)
	{
		super(t);
	}
}
