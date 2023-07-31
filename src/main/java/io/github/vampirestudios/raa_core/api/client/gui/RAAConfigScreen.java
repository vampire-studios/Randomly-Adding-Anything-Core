package io.github.vampirestudios.raa_core.api.client.gui;

import io.github.vampirestudios.raa_core.config.RAAConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class RAAConfigScreen extends Screen {

	private final RAAConfig config;
	
	public RAAConfigScreen(Text title, RAAConfig config) {
		super(title);
		this.config = config;
	}
	
	@Override
	protected void init() {
		super.init();
		
	}

}
