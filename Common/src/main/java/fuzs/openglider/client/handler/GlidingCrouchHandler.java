package fuzs.openglider.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class GlidingCrouchHandler {

    public static Optional<Unit> onRenderPlayer$Pre(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        if (player.isCrouching() && player.getFallFlyingTicks() > 4) {
            renderer.getModel().crouching = false;
            Vec3 vec3 = renderer.getRenderOffset((AbstractClientPlayer) player, partialTick);
            poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        }
        return Optional.empty();
    }

    public static void onRenderPlayer$Post(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        if (player.isCrouching() && player.getFallFlyingTicks() > 4) {
            Vec3 vec3 = renderer.getRenderOffset((AbstractClientPlayer) player, partialTick);
            poseStack.translate(vec3.x(), vec3.y(), vec3.z());
        }
    }
}
