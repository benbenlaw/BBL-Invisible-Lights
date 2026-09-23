package com.benbenlaw.bbllights;

import com.benbenlaw.bbllights.block.BBLLightsBlockEntities;
import com.benbenlaw.bbllights.block.BBLLightsBlocks;
import com.benbenlaw.bbllights.config.BBLLightsConfig;
import com.benbenlaw.bbllights.item.BBLLightsCreativeTab;
import com.benbenlaw.bbllights.item.BBLLightsDataComponents;
import com.benbenlaw.bbllights.item.BBLLightsItems;
import com.benbenlaw.bbllights.network.BBLLightsNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BBLLights.MOD_ID)
public class BBLLights {
    public static final String MOD_ID = "bbllights";
    public static final Logger LOGGER = LogManager.getLogger();

    public BBLLights(final IEventBus eventBus, final ModContainer modContainer) {

        BBLLightsBlocks.BLOCKS.register(eventBus);
        BBLLightsBlockEntities.BLOCK_ENTITIES.register(eventBus);
        BBLLightsItems.ITEMS.register(eventBus);
        BBLLightsCreativeTab.CREATIVE_MODE_TABS.register(eventBus);
        BBLLightsDataComponents.COMPONENTS.register(eventBus);
        eventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, BBLLightsConfig.SPEC, "bbl/invisible_lights.toml");

    }

    public void commonSetup(RegisterPayloadHandlersEvent event) {
        BBLLightsNetworking.registerNetworking(event);
    }
}
