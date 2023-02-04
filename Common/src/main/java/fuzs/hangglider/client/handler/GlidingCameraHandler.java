package fuzs.hangglider.client.handler;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ClientConfig;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class GlidingCameraHandler {
    private static float gliderRotation;
    private static float gliderRotationOld;
    private static CameraType oldCameraType;

    public static void onClientTick$End(Minecraft minecraft) {

        if (minecraft.player == null) return;

        if (HangGlider.CONFIG.get(ClientConfig.class).thirdPersonGliding) {

            setThirdPersonGliding(minecraft.player, minecraft.options);
        }

        if (HangGlider.CONFIG.get(ClientConfig.class).glidingCameraTilt) {

            updateGlidingRotation(minecraft.player);
        }
    }

    private static void setThirdPersonGliding(Player player, Options options) {

        if (PlayerGlidingHelper.isGliding(player)) {

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

        if (PlayerGlidingHelper.isGliding(player)) {

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
                rotationDelta = rotationDelta / (float) (Math.PI) * 180.0F * 0.4F * (float) HangGlider.CONFIG.get(ClientConfig.class).glidingTiltAmount;
                gliderRotationOld = gliderRotation;
                gliderRotation += (rotationDelta - gliderRotation) * (float) HangGlider.CONFIG.get(ClientConfig.class).glidingTiltSpeed;
            }

        } else {

            gliderRotationOld = gliderRotation = 0.0F;
        }
    }

    public static Optional<Float> onComputeCameraRoll(GameRenderer renderer, Camera camera, float tickDelta) {

        if (HangGlider.CONFIG.get(ClientConfig.class).glidingCameraTilt) {

            if (gliderRotation != 0.0F || gliderRotationOld != 0.0F) {

                return Optional.of(Mth.lerp(tickDelta, gliderRotationOld, gliderRotation));
            }
        }

        return Optional.empty();
    }
}
