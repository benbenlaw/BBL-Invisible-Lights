package com.benbenlaw.bbllights.data;

import com.benbenlaw.bbllights.BBLLights;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = BBLLights.MOD_ID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new BBLLightsRecipes.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new BBLLightsModelProvider(packOutput));
        generator.addProvider(true, new BBLLightsLangProvider(packOutput));
    }
}