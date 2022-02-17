package scouttea.seleni.common.render;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;
import scouttea.seleni.common.powers.ChargedPower;

// Source: net.minecraft.client.render.entity.feature.CreeperChargeFeatureRenderer
/* Disclaimer: I don't know how to renderer code */
@Environment(EnvType.CLIENT)
public class ChargedFeatureRenderer extends OriginEnergySwirlOverlayFeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/creeper/creeper_armor.png");
    public final PlayerEntityModel<AbstractClientPlayerEntity> model;

    public ChargedFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext, EntityRendererFactory.Context ctx, boolean bl) {
        super(featureRendererContext);
        ModelPart part = PlayerEntityModel.getTexturedModelData(new Dilation(1.2f), bl).getRoot().createPart(64, 32);
        model = new PlayerEntityModel<>(part, bl);
    }

    @Override
    protected boolean shouldRender(AbstractClientPlayerEntity entity) {
        return PowerHolderComponent.hasPower(entity, ChargedPower.class);
    }

    protected float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }

    protected Identifier getEnergySwirlTexture() {
        return SKIN;
    }

    protected PlayerEntityModel<AbstractClientPlayerEntity> getEnergySwirlModel() {
        return this.model;
    }
}