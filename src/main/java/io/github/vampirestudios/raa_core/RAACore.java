package io.github.vampirestudios.raa_core;

import io.github.vampirestudios.raa_core.api.RAAAddon;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class RAACore implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "raa_core";
    public static final String MOD_VERSION = "2.5.0+build.1";

    public static Map<String, RAAAddon> RAA_ADDON_LIST = new HashMap<>();

    public static RAACoreConfig CONFIG;

    @Override
    public void onInitialize() {
        log(Level.INFO, String.format("You're now running RAA: Core v%s for %s", MOD_VERSION, SharedConstants.getCurrentVersion().getName()));

        AutoConfig.register(RAACoreConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(RAACoreConfig.class).getConfig();

        log(Level.INFO, "RAA Addon discovery: Starting");
        FabricLoader.getInstance().getEntrypoints("raa:addon", RAAAddon.class).forEach(raaAddon -> {
            RAA_ADDON_LIST.put(raaAddon.getId(), raaAddon);
            log(Level.INFO, String.format("Discovered addon : %s", raaAddon.getId()));
        });
        log(Level.INFO, "RAA Addon Discovery: Done");
        log(Level.INFO, "RAA Addon Discovered: " + RAA_ADDON_LIST.size());

        Map<String, RAAAddon> loadOrder = new HashMap<>();

        Map<String, RAAAddon> remainingWithDependency = new HashMap<>();

        for (Map.Entry<String, RAAAddon> addonEntry : RAA_ADDON_LIST.entrySet()) {
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
            for (Map.Entry<String, RAAAddon> addonEntry : remainingWithDependency.entrySet()) {
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


        RAA_ADDON_LIST = loadOrder;

        RAA_ADDON_LIST.values().forEach(RAAAddon::onInitialize);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, message);
    }

}