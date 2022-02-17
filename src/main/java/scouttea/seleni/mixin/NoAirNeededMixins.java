package scouttea.seleni.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import scouttea.seleni.common.registry.ModPowers;

public class NoAirNeededMixins {

    //Source: https://github.com/apace100/origins-fabric/blob/1.17/src/main/java/io/github/apace100/origins/mixin/WaterBreathingMixin.java
    @Mixin(LivingEntity.class)
    public static abstract class LivingEntityMixin extends Entity {

        public LivingEntityMixin(EntityType<?> type, World world) {
            super(type, world);
        }

        @Inject(at = @At("HEAD"), method = "canBreatheInWater", cancellable = true)
        public void doWaterBreathing(CallbackInfoReturnable<Boolean> info) {
            if (ModPowers.NO_AIR.isActive(this)) {
                info.setReturnValue(true);
            }
        }
    }

}
