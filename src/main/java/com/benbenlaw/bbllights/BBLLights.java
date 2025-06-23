package com.benbenlaw.bbllights;


import com.benbenlaw.bbllights.item.BBLLightsCreativeTab;
import com.benbenlaw.bbllights.item.BBLLightsItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(BBLLights.MOD_ID)
public class BBLLights {
    public static final String MOD_ID = "bbllights";
    public static final Logger LOGGER = LogManager.getLogger();

    public BBLLights(final IEventBus eventBus, final ModContainer modContainer) {

        BBLLightsItems.ITEMS.register(eventBus);
        BBLLightsCreativeTab.CREATIVE_MODE_TABS.register(eventBus);

    }
}
