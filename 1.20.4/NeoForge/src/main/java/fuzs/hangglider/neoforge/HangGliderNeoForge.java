package fuzs.hangglider.neoforge;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.data.ModItemTagProvider;
import fuzs.hangglider.data.ModRecipeProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(HangGlider.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HangGliderNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(HangGlider.MOD_ID, HangGlider::new);
        DataProviderHelper.registerDataProviders(HangGlider.MOD_ID,
                ModItemTagProvider::new,
                ModRecipeProvider::new
        );
    }
}
