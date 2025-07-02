package fuzs.hangglider.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record Gliding(boolean deployed, boolean gliding) {
    public static final Codec<Gliding> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.BOOL.fieldOf(
                    "deployed").forGetter(Gliding::deployed), Codec.BOOL.fieldOf("gliding").forGetter(Gliding::gliding))
            .apply(instance, Gliding::new));
    public static final StreamCodec<ByteBuf, Gliding> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL,
            Gliding::deployed,
            ByteBufCodecs.BOOL,
            Gliding::gliding,
            Gliding::new);
    public static final Gliding EMPTY = new Gliding(false, false);

    public Gliding withGliding(boolean gliding) {
        gliding &= this.deployed;
        if (this.gliding != gliding) {
            return new Gliding(this.deployed, gliding);
        } else {
            return this;
        }
    }

    public Gliding withDeployed(boolean deployed) {
        if (this.deployed != deployed) {
            return new Gliding(deployed, deployed && this.gliding);
        } else {
            return this;
        }
    }
}
