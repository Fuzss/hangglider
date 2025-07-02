package fuzs.hangglider.data.client;

import fuzs.hangglider.client.renderer.item.properties.conditional.GliderDeployed;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.conditional.Broken;
import net.minecraft.world.item.Item;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModRegistry.GLIDER_WING_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModRegistry.GLIDER_FRAMEWORK_ITEM.value(), ModelTemplates.FLAT_ITEM);
        ItemModel.Unbaked deployedModel = ItemModelUtils.plainModel(itemModelGenerators.createFlatItemModel(ModRegistry.HANG_GLIDER_ITEM.value(),
                "_deployed",
                ModelTemplates.FLAT_ITEM));
        this.generateGlider(ModRegistry.HANG_GLIDER_ITEM.value(), deployedModel, itemModelGenerators);
        this.generateGlider(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value(), deployedModel, itemModelGenerators);
    }

    public final void generateGlider(Item item, ItemModel.Unbaked deployedModel, ItemModelGenerators itemModelGenerators) {
        ItemModel.Unbaked itemModel = ItemModelUtils.plainModel(itemModelGenerators.createFlatItemModel(item,
                ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked brokenModel = ItemModelUtils.plainModel(itemModelGenerators.createFlatItemModel(item,
                "_broken",
                ModelTemplates.FLAT_ITEM));
        itemModelGenerators.generateBooleanDispatch(item,
                new Broken(),
                brokenModel,
                ItemModelUtils.conditional(new GliderDeployed(), deployedModel, itemModel));
    }
}
