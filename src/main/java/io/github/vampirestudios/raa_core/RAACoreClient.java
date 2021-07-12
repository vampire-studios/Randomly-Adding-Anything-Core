package io.github.vampirestudios.raa_core;

import io.github.vampirestudios.raa_core.api.client.RAAAddonClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static io.github.vampirestudios.raa_core.RAACore.MOD_VERSION;

public class RAACoreClient implements ClientModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static Map<String, RAAAddonClient> RAA_ADDON_CLIENT_LIST = new HashMap<>();

    @Override
    public void onInitializeClient() {
        log(Level.INFO, String.format("You're now running RAA: Core - Client v%s for 1.17.1", MOD_VERSION));
        log(Level.INFO, "RAA Client Addon discovery: Starting");
        FabricLoader.getInstance().getEntrypoints("raa:addon_client", RAAAddonClient.class).forEach(raaAddon -> {
            RAA_ADDON_CLIENT_LIST.put(raaAddon.getId(), raaAddon);
            log(Level.INFO, String.format("Discovered Client Addon : %s", raaAddon.getId()));
        });
        log(Level.INFO, "RAA Client Addon discovery: Done");
        log(Level.INFO, "RAA Client Addon discovered: " + RAA_ADDON_CLIENT_LIST.size());

        Map<String, RAAAddonClient> loadOrder = new HashMap<>();

        Map<String, RAAAddonClient> remainingWithDependency = new HashMap<>();

        for (Map.Entry<String, RAAAddonClient> addonEntry : RAA_ADDON_CLIENT_LIST.entrySet()) {
            List<String> shouldLoadAfterList = new ArrayList<>(Arrays.asList(addonEntry.getValue().shouldLoadAfter()));
            if (shouldLoadAfterList.isEmpty()) {
                loadOrder.put(addonEntry.getKey(), addonEntry.getValue());
                continue;
            }
            List<Boolean> booleans = new ArrayList<>();
            for (String addonId : shouldLoadAfterList) {
                if (loadOrder.containsKey(addonId)) booleans.add(true);
                booleans.add(false);
            }

            boolean theBoolean = true;
            for (boolean bol : booleans) {
                if (!bol) {
                    theBoolean = false;
                    break;
                }
            }

            if (theBoolean) {
                loadOrder.put(addonEntry.getKey(), addonEntry.getValue());
                continue;
            }
            remainingWithDependency.put(addonEntry.getKey(), addonEntry.getValue());
        }

        while (!remainingWithDependency.isEmpty()) {
            for (Map.Entry<String, RAAAddonClient> addonEntry : remainingWithDependency.entrySet()) {
                List<String> shouldLoadAfterList = new ArrayList<>(Arrays.asList(addonEntry.getValue().shouldLoadAfter()));

                List<Boolean> booleans = new ArrayList<>();
                for (String addonId : shouldLoadAfterList) {
                    if (loadOrder.containsKey(addonId)) booleans.add(true);
                    booleans.add(false);
                }

                boolean theBoolean = true;
                for (boolean bol : booleans) {
                    if (!bol) {
                        theBoolean = false;
                        break;
                    }
                }

                if (theBoolean) {
                    loadOrder.put(addonEntry.getKey(), addonEntry.getValue());
                    remainingWithDependency.remove(addonEntry.getKey(), addonEntry.getValue());
                }
            }

        }


        RAA_ADDON_CLIENT_LIST = loadOrder;

        RAA_ADDON_CLIENT_LIST.values().forEach(RAAAddonClient::onClientInitialize);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, message);
    }
}
