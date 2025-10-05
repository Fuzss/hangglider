package fuzs.hangglider.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.GliderRenderHandler;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class GliderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HangGlider.MOD_ID);
    public static final ModelLayerLocation GLIDER = MODEL_LAYERS.registerModelLayer("glider");
    private static final ResourceLocation TEXTURE_LOCATION = getGliderLocation(HangGlider.id("hang_glider"));

    private final GliderModel<AbstractClientPlayer> gliderModel;

    public GliderLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderLayerParent, EntityRendererProvider.Context context) {
        super(renderLayerParent);
        this.gliderModel = new GliderModel<>(context.bakeLayer(GLIDER));
    }

    public static void addLivingEntityRenderLayers(EntityType<?> entityType, LivingEntityRenderer<?, ?, ?> entityRenderer, EntityRendererProvider.Context context) {
        if (entityRenderer instanceof AvatarRenderer<?> avatarRenderer) {
            avatarRenderer.addLayer(new GliderLayer(avatarRenderer, context));
        }
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, AvatarRenderState avatarRenderState, float yRot, float xRot) {
        ItemStack itemStack = RenderStateExtraData.getOrDefault(avatarRenderState,
                GliderRenderHandler.GLIDER_IN_HAND_KEY,
                ItemStack.EMPTY);
        if (!itemStack.isEmpty()) {
            ResourceLocation resourceLocation = itemStack.get(ModRegistry.HANG_GLIDER_DATA_COMPONENT_TYPE.value())
                    .textureLocation()
                    .map(GliderLayer::getGliderLocation)
                    .orElse(TEXTURE_LOCATION);
            submitNodeCollector.order(1)
                    .submitModel(this.gliderModel,
                            avatarRenderState,
                            poseStack,
                            RenderType.armorCutoutNoCull(resourceLocation),
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            avatarRenderState.outlineColor,
                            null);
            if (itemStack.hasFoil()) {
                submitNodeCollector.order(2)
                        .submitModel(this.gliderModel,
                                avatarRenderState,
                                poseStack,
                                RenderType.armorEntityGlint(),
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                avatarRenderState.outlineColor,
                                null);
            }
        }
    }

    private static ResourceLocation getGliderLocation(ResourceLocation resourceLocation) {
        Objects.requireNonNull(resourceLocation, "resource location is null");
        return resourceLocation.withPath((String string) -> "textures/models/glider/" + string + ".png");
    }
}
