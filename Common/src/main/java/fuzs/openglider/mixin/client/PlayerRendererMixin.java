package fuzs.openglider.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.openglider.helper.GliderCapabilityHelper;
import fuzs.openglider.helper.PlayerGlidingHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

//    @Inject(method = "setupRotations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isFallFlying()Z"))
    @Inject(method = "setupRotations", at = @At("HEAD"), cancellable = true)
    public void isFallFlying(AbstractClientPlayer entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo callback) {
        if (GliderCapabilityHelper.getIsGliderDeployed(entityLiving) && PlayerGlidingHelper.isAllowedToGlide(entityLiving)) {
            super.setupRotations(entityLiving, matrixStack, ageInTicks, rotationYaw, partialTicks);
//            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
            float g = (float)entityLiving.getFallFlyingTicks() + partialTicks;
            float h = Mth.clamp(g * g / 100.0F, 0.0F, 1.0F);
            h = 1.0F;
            if (!entityLiving.isAutoSpinAttack()) {
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(h * (-90.0F - entityLiving.getXRot() * 0)));
            }

//            Vec3 vec3 = entityLiving.getViewVector(partialTicks);
//            Vec3 vec32 = entityLiving.getDeltaMovement();
//            double d = vec32.horizontalDistanceSqr();
//            double e = vec3.horizontalDistanceSqr();
//            if (d > 0.0 && e > 0.0) {
//                double i = (vec32.x * vec3.x + vec32.z * vec3.z) / Math.sqrt(d * e);
//                double j = vec32.x * vec3.z - vec32.z * vec3.x;
//                matrixStack.mulPose(Vector3f.YP.rotation((float)(Math.signum(j) * Math.acos(i))));
//            }
//            return true;
            callback.cancel();
        }
//        return entity.isFallFlying();
    }
}
