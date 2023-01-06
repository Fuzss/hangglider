package fuzs.openglider.mixin;

import fuzs.openglider.api.event.PlayerTickEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick$head(CallbackInfo callbackInfo) {
        PlayerTickEvents.START_TICK.invoker().onStartTick((Player) (Object) this);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick$tail(CallbackInfo callbackInfo) {
        PlayerTickEvents.END_TICK.invoker().onEndTick((Player) (Object) this);
    }
}
