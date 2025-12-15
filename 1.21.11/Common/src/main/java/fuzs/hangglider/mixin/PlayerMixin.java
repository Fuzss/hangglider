package fuzs.hangglider.mixin;

import fuzs.hangglider.init.ModRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    protected void updatePlayerPose(CallbackInfo callback) {
        if (this.canPlayerFitWithinBlocksAndEntitiesWhen(Pose.SWIMMING) &&
                ModRegistry.GLIDING_ATTACHMENT_TYPE.get(this).gliding()) {
            this.setPose(Pose.SPIN_ATTACK);
            callback.cancel();
        }
    }

    @Shadow
    protected abstract boolean canPlayerFitWithinBlocksAndEntitiesWhen(Pose pose);
}
