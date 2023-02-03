package fuzs.hangglider.client;

import fuzs.hangglider.HangGlider;
import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.hangglider.client.handler.FovModifierHandler;
import fuzs.hangglider.client.handler.GlidingCameraHandler;
import fuzs.hangglider.client.handler.GlidingCrouchHandler;
import fuzs.hangglider.helper.PlayerGlidingHelper;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = HangGlider.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HangGliderForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientFactories.INSTANCE.clientModConstructor(HangGlider.MOD_ID).accept(new OpenGliderClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final RenderPlayerEvent.Pre evt) -> {
            GlidingCrouchHandler.onRenderPlayer$Pre(evt.getEntity(), evt.getRenderer(), evt.getPartialTick(), evt.getPoseStack(), evt.getMultiBufferSource(), evt.getPackedLight()).ifPresent(unit -> evt.setCanceled(true));
        });
        MinecraftForge.EVENT_BUS.addListener((final RenderPlayerEvent.Post evt) -> {
            GlidingCrouchHandler.onRenderPlayer$Post(evt.getEntity(), evt.getRenderer(), evt.getPartialTick(), evt.getPoseStack(), evt.getMultiBufferSource(), evt.getPackedLight());
        });
        MinecraftForge.EVENT_BUS.addListener((final ComputeFovModifierEvent evt) -> {
            FovModifierHandler.onComputeFovModifier(evt.getPlayer(), evt.getFovModifier(), evt.getNewFovModifier()).ifPresent(evt::setNewFovModifier);
        });
        MinecraftForge.EVENT_BUS.addListener((final RenderHandEvent evt) -> {
            if (PlayerGlidingHelper.isGliding(Minecraft.getInstance().player)) {
                evt.setCanceled(true);
            }
        });
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.ClientTickEvent evt) -> {
            if (evt.phase == TickEvent.Phase.END) ElytraEquippedHandler.INSTANCE.onClientTick$End(Minecraft.getInstance());
        });
        MinecraftForge.EVENT_BUS.addListener((final RenderGuiEvent.Post evt) -> {
            ElytraEquippedHandler.INSTANCE.onRenderGui(evt.getPoseStack(), evt.getWindow().getGuiScaledWidth(), evt.getWindow().getGuiScaledHeight(), evt.getPartialTick());
        });
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.ClientTickEvent evt) -> {
            if (evt.phase == TickEvent.Phase.END) GlidingCameraHandler.onClientTick$End(Minecraft.getInstance());
        });
        MinecraftForge.EVENT_BUS.addListener((final ViewportEvent.ComputeCameraAngles evt) -> {
            GlidingCameraHandler.onComputeCameraRoll(evt.getRenderer(), evt.getCamera(), (float) evt.getPartialTick()).ifPresent(evt::setRoll);
        });
    }
}
