package fuzs.openglider.data;

import fuzs.openglider.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(this.modLoc("deployed_hang_glider"));
        this.basicItem(this.modLoc("broken_hang_glider"));
        this.basicItem(this.modLoc("broken_reinforced_hang_glider"));
        this.basicItem(ModRegistry.GLIDER_WING_ITEM.get());
        this.basicItem(ModRegistry.GLIDER_FRAMEWORK_ITEM.get());
        this.basicItem(ModRegistry.HANG_GLIDER_ITEM.get())
                .override().predicate(this.modLoc("deployed"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/deployed_hang_glider"), this.existingFileHelper)).end()
                .override().predicate(this.modLoc("broken"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/broken_hang_glider"), this.existingFileHelper)).end();
        this.basicItem(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get())
                .override().predicate(this.modLoc("deployed"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/deployed_hang_glider"), this.existingFileHelper)).end()
                .override().predicate(this.modLoc("broken"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/broken_reinforced_hang_glider"), this.existingFileHelper)).end();
    }
}
