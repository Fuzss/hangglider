package fuzs.hangglider.mixin.client;

import com.mojang.authlib.GameProfile;
import fuzs.hangglider.handler.PlayerGlidingHandler;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RemotePlayer.class)
abstract class RemotePlayerMixin extends AbstractClientPlayer {

    public RemotePlayerMixin(ClientLevel clientLevel, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(clientLevel, gameProfile, profilePublicKey);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo callback) {

        // we need this hook here, as the remote player recalculates animations after the main player tick method where this is usually set by us

        if (PlayerGlidingHelper.isGliding(this)) {

            PlayerGlidingHandler.resetClientAnimations(this);
        }
    }
}
