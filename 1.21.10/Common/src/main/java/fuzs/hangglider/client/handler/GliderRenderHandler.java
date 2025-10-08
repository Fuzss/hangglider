package fuzs.hangglider.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GliderRenderHandler {
    public static final ContextKey<ItemStack> GLIDER_IN_HAND_KEY = new ContextKey<>(HangGlider.id("glider_in_hand"));
    public static final ContextKey<Boolean> IS_GLIDING_KEY = new ContextKey<>(HangGlider.id("is_gliding"));
    public static final ContextKey<Float> HEAD_ROT_KEY = new ContextKey<>(HangGlider.id("head_rot"));

    private static boolean appliedGlidingRotations;

    public static void onExtractRenderState(Entity entity, EntityRenderState renderState, float partialTick) {
        if (entity instanceof Player player && renderState instanceof AvatarRenderState avatarRenderState) {
            RenderStateExtraData.set(renderState, GLIDER_IN_HAND_KEY, PlayerGlidingHelper.getGliderInHand(player));
            RenderStateExtraData.set(renderState,
                    IS_GLIDING_KEY,
                    ModRegistry.GLIDING_ATTACHMENT_TYPE.get(player).gliding());
            RenderStateExtraData.set(renderState,
                    HEAD_ROT_KEY,
                    Mth.rotLerp(partialTick, player.yHeadRotO, player.yHeadRot));
            if (RenderStateExtraData.getOrDefault(renderState, IS_GLIDING_KEY, false)) {
                avatarRenderState.rightHandItem.clear();
                avatarRenderState.leftHandItem.clear();
                avatarRenderState.rightArmPose = avatarRenderState.leftArmPose = HumanoidModel.ArmPose.EMPTY;
                avatarRenderState.isCrouching = false;
            }
        }
    }

    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> EventResult onBeforeRenderEntity(S entityRenderState, LivingEntityRenderer<T, S, M> entityRenderer, float partialTick, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        if (entityRenderState instanceof AvatarRenderState avatarRenderState
                && entityRenderer instanceof AvatarRenderer<?>) {
            if (RenderStateExtraData.getOrDefault(entityRenderState, IS_GLIDING_KEY, false)) {
                float headRot = RenderStateExtraData.getOrDefault(entityRenderState, HEAD_ROT_KEY, 0.0F);
                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(-headRot));
                poseStack.translate(0.0F, avatarRenderState.boundingBoxHeight / 2.0F, 0.0F);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(0.0F, -avatarRenderState.boundingBoxHeight / 2.0F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(headRot));
                // reposition to better align with the bounding box
                poseStack.translate(0.0F, -0.5F, 0.0F);
                appliedGlidingRotations = true;
            }
        }

        return EventResult.PASS;
    }

    public static <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> void onAfterRenderEntity(S entityRenderState, LivingEntityRenderer<T, S, M> entityRenderer, float partialTick, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        if (entityRenderState instanceof AvatarRenderState && entityRenderer instanceof AvatarRenderer<?>) {
            if (appliedGlidingRotations) {
                appliedGlidingRotations = false;
                poseStack.popPose();
            }
        }
    }
}
