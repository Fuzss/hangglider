package fuzs.hangglider.neoforge.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.HangGliderClient;
import fuzs.hangglider.data.client.ModLanguageProvider;
import fuzs.hangglider.data.client.ModModelProvider;
import fuzs.hangglider.neoforge.data.client.ModSpriteSourceProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = HangGlider.MOD_ID, dist = Dist.CLIENT)
public class HangGliderNeoForgeClient {

    public HangGliderNeoForgeClient() {
        ClientModConstructor.construct(HangGlider.MOD_ID, HangGliderClient::new);
        DataProviderHelper.registerDataProviders(HangGlider.MOD_ID,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModSpriteSourceProvider::new);
    }
}
