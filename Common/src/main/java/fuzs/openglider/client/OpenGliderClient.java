package fuzs.openglider.client;

import fuzs.openglider.OpenGlider;
import fuzs.openglider.helper.GliderHelper;
import fuzs.openglider.helper.OpenGliderPlayerHelper;
import fuzs.openglider.init.ModRegistry;
import fuzs.openglider.api.world.item.Glider;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class OpenGliderClient implements ClientModConstructor {

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        registerHangGliderModelProperties(context, ModRegistry.HANG_GLIDER_ITEM.get());
        registerHangGliderModelProperties(context, ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
    }

    private static void registerHangGliderModelProperties(ItemModelPropertiesContext context, Item item) {
        context.registerItem(item, new ResourceLocation(OpenGlider.MOD_ID, "deployed"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return livingEntity instanceof Player player && GliderHelper.getIsGliderDeployed(player) && OpenGliderPlayerHelper.getGliderInHand(player) == itemStack ? 1.0F : 0.0F;
        });
        context.registerItem(item, new ResourceLocation(OpenGlider.MOD_ID, "broken"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return ((Glider) itemStack.getItem()).isBroken(itemStack) ? 1.0F : 0.0F;
        });
    }
}
