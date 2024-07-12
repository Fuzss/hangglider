package fuzs.hangglider.data;

import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.recipes.CopyTagShapedRecipeBuilder;
import fuzs.puzzleslib.api.data.v2.recipes.CopyTagShapelessRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.GLIDER_WING_ITEM.value())
                .define('S', Items.STICK)
                .define('#', Ingredient.of(Items.LEATHER, Items.RABBIT_HIDE))
                .pattern("  S")
                .pattern(" S#")
                .pattern("S##")
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.GLIDER_FRAMEWORK_ITEM.value())
                .define('#', Items.IRON_INGOT)
                .pattern(" # ")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.HANG_GLIDER_ITEM.value())
                .define('#', ModRegistry.GLIDER_WING_ITEM.value())
                .define('@', ModRegistry.GLIDER_FRAMEWORK_ITEM.value())
                .pattern("#@#")
                .unlockedBy(getHasName(ModRegistry.GLIDER_FRAMEWORK_ITEM.value()),
                        has(ModRegistry.GLIDER_FRAMEWORK_ITEM.value())
                )
                .save(recipeOutput);
        CopyTagShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value())
                .define('#', Items.PHANTOM_MEMBRANE)
                .define('@', ModRegistry.HANG_GLIDER_ITEM.value())
                .pattern("#@#")
                .copyFrom(ModRegistry.HANG_GLIDER_ITEM.value())
                .unlockedBy(getHasName(ModRegistry.HANG_GLIDER_ITEM.value()), has(ModRegistry.HANG_GLIDER_ITEM.value()))
                .save(recipeOutput);
    }
}
