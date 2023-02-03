package fuzs.hangglider.core;

import fuzs.hangglider.api.extension.ForcedPosePlayer;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class FabricAbstractions implements CommonAbstractions {

    @Override
    public void setForcedPlayerPose(Player player, @Nullable Pose pose) {
        ((ForcedPosePlayer) player).hangglider$setForcedPose(pose);
    }
}
