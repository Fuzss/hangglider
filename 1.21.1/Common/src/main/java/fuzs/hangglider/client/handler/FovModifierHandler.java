package fuzs.hangglider.client.handler;

import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.event.v1.data.DefaultedFloat;
import net.minecraft.world.entity.player.Player;

public class FovModifierHandler {

    public static void onComputeFovModifier(Player player, DefaultedFloat fieldOfViewModifier) {
        if (ModRegistry.GLIDING_CAPABILITY.get(player).isGliding()) {
            fieldOfViewModifier.mapFloat(f -> f * (player.isDescending() ? 1.1F : 1.05F));
        }
    }
}
