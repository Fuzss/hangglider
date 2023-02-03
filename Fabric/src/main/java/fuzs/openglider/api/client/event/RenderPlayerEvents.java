package fuzs.openglider.api.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public final class RenderPlayerEvents {
    public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, listeners -> (Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) -> {
        for (Before event : listeners) {
            if (event.beforeRenderPlayer(player, renderer, partialTick, poseStack, multiBufferSource, packedLight).isPresent()) {
                return Optional.of(Unit.INSTANCE);
            }
        }
        return Optional.empty();
    });

    public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, listeners -> (Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) -> {
        for (After event : listeners) {
            event.afterRenderPlayer(player, renderer, partialTick, poseStack, multiBufferSource, packedLight);
        }
    });

    private RenderPlayerEvents() {

    }

    @FunctionalInterface
    public interface Before {

        Optional<Unit> beforeRenderPlayer(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight);
    }

    @FunctionalInterface
    public interface After {

        void afterRenderPlayer(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight);
    }
}
