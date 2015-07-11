package com.myselia.sandbox.runtime;

import com.myselia.sandbox.constants.MyseliaRuntimeType;
import com.myselia.sandbox.runtime.local.LocalRuntime;
import com.myselia.sandbox.runtime.network.NetworkRuntime;
import com.myselia.sandbox.runtime.templates.MyseliaMasterModule;
import com.myselia.sandbox.runtime.templates.MyseliaSlaveModule;

public class MyseliaRuntimeFactory {

	/**
	 * Creates either a Local or a Network Runtime based on the settings given
	 * @param masterModuleClass
	 * @param slaveModuleClass
	 * @param settings
	 * @return
	 */
	public static <M extends MyseliaMasterModule, S extends MyseliaSlaveModule> MyseliaRuntime buildRuntime(
			Class<M> masterModuleClass, Class<S> slaveModuleClass, MyseliaApplicationSettings settings) {
		
		if (settings.getRuntimeType() == MyseliaRuntimeType.LOCAL) {
			LocalRuntime lr = new LocalRuntime(masterModuleClass, slaveModuleClass);
			lr.setSlaveCount(settings.getSlaveCount());
			return lr;
		} else if (settings.getRuntimeType() == MyseliaRuntimeType.NETWORK) {
			NetworkRuntime nr = new NetworkRuntime(masterModuleClass, slaveModuleClass);
			nr.setModuleType(settings.getModuleType());
			return nr;
		} else {
			System.err.println("Myselia Runtime Factory Error");
			return null;
		}
	}

}
