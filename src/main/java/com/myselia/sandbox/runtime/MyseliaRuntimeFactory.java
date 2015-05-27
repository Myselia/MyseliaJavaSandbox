package com.myselia.sandbox.runtime;

import com.myselia.sandbox.constants.MyseliaRuntimeType;
import com.myselia.sandbox.runtime.local.LocalRuntime;
import com.myselia.sandbox.runtime.network.NetworkRuntime;
import com.myselia.sandbox.runtime.templates.MyseliaMasterModule;
import com.myselia.sandbox.runtime.templates.MyseliaSlaveModule;

public class MyseliaRuntimeFactory {

	public static <M extends MyseliaMasterModule, S extends MyseliaSlaveModule> MyseliaRuntime buildRuntime(
			Class<M> masterModuleClass, Class<S> slaveModuleClass, MyseliaRuntimeType runtimeType) {
		
		if (runtimeType == MyseliaRuntimeType.LOCAL) {
			return new LocalRuntime(masterModuleClass, slaveModuleClass);
		} else if (runtimeType == MyseliaRuntimeType.NETWORK) {
			return new NetworkRuntime(masterModuleClass, slaveModuleClass);
		} else {
			return null;
		}
	}

}
