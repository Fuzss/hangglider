package fuzs.openglider.client;

import fuzs.openglider.OpenGlider;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.fabricmc.api.ClientModInitializer;

public class OpenGliderFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCoreServices.FACTORIES.clientModConstructor(OpenGlider.MOD_ID).accept(new OpenGliderClient());
    }
}
