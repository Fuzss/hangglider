package fuzs.openglider.mixin.client;

import fuzs.openglider.helper.GliderCapabilityHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getFallFlyingTicks", at = @At("HEAD"), cancellable = true)
    public void getFallFlyingTicks(CallbackInfoReturnable<Integer> callback) {
//        if (Player.class.isInstance(this) && GliderCapabilityHelper.getIsGliderDeployed(Player.class.cast(this))) {
//            callback.setReturnValue(100);
//        }
    }

    @Inject(method = "isVisuallySwimming", at = @At("HEAD"), cancellable = true)
    public void isVisuallySwimming(CallbackInfoReturnable<Boolean> callback) {
        if (this.hasPose(Pose.FALL_FLYING) && Player.class.isInstance(this) && GliderCapabilityHelper.getIsGliderDeployed(Player.class.cast(this))) {
            callback.setReturnValue(false);
        }
    }
}
