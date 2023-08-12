package fuzs.hangglider.handler;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.wind.WindHelper;
import fuzs.hangglider.world.item.GliderItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class PlayerGlidingHandler {

    public static void onPlayerTick$End(Player player) {

        if (PlayerGlidingHelper.isGliderDeployed(player)) {

            ItemStack stack = PlayerGlidingHelper.getGliderInHand(player);
            if (PlayerGlidingHelper.isValidGlider(stack) && !(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)) {

                if (PlayerGlidingHelper.isAllowedToGlide(player)) {

                    PlayerGlidingHelper.setGliding(player, true);
                    ServerConfig.GliderConfig glider = ((GliderItem) stack.getItem()).getGliderMaterialSettings();

                    handleGlidingMovement(player, stack, glider);

                    if (!player.level.isClientSide) {

                        handleGliderDurability(player, stack, glider);
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
        player.animationSpeed = 0.0F;
        player.animationPosition = 0.0F;
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

                brokenStack.broadcastBreakEvent(PlayerGlidingHelper.getGliderHoldingHand(player).orElseThrow(() -> new IllegalStateException("No valid glider held in hand")));
            });
        }
    }
}
