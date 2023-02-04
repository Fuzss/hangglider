package fuzs.hangglider;

import fuzs.hangglider.config.ClientConfig;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.init.ModRegistry;
import fuzs.hangglider.networking.ClientboundForcePlayerPoseMessage;
import fuzs.hangglider.proxy.ClientProxy;
import fuzs.hangglider.proxy.Proxy;
import fuzs.hangglider.proxy.ServerProxy;
import fuzs.puzzleslib.api.networking.v3.NetworkHandlerV3;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.core.DistTypeExecutor;
import fuzs.puzzleslib.core.ModConstructor;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HangGlider implements ModConstructor {
    public static final String MOD_ID = "hangglider";
    public static final String MOD_NAME = "Hang Glider";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final NetworkHandlerV3 NETWORKING = NetworkHandlerV3.builder(MOD_ID).registerClientbound(ClientboundForcePlayerPoseMessage.class).build();
    @SuppressWarnings("Convert2MethodRef")
    public static final Proxy PROXY = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CommonFactories.INSTANCE
            .clientConfig(ClientConfig.class, () -> new ClientConfig())
            .serverConfig(ServerConfig.class, () -> new ServerConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
        ModRegistry.touch();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
