package com.benbenlaw.bbllights.block;

import com.benbenlaw.bbllights.config.BBLLightsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

public class InvisibleLightSpreaderBlock extends Block implements EntityBlock {
    public InvisibleLightSpreaderBlock(Properties properties) {
        super(properties);
    }

    /**
     * Lights up the dark spots around itself with the mods light block, no items needed
     * The range and how often it looks come from BBLLightsConfig
     * Scanning is done with a scheduled tick, the block entity only keeps track of the lights this spreader placed
     * Light blocks only ever go into empty air inside the world height that sits against the face of a block,
     * so nothing a multiblock owns is replaced and no light is left floating in the middle of a room
     * Breaking the spreader takes the lights it placed back out again, so does powering it with redstone
     * */

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InvisibleLightSpreaderBlockEntity(pos, state);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, BBLLightsConfig.SCAN_INTERVAL_TICKS.get());
    }

    // Redstone takes the lights straight back out, placing starts again on the next scan once the signal is gone
    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        if (level.hasNeighborSignal(pos) && level.getBlockEntity(pos) instanceof InvisibleLightSpreaderBlockEntity spreader) {
            spreader.removeLights(level);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (!(level.getBlockEntity(pos) instanceof InvisibleLightSpreaderBlockEntity spreader)) return;

        if (level.hasNeighborSignal(pos)) {
            level.scheduleTick(pos, this, BBLLightsConfig.SCAN_INTERVAL_TICKS.get());
            return;
        }

        spreader.forgetMissingLights(level);

        int range = BBLLightsConfig.RANGE.get();
        int minimumLightLevel = BBLLightsConfig.MINIMUM_LIGHT_LEVEL.get();
        int lightLevel = 15;

        // Scan from closest first, then move further away
        for (BlockPos darkPos : BlockPos.withinManhattan(pos, range, range, range)) {

            // Only empty air inside the world height is ever used, anything a player or a multiblock put there is left alone
            // Without the height check a dark spot above the world would be picked every scan and nothing would get placed
            if (level.isOutsideBuildHeight(darkPos)) continue;
            // Unloaded chunks are skipped, reading them would load them from disk on every scan
            if (!level.isLoaded(darkPos)) continue;
            if (!level.isEmptyBlock(darkPos)) continue;
            if (level.getBrightness(LightLayer.BLOCK, darkPos) >= minimumLightLevel) continue;
            if (!isAgainstBlockFace(level, darkPos)) continue;

            spreader.placeLight(level, darkPos, BBLLightsBlocks.LIGHT.get().defaultBlockState().setValue(LightBlock.LEVEL, lightLevel));
            break;
        }

        level.scheduleTick(pos, this, BBLLightsConfig.SCAN_INTERVAL_TICKS.get());
    }

    // Lights go against the face of a block like a torch would instead of floating in the middle of the air
    private static boolean isAgainstBlockFace(@NotNull Level level, @NotNull BlockPos pos) {

        for (Direction direction : Direction.values()) {
            BlockPos facePos = pos.relative(direction);

            if (level.getBlockState(facePos).isFaceSturdy(level, facePos, direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }
}
