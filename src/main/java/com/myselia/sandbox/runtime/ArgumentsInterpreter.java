package com.myselia.sandbox.runtime;

import com.myselia.sandbox.constants.MyseliaModuleType;
import com.myselia.sandbox.constants.MyseliaRuntimeType;

public class ArgumentsInterpreter {
	public static String[] arguments;
	public static MyseliaApplicationSettings settings;
	public static char uid;

	public static MyseliaApplicationSettings interpret(String[] args) {
		arguments = args;
		settings = new MyseliaApplicationSettings();

		for (String arg : arguments) {
			read(arg);
			//System.out.println("||" + arg + "||");
		}

		return settings;
	}

	private static void read(String arg) {
		switch (arg) {
		case "-m":
			settings.setModuleType(MyseliaModuleType.MASTER);
			settings.setRuntimeType(MyseliaRuntimeType.NETWORK);
			break;
		case "-s":
			settings.setModuleType(MyseliaModuleType.SLAVE);
			settings.setRuntimeType(MyseliaRuntimeType.NETWORK);
			break;
		case "-t":
			settings.setRuntimeType(MyseliaRuntimeType.LOCAL);
			break;
		case "a":
			uid = 'a';
			break;
		case "b":
			uid = 'b';
			break;
		case "c":
			uid = 'c';
			break;
		case "d":
			uid = 'd';
			break;
		case "e":
			uid = 'e';
			break;
		default:
			System.err.println("unregistered flag used");
			break;
		}
	}

}
