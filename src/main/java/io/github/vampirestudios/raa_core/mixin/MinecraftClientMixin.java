package io.github.vampirestudios.raa_core.mixin;

import io.github.vampirestudios.raa_core.RAACore;
import io.github.vampirestudios.raa_core.RAACoreClient;
import io.github.vampirestudios.raa_core.client.RAACoreNoAddonScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow public abstract void openScreen(Screen screen);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void openAddonWarningScreen(RunArgs args, CallbackInfo ci) {
        if (RAACore.RAA_ADDON_LIST.size() == 0 && RAACoreClient.RAA_ADDON_CLIENT_LIST.size() == 0) {
            this.openScreen(new RAACoreNoAddonScreen());
        }
    }
}
