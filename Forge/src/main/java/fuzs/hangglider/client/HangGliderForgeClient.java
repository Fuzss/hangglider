package fuzs.hangglider.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.GlidingCameraHandler;
import fuzs.hangglider.client.handler.GlidingCrouchHandler;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = HangGlider.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HangGliderForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(HangGlider.MOD_ID, HangGliderClient::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        // TODO migrate to puzzles lib events in 1.19.4
        MinecraftForge.EVENT_BUS.addListener((final RenderPlayerEvent.Pre evt) -> {
            GlidingCrouchHandler.onRenderPlayer$Pre(evt.getEntity(), evt.getRenderer(), evt.getPartialTick(), evt.getPoseStack(), evt.getMultiBufferSource(), evt.getPackedLight()).ifPresent(unit -> evt.setCanceled(true));
        });
        MinecraftForge.EVENT_BUS.addListener((final RenderPlayerEvent.Post evt) -> {
            GlidingCrouchHandler.onRenderPlayer$Post(evt.getEntity(), evt.getRenderer(), evt.getPartialTick(), evt.getPoseStack(), evt.getMultiBufferSource(), evt.getPackedLight());
        });
        MinecraftForge.EVENT_BUS.addListener((final RenderHandEvent evt) -> {
            if (PlayerGlidingHelper.isGliding(Minecraft.getInstance().player)) {
                evt.setCanceled(true);
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final ViewportEvent.ComputeCameraAngles evt) -> {
            GlidingCameraHandler.onComputeCameraRoll(evt.getRenderer(), evt.getCamera(), (float) evt.getPartialTick()).ifPresent(evt::setRoll);
        });
    }
}
