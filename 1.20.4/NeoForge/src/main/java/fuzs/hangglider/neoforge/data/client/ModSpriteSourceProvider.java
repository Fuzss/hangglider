package fuzs.hangglider.neoforge.data.client;

import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractSpriteSourceProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.ForgeDataProviderContext;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;

import java.util.Optional;

public class ModSpriteSourceProvider extends AbstractSpriteSourceProvider {

    public ModSpriteSourceProvider(ForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addSpriteSources() {
        this.atlas(BLOCKS_ATLAS)
                .addSource(new SingleFile(ElytraEquippedHandler.CROSS_TEXTURE_LOCATION, Optional.empty()));
    }
}
