package fuzs.openglider.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.world.item.Glider;
import fuzs.openglider.client.model.GliderModel;
import fuzs.openglider.config.ClientConfig;
import fuzs.openglider.helper.GliderCapabilityHelper;
import fuzs.openglider.helper.PlayerGlidingHelper;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GliderLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final GliderModel<T> gliderModel;

    public GliderLayer(RenderLayerParent<T, M> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.gliderModel = new GliderModel<>(entityModelSet);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!OpenGlider.CONFIG.get(ClientConfig.class).thirdPersonRendering) return;


        //if not invisible and should render
        if (livingEntity instanceof Player player && !player.isInvisible()) {

            // get if gliding (to render or not)
            if (GliderCapabilityHelper.getIsGliderDeployed(player)) {
                
                poseStack.pushPose();

                // set rotation angles of the glider
                this.getParentModel().copyPropertiesTo(this.gliderModel);
                this.setRotationAngles(poseStack, player);

                // get texture and render the glider
                ItemStack stack = GliderCapabilityHelper.getGlider(player);
                ResourceLocation gliderTexture = ((Glider) stack.getItem()).getGliderTexture(stack);
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(gliderTexture), false, stack.hasFoil());
                this.gliderModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

                poseStack.popPose();
            }
        }
    }

    private void setRotationAngles(PoseStack poseStack, Player player) {

        //set it to the back (no rotation)
        poseStack.mulPose(Vector3f.YP.rotation(180.0F));
        //on same plane as player
        poseStack.mulPose(Vector3f.XP.rotation(90.0F));
        Util.make(Vector3f.YP.copy(), v -> v.mul(2.0F));
        //front facing
        poseStack.mulPose(Util.make(Vector3f.YP.copy(), v -> v.mul(2.0F)).rotation(180.0F));
        if (player.isShiftKeyDown()) {
            poseStack.translate(0, -0.5, 0); //move to on the back (more away than fpp)
        } else {
            poseStack.translate(0, -0.35, 0); //move to on the back (quite close)
        }

        if (!PlayerGlidingHelper.isAllowedToGlide(player)) {
            poseStack.scale(0.9F, 0.9F, 0.8F); //scale slightly smaller
            poseStack.translate(0.0, 0.0, -0.5); // move up if on ground
        }
    }
}
