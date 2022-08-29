package fuzs.openglider.capability;

import fuzs.puzzleslib.capability.data.CapabilityComponent;

/**
 * This interface defines the contract to deal with the gliding status of a player.
 * Handled internally/exposed through capabilities.
 *
 * <p>It should only ever be present on players.
 */
public interface GlidingPlayerCapability extends CapabilityComponent {

    /**
     * Get the current gliding status of the player.
     * If True, it inherently must mean that the glider is deployed as well.
     *
     * @return - True if the player is gliding, False otherwise.
     */
    boolean getIsPlayerGliding();

    /**
     * Set the player's current gliding status.
     * If True, it inherently must mean that the glider is deployed as well.
     *
     * @param isPlayerGliding - True if the player is gliding, False otherwise.
     */
    void setIsPlayerGliding(boolean isPlayerGliding);

    /**
     * Get the current deployment status of the glider on the player.
     *
     * @return True is the player has a deployed glider, False otherwise.
     */
    boolean getIsGliderDeployed();

    /**
     * Set the player's glider's deployment status.
     *
     * @param isGliderDeployed - True if the glider is deployed, False otherwise.
     */
    void setIsGliderDeployed(boolean isGliderDeployed);

    void syncToLocalHolder();
}
