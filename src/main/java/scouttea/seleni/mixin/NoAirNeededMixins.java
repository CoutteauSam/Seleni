package scouttea.seleni.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import scouttea.seleni.common.powers.NoAirPower;

import static io.github.apace100.apoli.component.PowerHolderComponent.hasPower;

public class NoAirNeededMixins {

    //Source: https://github.com/apace100/origins-fabric/blob/1.17/src/main/java/io/github/apace100/origins/mixin/WaterBreathingMixin.java
    @Mixin(LivingEntity.class)
    public abstract static class LivingEntityMixin extends Entity {

        public LivingEntityMixin(EntityType<?> type, World world) {
            super(type, world);
        }

        @Inject(method = "canBreatheInWater", at = @At("HEAD"), cancellable = true)
        public void doWaterBreathing(CallbackInfoReturnable<Boolean> info) {
            if(hasPower(this, NoAirPower.class)){
                info.setReturnValue(true);
            }
        }
    }
}