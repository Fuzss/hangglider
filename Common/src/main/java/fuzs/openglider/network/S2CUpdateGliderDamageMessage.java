package fuzs.openglider.network;

import fuzs.openglider.helper.GliderHelper;
import fuzs.openglider.world.item.Glider;
import fuzs.puzzleslib.network.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class S2CUpdateGliderDamageMessage implements Message<S2CUpdateGliderDamageMessage> {

    @Override
    public void write(FriendlyByteBuf buf) {

    }

    @Override
    public void read(FriendlyByteBuf buf) {

    }

    @Override
    public MessageHandler<S2CUpdateGliderDamageMessage> makeHandler() {
        return new MessageHandler<S2CUpdateGliderDamageMessage>() {
            @Override
            public void handle(S2CUpdateGliderDamageMessage message, Player player, Object gameInstance) {
                ItemStack glider = OpenGliderPlayerHelper.getGlider(player);
                if (glider != null && !glider.isEmpty()) {
                    glider.damageItem(ConfigHandler.durabilityPerUse, player);
                    if (((Glider) glider.getItem()).isBroken(glider)) { //broken item
                        GliderHelper.setIsGliderDeployed(player, false);
                    }
                }
            }
        };
    }
}
