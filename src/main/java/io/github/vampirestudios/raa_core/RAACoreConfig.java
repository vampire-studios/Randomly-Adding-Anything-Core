package io.github.vampirestudios.raa_core;

import com.google.gson.JsonPrimitive;

import io.github.vampirestudios.raa_core.api.name_generation.RAALanguage;
import io.github.vampirestudios.raa_core.config.RAAConfig;

public class RAACoreConfig extends RAAConfig {

	public RAALanguage language = RAALanguage.ENGLISH;
	
	public RAACoreConfig() {
		super("core");
		
		this.<RAALanguage, JsonPrimitive>field("language")
			.getter(() -> this.language)
			.setter((language) -> this.language = language)
			.toJson(Functions.registryObjToJson(RAALanguage.REGISTRY))
			.fromJson(Functions.jsonToRegistryObj(RAALanguage.REGISTRY))
			.build();
	}

}
