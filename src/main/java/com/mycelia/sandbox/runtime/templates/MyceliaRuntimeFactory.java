package com.mycelia.sandbox.runtime.templates;

import com.mycelia.sandbox.constants.MyceliaRuntimeType;
import com.mycelia.sandbox.runtime.MyceliaMasterModule;
import com.mycelia.sandbox.runtime.MyceliaSlaveModule;
import com.mycelia.sandbox.runtime.local.LocalRuntime;
import com.mycelia.sandbox.runtime.network.NetworkRuntime;

public class MyceliaRuntimeFactory {

	public static <M extends MyceliaMasterModule, S extends MyceliaSlaveModule> MyceliaRuntime buildRuntime(
			Class<M> masterModuleClass, Class<S> slaveModuleClass, MyceliaRuntimeType runtimeType) {
		
		if (runtimeType == MyceliaRuntimeType.LOCAL) {
			return new LocalRuntime(masterModuleClass, slaveModuleClass);
		} else if (runtimeType == MyceliaRuntimeType.NETWORK) {
			return new NetworkRuntime(masterModuleClass, slaveModuleClass);
		} else {
			return null;
		}
	}

}
