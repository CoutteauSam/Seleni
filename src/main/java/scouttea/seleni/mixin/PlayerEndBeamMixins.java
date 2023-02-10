package scouttea.seleni.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import scouttea.seleni.common.powers.EndCrystalPower;
import scouttea.seleni.mixin_typing.IEndCrystalEntityMixin;

import java.util.List;
import java.util.function.Consumer;

public class PlayerEndBeamMixins {
    //Sources:
    // https://github.com/Fusion-Flux/Something-Magical/blob/18d7344990f965e6f52ef71ad34fd59d593cf7f9/src/main/java/com/fusionflux/somethingmagical/mixin/PlayerEntityRendererMixin.java
    // https://github.com/Fusion-Flux/Something-Magical/search?q=PlayerEntityExtensions

    @Environment(EnvType.CLIENT)
    @Mixin(PlayerEntityRenderer.class)
    public abstract static class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
        public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
            super(ctx, model, shadowRadius);
        }

        private void render_beam(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
            List<EndCrystalPower> powers = PowerHolderComponent.getPowers(abstractClientPlayerEntity, EndCrystalPower.class);
            powers.removeIf(power -> !power.isActive());

            if (powers.isEmpty()) {
                return;
            }

            IEndCrystalEntityMixin crystalEntity = (IEndCrystalEntityMixin) abstractClientPlayerEntity;
            EndCrystalEntity connectedCrystal = crystalEntity.getEntity();

            Vec3d playerPos = abstractClientPlayerEntity.getPos();
            if (connectedCrystal != null && playerPos != null) {
                matrixStack.push();

                float height = 2.0f;
                float eye = (float) (abstractClientPlayerEntity.getEyePos().y - playerPos.y);

                float l = (float) (connectedCrystal.getX() - MathHelper.lerp(g, abstractClientPlayerEntity.prevX, abstractClientPlayerEntity.getX()));
                float m = (float) (connectedCrystal.getY() - MathHelper.lerp(g, abstractClientPlayerEntity.prevY, abstractClientPlayerEntity.getY()) + height - eye);
                float t = (float) (connectedCrystal.getZ() - MathHelper.lerp(g, abstractClientPlayerEntity.prevZ, abstractClientPlayerEntity.getZ()));

                matrixStack.translate(0, eye - height, 0);

                EnderDragonEntityRenderer.renderCrystalBeam(l, m + EndCrystalEntityRenderer.getYOffset(connectedCrystal, g), t, -g, -abstractClientPlayerEntity.age, matrixStack, vertexConsumerProvider, i);
                matrixStack.pop();
            }
        }

        @Inject(method = "render", at = @At("TAIL"))
        public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
            render_beam(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }

        /*
        @Inject(method="renderArm", at = @At("TAIL"))
        private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        }
        */
    }

    @Mixin(PlayerEntity.class)
    public abstract static class PlayerEntityMixin extends LivingEntity implements IEndCrystalEntityMixin {
        public EndCrystalEntity connectedCrystal;

        protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
            super(entityType, world);
        }

        private void tickWithEndCrystals() {
            List<EndCrystalPower> powers = PowerHolderComponent.getPowers(this, EndCrystalPower.class);
            powers.removeIf(power -> !power.isActive());

            if (powers.isEmpty()) {
                return;
            }

            //Don't use crystals if enderdragon is nearby
            if (!this.world.getNonSpectatingEntities(EnderDragonEntity.class, this.getBoundingBox().expand(128.0D)).isEmpty()) {
                return;
            }

            if (this.connectedCrystal != null) {
                if (this.connectedCrystal.isRemoved()) {
                    this.connectedCrystal = null;
                }
            }

            if (this.random.nextInt(10) == 0) {
                List<EndCrystalEntity> list = this.world.getNonSpectatingEntities(EndCrystalEntity.class, this.getBoundingBox().expand(32.0D));

                EndCrystalEntity endCrystalEntity = null;
                double d = 1.7976931348623157E308D;

                for (EndCrystalEntity endCrystalEntity2 : list) {
                    double e = endCrystalEntity2.squaredDistanceTo(this);
                    if (e < d) {
                        d = e;
                        endCrystalEntity = endCrystalEntity2;
                    }
                }
                this.connectedCrystal = endCrystalEntity;
            }

            if (this.connectedCrystal != null) {
                powers.forEach((power) -> {
                    Consumer<Pair<Entity, Entity>> action = power.getBiEntityAction();
                    if (action != null) {
                        action.accept(new Pair<>(this.connectedCrystal, this));
                    }
                });
            }
        }

        @Inject(method = "tick", at = @At("RETURN"))
        public void tick(CallbackInfo ci) {
            this.tickWithEndCrystals();
        }

        @Override
        public EndCrystalEntity getEntity() {
            return connectedCrystal;
        }
    }
}