package scouttea.seleni.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import scouttea.seleni.common.powers.ModifyBehaviorPower;

//Source (modified) :
// https://github.com/Latokike/lato-origins/blob/1.18.x/src/main/java/latokike/latoorigins/mixin/ActiveTargetGoalMixin.java

public class MobBehaviorMixins {

    @Mixin(ActiveTargetGoal.class)
    public abstract static class ActiveTargetGoalMixin extends TrackTargetGoal {
        @Shadow
        protected LivingEntity targetEntity;

        public ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
            super(mob, checkVisibility);
        }

        @Inject(method = "start", at = @At("HEAD"), cancellable = true)
        public void start(CallbackInfo ci) {
            if (
                    PowerHolderComponent.getPowers(targetEntity, ModifyBehaviorPower.class)
                            .stream().anyMatch(modifyBehaviorPower -> modifyBehaviorPower.apply(this.mob, targetEntity))
            ) {
                this.stop();
                ci.cancel();
            }
        }
    }


}
