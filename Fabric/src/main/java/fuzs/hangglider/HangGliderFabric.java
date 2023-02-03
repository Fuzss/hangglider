package fuzs.hangglider;

import fuzs.hangglider.api.event.PlayerTickEvents;
import fuzs.hangglider.handler.PlayerGlidingHandler;
import fuzs.puzzleslib.core.CommonFactories;
import net.fabricmc.api.ModInitializer;

public class HangGliderFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonFactories.INSTANCE.modConstructor(HangGlider.MOD_ID).accept(new HangGlider());
        registerHandlers();
    }

    private static void registerHandlers() {
        PlayerTickEvents.END_TICK.register(PlayerGlidingHandler::onPlayerTick$End);
    }
}
