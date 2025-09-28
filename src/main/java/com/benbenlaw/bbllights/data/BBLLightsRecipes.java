package com.benbenlaw.bbllights.data;

import com.benbenlaw.bbllights.item.BBLLightsItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BBLLightsRecipes extends RecipeProvider {

    public BBLLightsRecipes(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
            super(packOutput, provider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new BBLLightsRecipes(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return "BBLLights Recipes";
        }
    }

    @Override
    protected void buildRecipes() {

        // Invisible Light
        shapeless(RecipeCategory.MISC, BBLLightsItems.INVISIBLE_LIGHT.get(), 2)
                .requires(Items.TORCH)
                .group("invisible_light")
                .unlockedBy("has_torch", has(Items.TORCH))
                .save(output);

        // Iron Invisible Light Placer
        shaped(RecipeCategory.MISC, BBLLightsItems.IRON_INVISIBLE_LIGHT_PLACER.get())
                .pattern(" C ")
                .pattern("ILI")
                .pattern(" I ")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', Tags.Items.STORAGE_BLOCKS_COAL)
                .define('L', BBLLightsItems.INVISIBLE_LIGHT.get())
                .group("invisible_light")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        // Diamond Invisible Light Placer
        shaped(RecipeCategory.MISC, BBLLightsItems.DIAMOND_INVISIBLE_LIGHT_PLACER.get())
                .pattern(" C ")
                .pattern("DID")
                .pattern(" D ")
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('C', Tags.Items.STORAGE_BLOCKS_COAL)
                .define('I', BBLLightsItems.IRON_INVISIBLE_LIGHT_PLACER.get())
                .group("invisible_light")
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output);

        // Netherite Invisible Light Placer
        netheriteSmithing(BBLLightsItems.DIAMOND_INVISIBLE_LIGHT_PLACER.get(), RecipeCategory.MISC, BBLLightsItems.NETHERITE_INVISIBLE_LIGHT_PLACER.get()  );
    }
}