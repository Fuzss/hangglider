package fuzs.hangglider.neoforge.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.HangGliderClient;
import fuzs.hangglider.data.client.ModLanguageProvider;
import fuzs.hangglider.data.client.ModModelProvider;
import fuzs.hangglider.neoforge.data.client.ModSpriteSourceProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = HangGlider.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HangGliderNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(HangGlider.MOD_ID, HangGliderClient::new);
        DataProviderHelper.registerDataProviders(HangGlider.MOD_ID,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModSpriteSourceProvider::new
        );
    }
}
