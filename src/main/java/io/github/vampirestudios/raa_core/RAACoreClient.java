package io.github.vampirestudios.raa_core;

import java.util.Map;

import io.github.vampirestudios.raa_core.api.client.RAAAddonClient;
import net.fabricmc.api.ClientModInitializer;

public class RAACoreClient implements ClientModInitializer {

	public static Map<String, RAAAddonClient> ADDON_MAP;

	@Override
	public void onInitializeClient() {
		ADDON_MAP = RAACore.discoverAddons(RAAAddonClient.class, "raa:addon_client");
		ADDON_MAP.values().forEach(RAAAddonClient::onInitializeClient);
	}

}
