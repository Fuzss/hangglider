package fuzs.hangglider.api.extension;

import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.Nullable;

public interface ForcedPosePlayer {

    void hangglider$setForcedPose(@Nullable Pose pose);

    @Nullable
    Pose hangglider$getForcedPose();
}
