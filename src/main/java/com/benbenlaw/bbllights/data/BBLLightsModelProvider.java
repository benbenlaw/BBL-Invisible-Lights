package com.benbenlaw.bbllights.data;

import com.benbenlaw.bbllights.BBLLights;
import com.benbenlaw.bbllights.item.BBLLightsItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class BBLLightsModelProvider extends ModelProvider {

    public BBLLightsModelProvider(PackOutput output) {
        super(output, BBLLights.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        itemModels.generateFlatItem(BBLLightsItems.INVISIBLE_LIGHT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BBLLightsItems.IRON_INVISIBLE_LIGHT_PLACER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BBLLightsItems.DIAMOND_INVISIBLE_LIGHT_PLACER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BBLLightsItems.NETHERITE_INVISIBLE_LIGHT_PLACER.get(), ModelTemplates.FLAT_ITEM);

    }
}
