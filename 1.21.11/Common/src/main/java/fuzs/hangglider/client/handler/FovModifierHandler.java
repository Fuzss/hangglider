package fuzs.hangglider.client.handler;

import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.event.v1.data.MutableFloat;
import net.minecraft.world.entity.player.Player;

public class FovModifierHandler {

    public static void onComputeFovModifier(Player player, MutableFloat fieldOfViewModifier) {
        if (ModRegistry.GLIDING_ATTACHMENT_TYPE.get(player).gliding()) {
            fieldOfViewModifier.mapAsFloat((Float value) -> value * (player.isDescending() ? 1.1F : 1.05F));
        }
    }
}
