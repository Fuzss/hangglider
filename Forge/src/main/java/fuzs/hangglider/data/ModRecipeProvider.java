package fuzs.hangglider.data;

import fuzs.hangglider.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
        ShapedRecipeBuilder.shaped(ModRegistry.GLIDER_WING_ITEM.get())
                .define('S', Items.STICK)
                .define('#', Items.LEATHER)
                .define('P', Items.PHANTOM_MEMBRANE)
                .pattern(" SP")
                .pattern("SP#")
                .pattern("P##")
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ModRegistry.GLIDER_FRAMEWORK_ITEM.get())
                .define('#', Items.IRON_INGOT)
                .pattern(" # ")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ModRegistry.HANG_GLIDER_ITEM.get())
                .define('#', ModRegistry.GLIDER_WING_ITEM.get())
                .define('@', ModRegistry.GLIDER_FRAMEWORK_ITEM.get())
                .pattern("#@#")
                .unlockedBy(getHasName(ModRegistry.GLIDER_FRAMEWORK_ITEM.get()), has(ModRegistry.GLIDER_FRAMEWORK_ITEM.get()))
                .save(recipeConsumer);
        UpgradeRecipeBuilder.smithing(Ingredient.of(ModRegistry.HANG_GLIDER_ITEM.get()), Ingredient.of(Items.ELYTRA), ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get())
                .unlocks(getHasName(Items.ELYTRA), has(Items.ELYTRA))
                .save(recipeConsumer, getItemName(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get()) + "_smithing");
    }
}
