package fuzs.openglider.helper;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.world.item.Glider;
import fuzs.openglider.config.ServerConfig;
import fuzs.openglider.wind.WindHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class OpenGliderPlayerHelper {

    /**
     * Updates the position of the player when gliding.
     * Glider is assumed to be deployed already.
     *
     * @param player - the player gliding
     */
    public static void updatePosition(Player player){
        if (isAllowedToGlide(player)) {
            ItemStack stack = GliderHelper.getGlider(player);
            if (isValidGlider(stack)) {
                Glider glider = (Glider) stack.getItem();
                if (player.getDeltaMovement().y < 0) { //if falling (flying)

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
                    if (OpenGlider.CONFIG.get(ServerConfig.class).wind.airResistance) {
                        movement = player.getDeltaMovement();
                        player.setDeltaMovement(movement.x * glider.getAirResistance(stack), movement.y, movement.z * glider.getAirResistance(stack));
                    }

                    // Stop fall damage
                    player.fallDistance = 0.0F;
                }

                //no wild arm swinging while flying
                if (player.level.isClientSide) {
                    player.animationSpeed = 0;
                    player.animationPosition = 0;
                }

                //damage the hang glider
                if (glider.getMaterial(stack).consumesDurability()) { //durability should be taken away
                    if (!player.level.isClientSide) { //server
                        if (player.getRandom().nextInt(glider.getMaterial(stack).getDurabilityUseInterval()) == 0) { //damage about once per x ticks
                            stack.hurtAndBreak(1, player, brokenStack -> {
                                brokenStack.broadcastBreakEvent(OpenGliderPlayerHelper.getGliderHand(player).orElseThrow());
                            });
                            if (!OpenGliderPlayerHelper.isValidGlider(stack)) {
                                GliderHelper.setIsGliderDeployed(player, false);
                            }
                        }
                    }
                }

                //SetPositionAndUpdate on server only

            } else { //Invalid item (likely changed selected item slot, update)
                GliderHelper.setIsGliderDeployed(player, false);
            }
        }
    }

    /**
     * Check if the player should be gliding.
     * Checks if the player is alive, and not on the ground or in water.
     *
     * @param player - the player to check
     * @return - true if the conditions are met, false otherwise
     */
    public static boolean isAllowedToGlide(Player player){
        return player.isAlive() && !player.isOnGround() && !player.isInWater();
    }

    /**
     * Check if the itemStack is an unbroken HangGlider.
     *
     * @param stack - the itemstack to check
     * @return - true if the item is an unbroken glider, false otherwise
     */
    public static boolean isValidGlider(ItemStack stack) {
        return stack.getItem() instanceof Glider glider && !glider.isBroken(stack);
    }

    /**
     * Loop through player's inventory to get their hang glider.
     *
     * @param player - the player to search
     * @return - the first glider found (as an itemstack), null otherwise
     */
    public static ItemStack getGliderInHand(Player player) {
        return getGliderHand(player).map(player::getItemBySlot).orElse(ItemStack.EMPTY);
    }

    public static Optional<EquipmentSlot> getGliderHand(Player player) {
        if (player.getMainHandItem().getItem() instanceof Glider) {
            return Optional.of(EquipmentSlot.MAINHAND);
        } else if (player.getOffhandItem().getItem() instanceof Glider) {
            return Optional.of(EquipmentSlot.OFFHAND);
        }
        return Optional.empty();
    }
}
