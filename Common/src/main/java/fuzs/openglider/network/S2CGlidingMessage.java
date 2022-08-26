package fuzs.openglider.network;

import fuzs.openglider.helper.GliderHelper;
import fuzs.puzzleslib.network.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class S2CGlidingMessage implements Message<S2CGlidingMessage> {
    private boolean gliding;

    public S2CGlidingMessage() {

    }

    public S2CGlidingMessage(boolean gliding) {
        this.gliding = gliding;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.gliding);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.gliding = buf.readBoolean();
    }

    @Override
    public MessageHandler<S2CGlidingMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(S2CGlidingMessage message, Player player, Object gameInstance) {
                GliderHelper.setIsGliderDeployed(player, message.gliding);
            }
        };
    }
}
