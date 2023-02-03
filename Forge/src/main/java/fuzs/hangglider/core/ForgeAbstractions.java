package fuzs.hangglider.core;

import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public void setForcedPlayerPose(Player player, @Nullable Pose pose) {
        player.setForcedPose(pose);
    }
}
