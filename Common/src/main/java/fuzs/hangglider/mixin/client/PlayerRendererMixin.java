package fuzs.hangglider.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "setupRotations", at = @At("TAIL"), cancellable = true)
    public void setupRotations(AbstractClientPlayer entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo callback) {
        if (PlayerGlidingHelper.isGliding(entityLiving)) {
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            // reposition to better align with bounding box just like swimming model
            matrixStack.translate(0.0F, -1.0F, 0.3F);
        }
    }
}
