package fuzs.hangglider.client;

import fuzs.hangglider.HangGlider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class HangGliderFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(HangGlider.MOD_ID, HangGliderClient::new);
    }
}
