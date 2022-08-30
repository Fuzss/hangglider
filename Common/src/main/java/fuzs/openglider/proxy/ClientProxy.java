package fuzs.openglider.proxy;

import fuzs.openglider.client.resources.sounds.PlayerGlidingSoundInstance;
import fuzs.puzzleslib.proxy.Proxy;
import net.minecraft.client.Minecraft;

public class ClientProxy extends ServerProxy {

    @Override
    public void afterPlayerStartGliding() {
        ((Minecraft) Proxy.INSTANCE.getClientInstance()).getSoundManager().play(new PlayerGlidingSoundInstance(Proxy.INSTANCE.getClientPlayer()));
    }

    @Override
    public void addElytraWidget() {
        super.addElytraWidget();
    }
}
