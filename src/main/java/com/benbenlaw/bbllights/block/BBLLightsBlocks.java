package com.benbenlaw.bbllights.block;

import com.benbenlaw.bbllights.BBLLights;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BBLLightsBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BBLLights.MOD_ID);

    public static final DeferredBlock<Block> INVISIBLE_LIGHT_SPREADER = BLOCKS.registerBlock("invisible_light_spreader",
            InvisibleLightSpreaderBlock::new, properties -> properties.strength(2.0f).sound(SoundType.METAL));

    // The mods own light block, used by the light items and the spreader instead of minecraft:light
    public static final DeferredBlock<Block> LIGHT = BLOCKS.registerBlock("light",
            LightBlock::new, properties -> properties
                    .replaceable()
                    .strength(-1.0F, 3600000.8F)
                    .noLootTable()
                    .noOcclusion()
                    .replaceable()
                    .air()
                    .lightLevel(LightBlock.LIGHT_EMISSION));

}
