package fuzs.hangglider;

import fuzs.hangglider.config.ClientConfig;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.handler.PlayerGlidingHandler;
import fuzs.hangglider.init.ModRegistry;
import fuzs.hangglider.proxy.ClientProxy;
import fuzs.hangglider.proxy.Proxy;
import fuzs.hangglider.proxy.ServerProxy;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.DistTypeExecutor;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerTickEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HangGlider implements ModConstructor {
    public static final String MOD_ID = "hangglider";
    public static final String MOD_NAME = "Hang Glider";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final Proxy PROXY = DistTypeExecutor.getForDistType(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        PlayerTickEvents.END.register(PlayerGlidingHandler::onPlayerTick$End);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID, () -> new ItemStack(ModRegistry.HANG_GLIDER_ITEM.get())).displayItems((output) -> {
            output.accept(ModRegistry.HANG_GLIDER_ITEM.get());
            output.accept(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
            output.accept(ModRegistry.GLIDER_WING_ITEM.get());
            output.accept(ModRegistry.GLIDER_FRAMEWORK_ITEM.get());
        }));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
