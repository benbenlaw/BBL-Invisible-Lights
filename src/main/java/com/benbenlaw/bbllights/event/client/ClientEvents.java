package com.benbenlaw.bbllights.event.client;

import com.benbenlaw.bbllights.BBLLights;
import com.benbenlaw.bbllights.block.BBLLightsBlocks;
import com.benbenlaw.bbllights.config.BBLLightsConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = BBLLights.MOD_ID)
public class ClientEvents {

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        addShiftTooltip(stack, event, BBLLightsBlocks.INVISIBLE_LIGHT_SPREADER.get().asItem(), "tooltip.bbllights.light_spreader", BBLLightsConfig.RANGE.get().toString());
    }

    public static void addShiftTooltip(ItemStack stack, ItemTooltipEvent event, Item item, String tooltipText, String... additionalInfo) {
        if (stack.is(item)) {
            boolean alreadyAdded = event.getToolTip().stream().anyMatch((c) -> c.getString().equals(Component.translatable("tooltip.bbllights.shift").getString()));
            if (Minecraft.getInstance().hasShiftDown()) {
                event.getToolTip().add(Component.translatable(tooltipText, (Object[])additionalInfo).withStyle(ChatFormatting.BLUE));
            } else if (!alreadyAdded) {
                event.getToolTip().add(Component.translatable("tooltip.bbllights.shift").withStyle(ChatFormatting.YELLOW));
            }

        }
    }
}
