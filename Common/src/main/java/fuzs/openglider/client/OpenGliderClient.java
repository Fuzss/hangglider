package fuzs.openglider.client;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.world.item.Glider;
import fuzs.openglider.client.init.ModClientRegistry;
import fuzs.openglider.client.model.GliderModel;
import fuzs.openglider.client.renderer.entity.layers.GliderLayer;
import fuzs.openglider.helper.GliderCapabilityHelper;
import fuzs.openglider.helper.PlayerGlidingHelper;
import fuzs.openglider.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class OpenGliderClient implements ClientModConstructor {

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.GLIDER, GliderModel::createLayer);
    }

    @Override
    public void onRegisterLivingEntityRenderLayers(LivingEntityRenderLayersContext context) {
        context.registerRenderLayer(EntityType.PLAYER, (RenderLayerParent<Player, ? extends EntityModel<Player>> renderLayerParent, EntityRendererProvider.Context context1) -> {
            return new GliderLayer<>(renderLayerParent, context1.getModelSet());
        });
    }

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        registerHangGliderModelProperties(context, ModRegistry.HANG_GLIDER_ITEM.get());
        registerHangGliderModelProperties(context, ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
    }

    private static void registerHangGliderModelProperties(ItemModelPropertiesContext context, Item item) {
        context.registerItem(item, new ResourceLocation(OpenGlider.MOD_ID, "deployed"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return livingEntity instanceof Player player && GliderCapabilityHelper.getIsGliderDeployed(player) && PlayerGlidingHelper.getGliderInHand(player) == itemStack ? 1.0F : 0.0F;
        });
        context.registerItem(item, new ResourceLocation(OpenGlider.MOD_ID, "broken"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return ((Glider) itemStack.getItem()).isBroken(itemStack) ? 1.0F : 0.0F;
        });
    }
}
