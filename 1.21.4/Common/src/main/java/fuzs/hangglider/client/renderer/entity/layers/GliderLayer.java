package fuzs.hangglider.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.GliderRenderHandler;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class GliderLayer extends RenderLayer<PlayerRenderState, PlayerModel> {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HangGlider.MOD_ID);
    public static final ModelLayerLocation GLIDER = MODEL_LAYERS.registerModelLayer("glider");
    private static final ResourceLocation TEXTURE_LOCATION = getGliderLocation(HangGlider.id("hang_glider"));

    private final GliderModel<AbstractClientPlayer> gliderModel;

    public GliderLayer(RenderLayerParent<PlayerRenderState, PlayerModel> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.gliderModel = new GliderModel<>(entityModelSet.bakeLayer(GLIDER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, PlayerRenderState entityRenderState, float f, float g) {

        ItemStack itemStack = RenderPropertyKey.getRenderProperty(entityRenderState,
                GliderRenderHandler.GLIDER_IN_HAND_KEY);
        if (!itemStack.isEmpty()) {

            poseStack.pushPose();
            ResourceLocation resourceLocation = itemStack.get(ModRegistry.HANG_GLIDER_DATA_COMPONENT_TYPE.value())
                    .textureLocation()
                    .map(GliderLayer::getGliderLocation)
                    .orElse(TEXTURE_LOCATION);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                    RenderType.armorCutoutNoCull(resourceLocation),
                    itemStack.hasFoil());
            this.gliderModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

    static ResourceLocation getGliderLocation(ResourceLocation resourceLocation) {
        Objects.requireNonNull(resourceLocation, "resource location is null");
        return resourceLocation.withPath(s -> "textures/models/glider/" + s + ".png");
    }
}
