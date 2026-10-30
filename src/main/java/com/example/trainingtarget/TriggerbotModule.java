package com.example.trainingtarget;

import java.util.UUID;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;

public final class TriggerbotModule {
    private static boolean enabled;
    private static UUID lastTarget;

    private TriggerbotModule() {
    }

    public static boolean toggle() {
        enabled = !enabled;
        lastTarget = null;
        return enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void onTick(MinecraftClient client) {
        if (!enabled || client.player == null || client.world == null) {
            lastTarget = null;
            return;
        }

        if (!(client.crosshairTarget instanceof EntityHitResult hit)
                || !(hit.getEntity() instanceof LivingEntity target)
                || target == client.player
                || !target.isAlive()) {
            lastTarget = null;
            return;
        }

        UUID targetId = target.getUuid();
        if (!targetId.equals(lastTarget)) {
            client.player.sendMessage(
                Text.literal("Target: " + target.getName().getString()),
                true
            );
            lastTarget = targetId;
        }
    }
}
