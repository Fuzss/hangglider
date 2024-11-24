package fuzs.hangglider.init;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.capability.GlidingCapability;
import fuzs.hangglider.world.item.component.HangGliderComponent;
import fuzs.puzzleslib.api.capability.v3.CapabilityController;
import fuzs.puzzleslib.api.capability.v3.data.EntityCapabilityKey;
import fuzs.puzzleslib.api.capability.v3.data.SyncStrategy;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import java.util.Optional;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(HangGlider.MOD_ID);
    public static final Holder.Reference<DataComponentType<HangGliderComponent>> HANG_GLIDER_DATA_COMPONENT_TYPE = REGISTRIES.registerDataComponentType(
            "hang_glider",
            builder -> builder.persistent(HangGliderComponent.CODEC)
                    .networkSynchronized(HangGliderComponent.STREAM_CODEC)
                    .cacheEncoding());
    public static final Holder.Reference<Item> GLIDER_WING_ITEM = REGISTRIES.registerItem("glider_wing");
    public static final Holder.Reference<Item> GLIDER_FRAMEWORK_ITEM = REGISTRIES.registerItem("glider_framework");
    public static final Holder.Reference<Item> HANG_GLIDER_ITEM = REGISTRIES.registerItem("hang_glider",
            () -> new Item.Properties().durability(818)
                    .repairable(Items.LEATHER)
                    .component(HANG_GLIDER_DATA_COMPONENT_TYPE.value(),
                            new HangGliderComponent(false, Optional.empty())));
    public static final Holder.Reference<Item> REINFORCED_HANG_GLIDER_ITEM = REGISTRIES.registerItem(
            "reinforced_hang_glider",
            () -> new Item.Properties().durability(2202)
                    .rarity(Rarity.UNCOMMON)
                    .repairable(Items.PHANTOM_MEMBRANE)
                    .component(HANG_GLIDER_DATA_COMPONENT_TYPE.value(),
                            new HangGliderComponent(true, Optional.of(HangGlider.id("reinforced_hang_glider")))));

    static final CapabilityController CAPABILITIES = CapabilityController.from(HangGlider.MOD_ID);
    public static final EntityCapabilityKey<Player, GlidingCapability> GLIDING_CAPABILITY = CAPABILITIES.registerEntityCapability(
            "gliding",
            GlidingCapability.class,
            GlidingCapability::new,
            Player.class).setSyncStrategy(SyncStrategy.TRACKING);

    public static void bootstrap() {
        // NO-OP
    }
}
