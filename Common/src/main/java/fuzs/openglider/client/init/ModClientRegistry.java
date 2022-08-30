package fuzs.openglider.client.init;

import fuzs.openglider.OpenGlider;
import fuzs.puzzleslib.client.model.geom.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    private static final ModelLayerRegistry LAYER_REGISTRY = ModelLayerRegistry.of(OpenGlider.MOD_ID);
    public static final ModelLayerLocation GLIDER = LAYER_REGISTRY.register("glider");
}
