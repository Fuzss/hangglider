package fuzs.openglider.helper;

import fuzs.openglider.api.world.item.Glider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class PlayerGlidingHelper {

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
