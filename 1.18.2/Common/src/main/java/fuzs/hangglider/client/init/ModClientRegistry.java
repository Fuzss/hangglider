package fuzs.hangglider.client.init;

import fuzs.hangglider.HangGlider;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModClientRegistry {
    static final ModelLayerFactory FACTORY = ModelLayerFactory.from(HangGlider.MOD_ID);
    public static final ModelLayerLocation GLIDER = FACTORY.register("glider");
}
