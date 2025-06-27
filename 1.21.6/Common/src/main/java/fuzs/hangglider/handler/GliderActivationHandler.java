package fuzs.hangglider.handler;

import fuzs.hangglider.attachment.Gliding;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.init.ModRegistry;
import fuzs.hangglider.proxy.Proxy;
import fuzs.puzzleslib.api.event.v1.core.EventResultHolder;
import fuzs.puzzleslib.api.util.v1.InteractionResultHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GliderActivationHandler {

    public static EventResultHolder<InteractionResult> onUseItem(Player player, Level level, InteractionHand interactionHand) {

        ItemStack itemInHand = player.getItemInHand(interactionHand);
        if (itemInHand.has(ModRegistry.HANG_GLIDER_DATA_COMPONENT_TYPE.value())) {

            if (PlayerGlidingHelper.isWearingElytra(player)) {

                Proxy.INSTANCE.addElytraWidget();
            } else if (PlayerGlidingHelper.isValidGlider(itemInHand)) {

                if (!level.isClientSide) {

                    Gliding gliding = ModRegistry.GLIDING_ATTACHMENT_TYPE.get(player);
                    ModRegistry.GLIDING_ATTACHMENT_TYPE.set(player, gliding.withDeployed(!gliding.deployed()));
                }

                return EventResultHolder.interrupt(InteractionResultHelper.sidedSuccess(level.isClientSide));
            }

            return EventResultHolder.interrupt(InteractionResult.FAIL);
        }

        return EventResultHolder.pass();
    }
}
