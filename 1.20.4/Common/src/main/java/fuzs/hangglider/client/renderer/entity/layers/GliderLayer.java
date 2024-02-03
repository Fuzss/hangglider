package fuzs.hangglider.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.init.ModClientRegistry;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.init.ModRegistry;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class GliderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final Map<Item, ResourceLocation> TEXTURE_LOCATIONS = Map.of(ModRegistry.HANG_GLIDER_ITEM.value(),
            HangGlider.id("textures/models/glider/hang_glider.png"),
            ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value(),
            HangGlider.id("textures/models/glider/reinforced_hang_glider.png")
    );

    private final GliderModel<AbstractClientPlayer> gliderModel;

    public GliderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.gliderModel = new GliderModel<>(entityModelSet.bakeLayer(ModClientRegistry.GLIDER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        ItemStack stack = PlayerGlidingHelper.isGliderDeployed(player) ?
                PlayerGlidingHelper.getGliderInHand(player) :
                ItemStack.EMPTY;

        if (!stack.isEmpty()) {

            poseStack.pushPose();

            this.getParentModel().copyPropertiesTo(this.gliderModel);

            RenderType renderType = RenderType.armorCutoutNoCull(TEXTURE_LOCATIONS.get(stack.getItem()));
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, renderType, false, stack.hasFoil());
            this.gliderModel.renderToBuffer(poseStack,
                    vertexConsumer,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    1.0F,
                    1.0F,
                    1.0F,
                    1.0F
            );

            poseStack.popPose();
        }
    }
}
