package fuzs.hangglider.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.renderer.v1.RenderPropertyKey;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GliderRenderHandler {
    public static final RenderPropertyKey<ItemStack> GLIDER_IN_HAND_KEY = new RenderPropertyKey<>(HangGlider.id(
            "glider_in_hand"));
    public static final RenderPropertyKey<Boolean> IS_GLIDING_KEY = new RenderPropertyKey<>(HangGlider.id("is_gliding"));
    public static final RenderPropertyKey<Float> HEAD_ROT_KEY = new RenderPropertyKey<>(HangGlider.id("head_rot"));

    private static boolean appliedGlidingRotations;

    public static void onExtractRenderState(Entity entity, EntityRenderState renderState, float partialTick) {

        if (entity instanceof Player player && renderState instanceof PlayerRenderState playerRenderState) {

            RenderPropertyKey.setRenderProperty(renderState,
                    GLIDER_IN_HAND_KEY,
                    PlayerGlidingHelper.getGliderInHand(player));
            RenderPropertyKey.setRenderProperty(renderState,
                    IS_GLIDING_KEY,
                    ModRegistry.GLIDING_CAPABILITY.get(player).isGliding());
            RenderPropertyKey.setRenderProperty(renderState,
                    HEAD_ROT_KEY,
                    Mth.rotLerp(partialTick, player.yHeadRotO, player.yHeadRot));

            if (RenderPropertyKey.getRenderProperty(renderState, IS_GLIDING_KEY)) {

                playerRenderState.rightHandItem.clear();
                playerRenderState.leftHandItem.clear();
                playerRenderState.rightArmPose = playerRenderState.leftArmPose = HumanoidModel.ArmPose.EMPTY;
                playerRenderState.isCrouching = false;
            }
        }
    }

    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> EventResult onBeforeRenderEntity(S renderState, LivingEntityRenderer<T, S, M> entityRenderer, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if (renderState instanceof PlayerRenderState playerRenderState &&
                entityRenderer instanceof PlayerRenderer playerRenderer) {

            if (RenderPropertyKey.getRenderProperty(renderState, IS_GLIDING_KEY)) {

                float headRot = RenderPropertyKey.getRenderProperty(renderState, HEAD_ROT_KEY);
                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(-headRot));
                poseStack.translate(0.0F, playerRenderState.boundingBoxHeight / 2.0F, 0.0F);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(0.0F, -playerRenderState.boundingBoxHeight / 2.0F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(headRot));
                // reposition to better align with bounding box
                poseStack.translate(0.0F, -0.5F, 0.0F);
                appliedGlidingRotations = true;
            }
        }

        return EventResult.PASS;
    }

    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> void onAfterRenderEntity(S renderState, LivingEntityRenderer<T, S, M> entityRenderer, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if (renderState instanceof PlayerRenderState playerRenderState &&
                entityRenderer instanceof PlayerRenderer playerRenderer) {

            if (appliedGlidingRotations) {

                appliedGlidingRotations = false;
                poseStack.popPose();
            }
        }
    }
}
