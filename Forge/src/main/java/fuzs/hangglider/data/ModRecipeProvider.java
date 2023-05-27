package fuzs.hangglider.data;

import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeConsumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.GLIDER_WING_ITEM.get())
                .define('S', Items.STICK)
                .define('#', Items.LEATHER)
                .define('P', Items.PHANTOM_MEMBRANE)
                .pattern(" SP")
                .pattern("SP#")
                .pattern("P##")
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.GLIDER_FRAMEWORK_ITEM.get())
                .define('#', Items.IRON_INGOT)
                .pattern(" # ")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.HANG_GLIDER_ITEM.get())
                .define('#', ModRegistry.GLIDER_WING_ITEM.get())
                .define('@', ModRegistry.GLIDER_FRAMEWORK_ITEM.get())
                .pattern("#@#")
                .unlockedBy(getHasName(ModRegistry.GLIDER_FRAMEWORK_ITEM.get()), has(ModRegistry.GLIDER_FRAMEWORK_ITEM.get()))
                .save(recipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get())
                .requires(ModRegistry.HANG_GLIDER_ITEM.get())
                .requires(Items.ELYTRA)
                .unlockedBy(getHasName(Items.ELYTRA), has(Items.ELYTRA))
                .save(recipeConsumer);
    }
}
