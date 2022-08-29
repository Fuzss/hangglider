package fuzs.openglider.network;

import fuzs.openglider.init.ModRegistry;
import fuzs.puzzleslib.network.Message;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class S2CSyncGliderDataMessage implements Message<S2CSyncGliderDataMessage> {
    private CompoundTag tag;

    public S2CSyncGliderDataMessage() {

    }

    public S2CSyncGliderDataMessage(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeNbt(this.tag);
    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.tag = buf.readNbt();
    }

    @Override
    public MessageHandler<S2CSyncGliderDataMessage> makeHandler() {
        return new MessageHandler<>() {

            @Override
            public void handle(S2CSyncGliderDataMessage message, Player player, Object gameInstance) {
                ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(player).ifPresent(capability -> {
                    capability.read(message.tag);
                });
            }
        };
    }
}
