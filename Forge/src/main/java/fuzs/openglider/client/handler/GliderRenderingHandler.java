package fuzs.openglider.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.openglider.OpenGlider;
import fuzs.openglider.api.world.item.Glider;
import fuzs.openglider.config.ClientConfig;
import fuzs.openglider.helper.GliderCapabilityHelper;
import fuzs.openglider.helper.PlayerGlidingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.Optional;

public class GliderRenderingHandler {


    //==================================================Rotating the Player to a Flying Position (Horizontal)=====================================

    private static boolean needToPop = false;

    public static void onRenderPlayer$pre(final RenderPlayerEvent.Pre evt) {
        if (GliderCapabilityHelper.getIsGliderDeployed(evt.getEntity())) { //if gliderBasic deployed
            if (!PlayerGlidingHelper.isAllowedToGlide(evt.getEntity())) return; //don't continue if player is not flying

//            evt.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(1.0F * (-90.0F - evt.getEntity().getXRot())));
//
//            Player pEntityLiving = evt.getEntity();
//            Vec3 vec3 = pEntityLiving.getViewVector(evt.getPartialTick());
//            Vec3 vec31 = pEntityLiving.getDeltaMovement();
//            double d0 = vec31.horizontalDistanceSqr();
//            double d1 = vec3.horizontalDistanceSqr();
//            if (d0 > 0.0D && d1 > 0.0D) {
//                double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
//                double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
//                evt.getPoseStack().mulPose(Vector3f.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
//            }
//
            if (true) return;

            if (Minecraft.getInstance().screen instanceof InventoryScreen) return; //don't rotate if the player rendered is in an inventory
            rotateToHorizontal(evt.getPoseStack(), evt.getPartialTick(), evt.getEntity()); //rotate player to flying position
                needToPop = true; //mark the matrix to pop
        }
    }

    /**
     * Makes the player's body rotate visually to be flat, parallel to the ground (e.g. like superman flies).
     *
     * @param player - the player to rotate
     */
    private static void rotateToHorizontal(PoseStack poseStack, float partialTicks, Player player){
        
        float interpolatedYaw = Mth.lerp(partialTicks, player.yRotO, player.getYRot());

        poseStack.pushPose();
        //6. Redo yaw rotation
        poseStack.mulPose(Vector3f.YN.rotation(interpolatedYaw));
        //5. Move back to (0, 0, 0)
        poseStack.translate(0, player.getBbHeight() / 2f, 0);
        //4. Rotate about x, so player leans over forward (z direction is forwards)
        poseStack.mulPose(Vector3f.XP.rotation(90));
        //3. So we rotate around the centre of the player instead of the bottom of the player
        poseStack.translate(0, -player.getBbHeight() / 2f, 0);
        //2. Undo yaw rotation (this will make the player face +z (south)
        poseStack.mulPose(Vector3f.YP.rotation(interpolatedYaw));

    }

    public static void onRenderPlayer$post(RenderPlayerEvent.Post evt) {
        if (needToPop) {
            needToPop = false;
            evt.getPoseStack().popPose();
        }
    }

    //=============================================================Rendering In-World for 1st Person Perspective==================================================

    /**
     * For rendering as a perspective projection in-world, as opposed to the slightly odd looking orthogonal projection above
     */
    public static void onRenderLevelStage(final RenderLevelStageEvent evt) {
        if (!OpenGlider.CONFIG.get(ClientConfig.class).firstPersonRendering) return;
        // rendering enabled and first person perspective
        if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) return;
        if (evt.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER) return;
        Player player = Minecraft.getInstance().player;
        if (GliderCapabilityHelper.getIsGliderDeployed(player)) { //if gliderBasic deployed
            if (PlayerGlidingHelper.isAllowedToGlide(player)) { //if flying
                renderGliderFirstPersonPerspective(evt.getPoseStack(), evt.getPartialTick(), player); //render hang gliderBasic above head
            }
        }
    }

    /**
     * Renders the gliderBasic above the player
     *
     */
    private static void renderGliderFirstPersonPerspective(PoseStack poseStack, float partialTicks, Player player) {

        //push matrix
        poseStack.pushPose();
        //set the rotation correctly for fpp
        setRotationFirstPersonPerspective(poseStack, partialTicks, player);

        // get texture and render the glider
        ItemStack stack = GliderCapabilityHelper.getGlider(player);
        ResourceLocation gliderTexture = ((Glider) stack.getItem()).getGliderTexture(stack);
        //set the correct lighting
//        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.eyes(gliderTexture), false, stack.hasFoil());
//        //render the gliderBasic
//        GliderModel.get().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        //pop matrix
        poseStack.popPose();
    }

    /**
     * Sets the rotation of the hang gliderBasic to work for first person rendering in-world.
     *
     * @param player - the player
     * @param partialTicks - the partial ticks
     */
    private static void setRotationFirstPersonPerspective(PoseStack poseStack, float partialTicks, Player player) {
        float interpolatedYaw = Mth.lerp(partialTicks, player.yRotO, player.getYRot());
        //rotate the gliderBasic to the same orientation as the player is facing
        poseStack.mulPose(Vector3f.YN.rotation(interpolatedYaw));
        //rotate the gliderBasic, so it is forwards facing, as it should be
        poseStack.mulPose(Vector3f.YP.rotation(180.0F));
        //move up to correct position (above player's head)
        poseStack.translate(0, OpenGlider.CONFIG.get(ClientConfig.class).firstPersonGliderHeight, 0);

        //move away if sneaking
        if (player.isShiftKeyDown())
            poseStack.translate(0, 0, -OpenGlider.CONFIG.get(ClientConfig.class).firstPersonFastGliderShift); //subtle speed effect (makes gliderBasic smaller looking)
    }


    //================================================================Miscellaneous===========================================


    /**
     * Disable the offhand rendering if the player has a gliderBasic deployed (and is holding a gliderBasic)
     *
     */
    public static Optional<Unit> onRenderHand(InteractionHand hand, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, float partialTick, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack){
        if (stack.getItem() instanceof Glider glider) {
            Player player = Minecraft.getInstance().player;
            if (GliderCapabilityHelper.getIsGliderDeployed(player)) { //if gliderBasic deployed
                if (!glider.isBroken(stack)) {
                    return Optional.of(Unit.INSTANCE);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Fix mouse wheel scroll on deployed glider to changes active item away from the glider not undeploying it.
     *
     * @param evt - mouse event, fires before the slot is changed
     */
    public static void onMouseScrolling(final InputEvent.MouseScrollingEvent evt) {

        // Mouse Wheel
        double wheelState = evt.getScrollDelta();

        // Mouse wheel scrolled
        if (wheelState != 0.0) {
            Player player = Minecraft.getInstance().player;
            // Player has a deployed glider
            if (GliderCapabilityHelper.getIsGliderDeployed(player)) {
                //Undeploy it
                GliderCapabilityHelper.setIsGliderDeployed(player, false);
            }
        }

    }


}
