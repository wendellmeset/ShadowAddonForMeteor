package com.example.addon.modules;

import baritone.api.utils.BetterBlockPos;
import com.example.addon.Addon;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;

import static meteordevelopment.meteorclient.utils.Utils.mc;

public class Annihilator extends Module {
    private final DoubleSetting range = settings.create(new DoubleSetting.Builder()
            .name("range")
            .description("The range of the nuke.")
            .min(1)
            .max(14)
            .defaultValue(5)
            .build()
    );

    private final StringSetting block = settings.create(new StringSetting.Builder()
            .name("block")
            .description("The block to fill with.")
            .defaultValue("air")
            .build()
    );

    public Annihilator() {
        super(Addon.CATEGORY, "annihilator", "Nukes whatever you click at. Requires /fill permissions.");
    }

    @Override
    public void onActivate() {
        ChatUtils.moduleInfo(this, "Press right-click to use.");
    }

    @Override
    public void onDeactivate() {
        ChatUtils.moduleInfo(this, "Disabled.");
    }

    @Override
    public void onPacketSend(PacketEvent.Send event) {
        if (event.packet instanceof PlayerMoveC2SPacket) {
            return;
        }
        HitResult hitResult = mc.crosshairTarget;
        if (hitResult.getType() == HitResult.Type.MISS) {
            return;
        }
        Vec3d hitPos = hitResult.getPos();
        BlockPos pos = new BlockPos(hitPos.getX(), hitPos.getY(), hitPos.getZ());

        int startY = MathHelper.clamp(r(pos.getY() - range.get()), mc.world.getBottomY(), mc.world.getTopY());
        int endY = MathHelper.clamp(r(pos.getY() + range.get()), mc.world.getBottomY(), mc.world.getTopY());

        BetterBlockPos start = new BetterBlockPos(r(pos.getX() - range.get()), startY, r(pos.getZ() - range.get()));
        BetterBlockPos end = new BetterBlockPos(r(pos.getX() + range.get()), endY, r(pos.getZ() + range.get()));

        int size = (end.getX() - start.getX() + 1) * (end.getY() - start.getY() + 1) * (end.getZ() - start.getZ() + 1);

        if (size > 1000000) {
            ChatUtils.moduleWarning(this, "Too many blocks selected! Please reduce the range.");
            return;
        }

        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int y = start.getY(); y <= end.getY(); y++) {
                for (int z = start.getZ(); z <= end.getZ(); z++) {
                    String cmd = "/fill " + start.getX() + " " + start.getY() + " " + start.getZ() + " " +
                                 end.getX() + " " + end.getY() + " " + end.getZ() + " " +
                                 (block.get().isEmpty() ? "minecraft:air" : "minecraft:" + block.get());
                    mc.player.sendChatMessage(cmd);
                }
            }
        }
    }
}

