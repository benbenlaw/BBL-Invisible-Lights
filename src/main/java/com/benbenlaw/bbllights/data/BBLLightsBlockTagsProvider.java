package com.benbenlaw.bbllights.data;

import com.benbenlaw.bbllights.BBLLights;
import com.benbenlaw.bbllights.block.BBLLightsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BBLLightsBlockTagsProvider extends BlockTagsProvider {

    public BBLLightsBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, BBLLights.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // Same two tags minecraft:light sits in, so the lights can be built through and trees grow straight past them
        tag(BlockTags.REPLACEABLE).add(BBLLightsBlocks.LIGHT.get());
        tag(BlockTags.REPLACEABLE_BY_TREES).add(BBLLightsBlocks.LIGHT.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BBLLightsBlocks.INVISIBLE_LIGHT_SPREADER.get());
    }
}
