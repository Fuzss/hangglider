package fuzs.hangglider.mixin.client.accessor;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Camera.class)
public interface CameraAccessor {

    @Accessor("xRot")
    void hangglider$setXRot(float xRot);

    @Accessor("yRot")
    void hangglider$setYRot(float yRot);
}
