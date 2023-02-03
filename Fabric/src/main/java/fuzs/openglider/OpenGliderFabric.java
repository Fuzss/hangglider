package fuzs.openglider;

import fuzs.openglider.api.event.PlayerTickEvents;
import fuzs.openglider.handler.PlayerGlidingHandler;
import fuzs.puzzleslib.core.CommonFactories;
import net.fabricmc.api.ModInitializer;

public class OpenGliderFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonFactories.INSTANCE.modConstructor(OpenGlider.MOD_ID).accept(new OpenGlider());
        registerHandlers();
    }

    private static void registerHandlers() {
        PlayerTickEvents.END_TICK.register(PlayerGlidingHandler::onPlayerTick$End);
    }
}
