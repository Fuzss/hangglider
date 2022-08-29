package fuzs.openglider.network;

import fuzs.openglider.helper.GliderHelper;
import fuzs.puzzleslib.network.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class S2CUpdateClientTargetMessage implements Message<S2CUpdateClientTargetMessage> {
    private int targetEntityID;
    private boolean isGliding;

    public S2CUpdateClientTargetMessage() {

    }

    public S2CUpdateClientTargetMessage(Player targetEntity, boolean isGliding) {
        this.targetEntityID = targetEntity.getId();
        this.isGliding = isGliding;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.targetEntityID);
        buf.writeBoolean(this.isGliding);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.targetEntityID = buf.readInt();
        this.isGliding = buf.readBoolean();
    }

    @Override
    public MessageHandler<S2CUpdateClientTargetMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(S2CUpdateClientTargetMessage message, Player player, Object gameInstance) {
                Level level = ((Minecraft) gameInstance).level;
                if (level.getEntity(message.targetEntityID) instanceof Player player1) {
                    GliderHelper.setIsGliderDeployed(player1, message.isGliding);
                }
            }
        };
    }
}
