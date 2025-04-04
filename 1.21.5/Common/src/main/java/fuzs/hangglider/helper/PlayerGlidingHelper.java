package fuzs.hangglider.helper;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.init.ModRegistry;
import fuzs.hangglider.world.item.component.HangGliderComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerGlidingHelper {

    /**
     * Check if the player should be gliding. Checks if the player is alive, and not on the ground or in water.
     *
     * @param player - the player to check
     * @return - true if the conditions are met, false otherwise
     */
    public static boolean isAllowedToGlide(Player player) {
        // delta movement is not synced to remote players, so this is always false for those, but we just update their animation via mixin
        return !player.onGround() && !player.isPassenger() && !player.hasEffect(MobEffects.LEVITATION) &&
                !player.getAbilities().flying && !player.isInWater() && player.getDeltaMovement().y < 0;
    }

    public static boolean isWearingElytra(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST).has(DataComponents.GLIDER);
    }

    /**
     * Check if the itemStack is an unbroken HangGlider.
     *
     * @param itemStack - the itemstack to check
     * @return - true if the item is an unbroken glider, false otherwise
     */
    public static boolean isValidGlider(ItemStack itemStack) {
        return itemStack.has(ModRegistry.HANG_GLIDER_DATA_COMPONENT_TYPE.value()) && !itemStack.nextDamageWillBreak();
    }

    /**
     * Loop through player's inventory to get their hang glider.
     *
     * @param player - the player to search
     * @return - the first glider found (as an itemstack), null otherwise
     */
    public static ItemStack getGliderInHand(Player player) {

        if (ModRegistry.GLIDING_ATTACHMENT_TYPE.get(player).deployed()) {

            for (InteractionHand interactionHand : InteractionHand.values()) {

                ItemStack itemInHand = player.getItemInHand(interactionHand);
                if (isValidGlider(itemInHand)) {

                    return itemInHand;
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public static EquipmentSlot getGliderHoldingHand(Player player) {
        for (InteractionHand interactionHand : InteractionHand.values()) {
            ItemStack itemInHand = player.getItemInHand(interactionHand);
            if (itemInHand.has(ModRegistry.HANG_GLIDER_DATA_COMPONENT_TYPE.value())) {
                return interactionHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            }
        }

        return null;
    }

    public static ServerConfig.GliderConfig getGliderMaterialSettings(ItemStack itemStack) {
        HangGliderComponent hangGlider = itemStack.get(ModRegistry.HANG_GLIDER_DATA_COMPONENT_TYPE.value());
        if (hangGlider != null) {
            if (hangGlider.isReinforced()) {
                return HangGlider.CONFIG.get(ServerConfig.class).reinforcedHangGlider;
            } else {
                return HangGlider.CONFIG.get(ServerConfig.class).hangGlider;
            }
        } else {
            throw new IllegalArgumentException(itemStack + " is no hang glider");
        }
    }
}
