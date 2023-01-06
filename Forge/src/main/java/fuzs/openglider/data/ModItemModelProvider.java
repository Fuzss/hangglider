package fuzs.openglider.data;

import fuzs.openglider.init.ModRegistry;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
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
        this.getBuilder(this.modLoc("hang_glider_gliding").toString())
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .guiLight(BlockModel.GuiLight.FRONT)
                .transforms()
                .transform(ItemTransforms.TransformType.GUI)
                .rotation(15, -25, -5)
                .translation(2, 3, 0)
                .scale(0.625F, 0.625F, 0.625F)
                .end()
                .transform(ItemTransforms.TransformType.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 3, 0)
                .scale(0.25F, 0.25F, 0.25F)
                .end()
                .transform(ItemTransforms.TransformType.HEAD)
                .rotation(0, 180, 0)
                .translation(0, 0, 0)
                .scale(1, 1, 1)
                .end()
                .transform(ItemTransforms.TransformType.FIXED)
                .rotation(0, 180, 0)
                .translation(0, 0, 0)
                .scale(0.5F, 0.5F, 0.5F)
                .end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, 90, 180)
                .translation(8, -17, 9)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, -90, 25)
                .translation(-3, 17, 1)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND)
                .rotation(0, 90, 180)
                .translation(8, -17, -7)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 90, -25)
                .translation(13, 17, 1)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .end();
        this.getBuilder(this.modLoc("hang_glider_in_hand").toString())
                .override().predicate(this.modLoc("deployed"), 1.0F).model(new ModelFile.ExistingModelFile(this.modLoc("item/hang_glider_gliding"), this.existingFileHelper)).end();
    }
}
