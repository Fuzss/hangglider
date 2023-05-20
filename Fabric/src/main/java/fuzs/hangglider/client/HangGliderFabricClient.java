package fuzs.hangglider.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.api.client.event.ComputeCameraAngleEvents;
import fuzs.hangglider.api.client.event.RenderPlayerEvents;
import fuzs.hangglider.client.handler.GlidingCameraHandler;
import fuzs.hangglider.client.handler.GlidingCrouchHandler;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class HangGliderFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(HangGlider.MOD_ID, HangGliderClient::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        RenderPlayerEvents.BEFORE.register(GlidingCrouchHandler::onRenderPlayer$Pre);
        RenderPlayerEvents.AFTER.register(GlidingCrouchHandler::onRenderPlayer$Post);
        // TODO don't forget about render hand event when migrating to puzzles lib events in 1.19.4
        ComputeCameraAngleEvents.ROLL.register(GlidingCameraHandler::onComputeCameraRoll);
    }
}
