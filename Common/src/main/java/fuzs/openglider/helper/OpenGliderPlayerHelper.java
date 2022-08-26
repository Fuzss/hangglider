package fuzs.openglider.helper;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.config.ServerConfig;
import fuzs.openglider.wind.WindHelper;
import fuzs.openglider.world.item.Glider;
import gr8pefish.openglider.api.item.IGlider;
import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.network.PacketUpdateGliderDamage;
import gr8pefish.openglider.common.wind.WindHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class OpenGliderPlayerHelper {

    /**
     * Updates the position of the player when gliding.
     * Glider is assumed to be deployed already.
     *
     * @param player - the player gliding
     */
    public static void updatePosition(Player player){
        if (shouldBeGliding(player)) {
            ItemStack glider = GliderHelper.getGlider(player);
            if (isValidGlider(glider)) {
                if (player.getDeltaMovement().y < 0) { //if falling (flying)

                    // Init variables
                    final double horizontalSpeed;
                    final double verticalSpeed;
                    Glider iGlider = (Glider) glider.getItem();

                    // Get speed depending on glider and if player is sneaking
                    if (!player.isDescending()) {
                        horizontalSpeed = iGlider.getHorizontalFlightSpeed();
                        verticalSpeed = iGlider.getVerticalFlightSpeed();
                    } else {
                        horizontalSpeed = iGlider.getShiftHorizontalFlightSpeed();
                        verticalSpeed = iGlider.getShiftVerticalFlightSpeed();
                    }

                    // Apply wind effects
                    WindHelper.applyWind(player, glider);

                    // Apply heat uplift
                    if (OpenGlider.CONFIG.get(ServerConfig.class).wind.heatUpdraft) {
                        applyHeatUplift(player, iGlider);
                    }

                    // Apply forward motion
                    Vec3 movement = player.getDeltaMovement();
                    double deltaX = Math.cos(Math.toRadians(player.getYRot() + 90)) * horizontalSpeed;
                    // TODO Wrong, need multiplication to slow down
                    double deltaZ = Math.sin(Math.toRadians(player.getYRot() + 90)) * horizontalSpeed;
                    player.setDeltaMovement(movement.x + deltaX, movement.y * verticalSpeed, movement.z + deltaZ);

                    // Apply air resistance
                    if (ConfigHandler.airResistanceEnabled) {
                        player.motionX *= iGlider.getAirResistance();
                        player.motionZ *= iGlider.getAirResistance();
                    }

                    // Stop fall damage
                    player.fallDistance = 0.0F;

                    if (player instanceof EntityPlayerSP)
                    playWindSound(player); //ToDo: sounds
                }

                //no wild arm swinging while flying
                if (player.level.isClientSide) {
                    player.limbSwing = 0;
                    player.limbSwingAmount = 0;
                }

                //damage the hang glider
                if (ConfigHandler.durabilityEnabled) { //durability should be taken away
                    if (!player.world.isRemote) { //server
                        if (player.world.rand.nextInt(ConfigHandler.durabilityTimeframe) == 0) { //damage about once per x ticks
                            PacketHandler.HANDLER.sendTo(new PacketUpdateGliderDamage(), (EntityPlayerMP) player); //send to client
                            glider.damageItem(ConfigHandler.durabilityPerUse, player);
                            if (((IGlider)(glider.getItem())).isBroken(glider)) { //broken item
                                GliderHelper.setIsGliderDeployed(player, false);
                            }
                        }
                    }
                }

                //SetPositionAndUpdate on server only

            } else { //Invalid item (likely changed selected item slot, update)
                GliderHelper.setIsGliderDeployed(player, false);
            }
        }

    }

    private static void applyHeatUplift(EntityPlayer player, IGlider glider) {

        BlockPos pos = player.getPosition();
        World worldIn = player.getEntityWorld();

        int maxSearchDown = 5;
        int maxSquared = (maxSearchDown-1) * (maxSearchDown-1);

        int i = 0;
        while (i <= maxSearchDown) {
            BlockPos scanpos = pos.down(i);
            Block scanned = worldIn.getBlockState(scanpos).getBlock();
            if (scanned.equals(Blocks.FIRE) || scanned.equals(Blocks.LAVA) || scanned.equals(Blocks.FLOWING_LAVA)) { //ToDo: configurable

//                get closeness to heat as quadratic (squared)
                double closeness = (maxSearchDown - i) * (maxSearchDown - i);

                //set amount up
                double configMovement = 12.2;
                double upUnnormalized = configMovement * closeness;

//                Logger.info("UN-NORMALIZED: "+upUnnormalized);

                //normalize
                double upNormalized = 1 + (upUnnormalized/(configMovement * maxSquared));

//                Logger.info("NORMALIZED: "+upNormalized);

                //scale amount to player's current motion
                double motion = player.motionY;
                double scaled = motion - (motion * (upNormalized * upNormalized));
//                Logger.info("SCALED: "+scaled);

                //apply final
//                Logger.info("BEFORE: "+player.motionY);
                player.motionY += scaled;
//                Logger.info("AFTER: "+player.motionY);


//                double boostAmt = 1 + (0.5 * (maxSearchDown - i));
//                double calculated = (player.motionY - (player.motionY * boostAmt));
//                Logger.info(calculated);
//                Logger.info("BEFORE: "+player.motionY);
//                player.motionY += calculated;
//                Logger.info("AFTER: "+player.motionY);


//                Vec3d vec3d = player.getLookVec();
//                double d0 = 1.5D;
//                double d1 = 0.1D;
////                player.motionX += vec3d.x * d1 + (vec3d.x * d0 - player.motionX) * 0.2D;
////                player.motionZ += vec3d.z * d1 + (vec3d.z * d0 - player.motionZ) * 0.2D;
//                double up_boost;
//                if (i > 0) {
//                    up_boost = -0.07 * i + 0.6;
//                } else {
//                    up_boost = 0.07;
//                }
//                if (up_boost > 0) {
//                    player.addVelocity(0, up_boost, 0);
//
//                    if (ConfigHandler.airResistanceEnabled) {
//                        player.motionX *= glider.getAirResistance();
//                        player.motionZ *= glider.getAirResistance();
//                    }
//                }

                break;
            } else if (!scanned.equals(Blocks.AIR)) {
                break;
            }
            i++;
        }
    }

    /**
     * Check if the player should be gliding.
     * Checks if the player is alive, and not on the ground or in water.
     *
     * @param player - the player to check
     * @return - true if the conditions are met, false otherwise
     */
    public static boolean shouldBeGliding(Player player){
        return player.isAlive() && !player.isOnGround() && !player.isInWater();
    }

    /**
     * Check if the itemStack is an unbroken HangGlider.
     *
     * @param stack - the itemstack to check
     * @return - true if the item is an unbroken glider, false otherwise
     */
    public static boolean isValidGlider(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            if (stack.getItem() instanceof Glider && (!((Glider) (stack.getItem())).isBroken(stack))) { //hang glider, not broken
                return true;
            }
        }
        return false;
    }

    /**
     * Loop through player's inventory to get their hang glider.
     *
     * @param player - the player to search
     * @return - the first glider found (as an itemstack), null otherwise
     */
    public static ItemStack getGlider(Player player) {
        if (player.getMainHandItem().getItem() instanceof Glider) {
            return player.getMainHandItem();
        } else if (player.getOffhandItem().getItem() instanceof Glider) {
            return player.getOffhandItem();
        }
        return ItemStack.EMPTY;


//        if (ConfigHandler.holdingGliderEnforced)
//              return player.getHeldItemMainhand();
//        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof ItemHangglider) {
//            return player.getHeldItemOffhand();
//        }
//        for (ItemStack stack : player.inventory.mainInventory) {
//            if (stack != null) {
//                if (stack.getItem() instanceof ItemHangglider) {
//                    return stack;
//                }
//            }
//        }
//        return null;
    }

}
