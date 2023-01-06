package fuzs.openglider.client.model;

import com.google.common.collect.ImmutableList;
import fuzs.openglider.client.init.ModClientRegistry;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class GliderModel<T extends LivingEntity> extends AgeableListModel<T> {
    private static final String GLIDER = "glider";

    private static GliderModel<?> instance;

    private final ModelPart glider;

    public GliderModel(EntityModelSet entityModels) {
        ModelPart modelPart = entityModels.bakeLayer(ModClientRegistry.GLIDER);
        this.glider = modelPart.getChild(GLIDER);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild(GLIDER, CubeListBuilder.create().texOffs(0, 0).addBox(-32.0F, -32.0F, 6.0F, 64.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.glider);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public static GliderModel<?> get() {
        return instance;
    }

    public static void bakeModel(EntityModelSet entityModels) {
        instance = new GliderModel<>(entityModels);
    }
}
