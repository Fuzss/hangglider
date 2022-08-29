package fuzs.openglider.proxy;

import fuzs.openglider.client.resources.sounds.PlayerGlidingSoundInstance;
import fuzs.puzzleslib.proxy.Proxy;
import net.minecraft.client.Minecraft;

public class ClientProxy extends ServerProxy {

    @Override
    public void afterPlayerStartGliding() {
        Proxy proxy = Proxy.INSTANCE;
        ((Minecraft) proxy.getClientInstance()).getSoundManager().play(new PlayerGlidingSoundInstance(proxy.getClientPlayer()));
    }

    @Override
    public void addElytraWidget() {
        super.addElytraWidget();
    }
}
