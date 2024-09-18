package fuzs.hangglider.data.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.HangGliderClient;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.client.data.v2.ItemModelProperties;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addItemModels(ItemModelGenerators builder) {
        ResourceLocation deployedHangGlider = generateFlatItem(HangGlider.id("deployed_hang_glider"),
                ModelTemplates.FLAT_ITEM,
                builder.output
        );
        ResourceLocation brokenHangGlider = generateFlatItem(HangGlider.id("broken_hang_glider"),
                ModelTemplates.FLAT_ITEM,
                builder.output
        );
        ResourceLocation brokenReinforcedHangGlider = generateFlatItem(HangGlider.id("broken_reinforced_hang_glider"),
                ModelTemplates.FLAT_ITEM,
                builder.output
        );
        builder.generateFlatItem(ModRegistry.GLIDER_WING_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.GLIDER_FRAMEWORK_ITEM.value(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModRegistry.HANG_GLIDER_ITEM.value(),
                ModelTemplates.FLAT_ITEM,
                builder.output,
                createOverridesFactory(deployedHangGlider, brokenHangGlider)
        );
        generateFlatItem(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value(),
                ModelTemplates.FLAT_ITEM,
                builder.output,
                createOverridesFactory(deployedHangGlider, brokenReinforcedHangGlider)
        );
    }

    public static ModelTemplate.JsonFactory createOverridesFactory(ResourceLocation deployedLocation, ResourceLocation brokenLocation) {
        return ItemModelProperties.overridesFactory(ModelTemplates.FLAT_ITEM,
                ItemModelProperties.singleOverride(deployedLocation, HangGliderClient.ITEM_PROPERTY_DEPLOYED, 1.0F),
                ItemModelProperties.singleOverride(brokenLocation, HangGliderClient.ITEM_PROPERTY_BROKEN, 1.0F)
        );
    }
}
