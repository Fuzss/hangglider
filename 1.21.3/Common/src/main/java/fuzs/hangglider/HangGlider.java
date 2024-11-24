package fuzs.hangglider;

import fuzs.hangglider.config.ClientConfig;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.handler.GliderActivationHandler;
import fuzs.hangglider.handler.PlayerGlidingHandler;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerTickEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HangGlider implements ModConstructor {
    public static final String MOD_ID = "hangglider";
    public static final String MOD_NAME = "Hang Glider";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID)
            .client(ClientConfig.class)
            .server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        PlayerTickEvents.END.register(PlayerGlidingHandler::onPlayerTick$End);
        PlayerInteractEvents.USE_ITEM.register(GliderActivationHandler::onUseItem);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID,
                ModRegistry.HANG_GLIDER_ITEM
        ).displayItems((itemDisplayParameters, output) -> {
            output.accept(ModRegistry.HANG_GLIDER_ITEM.value());
            output.accept(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value());
            output.accept(ModRegistry.GLIDER_WING_ITEM.value());
            output.accept(ModRegistry.GLIDER_FRAMEWORK_ITEM.value());
        }));
    }

    @Override
    public ContentRegistrationFlags[] getContentRegistrationFlags() {
        return new ContentRegistrationFlags[]{ContentRegistrationFlags.CRAFTING_TRANSMUTE};
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
