package fuzs.hangglider.data;

import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;

public class ModItemTagProvider extends AbstractTagProvider.Items {

    public ModItemTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.HANG_GLIDERS_ITEM_TAG)
                .add(ModRegistry.HANG_GLIDER_ITEM.value())
                .addTag(ModRegistry.REINFORCED_HANG_GLIDERS_ITEM_TAG);
        this.tag(ModRegistry.REINFORCED_HANG_GLIDERS_ITEM_TAG).add(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value());
    }
}
