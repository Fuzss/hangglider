package fuzs.hangglider.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.hangglider.client.handler.GliderRenderHandler;
import fuzs.puzzleslib.api.client.renderer.v1.RenderStateExtraData;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
abstract class PlayerItemInHandLayerMixin<S extends AvatarRenderState, M extends EntityModel<S> & ArmedModel<S> & HeadedModel> extends ItemInHandLayer<S, M> {

    public PlayerItemInHandLayerMixin(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "submitArmWithItem", at = @At("HEAD"), cancellable = true)
    protected void submitArmWithItem(S avatarRenderState, ItemStackRenderState itemStackRenderState, HumanoidArm humanoidArm, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, CallbackInfo callback) {
        if (RenderStateExtraData.getOrDefault(avatarRenderState, GliderRenderHandler.IS_GLIDING_KEY, false)) {
            callback.cancel();
        }
    }
}
