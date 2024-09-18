package fuzs.hangglider.neoforge;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.data.ModItemTagProvider;
import fuzs.hangglider.data.ModRecipeProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(HangGlider.MOD_ID)
public class HangGliderNeoForge {

    public HangGliderNeoForge() {
        ModConstructor.construct(HangGlider.MOD_ID, HangGlider::new);
        DataProviderHelper.registerDataProviders(HangGlider.MOD_ID, ModItemTagProvider::new, ModRecipeProvider::new);
    }
}
