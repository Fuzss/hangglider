package fuzs.hangglider.client.handler;

import fuzs.hangglider.HangGlider;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElytraEquippedHandler {
    public static final ResourceLocation CROSS_TEXTURE_LOCATION = HangGlider.id("item/cross");

    private static int tickTime;

    public static void onEndClientTick(Minecraft minecraft) {
        if (!minecraft.isPaused() && tickTime > 0) {
            tickTime--;
        }
    }

    public static void activate() {
        tickTime = 40;
    }

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {

        if (tickTime > 0) {

            int leftPos = (guiGraphics.guiWidth() - 16) / 2;
            int topPos = guiGraphics.guiHeight() / 2 + 16;
            guiGraphics.renderItem(new ItemStack(Items.ELYTRA), leftPos, topPos);
            float alpha = (float) (Math.sin((tickTime - deltaTracker.getGameTimeDeltaPartialTick(false)) * 0.5) * 0.5
                    + 0.5);
            TextureAtlasSprite textureAtlasSprite = Minecraft.getInstance()
                    .getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                    .apply(CROSS_TEXTURE_LOCATION);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                    textureAtlasSprite,
                    leftPos,
                    topPos,
                    16,
                    16,
                    ARGB.white(alpha));
        }
    }
}
