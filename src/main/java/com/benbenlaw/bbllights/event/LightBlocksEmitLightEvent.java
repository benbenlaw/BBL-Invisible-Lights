package com.benbenlaw.bbllights.event;

import com.benbenlaw.bbllights.BBLLights;
import com.benbenlaw.bbllights.item.LightItem;
import com.benbenlaw.bbllights.network.LightItemPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;

import static com.benbenlaw.bbllights.item.BBLLightsDataComponents.LIGHT_LEVEL;

@EventBusSubscriber(modid = BBLLights.MOD_ID, value = Dist.CLIENT)
public class LightBlocksEmitLightEvent {

    private static final List<BlockPos> lightBlockPositions = new ArrayList<>();
    public static int particleDelay = 0;


    // Render a particle to show Light Blocks emitting light in the world
    @SubscribeEvent
    public static void onClientPlayerTickEvent(ClientTickEvent.Post event) {
        // Delay particle emission to avoid performance issues
        particleDelay++;
        if (particleDelay < 20) {
            return;
        }
        particleDelay = 0;

        // Ensure Minecraft instance and player are not null
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) {
            return;
        }

        // Check if the player is holding a LightItem
        ItemStack lightItem = Minecraft.getInstance().player.getMainHandItem();
        Level level = Minecraft.getInstance().level;

        // Execute only if the held item is a LightItem
        if (lightItem.getItem() instanceof LightItem) {
            BlockPos playerPos = Minecraft.getInstance().player.blockPosition();
            int radius = 32;

            for (BlockPos pos : BlockPos.betweenClosed(playerPos.offset(-radius, -radius, -radius), playerPos.offset(radius, radius, radius))) {
                assert level != null;
                if (level.getBlockState(pos).is(Blocks.LIGHT)) {
                    lightBlockPositions.add(pos);

                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.5;
                    double z = pos.getZ() + 0.5;

                    level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.0, 0.0, 0.0);

                }
            }
        }
    }

    // Render the light level above Light Blocks in the world
    @SubscribeEvent
    public static void onClientWorldRender(RenderLevelStageEvent.AfterTranslucentBlocks event) {

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return;
        }

        ItemStack lightItem = mc.player.getMainHandItem();
        if (!(lightItem.getItem() instanceof LightItem)) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();
        Font font = mc.font;

        BlockPos playerPos = mc.player.blockPosition();
        int radius = 16;

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-radius, -radius, -radius),
                playerPos.offset(radius, radius, radius))) {

            BlockState state = mc.level.getBlockState(pos);
            if (!state.is(Blocks.LIGHT)) continue;

            int lightLevel = state.getValue(LightBlock.LEVEL);

            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.75;
            double z = pos.getZ() + 0.5;

            poseStack.pushPose();
            poseStack.translate(
                    x - mc.getEntityRenderDispatcher().camera.position().x,
                    y - mc.getEntityRenderDispatcher().camera.position().y,
                    z - mc.getEntityRenderDispatcher().camera.position().z
            );
            poseStack.mulPose(mc.getEntityRenderDispatcher().camera.rotation());
            poseStack.scale(0.02f, -0.02f, 0.02f);

            float width = font.width(String.valueOf(lightLevel)) / 2f;
            font.drawInBatch(
                    String.valueOf(lightLevel), -width, 0, 0xFFFFFFFF,
                    false, poseStack.last().pose(), mc.renderBuffers().bufferSource(),
                    Font.DisplayMode.SEE_THROUGH, 0, LevelLightEngine.LIGHT_SECTION_PADDING
            );
            poseStack.popPose();
        }

        mc.renderBuffers().bufferSource().endBatch();
    }


    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        ItemStack stack = mc.player.getMainHandItem();
        if (stack.getItem() instanceof LightItem && mc.player.isShiftKeyDown()) {
            int lightLevel = stack.getOrDefault(LIGHT_LEVEL.get(), 15);
            boolean changed = false;

            if (event.getScrollDeltaY() > 0 && lightLevel < 15) {
                lightLevel++;
                changed = true;
            } else if (event.getScrollDeltaY() < 0 && lightLevel > 0) {
                lightLevel--;
                changed = true;
            }

            if (changed) {
                ClientPacketDistributor.sendToServer(new LightItemPacket(lightLevel));
                stack.set(LIGHT_LEVEL, lightLevel);

                mc.gui.setOverlayMessage(
                        Component.translatable("tooltip.bbllights.light_level", lightLevel),
                        false
                );

                event.setCanceled(true);
            }
        }
    }

}