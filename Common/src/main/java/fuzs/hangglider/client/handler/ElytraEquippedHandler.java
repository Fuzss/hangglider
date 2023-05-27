package fuzs.hangglider.client.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.hangglider.HangGlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElytraEquippedHandler {
    public static final ResourceLocation CROSS_TEXTURE_LOCATION = HangGlider.id("item/cross");
    public static final ElytraEquippedHandler INSTANCE = new ElytraEquippedHandler();

    private int tickTime;

    public void onClientTick$End(Minecraft minecraft) {
        if (!minecraft.isPaused() && this.tickTime > 0) this.tickTime--;
    }

    public void activate() {
        this.tickTime = 40;
    }

    public void onRenderGui(Minecraft minecraft, PoseStack poseStack, float tickDelta, int screenWidth, int screenHeight) {

        if (this.tickTime > 0) {

            int leftPos = (screenWidth - 16) / 2;
            int topPos = screenHeight / 2 + 16;
            minecraft.getItemRenderer().renderAndDecorateItem(poseStack, new ItemStack(Items.ELYTRA), leftPos, topPos);

            TextureAtlasSprite textureatlassprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(CROSS_TEXTURE_LOCATION);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, textureatlassprite.atlasLocation());
            float alpha = (float) (Math.sin((this.tickTime - tickDelta) * 0.5) * 0.5 + 0.5);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
            GuiComponent.blit(poseStack, leftPos, topPos, 400, 16, 16, textureatlassprite);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
        }
    }
}
