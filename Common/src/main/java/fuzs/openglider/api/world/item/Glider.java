package fuzs.openglider.api.world.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public interface Glider {

    //==============Flight==================

    //Blocks traveled horizontally per movement time.
    double getHorizontalFlightSpeed(ItemStack stack);

    default void setHorizontalFlightSpeed(ItemStack stack, double speed) {

    }

    //Blocks traveled vertically per movement time.
    double getVerticalFlightSpeed(ItemStack stack);

    default void setVerticalFlightSpeed(ItemStack stack, double speed) {

    }

    //Blocks traveled horizontally per movement time when going fast/pressing shift.
    double getShiftHorizontalFlightSpeed(ItemStack stack);

    default void setShiftHorizontalFlightSpeed(ItemStack stack, double speed) {

    }

    //Blocks traveled vertically per movement time when going fast/pressing shift.
    double getShiftVerticalFlightSpeed(ItemStack stack);

    default void setShiftVerticalFlightSpeed(ItemStack stack, double speed) {

    }

    //===============Wind====================

    double getWindMultiplier(ItemStack stack);

    default void setWindMultiplier(ItemStack stack, double windMultiplier) {

    }

    double getAirResistance(ItemStack stack);

    default void setAirResistance(ItemStack stack, double airResistance) {

    }

    //=============Durability================


    boolean consumesDurability(ItemStack stack);

    int getDurabilityUseInterval(ItemStack stack);

    default boolean isBroken(ItemStack stack) {
        return !ElytraItem.isFlyEnabled(stack);
    }

    //=============Client================

    ResourceLocation getGliderTexture(ItemStack stack);
}
