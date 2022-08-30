package fuzs.openglider.capability;

import fuzs.openglider.OpenGlider;
import net.minecraft.nbt.CompoundTag;

public class GlidingPlayerCapabilityImpl implements GlidingPlayerCapability {
    private boolean dirty;
    private boolean gliding;
    private boolean gliderDeployed;

    public GlidingPlayerCapabilityImpl() {
        this.gliding = false;
        this.gliderDeployed = false;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void markDirty() {
        this.dirty = true;
    }

    @Override
    public void markClean() {
        this.dirty = false;
    }

    @Override
    public boolean getIsPlayerGliding() {
        return this.gliderDeployed && this.gliding;
    }

    @Override
    public void setIsPlayerGliding(boolean isGliding) {
        if (!this.gliderDeployed && isGliding) {
            OpenGlider.LOGGER.error("Can't set a player to be gliding if they don't have a deployed glider!");
        } else {
            if (isGliding && !this.gliding) OpenGlider.PROXY.afterPlayerStartGliding();
            this.gliding = isGliding;
            this.markDirty();
        }
    }

    @Override
    public boolean getIsGliderDeployed() {
        return this.gliderDeployed;
    }

    @Override
    public void setIsGliderDeployed(boolean isDeployed) {
        if (this.gliding && isDeployed) {
            OpenGlider.LOGGER.error("Player is already flying, deploying now is not needed.");
        } else {
            this.gliderDeployed = isDeployed;
            if (!isDeployed) {
                this.gliding = false; //if not deployed, cannot be flying either
            }
            this.markDirty();
        }
    }

    @Override
    public void write(CompoundTag tag) {
        tag.putBoolean("Gliding", this.gliding);
        tag.putBoolean("GliderDeployed", this.gliderDeployed);
    }

    @Override
    public void read(CompoundTag tag) {
        this.gliding = tag.getBoolean("Gliding");
        this.gliderDeployed = tag.getBoolean("GliderDeployed");
    }

}
