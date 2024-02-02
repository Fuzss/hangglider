package fuzs.hangglider.data;

import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.puzzleslib.api.data.v1.AbstractSpriteSourceProvider;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraftforge.common.data.SpriteSourceProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Optional;

public class ModSpriteSourceProvider extends AbstractSpriteSourceProvider {

    public ModSpriteSourceProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addSources() {
        this.atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(ElytraEquippedHandler.CROSS_TEXTURE_LOCATION, Optional.empty()));
    }
}
