package fuzs.hangglider.api.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

@FunctionalInterface
public interface RenderHandCallback {
    Event<RenderHandCallback> EVENT = EventFactory.createArrayBacked(RenderHandCallback.class, listeners -> (InteractionHand hand, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) -> {
        for (RenderHandCallback event : listeners) {
            if (event.onRenderHand(hand, poseStack, multiBufferSource, packedLight, partialTick, interpolatedPitch, swingProgress, equipProgress, stack).isPresent()) {
                return Optional.of(Unit.INSTANCE);
            }
        }
        return Optional.empty();
    });

    Optional<Unit> onRenderHand(InteractionHand hand, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack);
}
