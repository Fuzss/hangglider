package fuzs.hangglider.handler;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.init.ModRegistry;
import fuzs.hangglider.mixin.accessor.ServerGamePacketListenerImplAccessor;
import fuzs.hangglider.world.wind.WindHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class PlayerGlidingHandler {

    public static void onPlayerTick$End(Player player) {

        ItemStack itemStack = PlayerGlidingHelper.getGliderInHand(player);
        if (!itemStack.isEmpty() && !PlayerGlidingHelper.isWearingElytra(player)) {

            if (PlayerGlidingHelper.isAllowedToGlide(player)) {

                ModRegistry.GLIDING_CAPABILITY.get(player).setGliding(true);
                ServerConfig.GliderConfig config = PlayerGlidingHelper.getGliderMaterialSettings(itemStack);

                handleGlidingMovement(player, itemStack, config);

                if (!player.level().isClientSide) {

                    handleGliderDurability(player, itemStack, config);
                    ((ServerGamePacketListenerImplAccessor) ((ServerPlayer) player).connection).hangglider$setAboveGroundTickCount(0);
                    ((ServerGamePacketListenerImplAccessor) ((ServerPlayer) player).connection).hangglider$setAboveGroundVehicleTickCount(0);
                }

                resetClientAnimations(player);

                return;
            }
        } else {

            ModRegistry.GLIDING_CAPABILITY.get(player).setGliderDeployed(false);
        }

        ModRegistry.GLIDING_CAPABILITY.get(player).setGliding(false);
    }

    public static void resetClientAnimations(Player player) {

        // no wild arm swinging while flying
        player.walkAnimation.setSpeed(0.0F);
        player.walkAnimation.position(0.0F);
    }

    private static void handleGlidingMovement(Player player, ItemStack stack, ServerConfig.GliderConfig glider) {

        final double horizontalSpeed;
        final double verticalSpeed;

        // Get speed depending on glider and if player is descending
        if (player.isDescending()) {
            horizontalSpeed = glider.fastHorizontalSpeed;
            verticalSpeed = glider.fastVerticalSpeed;
        } else {
            horizontalSpeed = glider.horizontalSpeed;
            verticalSpeed = glider.verticalSpeed;
        }

        // Apply wind effects
        WindHelper.applyWind(player, stack);

        // Apply forward motion
        Vec3 movement = player.getDeltaMovement();
        double deltaX = Math.cos(Math.toRadians(player.getYRot() + 90)) * horizontalSpeed;
        // TODO Wrong, need multiplication to slow down
        double deltaZ = Math.sin(Math.toRadians(player.getYRot() + 90)) * horizontalSpeed;
        player.setDeltaMovement(movement.x + deltaX, movement.y * verticalSpeed, movement.z + deltaZ);

        // Apply air resistance
        if (HangGlider.CONFIG.get(ServerConfig.class).wind.allowAirResistance) {
            movement = player.getDeltaMovement();
            double airResistance = glider.airResistance;
            player.setDeltaMovement(movement.x * airResistance, movement.y, movement.z * airResistance);
        }

        // Stop fall damage
        if (player.getDeltaMovement().y > -0.5) {
            player.fallDistance = 1.0F;
        }
    }

    private static void handleGliderDurability(Player player, ItemStack itemStack, ServerConfig.GliderConfig gliderMaterialSettings) {

        if (gliderMaterialSettings.consumeDurability && player.getRandom().nextInt(gliderMaterialSettings.durabilityUseInterval) == 0) {

            EquipmentSlot equipmentSlot = PlayerGlidingHelper.getGliderHoldingHand(player);
            Objects.requireNonNull(equipmentSlot, "equipment slot is null");
            itemStack.hurtAndBreak(1, player, equipmentSlot);
        }
    }
}
