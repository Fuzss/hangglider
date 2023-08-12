package fuzs.hangglider.capability;

import net.minecraft.nbt.CompoundTag;

public class GlidingCapabilityImpl implements GlidingCapability {
    private static final String TAG_GLIDING = "Gliding";
    private static final String TAG_GLIDER_DEPLOYED = "GliderDeployed";

    private boolean dirty;
    private boolean gliding;
    private boolean gliderDeployed;

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
    public boolean isGliding() {
        return this.gliding;
    }

    @Override
    public void setGliding(boolean gliding) {
        gliding &= this.gliderDeployed;
        if (this.gliding != gliding) {
            this.gliding = gliding;
            this.markDirty();
        }
    }

    @Override
    public boolean isGliderDeployed() {
        return this.gliderDeployed;
    }

    @Override
    public void setGliderDeployed(boolean gliderDeployed) {
        if (this.gliderDeployed != gliderDeployed) {
            this.gliderDeployed = gliderDeployed;
            this.markDirty();
            if (!gliderDeployed) {
                this.gliding = false;
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
        this.setGliderDeployed(tag.getBoolean(TAG_GLIDER_DEPLOYED));
        this.gliding = tag.getBoolean(TAG_GLIDING);
    }

}
