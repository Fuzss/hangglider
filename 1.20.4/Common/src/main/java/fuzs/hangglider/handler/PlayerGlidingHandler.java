package fuzs.hangglider.handler;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.mixin.accessor.ServerGamePacketListenerImplAccessor;
import fuzs.hangglider.world.item.GliderItem;
import fuzs.hangglider.world.wind.WindHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class PlayerGlidingHandler {

    public static void onPlayerTick$End(Player player) {

        if (PlayerGlidingHelper.isGliderDeployed(player)) {

            ItemStack stack = PlayerGlidingHelper.getGliderInHand(player);
            if (PlayerGlidingHelper.isValidGlider(stack) && !(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)) {

                if (PlayerGlidingHelper.isAllowedToGlide(player)) {

                    PlayerGlidingHelper.setGliding(player, true);
                    ServerConfig.GliderConfig config = ((GliderItem) stack.getItem()).getGliderMaterialSettings();

                    handleGlidingMovement(player, stack, config);

                    if (!player.level().isClientSide) {

                        handleGliderDurability(player, stack, config);
                        ((ServerGamePacketListenerImplAccessor) ((ServerPlayer) player).connection).hangglider$setAboveGroundTickCount(0);
                        ((ServerGamePacketListenerImplAccessor) ((ServerPlayer) player).connection).hangglider$setAboveGroundVehicleTickCount(0);
                    }

                    resetClientAnimations(player);

                    return;
                }
            } else {

                PlayerGlidingHelper.setGliderDeployed(player, false);
            }
        }

        PlayerGlidingHelper.setGliding(player, false);
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

    private static void handleGliderDurability(Player player, ItemStack stack, ServerConfig.GliderConfig glider) {

        if (glider.consumeDurability && player.getRandom().nextInt(glider.durabilityUseInterval) == 0) {

            stack.hurtAndBreak(1, player, brokenStack -> {

                EquipmentSlot equipmentSlot = PlayerGlidingHelper.getGliderHoldingHand(player);
                Objects.requireNonNull(equipmentSlot, "equipment slot is null");
                brokenStack.broadcastBreakEvent(equipmentSlot);
            });
        }
    }
}
