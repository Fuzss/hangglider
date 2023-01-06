package fuzs.openglider.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.world.item.Glider;
import fuzs.openglider.client.init.ModClientRegistry;
import fuzs.openglider.client.model.GliderModel;
import fuzs.openglider.client.renderer.entity.layers.GliderLayer;
import fuzs.openglider.helper.GliderCapabilityHelper;
import fuzs.openglider.helper.PlayerGlidingHelper;
import fuzs.openglider.init.ModRegistry;
import fuzs.puzzleslib.client.core.ClientModConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.Executor;

public class OpenGliderClient implements ClientModConstructor {
    public static final ResourceLocation HANG_GLIDER_LOCATION = new ResourceLocation(OpenGlider.MOD_ID, "hang_glider");
    public static final ResourceLocation REINFORCED_HANG_GLIDER_LOCATION = new ResourceLocation(OpenGlider.MOD_ID, "reinforced_hang_glider");
    public static final ResourceLocation HANG_GLIDER_IN_HAND_LOCATION = new ResourceLocation(OpenGlider.MOD_ID, "item/hang_glider_in_hand");
    public static final ResourceLocation HANG_GLIDER_GLIDING_LOCATION = new ResourceLocation(OpenGlider.MOD_ID, "item/hang_glider_gliding");

    public static BakedModel hangGliderInHandModel;
    public static BakedModel hangGliderGlidingModel;

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModClientRegistry.GLIDER, GliderModel::createLayer);
    }

    @Override
    public void onRegisterLivingEntityRenderLayers(LivingEntityRenderLayersContext context) {
        context.registerRenderLayer(EntityType.PLAYER, (RenderLayerParent<Player, ? extends EntityModel<Player>> renderLayerParent, EntityRendererProvider.Context context1) -> {
            return new GliderLayer<>(renderLayerParent, context1.getModelSet());
        });
    }

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        registerHangGliderModelProperties(context, ModRegistry.HANG_GLIDER_ITEM.get());
        registerHangGliderModelProperties(context, ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
    }

    private static void registerHangGliderModelProperties(ItemModelPropertiesContext context, Item item) {
        context.registerItem(item, new ResourceLocation(OpenGlider.MOD_ID, "deployed"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return livingEntity instanceof Player player && GliderCapabilityHelper.getIsGliderDeployed(player) && PlayerGlidingHelper.getGliderInHand(player) == itemStack ? 1.0F : 0.0F;
        });
        context.registerItem(item, new ResourceLocation(OpenGlider.MOD_ID, "broken"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return ((Glider) itemStack.getItem()).isBroken(itemStack) ? 1.0F : 0.0F;
        });
    }

    @Override
    public void onRegisterClientReloadListeners(ClientReloadListenersContext context) {
        context.registerReloadListener("glider_model", (PreparableReloadListener.PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) -> {
            return preparationBarrier.wait(Unit.INSTANCE).thenRunAsync(() -> {
                EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
                GliderModel.bakeModel(entityModels);
            }, executor2);
        });
    }

    @Override
    public void onRegisterBuiltinModelItemRenderers(BuiltinModelItemRendererContext context) {
        registerBuiltinModelHangGliderRenderer(context, ModRegistry.HANG_GLIDER_ITEM.get());
        registerBuiltinModelHangGliderRenderer(context, ModRegistry.REINFORCED_HANG_GLIDER_ITEM.get());
    }

    private static void registerBuiltinModelHangGliderRenderer(BuiltinModelItemRendererContext context, Item item) {
        context.register(item, (ItemStack stack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) -> {
            matrices.pushPose();
            matrices.scale(1.0F, -1.0F, -1.0F);
            ResourceLocation gliderTexture = ((Glider) stack.getItem()).getGliderTexture(stack);
            VertexConsumer vertexconsumer1 = ItemRenderer.getFoilBufferDirect(vertexConsumers, RenderType.armorCutoutNoCull(gliderTexture), false, stack.hasFoil());
            // always render with full brightness
            GliderModel.get().renderToBuffer(matrices, vertexconsumer1, 15728640, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            matrices.popPose();
        });
    }

    @Override
    public void onRegisterAdditionalModels(AdditionalModelsContext context) {
        context.registerAdditionalModel(HANG_GLIDER_IN_HAND_LOCATION);
        context.registerAdditionalModel(HANG_GLIDER_GLIDING_LOCATION);
    }

    @Override
    public void onRegisterModelBakingCompletedListeners(ModelBakingCompletedListenersContext context) {
        context.registerReloadListener(context1 -> {
            hangGliderInHandModel = context1.bakeModel(HANG_GLIDER_IN_HAND_LOCATION);
            hangGliderGlidingModel = context1.bakeModel(HANG_GLIDER_GLIDING_LOCATION);
        });
    }
}
