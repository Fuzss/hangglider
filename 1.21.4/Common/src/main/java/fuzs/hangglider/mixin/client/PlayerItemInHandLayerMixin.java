package fuzs.hangglider.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.hangglider.client.handler.GliderRenderHandler;
import fuzs.puzzleslib.api.client.util.v1.RenderPropertyKey;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
abstract class PlayerItemInHandLayerMixin<S extends PlayerRenderState, M extends EntityModel<S> & ArmedModel & HeadedModel> extends ItemInHandLayer<S, M> {

    public PlayerItemInHandLayerMixin(RenderLayerParent<S, M> renderLayerParent, ItemRenderer itemRenderer) {
        super(renderLayerParent, itemRenderer);
    }

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    protected void renderArmWithItem(S playerRenderState, @Nullable BakedModel bakedModel, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, CallbackInfo callback) {
        if (RenderPropertyKey.containsRenderProperty(playerRenderState, GliderRenderHandler.IS_GLIDING_KEY) &&
                RenderPropertyKey.getRenderProperty(playerRenderState, GliderRenderHandler.IS_GLIDING_KEY)) {
            callback.cancel();
        }
    }
}
