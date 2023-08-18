package fuzs.hangglider;

import fuzs.hangglider.capability.GlidingCapability;
import fuzs.hangglider.data.ModLanguageProvider;
import fuzs.hangglider.data.ModModelProvider;
import fuzs.hangglider.data.ModRecipeProvider;
import fuzs.hangglider.data.ModSpriteSourceProvider;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.api.capability.v2.ForgeCapabilityHelper;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(HangGlider.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HangGliderForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(HangGlider.MOD_ID, HangGlider::new, ContentRegistrationFlags.COPY_TAG_RECIPES);
        registerCapabilities();
    }

    private static void registerCapabilities() {
        ForgeCapabilityHelper.setCapabilityToken(ModRegistry.GLIDING_CAPABILITY, new CapabilityToken<GlidingCapability>() {});
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        evt.getGenerator().addProvider(true, new ModModelProvider(evt, HangGlider.MOD_ID));
        evt.getGenerator().addProvider(true, new ModLanguageProvider(evt, HangGlider.MOD_ID));
        evt.getGenerator().addProvider(true, new ModRecipeProvider(evt, HangGlider.MOD_ID));
        evt.getGenerator().addProvider(true, new ModSpriteSourceProvider(evt, HangGlider.MOD_ID));
    }
}
