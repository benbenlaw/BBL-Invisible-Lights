package com.benbenlaw.bbllights.event;

import com.benbenlaw.bbllights.BBLLights;
import com.benbenlaw.bbllights.item.LightItem;
import com.benbenlaw.bbllights.network.LightItemPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;

import static com.benbenlaw.bbllights.item.BBLLightsDataComponents.LIGHT_LEVEL;

@EventBusSubscriber(modid = BBLLights.MOD_ID, value = Dist.CLIENT)
public class LightBlocksEmitLightEvent {

    private static final ContextKey<List<LightLabel>> LIGHT_LABELS =
            ContextKey.vanilla("bbllights_light_labels");

    public static int particleDelay = 0;

    private record LightLabel(double relX, double relY, double relZ, int lightLevel) {}

    @SubscribeEvent
    public static void onClientPlayerTickEvent(ClientTickEvent.Post event) {
        particleDelay++;
        if (particleDelay < 20) return;
        particleDelay = 0;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) return;

        ItemStack lightItem = minecraft.player.getMainHandItem();
        Level level = minecraft.level;

        if (lightItem.getItem() instanceof LightItem) {
            BlockPos playerPos = minecraft.player.blockPosition();
            int radius = 32;

            for (BlockPos pos : BlockPos.betweenClosed(
                    playerPos.offset(-radius, -radius, -radius),
                    playerPos.offset(radius, radius, radius))) {

                if (level.getBlockState(pos).is(Blocks.LIGHT)) {
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.5;
                    double z = pos.getZ() + 0.5;

                    level.addParticle(net.minecraft.core.particles.ParticleTypes.END_ROD, x, y, z, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onExtractLevelRenderState(ExtractLevelRenderStateEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        ItemStack lightItem = mc.player.getMainHandItem();
        if (!(lightItem.getItem() instanceof LightItem)) return;

        Vec3 camPos = event.getCamera().position();
        Level level = event.getLevel();
        BlockPos playerPos = mc.player.blockPosition();
        int radius = 32;

        List<LightLabel> labels = new ArrayList<>();

        for (BlockPos pos : BlockPos.betweenClosed(
                playerPos.offset(-radius, -radius, -radius),
                playerPos.offset(radius, radius, radius))) {

            BlockState state = level.getBlockState(pos);
            if (!state.is(Blocks.LIGHT)) continue;

            int lightLevel = state.getValue(LightBlock.LEVEL);

            double x = pos.getX() + 0.5 - camPos.x;
            double y = pos.getY() + 0.75 - camPos.y;
            double z = pos.getZ() + 0.5 - camPos.z;

            labels.add(new LightLabel(x, y, z, lightLevel));
        }

        event.getRenderState().setRenderData(LIGHT_LABELS, labels);
    }

    @SubscribeEvent
    public static void onSubmitCustomGeometry(SubmitCustomGeometryEvent event) {
        List<LightLabel> labels = event.getLevelRenderState().getRenderData(LIGHT_LABELS);
        if (labels == null || labels.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        PoseStack poseStack = event.getPoseStack();

        for (LightLabel label : labels) {
            poseStack.pushPose();
            poseStack.translate(label.relX(), label.relY(), label.relZ());
            poseStack.mulPose(mc.getEntityRenderDispatcher().camera.rotation());
            poseStack.scale(0.02f, -0.02f, 0.02f);

            String text = String.valueOf(label.lightLevel());
            float width = font.width(text) / 2f;
            FormattedCharSequence sequence = FormattedCharSequence.forward(text, net.minecraft.network.chat.Style.EMPTY);

            event.getSubmitNodeCollector().submitText(
                    poseStack, -width, 0, sequence,
                    false, Font.DisplayMode.NORMAL,
                    15728880, 0xFFFFFFFF, 0, 0
            );

            poseStack.popPose();
        }
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

                mc.gui.hud.setOverlayMessage(
                        Component.translatable("tooltip.bbllights.light_level", lightLevel),
                        false
                );

                event.setCanceled(true);
            }
        }
    }
}