package io.github.vampirestudios.raa_core.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface RAAAddonClient {
	
	static final String[] NO_DEPS = {};
	
	default String[] dependencies() {
		return NO_DEPS;
	}
	
	void onInitializeClient();

}
