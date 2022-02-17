package scouttea.seleni.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import scouttea.seleni.common.render.ChargedFeatureRenderer;
import scouttea.seleni.common.render.OverlayFeatureRenderer;

public class PlayerRendererFeaturesMixins {
    @Environment(EnvType.CLIENT)
    @Mixin(PlayerEntityRenderer.class)
    public abstract static class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
        public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
            super(ctx, model, shadowRadius);
        }

        @Inject(method = "<init>", at = @At("RETURN"))
        private void construct(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
            this.addFeature(new ChargedFeatureRenderer(this, ctx, slim));
            this.addFeature(new OverlayFeatureRenderer(this));
        }
    }
}
