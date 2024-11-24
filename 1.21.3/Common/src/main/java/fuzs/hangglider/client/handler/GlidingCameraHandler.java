package fuzs.hangglider.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ClientConfig;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.data.MutableFloat;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class GlidingCameraHandler {
    private static float gliderRotation;
    private static float gliderRotationOld;
    private static CameraType oldCameraType;

    public static void onEndClientTick(Minecraft minecraft) {

        if (minecraft.player == null) return;

        if (HangGlider.CONFIG.get(ClientConfig.class).autoThirdPersonGliding) {

            setThirdPersonGliding(minecraft.player, minecraft.options);
        }

        if (HangGlider.CONFIG.get(ClientConfig.class).glidingCameraTilt) {

            updateGlidingRotation(minecraft.player);
        }
    }

    private static void setThirdPersonGliding(Player player, Options options) {

        if (ModRegistry.GLIDING_CAPABILITY.get(player).isGliding()) {

            if (oldCameraType == null) {

                oldCameraType = options.getCameraType();
                if (options.getCameraType() == CameraType.FIRST_PERSON) {

                    options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            }
        } else if (oldCameraType != null) {

            if (options.getCameraType() == CameraType.THIRD_PERSON_BACK) {

                options.setCameraType(oldCameraType);
            }

            oldCameraType = null;
        }
    }

    private static void updateGlidingRotation(Player player) {

        if (ModRegistry.GLIDING_CAPABILITY.get(player).isGliding()) {

            // code from PlayerRenderer#applyRotations which is used there for rotating player model while flying
            Vec3 vector3d = player.getViewVector(1.0F);
            Vec3 vector3d1 = player.getDeltaMovement();
            double d0 = vector3d1.horizontalDistanceSqr();
            double d1 = vector3d.horizontalDistanceSqr();
            if (d0 > 0.0 && d1 > 0.0) {

                double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d0 * d1);
                double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
                // fix Math#acos returning NaN when d2 > 1.0
                float rotationDelta = (float) (Math.signum(d3) * Math.acos(Math.min(d2, 1.0)));
                rotationDelta *= Mth.RAD_TO_DEG * 0.4F * (float) HangGlider.CONFIG.get(ClientConfig.class).glidingTiltAmount;
                gliderRotationOld = gliderRotation;
                gliderRotation += (rotationDelta - gliderRotation) * (float) HangGlider.CONFIG.get(ClientConfig.class).glidingTiltSpeed;
            }

        } else {

            gliderRotationOld = gliderRotation = 0.0F;
        }
    }

    public static void onComputeCameraRoll(GameRenderer renderer, Camera camera, float tickDelta, MutableFloat pitch, MutableFloat yaw, MutableFloat roll) {

        if (HangGlider.CONFIG.get(ClientConfig.class).glidingCameraTilt) {

            if (gliderRotation != 0.0F || gliderRotationOld != 0.0F) {

                roll.accept(Mth.lerp(tickDelta, gliderRotationOld, gliderRotation));
            }
        }
    }

    public static EventResult onRenderHand(ItemInHandRenderer itemInHandRenderer, AbstractClientPlayer player, HumanoidArm humanoidArm, ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress) {
        return ModRegistry.GLIDING_CAPABILITY.get(player).isGliding() ? EventResult.INTERRUPT : EventResult.PASS;
    }
}
