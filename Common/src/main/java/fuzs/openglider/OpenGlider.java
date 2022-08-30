package fuzs.openglider;

import fuzs.openglider.config.ClientConfig;
import fuzs.openglider.config.ServerConfig;
import fuzs.openglider.init.ModRegistry;
import fuzs.openglider.proxy.ClientProxy;
import fuzs.openglider.proxy.Proxy;
import fuzs.openglider.proxy.ServerProxy;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.DistTypeExecutor;
import fuzs.puzzleslib.core.ModConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenGlider implements ModConstructor {
    public static final String MOD_ID = "openglider";
    public static final String MOD_NAME = "Open Glider";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final Proxy PROXY = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CoreServices.FACTORIES
            .clientConfig(ClientConfig.class, () -> new ClientConfig())
            .serverConfig(ServerConfig.class, () -> new ServerConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
        ModRegistry.touch();
    }
}
