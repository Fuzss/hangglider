package fuzs.hangglider.world.item;

import com.google.common.base.Suppliers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class RepairableItem extends Item {
    private final Supplier<Item> repairMaterial;

    public RepairableItem(Supplier<Item> repairMaterial, Properties properties) {
        super(properties);
        this.repairMaterial = Suppliers.memoize(repairMaterial::get);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(this.repairMaterial.get());
    }
}
