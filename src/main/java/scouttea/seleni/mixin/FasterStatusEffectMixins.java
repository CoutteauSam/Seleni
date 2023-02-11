package scouttea.seleni.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import scouttea.seleni.common.powers.FasterPotionPower;

public class FasterStatusEffectMixins {

    @Mixin(StatusEffectInstance.class)
    public static abstract class StatusEffectInstanceMixin implements Comparable<StatusEffectInstance> {
        @Shadow
        int duration;
        @Shadow
        private StatusEffectInstance hiddenEffect;
        private LivingEntity last_user;

        public void set_last_user(LivingEntity last_user) {
            if (this.hiddenEffect != null) {
                ((StatusEffectInstanceMixin) (Object) hiddenEffect).set_last_user(last_user);
            }
            this.last_user = last_user;
        }

        @Inject(method = "update", at = @At("HEAD"))
        public void update(LivingEntity entity, Runnable overwriteCallback, CallbackInfoReturnable<Boolean> ci) {
            this.set_last_user(entity);
        }

        @Shadow
        private int updateDuration() {
            return 0;
        }

        @Inject(method = "updateDuration", at = @At("HEAD"), cancellable = true)
        private void updateDuration(CallbackInfoReturnable<Integer> cir) {
            if (this.hiddenEffect != null) {
                ((StatusEffectInstanceMixin) (Object) this.hiddenEffect).updateDuration();
            }

            int tickSpeed = PowerHolderComponent.getPowers(this.last_user, FasterPotionPower.class).stream().filter(Power::isActive).map(FasterPotionPower::getSpeed).reduce(1, Math::max);

            this.duration -= tickSpeed;
            cir.setReturnValue(this.duration);
        }
    }
}