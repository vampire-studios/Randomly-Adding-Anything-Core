/*package io.github.vampirestudios.raa_core.client;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.vampirestudios.raa_core.RAACore;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

public class RAACoreNoAddonScreen extends Screen {
	private final Text OPEN_ADDON_LIST = Text.translatable("text.raa_core.no_addon_screen.message.open_addon_list").formatted(Formatting.BOLD, Formatting.BLUE);
	private final Text MESSAGE_1 = Text.translatable("text.raa_core.no_addon_screen.message.1").formatted(Formatting.BOLD, Formatting.RED);
	private final Text MESSAGE_2 = Text.translatable("text.raa_core.no_addon_screen.message.2").formatted(Formatting.GRAY);
	private final Text MESSAGE_3 = Text.translatable("text.raa_core.no_addon_screen.message.3").formatted(Formatting.GRAY);
	private final String ADDONS_LIST = "https://github.com/vampire-studios/Randomly-Adding-Anything-Core/wiki/Official-Addon-list";

	@Nullable
	private final Text check;
	@Nullable
	protected CheckboxWidget stopShowing;

	public RAACoreNoAddonScreen() {
		super(Text.translatable("text.raa_core.no_addon_screen.title").formatted(Formatting.BOLD, Formatting.RED));
		this.check = Text.translatable("multiplayerWarning.check");
	}

	@Override
	protected void init() {
		this.addDrawableChild(new PressableTextWidget(
				(this.width + this.textRenderer.getWidth(MESSAGE_3) - this.textRenderer.getWidth(OPEN_ADDON_LIST)) / 2,
				this.height/2 - 5,
				this.textRenderer.getWidth(OPEN_ADDON_LIST),
				10, OPEN_ADDON_LIST, press -> Util.getOperatingSystem().open(ADDONS_LIST),
				this.textRenderer
		));
		if (this.check != null) {
			int j = this.textRenderer.getWidth(this.check);
			this.stopShowing = new CheckboxWidget(this.width / 2 - j / 2 - 8, this.height - 50, j + 24, 20, this.check, false);
			this.addDrawableChild(this.stopShowing);
		}
		addButton("text.raa_core.no_addon_screen.message.continue", 46, press -> {
			assert this.stopShowing != null;
			if (this.stopShowing.isChecked()) {
				RAACore.CONFIG.shownNoAddonsScreen = true;
				RAACore.CONFIG.save();
			}
			Objects.requireNonNull(this.client).setScreen(new TitleScreen());
		});
		addButton("menu.quit", -32, press -> Objects.requireNonNull(this.client).scheduleStop());
	}

	private void addButton(String text, int xOffset, ButtonWidget.PressAction onPress) {
		Text string = Text.translatable(text);
		this.addDrawableChild(ButtonWidget.builder(string, onPress)
				.position((this.width / 2 - 10 - textRenderer.getWidth(string) / 2) - xOffset, this.height - 25)
				.size(textRenderer.getWidth(string) + 30, 20)
				.build()
		);
	}

	@Override
	public void render(@NotNull MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		drawText(matrices, MESSAGE_1, 30);
		drawText(matrices, MESSAGE_2, 15);
		drawText(matrices, MESSAGE_3, this.textRenderer.getWidth(OPEN_ADDON_LIST) / 2, 5);
		super.render(matrices, mouseX, mouseY, delta);
	}

	private void drawText(MatrixStack poseStack, Text text, int xOffset, int yOffset) {
		drawCenteredTextWithShadow(poseStack, this.textRenderer, text, this.width/2 - xOffset, this.height/2 - yOffset, Objects.requireNonNull(text.getStyle().getColor()).getRgb());
	}

	private void drawText(MatrixStack poseStack, Text text, int yOffset) {
		drawText(poseStack, text, 0, yOffset);
	}

}*/
