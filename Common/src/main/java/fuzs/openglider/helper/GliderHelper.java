package fuzs.openglider.helper;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.capability.GlidingPlayerCapability;
import fuzs.openglider.init.ModRegistry;
import fuzs.openglider.api.world.item.Glider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class GliderHelper {

    /**
     * Get the gliderBasic used, contains all the stats/modifiers of it.
     * Should only be needed when {@link GlidingPlayerCapability#getIsPlayerGliding} is true.
     * See {@link Glider} for details.
     * Currently only gets the currently held item of the player when they have it deployed.
     *
     * @return - the IGlider the player is using, null if not using any.
     */
    public static ItemStack getGlider(Player player) {
        return ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(player).map(capability -> {

            if (capability.getIsGliderDeployed()) {

                //if player holding a gliderBasic
                if (player.getMainHandItem().getItem() instanceof Glider) {

                    //return that held gliderBasic
                    return player.getMainHandItem();
                }
            }
            OpenGlider.LOGGER.error("Cannot get gliderBasic used, gliderBasic capability not present.");
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    /**
     * Wrapper method for {@link GlidingPlayerCapability#getIsPlayerGliding()}, taking into account capabilities.
     *
     * @param player - the player to check
     * @return - True if gliding, False otherwise (includes no capability)
     */
    public static boolean getIsPlayerGliding(Player player) {
        return ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(player).map(GlidingPlayerCapability::getIsPlayerGliding).orElseGet(() -> {
            OpenGlider.LOGGER.error("Cannot get player gliding status, gliderBasic capability not present.");
            return false;
        });
    }

    /**
     * Wrapper method for {@link GlidingPlayerCapability#setIsPlayerGliding(boolean)}, taking into account capabilities.
     *
     * @param player - the player to check
     * @param isGliding - the gliding state to set
     */
    public static void setIsPlayerGliding(Player player, boolean isGliding) {
        Optional<GlidingPlayerCapability> cap = ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(player);
        if (cap.isPresent()) {
            cap.get().setIsPlayerGliding(isGliding);
        } else {
            OpenGlider.LOGGER.error("Cannot set player gliding, gliderBasic capability not present.");
        }
    }

    /**
     * Wrapper method for {@link GlidingPlayerCapability#getIsGliderDeployed()}, taking into account capabilities.
     *
     * @param player - the player to check
     * @return - True if deployed, False otherwise (includes no capability)
     */
    public static boolean getIsGliderDeployed(Player player) {
        Optional<GlidingPlayerCapability> cap = ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(player);
        if (cap.isPresent()) {
            return cap.get().getIsGliderDeployed();
        } else {
            OpenGlider.LOGGER.error("Cannot get gliderBasic deployment status, gliderBasic capability not present.");
        }
        return false;
    }

    /**
     * Wrapper method for {@link GlidingPlayerCapability#setIsGliderDeployed(boolean)}, taking into account capabilities.
     *
     * @param player - the player to check
     * @param isDeployed - the gliderBasic deployment state to set
     */
    public static void setIsGliderDeployed(Player player, boolean isDeployed) {
        Optional<GlidingPlayerCapability> cap = ModRegistry.GLIDING_PLAYER_CAPABILITY.maybeGet(player);
        if (cap.isPresent()) {
            cap.get().setIsGliderDeployed(isDeployed);
        } else {
            OpenGlider.LOGGER.error("Cannot set gliderBasic deployed, gliderBasic capability not present.");
        }
    }

}
