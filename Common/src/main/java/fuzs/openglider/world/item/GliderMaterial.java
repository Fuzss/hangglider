package fuzs.openglider.world.item;

public interface GliderMaterial {

    //==============Flight==================

    /**
     * @return Blocks traveled horizontally per movement time.
     */
    double getHorizontalFlightSpeed();

    /**
     * @return Blocks traveled vertically per movement time.
     */
    double getVerticalFlightSpeed();

    /**
     * @return Blocks traveled horizontally per movement time when going fast/pressing shift.
     */
    double getShiftHorizontalFlightSpeed();

    /**
     * @return Blocks traveled vertically per movement time when going fast/pressing shift.
     */
    double getShiftVerticalFlightSpeed();

    //===============Wind====================

    double getWindMultiplier();

    double getAirResistance();

    //===============Durability====================

    boolean consumesDurability();

    int getDurabilityUseInterval();
}
