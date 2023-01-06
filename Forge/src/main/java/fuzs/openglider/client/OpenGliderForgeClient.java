package fuzs.openglider.client;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.client.handler.GliderRenderingHandler;
import fuzs.puzzleslib.client.core.ClientCoreServices;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = OpenGlider.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OpenGliderForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientCoreServices.FACTORIES.clientModConstructor(OpenGlider.MOD_ID).accept(new OpenGliderClient());
        registerHandlers();
    }

    private static void registerHandlers() {
//        MinecraftForge.EVENT_BUS.addListener((final RenderHandEvent evt) -> {
//            GliderRenderingHandler.onRenderHand(evt.getHand(), evt.getPoseStack(), evt.getMultiBufferSource(), evt.getPackedLight(), evt.getPartialTick(), evt.getInterpolatedPitch(), evt.getSwingProgress(), evt.getEquipProgress(), evt.getItemStack()).ifPresent(unit -> evt.setCanceled(true));
//        });
        MinecraftForge.EVENT_BUS.addListener(GliderRenderingHandler::onRenderPlayer$pre);
//        MinecraftForge.EVENT_BUS.addListener(GliderRenderingHandler::onRenderPlayer$post);
//        MinecraftForge.EVENT_BUS.addListener(GliderRenderingHandler::onRenderLevelStage);
        MinecraftForge.EVENT_BUS.addListener(GliderRenderingHandler::onMouseScrolling);
    }
}
