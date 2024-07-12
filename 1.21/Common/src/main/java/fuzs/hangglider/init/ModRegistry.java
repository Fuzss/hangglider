package fuzs.hangglider.init;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.capability.GlidingCapability;
import fuzs.hangglider.world.item.RepairableItem;
import fuzs.puzzleslib.api.capability.v3.CapabilityController;
import fuzs.puzzleslib.api.capability.v3.data.EntityCapabilityKey;
import fuzs.puzzleslib.api.capability.v3.data.SyncStrategy;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.BoundTagFactory;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.from(HangGlider.MOD_ID);
    public static final Holder.Reference<Item> GLIDER_WING_ITEM = REGISTRY.registerItem("glider_wing",
            () -> new Item(new Item.Properties())
    );
    public static final Holder.Reference<Item> GLIDER_FRAMEWORK_ITEM = REGISTRY.registerItem("glider_framework",
            () -> new Item(new Item.Properties())
    );
    public static final Holder.Reference<Item> HANG_GLIDER_ITEM = REGISTRY.registerItem("hang_glider",
            () -> new RepairableItem(() -> Items.LEATHER, new Item.Properties().durability(818))
    );
    public static final Holder.Reference<Item> REINFORCED_HANG_GLIDER_ITEM = REGISTRY.registerItem(
            "reinforced_hang_glider",
            () -> new RepairableItem(() -> Items.PHANTOM_MEMBRANE,
                    new Item.Properties().durability(2202).rarity(Rarity.UNCOMMON)
            )
    );

    static final BoundTagFactory TAGS = BoundTagFactory.make(HangGlider.MOD_ID);
    public static final TagKey<Item> HANG_GLIDERS_ITEM_TAG = TAGS.registerItemTag("hang_gliders");
    public static final TagKey<Item> REINFORCED_HANG_GLIDERS_ITEM_TAG = TAGS.registerItemTag("reinforced_hang_gliders");

    static final CapabilityController CAPABILITIES = CapabilityController.from(HangGlider.MOD_ID);
    public static final EntityCapabilityKey<Player, GlidingCapability> GLIDING_CAPABILITY = CAPABILITIES.registerEntityCapability(
            "gliding",
            GlidingCapability.class,
            GlidingCapability::new,
            Player.class
    ).setSyncStrategy(SyncStrategy.TRACKING);

    public static void touch() {

    }
}
