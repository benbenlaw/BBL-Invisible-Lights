package com.benbenlaw.bbllights.item;

import com.benbenlaw.bbllights.BBLLights;
import com.benbenlaw.core.item.LightItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BBLLightsItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BBLLights.MOD_ID);

    public static final DeferredItem<Item> INVISIBLE_LIGHT = ITEMS.registerItem("invisible_light",
            LightItem::new, new Item.Properties());

    public static final DeferredItem<Item> IRON_INVISIBLE_LIGHT_PLACER = ITEMS.registerItem("iron_invisible_light_placer",
            LightItem::new, new Item.Properties().durability(121));

    public static final DeferredItem<Item> DIAMOND_INVISIBLE_LIGHT_PLACER = ITEMS.registerItem("diamond_invisible_light_placer",
            LightItem::new, new Item.Properties().durability(481));

    public static final DeferredItem<Item> NETHERITE_INVISIBLE_LIGHT_PLACER = ITEMS.registerItem("netherite_invisible_light_placer",
            LightItem::new, new Item.Properties().durability(961));

}

