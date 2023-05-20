package fuzs.hangglider.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.hangglider.client.handler.FovModifierHandler;
import fuzs.hangglider.client.handler.GlidingCameraHandler;
import fuzs.hangglider.client.init.ModClientRegistry;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.client.renderer.entity.layers.GliderLayer;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.BuildCreativeModeTabContentsContext;
import fuzs.puzzleslib.api.client.core.v1.context.ItemModelPropertiesContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LivingEntityRenderLayersContext;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.ComputeFovModifierCallback;
import fuzs.puzzleslib.api.client.event.v1.RenderGuiCallback;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HangGliderClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerHandlers();
    }

    private static void registerHandlers() {
        ComputeFovModifierCallback.EVENT.register(FovModifierHandler::onComputeFovModifier);
        ClientTickEvents.END.register(GlidingCameraHandler::onClientTick$End);
        ClientTickEvents.END.register(ElytraEquippedHandler.INSTANCE::onClientTick$End);
        RenderGuiCallback.EVENT.register(ElytraEquippedHandler.INSTANCE::onRenderGui);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.GLIDER, GliderModel::createLayer);
    }

    @Override
    public void onRegisterLivingEntityRenderLayers(LivingEntityRenderLayersContext context) {
        context.registerRenderLayer(EntityType.PLAYER, (RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent, EntityRendererProvider.Context context1) -> {
            return new GliderLayer(renderLayerParent, context1.getModelSet());
        });
    }

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        context.registerItemProperty(HangGlider.id("deployed"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return livingEntity instanceof Player player && PlayerGlidingHelper.isValidGlider(itemStack) && PlayerGlidingHelper.isGliderDeployed(player) && PlayerGlidingHelper.getGliderInHand(player) == itemStack ? 1.0F : 0.0F;
        }, ModRegistry.HANG_GLIDER_ITEM.get(), ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
        context.registerItemProperty(HangGlider.id("broken"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return !PlayerGlidingHelper.isValidGlider(itemStack) ? 1.0F : 0.0F;
        }, ModRegistry.HANG_GLIDER_ITEM.get(), ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
    }

    @Override
    public void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsContext context) {
        context.registerBuildListener(HangGlider.MOD_ID, (featureFlagSet, output, bl) -> {
            output.accept(ModRegistry.HANG_GLIDER_ITEM.get());
            output.accept(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
            output.accept(ModRegistry.GLIDER_WING_ITEM.get());
            output.accept(ModRegistry.GLIDER_FRAMEWORK_ITEM.get());
        });
    }
}
