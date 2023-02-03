package fuzs.hangglider.world.item;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.config.ServerConfig;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class GliderItem extends Item {
    private final Type type;

    public GliderItem(Properties properties, Type type) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem) {

            HangGlider.PROXY.addElytraWidget();
        } else if (PlayerGlidingHelper.isValidGlider(stack)) {

            PlayerGlidingHelper.setGliderDeployed(player, !PlayerGlidingHelper.isGliderDeployed(player));

            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        return new InteractionResultHolder<>(InteractionResult.CONSUME_PARTIAL, stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(this.type.repairMaterial);
    }

    public ServerConfig.GliderConfig getGliderMaterialSettings() {
        return this.type.materialSettings.get();
    }

    public enum Type {
        BASIC(() -> HangGlider.CONFIG.get(ServerConfig.class).hangGlider, Items.LEATHER),
        REINFORCED(() -> HangGlider.CONFIG.get(ServerConfig.class).reinforcedHangGlider, Items.PHANTOM_MEMBRANE);

        private final Supplier<ServerConfig.GliderConfig> materialSettings;
        private final Item repairMaterial;

        Type(Supplier<ServerConfig.GliderConfig> materialSettings, Item repairMaterial) {
            this.materialSettings = materialSettings;
            this.repairMaterial = repairMaterial;
        }
    }
}
