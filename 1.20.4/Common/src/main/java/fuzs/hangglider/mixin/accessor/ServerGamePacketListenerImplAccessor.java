package fuzs.hangglider.mixin.accessor;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerGamePacketListenerImpl.class)
public interface ServerGamePacketListenerImplAccessor {

    @Accessor("aboveGroundTickCount")
    void hangglider$setAboveGroundTickCount(int aboveGroundTickCount);

    @Accessor("aboveGroundVehicleTickCount")
    void hangglider$setAboveGroundVehicleTickCount(int aboveGroundVehicleTickCount);
}
