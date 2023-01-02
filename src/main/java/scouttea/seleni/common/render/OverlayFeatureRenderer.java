package scouttea.seleni.common.render;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import scouttea.seleni.common.powers.SkinOverlayPower;

/* Disclaimer: I don't know how to renderer code */
@Environment(EnvType.CLIENT)
public class OverlayFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public final PlayerEntityModel<AbstractClientPlayerEntity> model;

    public OverlayFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
        ModelPart part = PlayerEntityModel.getTexturedModelData(new Dilation(0.2f), false).getRoot().createPart(64, 64);
        model = new PlayerEntityModel<>(part, true);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        PowerHolderComponent.getPowers(entity, SkinOverlayPower.class).stream().filter(Power::isActive).forEach(skinOverlayPower -> {
            model.animateModel(entity, limbAngle, limbDistance, tickDelta);
            this.getContextModel().copyBipedStateTo(model);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(skinOverlayPower.getTexture()));
            model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5F, 0.5F, 0.5F, 1.0F);
        });
    }
}