package io.github.vampirestudios.raa_core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class RAACoreNoAddonScreen extends Screen {
    public RAACoreNoAddonScreen() {
        super(new TranslatableComponent("text.raa_core.no_addon_screen.title"));
    }

    @Override
    public void init() {
        Component string = new TranslatableComponent("text.raa_core.no_addon_screen.message.open_addon_list");
        this.addRenderableWidget(new Button(
                (this.width/2 -10- font.width(string)/2),
                this.height/2 + 10, font.width(string) + 20, 20, string, press ->
                Util.getPlatform().openUri("https://github.com/vampire-studios/Randomly-Adding-Anything-Core/wiki/Official-Addon-list")));

        string = new TranslatableComponent("text.raa_core.no_addon_screen.message.continue");
        this.addRenderableWidget(new Button(
                (this.width/2 -10- font.width(string)/2),
                this.height/2 + 40, font.width(string) + 20, 20, string, press -> this.minecraft.setScreen(new TitleScreen())));

        string = new TranslatableComponent("text.raa_core.no_addon_screen.message.quit_game");
        this.addRenderableWidget(new Button(
                (this.width/2 -10- font.width(string)/2),
                this.height/2 + 70, font.width(string) + 20, 20, string, press -> this.minecraft.stop()));
    }

    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        String string = new TranslatableComponent("text.raa_core.no_addon_screen.message.1").getString();
        drawCenteredString(matrices, this.font, string, this.width/2, this.height/2 -20, 0xFFFFFF);
        string = new TranslatableComponent("text.raa_core.no_addon_screen.message.2").getString();
        drawCenteredString(matrices, this.font, string, this.width/2, this.height/2 - 10, 0xFFFFFF);
        string = new TranslatableComponent("text.raa_core.no_addon_screen.message.3").getString();
        drawCenteredString(matrices, this.font, string, this.width/2, this.height/2, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
