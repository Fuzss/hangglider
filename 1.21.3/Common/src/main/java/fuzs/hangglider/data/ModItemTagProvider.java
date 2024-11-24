package fuzs.hangglider.data;

import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(ModRegistry.HANG_GLIDERS_ITEM_TAG)
                .add(ModRegistry.HANG_GLIDER_ITEM.value())
                .addTag(ModRegistry.REINFORCED_HANG_GLIDERS_ITEM_TAG);
        this.add(ModRegistry.REINFORCED_HANG_GLIDERS_ITEM_TAG).add(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value());
        this.add(ItemTags.DURABILITY_ENCHANTABLE).addTag(ModRegistry.HANG_GLIDERS_ITEM_TAG);
    }
}
