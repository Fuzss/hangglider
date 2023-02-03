package fuzs.hangglider.client.init;

import fuzs.hangglider.HangGlider;
import fuzs.puzzleslib.client.model.geom.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    private static final ModelLayerRegistry LAYER_REGISTRY = ModelLayerRegistry.of(HangGlider.MOD_ID);
    public static final ModelLayerLocation GLIDER = LAYER_REGISTRY.register("glider");
}
