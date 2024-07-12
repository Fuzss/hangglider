package fuzs.hangglider.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.hangglider.client.handler.FovModifierHandler;
import fuzs.hangglider.client.handler.GlidingCameraHandler;
import fuzs.hangglider.client.handler.GlidingCrouchHandler;
import fuzs.hangglider.client.init.ModClientRegistry;
import fuzs.hangglider.client.model.GliderModel;
import fuzs.hangglider.client.renderer.entity.layers.GliderLayer;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.ItemModelPropertiesContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LivingEntityRenderLayersContext;
import fuzs.puzzleslib.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.api.client.event.v1.entity.player.ComputeFovModifierCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.ComputeCameraAnglesCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderGuiCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderHandCallback;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderPlayerEvents;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HangGliderClient implements ClientModConstructor {
    public static final ResourceLocation ITEM_PROPERTY_DEPLOYED = HangGlider.id("deployed");
    public static final ResourceLocation ITEM_PROPERTY_BROKEN = HangGlider.id("broken");

    @Override
    public void onConstructMod() {
        registerHandlers();
    }

    private static void registerHandlers() {
        ComputeFovModifierCallback.EVENT.register(FovModifierHandler::onComputeFovModifier);
        ClientTickEvents.END.register(GlidingCameraHandler::onEndClientTick);
        ClientTickEvents.END.register(ElytraEquippedHandler.INSTANCE::onClientTick$End);
        RenderGuiCallback.EVENT.register(ElytraEquippedHandler.INSTANCE::onRenderGui);
        RenderPlayerEvents.BEFORE.register(GlidingCrouchHandler::onRenderPlayer$Pre);
        RenderPlayerEvents.AFTER.register(GlidingCrouchHandler::onRenderPlayer$Post);
        RenderHandCallback.EVENT.register(GlidingCameraHandler::onRenderHand);
        ComputeCameraAnglesCallback.EVENT.register(GlidingCameraHandler::onComputeCameraRoll);
    }

    @Override
    public void onClientSetup() {
        GliderLayer.registerGliderTexture(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value(),
                HangGlider.id("textures/models/glider/reinforced_hang_glider.png")
        );
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.GLIDER, GliderModel::createLayer);
    }

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        context.registerItemProperty(ITEM_PROPERTY_DEPLOYED,
                (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
                    return livingEntity instanceof Player player &&
                            PlayerGlidingHelper.getGliderInHand(player) == itemStack ? 1.0F : 0.0F;
                },
                ModRegistry.HANG_GLIDER_ITEM.value(),
                ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value()
        );
        context.registerItemProperty(ITEM_PROPERTY_BROKEN,
                (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
                    return !PlayerGlidingHelper.isValidGlider(itemStack) ? 1.0F : 0.0F;
                },
                ModRegistry.HANG_GLIDER_ITEM.value(),
                ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value()
        );
    }

    @Override
    public void onRegisterLivingEntityRenderLayers(LivingEntityRenderLayersContext context) {
        context.registerRenderLayer(EntityType.PLAYER,
                (RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent, EntityRendererProvider.Context context1) -> {
                    return new GliderLayer(renderLayerParent, context1.getModelSet());
                }
        );
    }
}
