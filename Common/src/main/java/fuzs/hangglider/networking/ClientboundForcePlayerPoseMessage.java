package fuzs.hangglider.networking;

import fuzs.hangglider.core.CommonAbstractions;
import fuzs.puzzleslib.api.networking.v3.ClientMessageListener;
import fuzs.puzzleslib.api.networking.v3.ClientboundMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Pose;

public record ClientboundForcePlayerPoseMessage(boolean gliding) implements ClientboundMessage<ClientboundForcePlayerPoseMessage> {

    @Override
    public ClientMessageListener<ClientboundForcePlayerPoseMessage> getHandler() {
        return new ClientMessageListener<>() {

            @Override
            public void handle(ClientboundForcePlayerPoseMessage message, Minecraft client, ClientPacketListener handler, LocalPlayer player, ClientLevel level) {
                CommonAbstractions.INSTANCE.setForcedPlayerPose(player, message.gliding ? Pose.SPIN_ATTACK : null);
            }
        };
    }
}
