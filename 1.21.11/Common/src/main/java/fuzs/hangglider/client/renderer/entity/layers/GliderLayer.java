package fuzs.hangglider.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.GliderRenderHandler;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class GliderLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(HangGlider.MOD_ID);
    public static final ModelLayerLocation GLIDER = MODEL_LAYERS.registerModelLayer("glider");
    private static final Identifier TEXTURE_LOCATION = getGliderLocation(HangGlider.id("hang_glider"));

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
            Identifier identifier = itemStack.get(ModRegistry.HANG_GLIDER_DATA_COMPONENT_TYPE.value())
                    .textureLocation()
                    .map(GliderLayer::getGliderLocation)
                    .orElse(TEXTURE_LOCATION);
            submitNodeCollector.order(1)
                    .submitModel(this.gliderModel,
                            avatarRenderState,
                            poseStack,
                            RenderTypes.armorCutoutNoCull(identifier),
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            avatarRenderState.outlineColor,
                            null);
            if (itemStack.hasFoil()) {
                submitNodeCollector.order(2)
                        .submitModel(this.gliderModel,
                                avatarRenderState,
                                poseStack,
                                RenderTypes.armorEntityGlint(),
                                packedLight,
                                OverlayTexture.NO_OVERLAY,
                                avatarRenderState.outlineColor,
                                null);
            }
        }
    }

    private static Identifier getGliderLocation(Identifier identifier) {
        Objects.requireNonNull(identifier, "identifier is null");
        return identifier.withPath((String string) -> "textures/models/glider/" + string + ".png");
    }
}
