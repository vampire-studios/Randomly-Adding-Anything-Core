package io.github.vampirestudios.raa_core.api;

public interface RAAAddon {

	static final String[] NO_DEPS = {};
	
	default String[] dependencies() {
		return NO_DEPS;
	}

	void onInitialize();

}
