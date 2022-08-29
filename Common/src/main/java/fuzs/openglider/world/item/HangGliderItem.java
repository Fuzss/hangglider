package fuzs.openglider.world.item;

import com.google.common.base.Suppliers;
import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.world.item.Glider;
import fuzs.openglider.api.world.item.GliderMaterial;
import fuzs.openglider.helper.GliderHelper;
import fuzs.openglider.network.S2CUpdateClientTargetMessage;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class HangGliderItem extends Item implements Glider {
    private final Supplier<GliderMaterial> material;

    public HangGliderItem(Supplier<GliderMaterial> material, Properties properties) {
        super(properties);
        this.material = Suppliers.memoize(material::get);
    }

    /**
     * Handles a right click of the item attempting to deploy the hang glider.
     *
     * @param level - the world this occurs in
     * @param player - the player clicking
     * @param usedHand - the hand used
     *
     * @return - an ActionResult of the occurrence
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestStack.getItem() instanceof ElytraItem) {
            OpenGlider.PROXY.addElytraWidget();
        } else {
            ItemStack stack = player.getItemInHand(usedHand);
            if (!this.isBroken(stack)) {

                //old deployment state
                boolean isDeployed = GliderHelper.getIsGliderDeployed(player);

                //toggle state of glider deployment
                GliderHelper.setIsGliderDeployed(player, !isDeployed);

                if (!level.isClientSide) {
                    OpenGlider.NETWORK.sendToAllTracking(new S2CUpdateClientTargetMessage(player, GliderHelper.getIsGliderDeployed(player)), player);
                }

                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
            }
        }
        return InteractionResultHolder.fail(chestStack);
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

    @Override
    public GliderMaterial getMaterial(ItemStack stack) {
        return this.material.get();
    }
}
