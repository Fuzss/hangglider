package fuzs.openglider.data;

import fuzs.openglider.init.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        ShapedRecipeBuilder.shaped(ModRegistry.GLIDER_WING_ITEM.get())
                .define('S', Items.STICK)
                .define('#', Items.LEATHER)
                .pattern(" S#")
                .pattern("S##")
                .pattern("###")
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModRegistry.GLIDER_FRAMEWORK_ITEM.get())
                .define('#', Items.IRON_INGOT)
                .pattern(" # ")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModRegistry.HANG_GLIDER_ITEM.get())
                .define('#', ModRegistry.GLIDER_WING_ITEM.get())
                .define('@', ModRegistry.GLIDER_FRAMEWORK_ITEM.get())
                .pattern("#@#")
                .unlockedBy("has_glider_framework", has(ModRegistry.GLIDER_FRAMEWORK_ITEM.get()))
                .save(p_176532_);
        UpgradeRecipeBuilder.smithing(Ingredient.of(ModRegistry.HANG_GLIDER_ITEM.get()), Ingredient.of(Items.ELYTRA), ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get())
                .unlocks("has_elytra", has(Items.ELYTRA))
                .save(p_176532_, getItemName(ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get()) + "_smithing");
    }
}
