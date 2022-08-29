package fuzs.openglider;

import fuzs.openglider.handler.FlyingHandler;
import fuzs.puzzleslib.core.CoreServices;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class OpenGliderFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CoreServices.FACTORIES.modConstructor(OpenGlider.MOD_ID).accept(new OpenGlider());
        registerHandlers();
    }

    private static void registerHandlers() {
        FlyingHandler flyingHandler = new FlyingHandler();
        ServerEntityEvents.ENTITY_LOAD.register(flyingHandler::onEntityJoinLevel);
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((ServerPlayer player, ServerLevel origin, ServerLevel destination) -> {
            flyingHandler.onEntityJoinLevel(player, destination);
        });
        EntityTrackingEvents.START_TRACKING.register(flyingHandler::onStartTracking);
    }
}
