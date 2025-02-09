package fuzs.hangglider.neoforge.data.client;

import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.neoforge.api.client.data.v2.AbstractAtlasProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;

import java.util.Optional;

public class ModSpriteSourceProvider extends AbstractAtlasProvider {

    public ModSpriteSourceProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addAtlases() {
        this.atlas(TextureAtlas.LOCATION_BLOCKS)
                .addSource(new SingleFile(ElytraEquippedHandler.CROSS_TEXTURE_LOCATION, Optional.empty()));
    }
}
