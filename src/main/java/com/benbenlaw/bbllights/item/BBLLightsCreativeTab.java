package com.benbenlaw.bbllights.item;

import com.benbenlaw.bbllights.BBLLights;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BBLLightsCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BBLLights.MOD_ID);

    public static final Supplier<CreativeModeTab> BBLLIGHTS_TAB = CREATIVE_MODE_TABS.register("bbllights", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BBLLightsItems.INVISIBLE_LIGHT.get().asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.bbllights"))
            .displayItems((parameters, output) -> {

                output.accept(BBLLightsItems.INVISIBLE_LIGHT.get().asItem().getDefaultInstance());
                output.accept(BBLLightsItems.IRON_INVISIBLE_LIGHT_PLACER.get().asItem().getDefaultInstance());
                output.accept(BBLLightsItems.DIAMOND_INVISIBLE_LIGHT_PLACER.get().asItem().getDefaultInstance());
                output.accept(BBLLightsItems.NETHERITE_INVISIBLE_LIGHT_PLACER.get().asItem().getDefaultInstance());



            }).build());
}
