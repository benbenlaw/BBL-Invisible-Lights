package com.benbenlaw.bbllights.item;

import com.benbenlaw.bbllights.BBLLights;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BBLLightsItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BBLLights.MOD_ID);

    public static final DeferredItem<Item> INVISIBLE_LIGHT = ITEMS.registerItem("invisible_light",
            LightItem::new);

    public static final DeferredItem<Item> IRON_INVISIBLE_LIGHT_PLACER = ITEMS.registerItem("iron_invisible_light_placer",
            properties -> new LightItem(properties.durability(121)));

    public static final DeferredItem<Item> DIAMOND_INVISIBLE_LIGHT_PLACER = ITEMS.registerItem("diamond_invisible_light_placer",
            properties -> new LightItem(properties.durability(418)));

    public static final DeferredItem<Item> NETHERITE_INVISIBLE_LIGHT_PLACER = ITEMS.registerItem("netherite_invisible_light_placer",
            properties -> new LightItem(properties.durability(961)));

}

