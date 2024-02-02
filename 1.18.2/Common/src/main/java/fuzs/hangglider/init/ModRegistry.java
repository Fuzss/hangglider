package fuzs.hangglider.init;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.capability.GlidingCapability;
import fuzs.hangglider.capability.GlidingCapabilityImpl;
import fuzs.hangglider.world.item.GliderItem;
import fuzs.puzzleslib.api.capability.v2.CapabilityController;
import fuzs.puzzleslib.api.capability.v2.data.PlayerCapabilityKey;
import fuzs.puzzleslib.api.capability.v2.data.PlayerRespawnCopyStrategy;
import fuzs.puzzleslib.api.capability.v2.data.SyncStrategy;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(HangGlider.MOD_ID);
    public static final RegistryReference<Item> GLIDER_WING_ITEM  = REGISTRY.registerItem("glider_wing", () -> new Item(new Item.Properties()));
    public static final RegistryReference<Item> GLIDER_FRAMEWORK_ITEM  = REGISTRY.registerItem("glider_framework", () -> new Item(new Item.Properties()));
    public static final RegistryReference<Item> HANG_GLIDER_ITEM  = REGISTRY.registerItem("hang_glider", () -> new GliderItem(new Item.Properties().durability(818), GliderItem.Type.BASIC));
    public static final RegistryReference<Item> REINFORCED_HANG_GLIDER_ITEM  = REGISTRY.registerItem("reinforced_hang_glider", () -> new GliderItem(new Item.Properties().durability(2202).rarity(Rarity.UNCOMMON), GliderItem.Type.REINFORCED));

    static final CapabilityController CAPABILITIES = CapabilityController.from(HangGlider.MOD_ID);
    public static final PlayerCapabilityKey<GlidingCapability> GLIDING_CAPABILITY = CAPABILITIES.registerPlayerCapability("gliding", GlidingCapability.class, entity -> new GlidingCapabilityImpl(), PlayerRespawnCopyStrategy.RETURNING_FROM_END, SyncStrategy.SELF_AND_TRACKING);

    public static void touch() {

    }
}
