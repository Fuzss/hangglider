package fuzs.hangglider.helper;

import fuzs.hangglider.capability.GlidingCapability;
import fuzs.hangglider.init.ModRegistry;
import fuzs.hangglider.world.item.GliderItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.stream.Stream;

public class PlayerGlidingHelper {

    /**
     * Check if the player should be gliding.
     * Checks if the player is alive, and not on the ground or in water.
     *
     * @param player - the player to check
     * @return - true if the conditions are met, false otherwise
     */
    public static boolean isAllowedToGlide(Player player) {
        // delta movement is not synced to remote players, so this is always false for those, but we just update their animation via mixin
        return !player.onGround() && !player.isPassenger() && !player.hasEffect(MobEffects.LEVITATION) && !player.getAbilities().flying && !player.isInWater() && player.getDeltaMovement().y < 0;
    }

    /**
     * Check if the itemStack is an unbroken HangGlider.
     *
     * @param stack - the itemstack to check
     * @return - true if the item is an unbroken glider, false otherwise
     */
    public static boolean isValidGlider(ItemStack stack) {
        return stack.getItem() instanceof GliderItem && ElytraItem.isFlyEnabled(stack);
    }

    /**
     * Loop through player's inventory to get their hang glider.
     *
     * @param player - the player to search
     * @return - the first glider found (as an itemstack), null otherwise
     */
    public static ItemStack getGliderInHand(Player player) {
        return getGliderHoldingHand(player).map(player::getItemBySlot).orElse(ItemStack.EMPTY);
    }

    public static Optional<EquipmentSlot> getGliderHoldingHand(Player player) {
        return Stream.of(EquipmentSlot.values()).filter(slot -> slot.getType() == EquipmentSlot.Type.HAND).filter(slot -> player.getItemBySlot(slot).getItem() instanceof GliderItem).findAny();
    }

    public static boolean isGliderDeployed(Player player) {
        return ModRegistry.GLIDING_CAPABILITY.maybeGet(player).map(GlidingCapability::isGliderDeployed).orElse(false);
    }

    public static void setGliderDeployed(Player player, boolean gliderDeployed) {
        if (player.level().isClientSide) return;
        ModRegistry.GLIDING_CAPABILITY.maybeGet(player).ifPresent(capability -> {
            boolean wasGliderDeployed = capability.isGliderDeployed();
            capability.setGliderDeployed(gliderDeployed);
            if (wasGliderDeployed != capability.isGliderDeployed() && player instanceof ServerPlayer serverPlayer) {
                ModRegistry.GLIDING_CAPABILITY.syncToRemote(serverPlayer);
            }
        });
    }

    public static boolean isGliding(Player player) {
        return ModRegistry.GLIDING_CAPABILITY.maybeGet(player).map(GlidingCapability::isGliding).orElse(false);
    }

    public static void setGliding(Player player, boolean gliding) {
        if (player.level().isClientSide) return;
        ModRegistry.GLIDING_CAPABILITY.maybeGet(player).ifPresent(capability -> {
            boolean wasGliding = capability.isGliding();
            capability.setGliding(gliding);
            if (wasGliding != capability.isGliding() && player instanceof ServerPlayer serverPlayer) {
                ModRegistry.GLIDING_CAPABILITY.syncToRemote(serverPlayer);
            }
        });
    }
}
