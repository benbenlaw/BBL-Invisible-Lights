package com.benbenlaw.bbllights.data;

import com.benbenlaw.bbllights.BBLLights;
import com.benbenlaw.bbllights.block.BBLLightsBlocks;
import com.benbenlaw.bbllights.item.BBLLightsItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class BBLLightsModelProvider extends ModelProvider {

    public BBLLightsModelProvider(PackOutput output) {
        super(output, BBLLights.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        blockModels.createTrivialCube(BBLLightsBlocks.INVISIBLE_LIGHT_SPREADER.get());

        // The lights are invisible like the vanilla light block, only a particle model is needed
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BBLLightsBlocks.LIGHT.get(),
                BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(BBLLightsBlocks.LIGHT.get(),
                        TextureMapping.particle(TextureMapping.getItemTexture(Items.LIGHT, "_15")), blockModels.modelOutput))));

        itemModels.generateFlatItem(BBLLightsItems.INVISIBLE_LIGHT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BBLLightsItems.IRON_INVISIBLE_LIGHT_PLACER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(BBLLightsItems.DIAMOND_INVISIBLE_LIGHT_PLACER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(BBLLightsItems.NETHERITE_INVISIBLE_LIGHT_PLACER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

    }
}