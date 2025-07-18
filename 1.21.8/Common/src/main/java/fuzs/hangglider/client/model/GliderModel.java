package fuzs.hangglider.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class GliderModel<T extends LivingEntity> extends EntityModel<LivingEntityRenderState> {

    public GliderModel(ModelPart modelPart) {
        super(modelPart.getChild("glider"));
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("glider",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-32.0F, -32.0F, 4.0F, 64.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 256, 128);
    }
}
