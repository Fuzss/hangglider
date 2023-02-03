package fuzs.hangglider;

import fuzs.hangglider.capability.GlidingCapability;
import fuzs.hangglider.data.ModItemModelProvider;
import fuzs.hangglider.data.ModLanguageProvider;
import fuzs.hangglider.data.ModRecipeProvider;
import fuzs.hangglider.handler.PlayerGlidingHandler;
import fuzs.hangglider.init.ModRegistry;
import fuzs.puzzleslib.capability.ForgeCapabilityController;
import fuzs.puzzleslib.core.CommonFactories;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(HangGlider.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HangGliderForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CommonFactories.INSTANCE.modConstructor(HangGlider.MOD_ID).accept(new HangGlider());
        registerCapabilities();
        registerHandlers();
    }

    private static void registerCapabilities() {
        ForgeCapabilityController.setCapabilityToken(ModRegistry.GLIDING_CAPABILITY, new CapabilityToken<GlidingCapability>() {});
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.PlayerTickEvent evt) -> {
            if (evt.phase == TickEvent.Phase.END) PlayerGlidingHandler.onPlayerTick$End(evt.player);
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, new ModLanguageProvider(generator, HangGlider.MOD_ID));
        generator.addProvider(true, new ModItemModelProvider(generator, HangGlider.MOD_ID, existingFileHelper));
    }
}
