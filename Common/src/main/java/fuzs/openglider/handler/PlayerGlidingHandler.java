package fuzs.openglider.handler;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.world.item.Glider;
import fuzs.openglider.config.ServerConfig;
import fuzs.openglider.helper.GliderCapabilityHelper;
import fuzs.openglider.helper.PlayerGlidingHelper;
import fuzs.openglider.wind.WindHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class PlayerGlidingHandler {

    private PlayerGlidingHandler() {

    }

    /**
     * Updates the position of the player when gliding.
     * Glider is assumed to be deployed already.
     *
     * @param player - the player gliding
     */
    public static void onPlayerTick$End(Player player){
        if (!GliderCapabilityHelper.getIsGliderDeployed(player) || !PlayerGlidingHelper.isAllowedToGlide(player)) return;
        ItemStack stack = GliderCapabilityHelper.getGlider(player);
        if (PlayerGlidingHelper.isValidGlider(stack)) {
            Glider glider = (Glider) stack.getItem();
            if (player.getDeltaMovement().y < 0) { //if falling (flying)

                handleGlidingMovement(player, stack, glider);
            }

            handleClientAnimations(player);

            handleGliderDurability(player, stack, glider);

            //SetPositionAndUpdate on server only

        } else { //Invalid item (likely changed selected item slot, update)
            GliderCapabilityHelper.setIsGliderDeployed(player, false);
        }
    }

    private static void handleGlidingMovement(Player player, ItemStack stack, Glider glider) {
        // Init variables
        final double horizontalSpeed;
        final double verticalSpeed;

        // Get speed depending on glider and if player is sneaking
        if (!player.isDescending()) {
            horizontalSpeed = glider.getHorizontalFlightSpeed(stack);
            verticalSpeed = glider.getVerticalFlightSpeed(stack);
        } else {
            horizontalSpeed = glider.getShiftHorizontalFlightSpeed(stack);
            verticalSpeed = glider.getShiftVerticalFlightSpeed(stack);
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
        if (OpenGlider.CONFIG.get(ServerConfig.class).wind.allowAirResistance) {
            movement = player.getDeltaMovement();
            player.setDeltaMovement(movement.x * glider.getAirResistance(stack), movement.y, movement.z * glider.getAirResistance(stack));
        }

        // Stop fall damage
        if (player.getDeltaMovement().y > -0.5) {
            player.fallDistance = 1.0F;
        }
    }

    private static void handleClientAnimations(Player player) {
        //no wild arm swinging while flying
        if (player.level.isClientSide) {
            player.animationSpeed = 0.0F;
            player.animationPosition = 0.0F;
        }
    }

    private static void handleGliderDurability(Player player, ItemStack stack, Glider glider) {
        //damage the hang glider
        if (glider.consumesDurability(stack)) { //durability should be taken away
            if (!player.level.isClientSide) { //server
                if (player.getRandom().nextInt(glider.getDurabilityUseInterval(stack)) == 0) { //damage about once per x ticks
                    stack.hurtAndBreak(1, player, brokenStack -> {
                        brokenStack.broadcastBreakEvent(PlayerGlidingHelper.getGliderHand(player).orElseThrow());
                    });
                    if (!PlayerGlidingHelper.isValidGlider(stack)) {
                        GliderCapabilityHelper.setIsGliderDeployed(player, false);
                    }
                }
            }
        }
    }
}
