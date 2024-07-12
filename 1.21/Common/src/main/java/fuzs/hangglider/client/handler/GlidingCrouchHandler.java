package fuzs.hangglider.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GlidingCrouchHandler {
    private static boolean appliedGlidingRotations;

    public static EventResult onRenderPlayer$Pre(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {

        if (ModRegistry.GLIDING_CAPABILITY.get(player).isGliding()) {

            PlayerModel<AbstractClientPlayer> model = renderer.getModel();
            model.leftArmPose = model.rightArmPose = HumanoidModel.ArmPose.EMPTY;
            if (player.isCrouching()) {
                model.crouching = false;
                Vec3 vec3 = renderer.getRenderOffset((AbstractClientPlayer) player, partialTick);
                poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
            }
            
            float interpolatedYaw = Mth.lerp(partialTick, player.yHeadRotO, player.yHeadRot);

            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(-interpolatedYaw));
            poseStack.translate(0.0F, player.getBbHeight() / 2.0F, 0.0F);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.translate(0.0F, -player.getBbHeight() / 2.0F, 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(interpolatedYaw));

            appliedGlidingRotations = true;

            // reposition to better align with bounding box
            poseStack.translate(0.0F, -0.5F, 0.0F);
            
        }

        return EventResult.PASS;
    }

    public static void onRenderPlayer$Post(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {

        if (appliedGlidingRotations) {

            appliedGlidingRotations = false;
            poseStack.popPose();
        }

        if (ModRegistry.GLIDING_CAPABILITY.get(player).isGliding()) {

            if (player.isCrouching()) {
                
                Vec3 vec3 = renderer.getRenderOffset((AbstractClientPlayer) player, partialTick);
                poseStack.translate(vec3.x(), vec3.y(), vec3.z());
            }
        }
    }
}
