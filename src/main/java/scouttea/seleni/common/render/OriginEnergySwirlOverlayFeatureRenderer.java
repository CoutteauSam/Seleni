package scouttea.seleni.common.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

// Source: net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer
/** Disclaimer: I don't know how to renderer code */
@Environment(EnvType.CLIENT)
public abstract class OriginEnergySwirlOverlayFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public OriginEnergySwirlOverlayFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (shouldRender(entity)) {
            float f = (float) entity.age + tickDelta;
            BipedEntityModel<T> entityModel = this.getEnergySwirlModel();

            entityModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
            //this.getContextModel().copyStateTo(entityModel);
            this.getContextModel().copyBipedStateTo(entityModel);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(), this.getEnergySwirlX(f), f * 0.01F));
            entityModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5F, 0.5F, 0.5F, 1.0F);
        }
    }

    protected abstract boolean shouldRender(T entity);

    protected abstract float getEnergySwirlX(float partialAge);

    protected abstract Identifier getEnergySwirlTexture();

    protected abstract BipedEntityModel<T> getEnergySwirlModel();
}