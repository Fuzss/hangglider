package fuzs.openglider.client;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.client.event.ComputeFovModifierCallback;
import fuzs.openglider.api.client.event.RenderPlayerEvents;
import fuzs.openglider.client.handler.FovModifierHandler;
import fuzs.openglider.client.handler.GlidingCrouchHandler;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.fabricmc.api.ClientModInitializer;

public class OpenGliderFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientFactories.INSTANCE.clientModConstructor(OpenGlider.MOD_ID).accept(new OpenGliderClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        RenderPlayerEvents.BEFORE.register(GlidingCrouchHandler::onRenderPlayer$Pre);
        RenderPlayerEvents.AFTER.register(GlidingCrouchHandler::onRenderPlayer$Post);
        ComputeFovModifierCallback.EVENT.register(FovModifierHandler::onComputeFovModifier);
    }
}
