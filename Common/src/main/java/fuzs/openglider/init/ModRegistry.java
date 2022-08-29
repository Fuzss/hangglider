package fuzs.openglider.init;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.capability.GlidingPlayerCapability;
import fuzs.openglider.capability.GlidingPlayerCapabilityImpl;
import fuzs.openglider.config.ServerConfig;
import fuzs.openglider.world.item.HangGliderItem;
import fuzs.puzzleslib.capability.CapabilityController;
import fuzs.puzzleslib.capability.data.CapabilityKey;
import fuzs.puzzleslib.capability.data.PlayerRespawnStrategy;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

public class ModRegistry {
    public static final CreativeModeTab OPEN_GLIDER_TAB = ClientCoreServices.FACTORIES.creativeTab(OpenGlider.MOD_ID, () -> new ItemStack(Items.ELYTRA));

    private static final RegistryManager REGISTRY = CoreServices.FACTORIES.registration(OpenGlider.MOD_ID);
    public static final RegistryReference<Item> GLIDER_WING_ITEM  = REGISTRY.registerItem("glider_wing", () -> new Item(new Item.Properties().tab(OPEN_GLIDER_TAB)));
    public static final RegistryReference<Item> GLIDER_FRAMEWORK_ITEM  = REGISTRY.registerItem("glider_framework", () -> new Item(new Item.Properties().tab(OPEN_GLIDER_TAB)));
    public static final RegistryReference<Item> HANG_GLIDER_ITEM  = REGISTRY.registerItem("hang_glider", () -> new HangGliderItem(() -> OpenGlider.CONFIG.get(ServerConfig.class).hangGlider, new Item.Properties().durability(818).tab(OPEN_GLIDER_TAB)));
    public static final RegistryReference<Item> REINFORCED_HANG_GLIDER_ITEM  = REGISTRY.registerItem("reinforced_hang_glider", () -> new HangGliderItem(() -> OpenGlider.CONFIG.get(ServerConfig.class).reinforcedHangGlider, new Item.Properties().durability(2202).tab(OPEN_GLIDER_TAB).rarity(Rarity.UNCOMMON)));

    private static final CapabilityController CAPABILITIES = CoreServices.FACTORIES.capabilities(OpenGlider.MOD_ID);
    public static final CapabilityKey<GlidingPlayerCapability> GLIDING_PLAYER_CAPABILITY = CAPABILITIES.registerPlayerCapability("gliding_player", GlidingPlayerCapability.class, entity -> new GlidingPlayerCapabilityImpl((Player) entity), PlayerRespawnStrategy.LOSSLESS);

    public static void touch() {

    }
}
