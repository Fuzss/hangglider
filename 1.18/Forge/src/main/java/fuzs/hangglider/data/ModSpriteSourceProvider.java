//package fuzs.hangglider.data;
//
//import fuzs.hangglider.client.handler.ElytraEquippedHandler;
//import fuzs.puzzleslib.api.data.v1.AbstractSpriteSourceProvider;
//import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
//import net.minecraft.data.PackOutput;
//import net.minecraftforge.common.data.ExistingFileHelper;
//import net.minecraftforge.common.data.SpriteSourceProvider;
//
//import java.util.Optional;
//
//public class ModSpriteSourceProvider extends AbstractSpriteSourceProvider {
//
//    public ModSpriteSourceProvider(PackOutput packOutput, ExistingFileHelper fileHelper) {
//        super(packOutput, fileHelper);
//    }
//
//    @Override
//    protected void addSources() {
//        this.atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(new SingleFile(ElytraEquippedHandler.CROSS_TEXTURE_LOCATION, Optional.empty()));
//    }
//}
