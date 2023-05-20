package fuzs.hangglider.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.hangglider.api.client.event.ComputeCameraAngleEvents;
import fuzs.hangglider.mixin.client.accessor.CameraAccessor;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
abstract class GameRendererMixin {
    @Shadow
    @Final
    private Camera mainCamera;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V", shift = At.Shift.AFTER))
    public void renderLevel(float partialTicks, long finishTimeNano, PoseStack matrixStack, CallbackInfo callback) {
        ComputeCameraAngleEvents.ROLL.invoker().onComputeCameraAngle(GameRenderer.class.cast(this), this.mainCamera, partialTicks).ifPresent(roll -> {
            matrixStack.mulPose(Axis.ZP.rotationDegrees(roll));
        });
        ComputeCameraAngleEvents.PITCH.invoker().onComputeCameraAngle(GameRenderer.class.cast(this), this.mainCamera, partialTicks).ifPresent(pitch -> {
            ((CameraAccessor) this.mainCamera).hangglider$setXRot(pitch);
        });
        ComputeCameraAngleEvents.YAW.invoker().onComputeCameraAngle(GameRenderer.class.cast(this), this.mainCamera, partialTicks).ifPresent(yaw -> {
            ((CameraAccessor) this.mainCamera).hangglider$setYRot(yaw);
        });
    }
}
