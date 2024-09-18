package fuzs.hangglider.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.init.ModClientRegistry;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.helper.PlayerGlidingHelper;
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
    private static final ResourceLocation TEXTURE_LOCATION = HangGlider.id("textures/models/glider/hang_glider.png");
    private static final Map<Item, ResourceLocation> TEXTURE_LOCATION_OVERRIDES = Maps.newLinkedHashMapWithExpectedSize(
            1);

    private final GliderModel<AbstractClientPlayer> gliderModel;

    public GliderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.gliderModel = new GliderModel<>(entityModelSet.bakeLayer(ModClientRegistry.GLIDER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        ItemStack itemStack = PlayerGlidingHelper.getGliderInHand(player);
        if (!itemStack.isEmpty()) {

            poseStack.pushPose();

            this.getParentModel().copyPropertiesTo(this.gliderModel);

            ResourceLocation resourceLocation = TEXTURE_LOCATION_OVERRIDES.getOrDefault(itemStack.getItem(),
                    TEXTURE_LOCATION
            );
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer,
                    RenderType.armorCutoutNoCull(resourceLocation),
                    itemStack.hasFoil()
            );
            this.gliderModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

            poseStack.popPose();
        }
    }

    public static void registerGliderTexture(Item item, ResourceLocation resourceLocation) {
        TEXTURE_LOCATION_OVERRIDES.putIfAbsent(item, resourceLocation);
    }
}
