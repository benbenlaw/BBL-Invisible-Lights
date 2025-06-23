package com.benbenlaw.bbllights.data;

import com.benbenlaw.bbllights.BBLLights;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class BBLLightsLangProvider extends LanguageProvider {

    public BBLLightsLangProvider(PackOutput packOutput) {
        super(packOutput, BBLLights.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        add("itemGroup.bbllights", "BBL Invisible Lights");

        addItemTranslation("invisible_light", "Invisible Light");
        addItemTranslation("iron_invisible_light_placer", "Iron Invisible Light Placer");
        addItemTranslation("diamond_invisible_light_placer", "Diamond Invisible Light Placer");
        addItemTranslation("netherite_invisible_light_placer", "Netherite Invisible Light Placer");
    }

    private void addItemTranslation(String name, String translation) {
        add("item." + BBLLights.MOD_ID + "." + name, translation);
    }
}
