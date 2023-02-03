package fuzs.openglider.mixin.client;

import com.mojang.authlib.GameProfile;
import fuzs.openglider.api.client.event.ComputeFovModifierCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
abstract class AbstractClientPlayerFabricMixin extends Player {
    @Unique
    private float hangglider$currentFovModifier;

    public AbstractClientPlayerFabricMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(level, blockPos, f, gameProfile, profilePublicKey);
    }

    @ModifyVariable(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;fovEffectScale()Lnet/minecraft/client/OptionInstance;"), ordinal = 0)
    public float getFieldOfViewModifier$0(float fovModifier) {
        this.hangglider$currentFovModifier = fovModifier;
        return fovModifier;
    }

    @Inject(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;fovEffectScale()Lnet/minecraft/client/OptionInstance;"), cancellable = true)
    public void getFieldOfViewModifier$1(CallbackInfoReturnable<Float> callback) {
        float newFovModifier = Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get().floatValue(), 1.0F, this.hangglider$currentFovModifier);
        float fovModifier = ComputeFovModifierCallback.EVENT.invoker().onComputeFovModifier(this, this.hangglider$currentFovModifier, newFovModifier).orElseThrow();
        if (fovModifier != newFovModifier) callback.setReturnValue(fovModifier);
    }
}
