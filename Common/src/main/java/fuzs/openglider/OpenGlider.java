package fuzs.openglider;

import fuzs.openglider.config.ClientConfig;
import fuzs.openglider.config.ServerConfig;
import fuzs.openglider.init.ModRegistry;
import fuzs.openglider.network.S2CGlidingMessage;
import fuzs.openglider.network.S2CSyncGliderDataMessage;
import fuzs.openglider.network.S2CUpdateClientTargetMessage;
import fuzs.openglider.proxy.ClientProxy;
import fuzs.openglider.proxy.CommonProxy;
import fuzs.openglider.proxy.ServerProxy;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.DistTypeExecutor;
import fuzs.puzzleslib.core.ModConstructor;
import fuzs.puzzleslib.network.MessageDirection;
import fuzs.puzzleslib.network.NetworkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenGlider implements ModConstructor {
    public static final String MOD_ID = "openglider";
    public static final String MOD_NAME = "Open Glider";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final CommonProxy PROXY = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static final NetworkHandler NETWORK = CoreServices.FACTORIES.network(MOD_ID);
    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CoreServices.FACTORIES
            .clientConfig(ClientConfig.class, () -> new ClientConfig())
            .serverConfig(ServerConfig.class, () -> new ServerConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
        ModRegistry.touch();
        registerMessages();
    }

    private static void registerMessages() {
        NETWORK.register(S2CGlidingMessage.class, S2CGlidingMessage::new, MessageDirection.TO_CLIENT);
        NETWORK.register(S2CSyncGliderDataMessage.class, S2CSyncGliderDataMessage::new, MessageDirection.TO_CLIENT);
        NETWORK.register(S2CUpdateClientTargetMessage.class, S2CUpdateClientTargetMessage::new, MessageDirection.TO_CLIENT);
    }
}
