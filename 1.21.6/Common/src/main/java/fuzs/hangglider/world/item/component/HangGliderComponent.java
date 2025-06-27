package fuzs.hangglider.world.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record HangGliderComponent(boolean isReinforced, Optional<ResourceLocation> textureLocation) {
    public static final Codec<HangGliderComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.BOOL.fieldOf(
                            "is_reinforced").forGetter(HangGliderComponent::isReinforced),
                    ResourceLocation.CODEC.optionalFieldOf("texture_location").forGetter(HangGliderComponent::textureLocation))
            .apply(instance, HangGliderComponent::new));
    public static final StreamCodec<ByteBuf, HangGliderComponent> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL,
            HangGliderComponent::isReinforced,
            ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs::optional),
            HangGliderComponent::textureLocation,
            HangGliderComponent::new);
}
