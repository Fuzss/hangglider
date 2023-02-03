package fuzs.hangglider.mixin;

import fuzs.hangglider.api.event.PlayerTickEvents;
import fuzs.hangglider.api.extension.ForcedPosePlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
abstract class PlayerFabricMixin extends LivingEntity implements ForcedPosePlayer {
    @Nullable
    private Pose hangglider$forcedPose;

    protected PlayerFabricMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick$0(CallbackInfo callbackInfo) {
        PlayerTickEvents.START_TICK.invoker().onStartTick(Player.class.cast(this));
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick$1(CallbackInfo callbackInfo) {
        PlayerTickEvents.END_TICK.invoker().onEndTick(Player.class.cast(this));
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    protected void updatePlayerPose(CallbackInfo callback) {
        if (this.hangglider$forcedPose != null) {
            this.setPose(this.hangglider$forcedPose);
            callback.cancel();
        }
    }

    @Override
    public void hangglider$setForcedPose(@Nullable Pose pose) {
        this.hangglider$forcedPose = pose;
    }

    @Nullable
    @Override
    public Pose hangglider$getForcedPose() {
        return this.hangglider$forcedPose;
    }
}
