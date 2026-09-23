package com.benbenlaw.bbllights.data;

import com.benbenlaw.bbllights.block.BBLLightsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BBLLightsLootTableProvider extends LootTableProvider {

    public BBLLightsLootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Set.of(), List.of(new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)), lookupProvider);
    }

    private static class BlockLoot extends BlockLootSubProvider {

        protected BlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            dropSelf(BBLLightsBlocks.INVISIBLE_LIGHT_SPREADER.get());
        }

        // Only our blocks, the light block has noLootTable() so only the spreader needs one
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BBLLightsBlocks.BLOCKS.getEntries().stream().map(block -> (Block) block.value()).toList();
        }
    }
}
