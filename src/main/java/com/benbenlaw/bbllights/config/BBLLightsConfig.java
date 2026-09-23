package com.benbenlaw.bbllights.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BBLLightsConfig {

    /**
     * Config is currently only used for the Invisible Light Spreader block
     * range is how far the block reaches in each direction, so a range of 8 lights up to 8 blocks in each direction
     * minimumLightLevel is how dark a spot has to be before the spreader lights it up
     * scanIntervalTicks is how often the spreader looks for a dark spot, then places a light
     * */

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue RANGE = BUILDER
            .comment("How far in each direction the Invisible Light Spreader looks for dark spots")
            .defineInRange("range", 32, 1, 64);

    public static final ModConfigSpec.IntValue MINIMUM_LIGHT_LEVEL = BUILDER
            .comment("Spots with a block light level below this are considered dark")
            .defineInRange("minimumLightLevel", 6, 1, 15);

    public static final ModConfigSpec.IntValue SCAN_INTERVAL_TICKS = BUILDER
            .comment("Ticks between each scan, one light block is placed per scan")
            .defineInRange("scanIntervalTicks", 30, 1, 1200);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
