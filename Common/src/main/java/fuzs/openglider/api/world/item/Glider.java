package fuzs.openglider.api.world.item;

import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public interface Glider {

    GliderMaterial getMaterial(ItemStack stack);

    //==============Flight==================

    //Blocks traveled horizontally per movement time.
    default double getHorizontalFlightSpeed(ItemStack stack) {
        return this.getMaterial(stack).getHorizontalFlightSpeed();
    }

    default void setHorizontalFlightSpeed(ItemStack stack, double speed) {

    }

    //Blocks traveled vertically per movement time.
    default double getVerticalFlightSpeed(ItemStack stack) {
        return this.getMaterial(stack).getVerticalFlightSpeed();
    }

    default void setVerticalFlightSpeed(ItemStack stack, double speed) {

    }

    //Blocks traveled horizontally per movement time when going fast/pressing shift.
    default double getShiftHorizontalFlightSpeed(ItemStack stack) {
        return this.getMaterial(stack).getShiftHorizontalFlightSpeed();
    }

    default void setShiftHorizontalFlightSpeed(ItemStack stack, double speed) {

    }

    //Blocks traveled vertically per movement time when going fast/pressing shift.
    default double getShiftVerticalFlightSpeed(ItemStack stack) {
        return this.getMaterial(stack).getShiftVerticalFlightSpeed();
    }

    default void setShiftVerticalFlightSpeed(ItemStack stack, double speed) {

    }

    //===============Wind====================

    default double getWindMultiplier(ItemStack stack) {
        return this.getMaterial(stack).getWindMultiplier();
    }

    default void setWindMultiplier(ItemStack stack, double windMultiplier) {

    }

    default double getAirResistance(ItemStack stack) {
        return this.getMaterial(stack).getAirResistance();
    }

    default void setAirResistance(ItemStack stack, double airResistance) {

    }

    //=============Durability================


    default boolean consumesDurability(ItemStack stack) {
        return this.getMaterial(stack).consumesDurability();
    }

    default int getDurabilityUseInterval(ItemStack stack) {
        return this.getMaterial(stack).getDurabilityUseInterval();
    }

    default boolean isBroken(ItemStack stack) {
        return !ElytraItem.isFlyEnabled(stack);
    }
}
