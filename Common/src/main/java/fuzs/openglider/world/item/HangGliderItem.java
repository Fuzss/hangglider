package fuzs.openglider.world.item;

import gr8pefish.openglider.api.helper.GliderHelper;
import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.network.PacketUpdateClientTarget;
import gr8pefish.openglider.common.util.OpenGliderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.World;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class HangGliderItem extends Item implements Glider {

    //ToDo: NBT saving tags of upgrade (need IRecipe for them)

    private double horizSpeed;
    private double vertSpeed;
    private double shiftHorizSpeed;
    private double shiftVertSpeed;
    private double windMultiplier;
    private double airResistance;
    private int totalDurability;
    private ResourceLocation modelRL;

    public HangGliderItem(double horizSpeed, double vertSpeed, double shiftHorizSpeed, double shiftVertSpeed, double windMultiplier, double airResistance, int totalDurability, ResourceLocation modelRL) {
        super();
        this.horizSpeed = horizSpeed;
        this.vertSpeed = vertSpeed;
        this.shiftHorizSpeed = shiftHorizSpeed;
        this.shiftVertSpeed = shiftVertSpeed;
        this.windMultiplier = windMultiplier;
        this.airResistance = airResistance;
        this.totalDurability = totalDurability;
        this.modelRL = modelRL;

        //Add different icons for if the glider is deployed or not
        this.addPropertyOverride(new ResourceLocation("status"), new IItemPropertyGetter() {

            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return isGlidingGlider(entityIn, stack) ? 1.0F : isBroken(stack) ? 2.0F : 0.0F; //0 = undeployed, 1 = deployed, 2 = broken
            }

            private boolean isGlidingGlider(@org.jetbrains.annotations.Nullable LivingEntity entityIn, ItemStack stack){
                return entityIn != null && entityIn instanceof Player player && GliderHelper.getIsGliderDeployed((EntityPlayer)entityIn) && OpenGliderPlayerHelper.getGlider((EntityPlayer)entityIn) == stack;
            }

        });

    }

    /**
     * Handles a right click of the item attempting to deploy the hang glider.
     *
     * @param world - the world this occurs in
     * @param player - the player clicking
     * @param hand - the hand used
     *
     * @return - an ActionResult of the occurrence
     */
    @Override
    public InteractionResult<ItemStack> onItemRightClick(Level world, Player player, InteractionHand hand) {

        //ToDo: test enforce mainhand only
        ItemStack chestItem = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack itemStack = player.getHeldItem(hand);

        //if no elytra equipped
        if (!(chestItem != null && !chestItem.isEmpty() && chestItem.getItem() instanceof ItemElytra)) {

            if (this.isBroken(itemStack)) return ActionResult.newResult(EnumActionResult.PASS, itemStack); //nothing if broken
            if (!hand.equals(EnumHand.MAIN_HAND)) return ActionResult.newResult(EnumActionResult.PASS, itemStack); //return if not using main hand

            //old deployment state
            boolean isDeployed = GliderHelper.getIsGliderDeployed(player);

            //toggle state of glider deployment
            GliderHelper.setIsGliderDeployed(player, !isDeployed);

            //client only
            if (!world.isRemote) {
                //send packet to nearby players to update visually
                EntityTracker tracker = world.getMinecraftServer().getWorld(player.dimension).getEntityTracker();
                tracker.sendToTracking(player, PacketHandler.HANDLER.getPacketFrom(new PacketUpdateClientTarget(player, GliderHelper.getIsGliderDeployed(player))));
            }

        } else {
            if (world.isRemote) { //client
                player.sendMessage(new TextComponentTranslation("openglider.elytra.error"));
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestStack.getItem() instanceof ElytraItem) {
            // TODO
        } else {

        }



        ItemStack itemStack = player.getItemInHand(usedHand);
        EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemStack);
        ItemStack itemStack2 = player.getItemBySlot(equipmentSlot);
        if (itemStack2.isEmpty()) {
            player.setItemSlot(equipmentSlot, itemStack.copy());
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            itemStack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    /**
     * Handles the visual bobbing of reequipping the item.
     * Doesn't do so unless the item breaks, or it is a new slot.
     * This means that it won't bob when updating the damage value when flying.
     *
     * @param oldStack - the old stack selected
     * @param newStack - the new stack selected
     * @param slotChanged - if the slot was changed
     *
     * @return - True to cause re-equip, false otherwise
     */

    //ToDo: This bish (why does it error)
    //ToDo: Possible solution: Ignore rendering when gliding, and add in model for bars.
    /**
     Hmm, so I have an item that changes texture depending on the state the player is in. The issue is with item re-equipping animation.

     The item is a glider that turns into handlebars if the player has deployed the glider. The glider has durability, so every flight tick there is a chance for the glider to be damaged, which, by default, causes the re-equip animation. This looks odd with it bobbing up and down a lot, so I'd like to remove it. Here's what it looks like bobbing: https://streamable.com/o4sak Note that it also changes to the other texture before reverting back to the handlebars.

     This can be fixed with an override of shouldCauseReequipAnimation(), but that causes the dynamic texturing of the item to not occur correctly, so the item changes to the glider sprite (not the handlebars as it should) and stays there. Video example: https://streamable.com/z9qze

     Now, anyone have an idea of how to make it not bob up and down when flying/taking damage, but still respect the changing/dynamic item texture (glider sprite -> handlebars)?

     Here's all my code for reference: https://github.com/gr8pefish/OpenGlider
     */

//    @Override
//    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
//        if (oldStack.getItem() == newStack.getItem()) {
//            if (newStack.getItem() instanceof ItemHangGliderBasic && isBroken(newStack))
//                return true;
//            else if (GliderHelper.getIsGliderDeployed(Minecraft.getMinecraft().thePlayer)) {
//                return false;
//            }
//        }
//        return !oldStack.equals(newStack);
//    }
//        return newStack.getItem() instanceof ItemHangGliderBase;
//        if (slotChanged) return true;
//        //ToDo: Allow broken stacks to reequip, need to alter fp rotation in the json files
//        if (newStack != null && newStack.getItem() != null && newStack.getItem() instanceof ItemHangGliderBasic && isBroken(newStack))
//            return true;
//        else
//            return !(oldStack.getItem().equals(newStack.getItem())); //no more bobbing when damaged if it is the same exact item
//        return !(oldStack.getItemDamage() == newStack.getItemDamage());
//    }

    /**
     * Return whether this item is repairable in an anvil. Uses leather.
     */
    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.LEATHER) || super.isValidRepairItem(stack, repairCandidate);
    }

    //==============================================Glider========================================

    @Override
    public double getHorizontalFlightSpeed() {
        return this.horizSpeed;
    }

    @Override
    public void setHorizontalFlightSpeed(double speed) {
        this.horizSpeed = speed;
    }

    @Override
    public double getVerticalFlightSpeed() {
        return this.vertSpeed;
    }

    @Override
    public void setVerticalFlightSpeed(double speed) {
        this.vertSpeed = speed;
    }

    @Override
    public double getShiftHorizontalFlightSpeed() {
        return this.shiftHorizSpeed;
    }

    @Override
    public void setShiftHorizontalFlightSpeed(double speed) {
        this.shiftHorizSpeed = speed;
    }

    @Override
    public double getShiftVerticalFlightSpeed() {
        return this.shiftVertSpeed;
    }

    @Override
    public void setShiftVerticalFlightSpeed(double speed) {
        this.shiftVertSpeed = speed;
    }

    @Override
    public double getWindMultiplier() {
        return this.windMultiplier;
    }

    @Override
    public void setWindMultiplier(double windMultiplierToSetTo) {
        this.windMultiplier = windMultiplierToSetTo;
    }

    @Override
    public double getAirResistance() {
        return this.airResistance;
    }

    @Override
    public void setAirResistance(double airResistanceToSetTo) {
        this.airResistance = airResistanceToSetTo;
    }

    @Override
    public int getTotalDurability() {
        return this.totalDurability;
    }

    @Override
    public void setTotalDurability(int durability) {
        this.totalDurability = durability;
    }

    @Override
    public int getCurrentDurability(ItemStack stack) {
        return stack.getDamageValue();
    }

    @Override
    public void setCurrentDurability(ItemStack stack, int durability) {
        stack.setDamageValue(durability);
        if (stack.getDamageValue() < 1)
            stack.setDamageValue(1);
    }
    @Override
    public boolean isBroken(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }
}
