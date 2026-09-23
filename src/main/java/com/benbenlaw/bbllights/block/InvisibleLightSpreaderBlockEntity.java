package com.benbenlaw.bbllights.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvisibleLightSpreaderBlockEntity extends BlockEntity {

    private final Set<BlockPos> placedLights = new HashSet<>();

    public InvisibleLightSpreaderBlockEntity(BlockPos pos, BlockState state) {
        super(BBLLightsBlockEntities.INVISIBLE_LIGHT_SPREADER.get(), pos, state);
    }

    public void placeLight(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState lightState) {
        if (level.setBlock(pos, lightState, Block.UPDATE_CLIENTS)) {
            placedLights.add(pos.immutable());
            setChanged();
        }
    }

    public void removeLights(@NotNull Level level) {
        for (BlockPos lightPos : placedLights) {
            if (level.getBlockState(lightPos).is(BBLLightsBlocks.LIGHT.get())) {
                level.setBlock(lightPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
            }
        }

        placedLights.clear();
        setChanged();
    }

    // Forget lights that a player or a tree replaced, so if another spreader lights that spot later this one won't remove it
    public void forgetMissingLights(@NotNull Level level) {
        if (placedLights.removeIf(lightPos -> level.isLoaded(lightPos) && !level.getBlockState(lightPos).is(BBLLightsBlocks.LIGHT.get()))) {
            setChanged();
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (level != null) {
            removeLights(level);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("placed_lights", BlockPos.CODEC.listOf(), List.copyOf(placedLights));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        placedLights.clear();
        input.read("placed_lights", BlockPos.CODEC.listOf()).ifPresent(placedLights::addAll);
    }
}
