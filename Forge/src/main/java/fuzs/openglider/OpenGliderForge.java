package fuzs.openglider;

import fuzs.openglider.capability.GlidingPlayerCapability;
import fuzs.openglider.data.ModItemModelProvider;
import fuzs.openglider.data.ModLanguageProvider;
import fuzs.openglider.data.ModRecipeProvider;
import fuzs.openglider.init.ModRegistry;
import fuzs.puzzleslib.capability.ForgeCapabilityController;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(OpenGlider.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class OpenGliderForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(OpenGlider.MOD_ID).accept(new OpenGlider());
        registerCapabilities();
        registerHandlers();
    }

    private static void registerCapabilities() {
        ForgeCapabilityController.setCapabilityToken(ModRegistry.GLIDING_PLAYER_CAPABILITY, new CapabilityToken<GlidingPlayerCapability>() {});
    }

    private static void registerHandlers() {

    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, new ModLanguageProvider(generator, OpenGlider.MOD_ID));
        generator.addProvider(true, new ModItemModelProvider(generator, OpenGlider.MOD_ID, existingFileHelper));
    }
}
