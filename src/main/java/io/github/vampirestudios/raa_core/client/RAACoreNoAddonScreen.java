package io.github.vampirestudios.raa_core.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import net.minecraft.util.Util;

public class RAACoreNoAddonScreen extends Screen {
    public RAACoreNoAddonScreen() {
        super(new TranslatableText("text.raa_core.no_addon_screen.title"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        Text string = new TranslatableText("text.raa_core.no_addon_screen.message.open_addon_list");
        this.addButton(new ButtonWidget(
                (this.width/2 -10- this.minecraft.textRenderer.getStringWidth(string.asString())/2),
                this.height/2 + 10, this.minecraft.textRenderer.getStringWidth(string.asString()) + 20, 20, string.asString(), press -> {
            Util.getOperatingSystem().open("https://github.com/vampire-studios/Randomly-Adding-Anything-Core/wiki/Official-Addon-list");
        }));

        string = new TranslatableText("text.raa_core.no_addon_screen.message.continue");
        this.addButton(new ButtonWidget(
                (this.width/2 -10- this.minecraft.textRenderer.getStringWidth(string.asString())/2),
                this.height/2 + 40, this.minecraft.textRenderer.getStringWidth(string.asString()) + 20, 20, string.asString(), press -> {
            this.minecraft.openScreen(new TitleScreen());
        }));

        string = new TranslatableText("text.raa_core.no_addon_screen.message.quit_game");
        this.addButton(new ButtonWidget(
                (this.width/2 -10- this.minecraft.textRenderer.getStringWidth(string.asString())/2),
                this.height/2 + 70, this.minecraft.textRenderer.getStringWidth(string.asString()) + 20, 20, string.asString(), press -> {
            this.minecraft.scheduleStop();
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        String string = new TranslatableText("text.raa_core.no_addon_screen.message.1").getString();
        this.drawCenteredString(this.font, string, this.width/2, this.height/2 -20, 0xFFFFFF);
        string = new TranslatableText("text.raa_core.no_addon_screen.message.2").getString();
        this.drawCenteredString(this.font, string, this.width/2, this.height/2 - 10, 0xFFFFFF);
        string = new TranslatableText("text.raa_core.no_addon_screen.message.3").getString();
        this.drawCenteredString(this.font, string, this.width/2, this.height/2, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }
}
