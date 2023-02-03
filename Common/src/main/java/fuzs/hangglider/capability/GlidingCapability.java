package fuzs.hangglider.capability;

import fuzs.puzzleslib.capability.data.SyncedCapabilityComponent;

/**
 * This interface defines the contract to deal with the gliding status of a player.
 * Handled internally/exposed through capabilities.
 *
 * <p>It should only ever be present on players.
 */
public interface GlidingCapability extends SyncedCapabilityComponent {

    /**
     * Get the current gliding status of the player.
     * If True, it inherently must mean that the glider is deployed as well.
     *
     * @return - True if the player is gliding, False otherwise.
     */
    boolean isGliding();

    /**
     * Set the player's current gliding status.
     * If True, it inherently must mean that the glider is deployed as well.
     *
     * @param gliding - True if the player is gliding, False otherwise.
     */
    void setGliding(boolean gliding);

    /**
     * Get the current deployment status of the glider on the player.
     *
     * @return True is the player has a deployed glider, False otherwise.
     */
    boolean isGliderDeployed();

    /**
     * Set the player's glider's deployment status.
     *
     * @param gliderDeployed - True if the glider is deployed, False otherwise.
     */
    void setGliderDeployed(boolean gliderDeployed);
}
