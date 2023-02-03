package fuzs.hangglider.client;

import com.mojang.blaze3d.platform.Window;
import fuzs.hangglider.HangGlider;
import fuzs.hangglider.api.client.event.ComputeCameraAngleEvents;
import fuzs.hangglider.api.client.event.ComputeFovModifierCallback;
import fuzs.hangglider.api.client.event.RenderPlayerEvents;
import fuzs.hangglider.client.handler.ElytraEquippedHandler;
import fuzs.hangglider.client.handler.FovModifierHandler;
import fuzs.hangglider.client.handler.GlidingCameraHandler;
import fuzs.hangglider.client.handler.GlidingCrouchHandler;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class HangGliderFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientFactories.INSTANCE.clientModConstructor(HangGlider.MOD_ID).accept(new OpenGliderClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        RenderPlayerEvents.BEFORE.register(GlidingCrouchHandler::onRenderPlayer$Pre);
        RenderPlayerEvents.AFTER.register(GlidingCrouchHandler::onRenderPlayer$Post);
        ComputeFovModifierCallback.EVENT.register(FovModifierHandler::onComputeFovModifier);
        ClientTickEvents.END_CLIENT_TICK.register(ElytraEquippedHandler.INSTANCE::onClientTick$End);
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            Window window = Minecraft.getInstance().getWindow();
            ElytraEquippedHandler.INSTANCE.onRenderGui(matrixStack, window.getGuiScaledWidth(), window.getGuiScaledHeight(), tickDelta);
        });
        ClientTickEvents.END_CLIENT_TICK.register(GlidingCameraHandler::onClientTick$End);
        ComputeCameraAngleEvents.ROLL.register(GlidingCameraHandler::onComputeCameraRoll);
    }
}
