package fuzs.hangglider.client.handler;

import fuzs.hangglider.helper.PlayerGlidingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class FovModifierHandler {

    public static Optional<Float> onComputeFovModifier(Player player, float fovModifier, float newFovModifier) {
        if (PlayerGlidingHelper.isGliding(player)) {
            fovModifier *= player.isDescending() ? 1.1F : 1.05F;
            return Optional.of(Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get().floatValue(), 1.0F, fovModifier));
        }
        return Optional.empty();
    }
}
