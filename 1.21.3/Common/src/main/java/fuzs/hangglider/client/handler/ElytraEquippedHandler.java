package fuzs.hangglider.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import fuzs.hangglider.HangGlider;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElytraEquippedHandler {
    public static final ElytraEquippedHandler INSTANCE = new ElytraEquippedHandler();
    public static final ResourceLocation CROSS_TEXTURE_LOCATION = HangGlider.id("item/cross");

    private int tickTime;

    public void onEndClientTick(Minecraft minecraft) {
        if (!minecraft.isPaused() && this.tickTime > 0) this.tickTime--;
    }

    public void setActive() {
        this.tickTime = 40;
    }

    public void onAfterRenderGui(Gui gui, GuiGraphics guiGraphics, DeltaTracker deltaTracker) {

        if (this.tickTime > 0) {

            int leftPos = (guiGraphics.guiWidth() - 16) / 2;
            int topPos = guiGraphics.guiHeight() / 2 + 16;
            guiGraphics.renderItem(new ItemStack(Items.ELYTRA), leftPos, topPos);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float alpha = (float) (Math.sin((this.tickTime - deltaTracker.getGameTimeDeltaPartialTick(false)) * 0.5) * 0.5 + 0.5);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
            TextureAtlasSprite atlasSprite = gui.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(CROSS_TEXTURE_LOCATION);
            guiGraphics.blitSprite(RenderType::guiTextured, atlasSprite, leftPos, topPos, 16, 16);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
        }
    }
}
