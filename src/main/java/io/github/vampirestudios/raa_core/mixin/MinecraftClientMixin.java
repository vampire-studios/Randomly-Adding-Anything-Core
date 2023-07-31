package io.github.vampirestudios.raa_core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

	@Shadow public abstract void setScreen(Screen screen);

	@Inject(method = "<init>", at = @At("TAIL"))
	private void openAddonWarningScreen(RunArgs args, CallbackInfo ci) {
		/*if (RAACore.ADDON_MAP.size() == 0 && RAACoreClient.ADDON_MAP.size() == 0 && !RAACore.CONFIG.shownNoAddonsScreen) {
			this.setScreen(new RAACoreNoAddonScreen());
		}*/
	}

}
