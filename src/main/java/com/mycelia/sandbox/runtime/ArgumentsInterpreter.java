package com.mycelia.sandbox.runtime;

import com.mycelia.sandbox.constants.MyceliaModuleType;
import com.mycelia.sandbox.constants.MyceliaRuntimeType;

public class ArgumentsInterpreter {
	public static String[] arguments;
	public static MyceliaApplicationSettings settings;

	public static MyceliaApplicationSettings interpret(String[] args) {
		arguments = args;
		settings = new MyceliaApplicationSettings();

		for (String arg : arguments) {
			read(arg);
			//System.out.println("||" + arg + "||");
		}

		return settings;
	}

	private static void read(String arg) {
		switch (arg) {
		case "-m":
			settings.setModuleType(MyceliaModuleType.MASTER);
			settings.setRuntimeType(MyceliaRuntimeType.NETWORK);
			break;
		case "-s":
			settings.setModuleType(MyceliaModuleType.SLAVE);
			settings.setRuntimeType(MyceliaRuntimeType.NETWORK);
			break;
		case "-t":
			settings.setRuntimeType(MyceliaRuntimeType.LOCAL);
			break;
		default:
			System.err.println("unregistered flag used");
			break;
		}
	}

}
