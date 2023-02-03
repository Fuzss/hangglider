package fuzs.openglider.client.handler;

import fuzs.openglider.helper.GliderCapabilityHelper;
import fuzs.openglider.helper.PlayerGlidingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class FovModifierHandler {

    public static Optional<Float> onComputeFovModifier(Player player, float fovModifier, float newFovModifier) {
        if (GliderCapabilityHelper.getIsGliderDeployed(player) && PlayerGlidingHelper.isAllowedToGlide(player)) {
            return Optional.of(Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get().floatValue(), 1.0F, fovModifier * 1.05F));
        }
        return Optional.empty();
    }
}
