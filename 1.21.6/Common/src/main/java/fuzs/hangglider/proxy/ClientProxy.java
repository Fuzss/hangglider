package fuzs.hangglider.proxy;

import fuzs.hangglider.client.handler.ElytraEquippedHandler;

public class ClientProxy extends ServerProxy {

    @Override
    public void addElytraWidget() {
        ElytraEquippedHandler.activate();
    }
}
