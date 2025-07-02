package fuzs.hangglider.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.hangglider.client.handler.FovModifierHandler;
import fuzs.hangglider.client.handler.GliderRenderHandler;
import fuzs.hangglider.client.handler.GlidingCameraHandler;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.client.renderer.entity.layers.GliderLayer;
import fuzs.hangglider.client.renderer.item.properties.conditional.GliderDeployed;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.GuiLayersContext;
import fuzs.puzzleslib.api.client.core.v1.context.ItemModelsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LivingEntityRenderLayersContext;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.entity.player.ComputeFovModifierCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.ComputeCameraAnglesCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.ExtractRenderStateCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderHandEvents;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderLivingEvents;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.entity.EntityType;

public class HangGliderClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractRenderStateCallback.EVENT.register(GliderRenderHandler::onExtractRenderState);
        ComputeFovModifierCallback.EVENT.register(FovModifierHandler::onComputeFovModifier);
        ClientTickEvents.END.register(GlidingCameraHandler::onEndClientTick);
        ClientTickEvents.END.register(ElytraEquippedHandler::onEndClientTick);
        RenderLivingEvents.BEFORE.register(GliderRenderHandler::onBeforeRenderEntity);
        RenderLivingEvents.AFTER.register(GliderRenderHandler::onAfterRenderEntity);
        RenderHandEvents.BOTH.register(GlidingCameraHandler::onRenderHand);
        ComputeCameraAnglesCallback.EVENT.register(GlidingCameraHandler::onComputeCameraRoll);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(GliderLayer.GLIDER, GliderModel::createLayer);
    }

    @Override
    public void onRegisterItemModels(ItemModelsContext context) {
        context.registerConditionalItemModelProperty(HangGlider.id("glider/deployed"), GliderDeployed.MAP_CODEC);
    }

    @Override
    public void onRegisterLivingEntityRenderLayers(LivingEntityRenderLayersContext context) {
        context.registerRenderLayer(EntityType.PLAYER,
                (RenderLayerParent<PlayerRenderState, PlayerModel> renderLayerParent, EntityRendererProvider.Context context1) -> {
                    return new GliderLayer(renderLayerParent, context1.getModelSet());
                });
    }

    @Override
    public void onRegisterGuiLayers(GuiLayersContext context) {
        context.registerGuiLayer(GuiLayersContext.CROSSHAIR,
                HangGlider.id("elytra_equipped"),
                ElytraEquippedHandler::render);
    }
}
