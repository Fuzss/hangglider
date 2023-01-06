package fuzs.openglider.mixin.client;

import fuzs.openglider.client.OpenGliderClient;
import fuzs.openglider.init.ModRegistry;
import fuzs.openglider.world.item.HangGliderItem;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
abstract class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @ModifyVariable(method = "render", at = @At("HEAD"), ordinal = 0)
    public BakedModel render$modifyVariable$head$bakedModel(BakedModel bakedModel, ItemStack stack, ItemTransforms.TransformType transformType) {
        if (transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED) {
            if (stack.is(ModRegistry.HANG_GLIDER_ITEM.get())) {
                return this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation(OpenGliderClient.HANG_GLIDER_LOCATION, "inventory"));
            } else if (stack.is(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get())) {
                return this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation(OpenGliderClient.REINFORCED_HANG_GLIDER_LOCATION, "inventory"));
            }
        }
        return bakedModel;
    }

    @ModifyVariable(method = "getModel", at = @At(value = "LOAD", ordinal = 0), ordinal = 0)
    public BakedModel getModel$modifyVariable$load$bakedModel(BakedModel bakedModel, ItemStack stack) {
        if (stack.getItem() instanceof HangGliderItem) {
//            return this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation(OpenGliderClient.HANG_GLIDER_IN_HAND_LOCATION, "inventory"));
            return OpenGliderClient.hangGliderInHandModel;
        }
        return bakedModel;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;isCustomRenderer()Z"))
    public boolean isCustomRenderer(BakedModel bakedModel, ItemStack stack, ItemTransforms.TransformType transformType) {
        boolean itemModel = transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED;
        if (!itemModel && stack.getItem() instanceof HangGliderItem) return true;
        return bakedModel.isCustomRenderer();
    }
}
