package fuzs.openglider.api.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public interface ComputeFovModifierCallback {
    Event<ComputeFovModifierCallback> EVENT = EventFactory.createArrayBacked(ComputeFovModifierCallback.class, listeners -> (Player player, float fovModifier, float newFovModifier) -> {
        for (ComputeFovModifierCallback event : listeners) {
            Optional<Float> result = event.onComputeFovModifier(player, fovModifier, newFovModifier);
            if (result.isPresent()) newFovModifier = result.get();
        }
        return Optional.of(newFovModifier);
    });

    /**
     * Called when computing the field of view modifier on the client, mostly depending on {@link Attributes#MOVEMENT_SPEED}.
     *
     * @param player      the client player this is calculated for
     * @param fovModifier modifier as calculated by vanilla
     * @return the modified value if needed
     */
    Optional<Float> onComputeFovModifier(Player player, float fovModifier, float newFovModifier);
}
