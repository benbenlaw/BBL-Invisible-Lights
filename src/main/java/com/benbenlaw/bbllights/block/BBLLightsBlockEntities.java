package com.benbenlaw.bbllights.block;

import com.benbenlaw.bbllights.BBLLights;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BBLLightsBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BBLLights.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InvisibleLightSpreaderBlockEntity>> INVISIBLE_LIGHT_SPREADER =
            BLOCK_ENTITIES.register("invisible_light_spreader", () ->
                    new BlockEntityType<>(InvisibleLightSpreaderBlockEntity::new, BBLLightsBlocks.INVISIBLE_LIGHT_SPREADER.get()));

}
