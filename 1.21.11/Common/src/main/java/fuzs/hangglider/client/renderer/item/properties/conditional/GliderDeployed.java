package fuzs.hangglider.client.renderer.item.properties.conditional;

import com.mojang.serialization.MapCodec;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record GliderDeployed() implements ConditionalItemModelProperty {
    public static final MapCodec<GliderDeployed> MAP_CODEC = MapCodec.unit(new GliderDeployed());

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int seed, ItemDisplayContext itemDisplayContext) {
        return livingEntity instanceof Player player && PlayerGlidingHelper.getGliderInHand(player) == itemStack;
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
