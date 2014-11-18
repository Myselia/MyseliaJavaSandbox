package com.mycelia.sandbox.communication;

import java.io.*;

import org.apache.commons.codec.binary.Base64;

import com.mycelia.sandbox.communication.bean.Atom;

public class AtomConverter
{
	public Atom toAtom(Integer integer)
	{
		Atom atom=new Atom("", Integer.class.getCanonicalName());
		atom.addContent(Integer.toString(integer));

		return atom;
	}

	public Atom toAtom(Double value)
	{
		Atom atom=new Atom("", Integer.class.getCanonicalName());
		atom.addContent(Double.toString(value));

		return atom;
	}

	public Atom toAtom(Serializable serializable) throws IOException
	{
		Atom atom=new Atom("", Serializable.class.getCanonicalName());
		atom.addContent(toString(serializable));

		return atom;
	}

	public Integer getAsInt(Atom atom)
	{
		return (Integer)Integer.parseInt(atom.getContent());
	}
	
	public Double getAsDouble(Atom atom)
	{
		return (Double)Double.parseDouble(atom.getContent());
	}
	
	public Serializable getAsSerializable(Atom atom) throws ClassNotFoundException, IOException
	{
		return (Serializable)fromString(atom.getContent());
	}
	
	private Object fromString(String string) throws IOException, ClassNotFoundException
	{
		byte[] data=Base64.decodeBase64(string);
		ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(data));
		Object object=ois.readObject();
		ois.close(); 
		
		return object;
	}

	private String toString(Serializable serializable) throws IOException
	{
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(baos);
		oos.writeObject(serializable);
		oos.close();
		
		return new String(Base64.encodeBase64(baos.toByteArray()));
	}
}
