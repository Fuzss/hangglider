package fuzs.hangglider.world.item;

import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.hangglider.proxy.Proxy;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GliderItem extends Item {
    private final GliderType type;

    public GliderItem(Properties properties, GliderType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem) {

            Proxy.INSTANCE.addElytraWidget();
        } else if (PlayerGlidingHelper.isValidGlider(stack)) {

            PlayerGlidingHelper.setGliderDeployed(player, !PlayerGlidingHelper.isGliderDeployed(player));

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(this.type.repairMaterial);
    }

    public ServerConfig.GliderConfig getGliderMaterialSettings() {
        return this.type.materialSettings.get();
    }
}
