package fuzs.openglider.world.item;

import net.minecraft.world.item.ItemStack;

public interface Glider {

    //==============Flight==================

    //Blocks traveled horizontally per movement time.
    double getHorizontalFlightSpeed();

    void setHorizontalFlightSpeed(double speed);

    //Blocks traveled vertically per movement time.
    double getVerticalFlightSpeed();

    void setVerticalFlightSpeed(double speed);

    //Blocks traveled horizontally per movement time when going fast/pressing shift.
    double getShiftHorizontalFlightSpeed();

    void setShiftHorizontalFlightSpeed(double speed);

    //Blocks traveled vertically per movement time when going fast/pressing shift.
    double getShiftVerticalFlightSpeed();

    void setShiftVerticalFlightSpeed(double speed);

    //===============Wind====================

    double getWindMultiplier();

    void setWindMultiplier(double windMultiplier);

    double getAirResistance();

    void setAirResistance(double airResistance);

    //=============Durability================

    int getTotalDurability();

    void setTotalDurability(int durability);

    int getCurrentDurability(ItemStack glider);

    void setCurrentDurability(ItemStack glider, int durability);

    boolean isBroken(ItemStack glider);
}
