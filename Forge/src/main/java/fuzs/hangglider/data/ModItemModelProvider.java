package fuzs.hangglider.data;

import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends AbstractModelProvider {

    public ModItemModelProvider(PackOutput packOutput, String modId, ExistingFileHelper fileHelper) {
        super(packOutput, modId, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.basicItem(this.modLoc("deployed_hang_glider"));
        this.basicItem(this.modLoc("broken_hang_glider"));
        this.basicItem(this.modLoc("broken_reinforced_hang_glider"));
        this.basicItem(ModRegistry.GLIDER_WING_ITEM.get());
        this.basicItem(ModRegistry.GLIDER_FRAMEWORK_ITEM.get());
        this.basicItem(ModRegistry.HANG_GLIDER_ITEM.get())
                .override().predicate(this.modLoc("deployed"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/deployed_hang_glider"), this.models().existingFileHelper)).end()
                .override().predicate(this.modLoc("broken"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/broken_hang_glider"), this.models().existingFileHelper)).end();
        this.basicItem(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get())
                .override().predicate(this.modLoc("deployed"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/deployed_hang_glider"), this.models().existingFileHelper)).end()
                .override().predicate(this.modLoc("broken"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/broken_reinforced_hang_glider"), this.models().existingFileHelper)).end();
    }
}
