package fuzs.hangglider.data.client;

import com.google.gson.JsonElement;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.HangGliderClient;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
                ItemOverride.of(deployedHangGlider, HangGliderClient.ITEM_PROPERTY_DEPLOYED, 1.0F),
                ItemOverride.of(brokenHangGlider, HangGliderClient.ITEM_PROPERTY_BROKEN, 1.0F)
        );
        generateFlatItem(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value(),
                ModelTemplates.FLAT_ITEM,
                builder.output,
                ItemOverride.of(deployedHangGlider, HangGliderClient.ITEM_PROPERTY_DEPLOYED, 1.0F),
                ItemOverride.of(brokenReinforcedHangGlider, HangGliderClient.ITEM_PROPERTY_BROKEN, 1.0F)
        );
    }

    public static ResourceLocation generateFlatItem(ResourceLocation resourceLocation, ModelTemplate modelTemplate, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput) {
        return modelTemplate.create(decorateItemModelLocation(resourceLocation),
                TextureMapping.layer0(decorateItemModelLocation(resourceLocation)),
                modelOutput
        );
    }

    public static ResourceLocation generateFlatItem(Item item, ModelTemplate modelTemplate, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput, ItemOverride... itemOverrides) {
        return generateFlatItem(item,
                modelTemplate,
                modelOutput,
                overrides(modelTemplate,
                        Stream.of(itemOverrides)
                                .map(override -> (ItemOverride.Factory) (ResourceLocation resourceLocation) -> override)
                                .toArray(ItemOverride.Factory[]::new)
                )
        );
    }

    public static ResourceLocation generateFlatItem(Item item, ModelTemplate modelTemplate, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput, ModelTemplate.JsonFactory factory) {
        return modelTemplate.create(ModelLocationUtils.getModelLocation(item),
                TextureMapping.layer0(item),
                modelOutput,
                factory
        );
    }
}
