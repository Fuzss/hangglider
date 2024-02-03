package fuzs.hangglider.world.item;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ServerConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public enum GliderType {
    BASIC(() -> HangGlider.CONFIG.get(ServerConfig.class).hangGlider,
            Items.LEATHER
    ), REINFORCED(() -> HangGlider.CONFIG.get(ServerConfig.class).reinforcedHangGlider, Items.PHANTOM_MEMBRANE);

    final Supplier<ServerConfig.GliderConfig> materialSettings;
    final Item repairMaterial;

    GliderType(Supplier<ServerConfig.GliderConfig> materialSettings, Item repairMaterial) {
        this.materialSettings = materialSettings;
        this.repairMaterial = repairMaterial;
    }
}
