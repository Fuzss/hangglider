package fuzs.openglider.handler;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.capability.GlidingPlayerCapability;
import fuzs.openglider.helper.GliderHelper;
import fuzs.openglider.helper.OpenGliderPlayerHelper;
import fuzs.openglider.init.ModRegistry;
import fuzs.openglider.network.S2CUpdateClientTargetMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FlyingHandler {

    public void onPlayerTick(Player player) {
        if (GliderHelper.getIsGliderDeployed(player)){
            OpenGliderPlayerHelper.updatePosition(player);
        }
    }

    public void onStartTracking(Entity trackedEntity, ServerPlayer player) {
        if (trackedEntity instanceof ServerPlayer trackedPlayer) { //only entityPlayerMP ( MP part is very important!)
            if (ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(trackedPlayer).isPresent()) { //if have the capability
                if (GliderHelper.getIsGliderDeployed(trackedPlayer)) { //if the target has capability need to update
                    OpenGlider.NETWORK.sendTo(new S2CUpdateClientTargetMessage(trackedPlayer, true), player); //send a packet to the tracker's client to update their target
                } else {
                    OpenGlider.NETWORK.sendTo(new S2CUpdateClientTargetMessage(trackedPlayer, false), player);
                }
            }
        }
    }

    public void onEntityJoinLevel(Entity entity, Level level) {
        ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(entity).ifPresent(GlidingPlayerCapability::syncToLocalHolder);
    }
}
