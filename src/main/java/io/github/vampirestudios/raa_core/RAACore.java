package io.github.vampirestudios.raa_core;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RAACore implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "raa_core";
    public static final String MOD_NAME = "RAA: Core";
    public static final String MOD_VERSION = "0.1.0";

    @Override
    public void onInitialize() {
        log(Level.INFO, String.format("Initializing %s v%s", MOD_NAME, MOD_VERSION));
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}