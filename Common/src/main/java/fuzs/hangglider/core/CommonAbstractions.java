package fuzs.hangglider.core;

import fuzs.puzzleslib.util.PuzzlesUtil;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public interface CommonAbstractions {
    CommonAbstractions INSTANCE = PuzzlesUtil.loadServiceProvider(CommonAbstractions.class);

    void setForcedPlayerPose(Player player, @Nullable Pose pose);
}
