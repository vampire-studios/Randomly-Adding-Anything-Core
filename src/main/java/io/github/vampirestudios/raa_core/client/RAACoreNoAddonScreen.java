package io.github.vampirestudios.raa_core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RAACoreNoAddonScreen extends Screen {
    private final Component OPEN_ADDON_LIST = Component.translatable("text.raa_core.no_addon_screen.message.open_addon_list").withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE);
    private final Component MESSAGE_1 = Component.translatable("text.raa_core.no_addon_screen.message.1").withStyle(ChatFormatting.BOLD, ChatFormatting.RED);
    private final Component MESSAGE_2 = Component.translatable("text.raa_core.no_addon_screen.message.2").withStyle(ChatFormatting.GRAY);
    private final Component MESSAGE_3 = Component.translatable("text.raa_core.no_addon_screen.message.3").withStyle(ChatFormatting.GRAY);
    private final String ADDONS_LIST = "https://github.com/vampire-studios/Randomly-Adding-Anything-Core/wiki/Official-Addon-list";

    public RAACoreNoAddonScreen() {
        super(Component.translatable("text.raa_core.no_addon_screen.title").withStyle(ChatFormatting.BOLD, ChatFormatting.RED));
    }

    @Override
    public void init() {
        this.addRenderableWidget(new PlainTextButton(
                (this.width + this.font.width(MESSAGE_3) - this.font.width(OPEN_ADDON_LIST)) / 2,
                this.height/2 - 5,
                this.font.width(OPEN_ADDON_LIST),
                10, OPEN_ADDON_LIST, press -> Util.getPlatform().openUri(ADDONS_LIST),
                this.font
        ));
        addButton("text.raa_core.no_addon_screen.message.continue", 46, press ->
                Objects.requireNonNull(this.minecraft).setScreen(new TitleScreen())
        );
        addButton("menu.quit", -32, press -> Objects.requireNonNull(this.minecraft).stop());
    }

    private void addButton(String text, int xOffset, Button.OnPress onPress) {
        Component string = Component.translatable(text);
        this.addRenderableWidget(new Button(
                (this.width / 2 - 10 - font.width(string) / 2) - xOffset,
                this.height - 25,
                font.width(string) + 30,
                20, string, onPress
        ));
    }

    @Override
    public void render(@NotNull PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawText(matrices, MESSAGE_1, 30);
        drawText(matrices, MESSAGE_2, 15);
        drawText(matrices, MESSAGE_3, this.font.width(OPEN_ADDON_LIST) / 2, 5);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void drawText(PoseStack poseStack, Component text, int xOffset, int yOffset) {
        drawCenteredString(poseStack, this.font, text, this.width/2 - xOffset, this.height/2 - yOffset, Objects.requireNonNull(text.getStyle().getColor()).getValue());
    }

    private void drawText(PoseStack poseStack, Component text, int yOffset) {
        drawText(poseStack, text, 0, yOffset);
    }

}
