package fuzs.hangglider.init;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.capability.GlidingCapability;
import fuzs.hangglider.capability.GlidingCapabilityImpl;
import fuzs.hangglider.world.item.GliderItem;
import fuzs.puzzleslib.capability.CapabilityController;
import fuzs.puzzleslib.capability.data.PlayerCapabilityKey;
import fuzs.puzzleslib.capability.data.PlayerRespawnStrategy;
import fuzs.puzzleslib.capability.data.SyncStrategy;
import fuzs.puzzleslib.core.CommonAbstractions;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.init.RegistryManager;
import fuzs.puzzleslib.init.RegistryReference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class ModRegistry {
    public static final CreativeModeTab CREATIVE_MODE_TAB = CommonAbstractions.INSTANCE.creativeModeTab(HangGlider.MOD_ID, () -> new ItemStack(ModRegistry.HANG_GLIDER_ITEM.get()));
    private static final RegistryManager REGISTRY = CommonFactories.INSTANCE.registration(HangGlider.MOD_ID);
    public static final RegistryReference<Item> GLIDER_WING_ITEM  = REGISTRY.registerItem("glider_wing", () -> new Item(new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistryReference<Item> GLIDER_FRAMEWORK_ITEM  = REGISTRY.registerItem("glider_framework", () -> new Item(new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistryReference<Item> HANG_GLIDER_ITEM  = REGISTRY.registerItem("hang_glider", () -> new GliderItem(new Item.Properties().durability(818).tab(CREATIVE_MODE_TAB), GliderItem.Type.BASIC));
    public static final RegistryReference<Item> REINFORCED_HANG_GLIDER_ITEM  = REGISTRY.registerItem("reinforced_hang_glider", () -> new GliderItem(new Item.Properties().durability(2202).tab(CREATIVE_MODE_TAB).rarity(Rarity.UNCOMMON), GliderItem.Type.REINFORCED));

    private static final CapabilityController CAPABILITIES = CommonFactories.INSTANCE.capabilities(HangGlider.MOD_ID);
    public static final PlayerCapabilityKey<GlidingCapability> GLIDING_CAPABILITY = CAPABILITIES.registerPlayerCapability("gliding", GlidingCapability.class, entity -> new GlidingCapabilityImpl(), PlayerRespawnStrategy.LOSSLESS, SyncStrategy.SELF);

    public static void touch() {

    }
}
