package io.github.vampirestudios.raa_core;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> AutoConfig.getConfigScreen(RAACoreConfig.class, screen).get();
    }

}
