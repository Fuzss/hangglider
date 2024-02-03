package fuzs.hangglider.capability;

import fuzs.hangglider.HangGlider;
import fuzs.puzzleslib.api.capability.v3.data.CapabilityComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

/**
 * This interface defines the contract to deal with the gliding status of a player.
 * Handled internally/exposed through capabilities.
 *
 * <p>It should only ever be present on players.
 */
public class GlidingCapability extends CapabilityComponent<Player> {
    public static final String TAG_GLIDING = HangGlider.id("gliding").toString();
    public static final String TAG_GLIDER_DEPLOYED = HangGlider.id("glider_deployed").toString();

    private boolean gliding;
    private boolean gliderDeployed;

    /**
     * Get the current gliding status of the player.
     * If True, it inherently must mean that the glider is deployed as well.
     *
     * @return - True if the player is gliding, False otherwise.
     */
    public boolean isGliding() {
        return this.gliding;
    }

    /**
     * Set the player's current gliding status.
     * If True, it inherently must mean that the glider is deployed as well.
     *
     * @param gliding - True if the player is gliding, False otherwise.
     */
    public void setGliding(boolean gliding) {
        if (!this.getHolder().level().isClientSide) {
            gliding &= this.gliderDeployed;
            if (this.gliding != gliding) {
                this.gliding = gliding;
                this.setChanged();
            }
        }
    }

    /**
     * Get the current deployment status of the glider on the player.
     *
     * @return True is the player has a deployed glider, False otherwise.
     */
    public boolean isGliderDeployed() {
        return this.gliderDeployed;
    }

    /**
     * Set the player's glider's deployment status.
     *
     * @param gliderDeployed - True if the glider is deployed, False otherwise.
     */
    public void setGliderDeployed(boolean gliderDeployed) {
        if (!this.getHolder().level().isClientSide) {
            if (this.gliderDeployed != gliderDeployed) {
                this.gliderDeployed = gliderDeployed;
                if (!gliderDeployed) {
                    this.gliding = false;
                }
                this.setChanged();
            }
        }
    }

    @Override
    public void write(CompoundTag tag) {
        tag.putBoolean(TAG_GLIDER_DEPLOYED, this.gliderDeployed);
        tag.putBoolean(TAG_GLIDING, this.gliding);
    }

    @Override
    public void read(CompoundTag tag) {
        this.gliderDeployed = tag.getBoolean(TAG_GLIDER_DEPLOYED);
        this.gliding = tag.getBoolean(TAG_GLIDING);
    }
}
