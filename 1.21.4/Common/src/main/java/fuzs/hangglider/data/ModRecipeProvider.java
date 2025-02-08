package fuzs.hangglider.data;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.recipes.TransmuteShapedRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.TOOLS, ModRegistry.GLIDER_WING_ITEM.value())
                .define('S', Items.STICK)
                .define('#', Ingredient.of(Items.LEATHER, Items.RABBIT_HIDE))
                .pattern("  S")
                .pattern(" S#")
                .pattern("S##")
                .unlockedBy(getHasName(Items.LEATHER), this.has(Items.LEATHER))
                .unlockedBy(getHasName(Items.RABBIT_HIDE), this.has(Items.RABBIT_HIDE))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.TOOLS, ModRegistry.GLIDER_FRAMEWORK_ITEM.value())
                .define('#', Items.IRON_INGOT)
                .pattern(" # ")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(Items.IRON_INGOT), this.has(Items.IRON_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.TOOLS, ModRegistry.HANG_GLIDER_ITEM.value())
                .define('#', ModRegistry.GLIDER_WING_ITEM.value())
                .define('@', ModRegistry.GLIDER_FRAMEWORK_ITEM.value())
                .pattern("#@#")
                .unlockedBy(getHasName(ModRegistry.GLIDER_WING_ITEM.value()),
                        this.has(ModRegistry.GLIDER_WING_ITEM.value()))
                .unlockedBy(getHasName(ModRegistry.GLIDER_FRAMEWORK_ITEM.value()),
                        this.has(ModRegistry.GLIDER_FRAMEWORK_ITEM.value()))
                .save(recipeOutput);
        RecipeSerializer<?> recipeSerializer = TransmuteShapedRecipeBuilder.getRecipeSerializer(HangGlider.MOD_ID);
        TransmuteShapedRecipeBuilder.shaped(recipeSerializer,
                        this.items(),
                        RecipeCategory.TOOLS,
                        ModRegistry.REINFORCED_HANG_GLIDER_ITEM.value())
                .define('#', Items.PHANTOM_MEMBRANE)
                .define('@', ModRegistry.HANG_GLIDER_ITEM.value())
                .pattern("#@#")
                .input(ModRegistry.HANG_GLIDER_ITEM.value())
                .unlockedBy(getHasName(ModRegistry.HANG_GLIDER_ITEM.value()),
                        this.has(ModRegistry.HANG_GLIDER_ITEM.value()))
                .save(recipeOutput);
    }
}
