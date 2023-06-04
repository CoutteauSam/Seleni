package scouttea.seleni.common.actions;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Collection;

// Source: net.minecraft.entity.mob.CreeperEntity

/**
 * Spawns an area effect cloud with the same effects as @param(entity)
 */
public class AreaEffectFromActiveStatusEffects {
    public static void spawnEffectsCloud(LivingEntity entity, boolean remove_own_effects, float radius, float radius_on_use) {
        Collection<StatusEffectInstance> collection = entity.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ());
            areaEffectCloudEntity.setRadius(radius);
            areaEffectCloudEntity.setRadiusOnUse(radius_on_use);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());

            for (StatusEffectInstance statusEffectInstance : collection) {
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance.getEffectType()));
            }

            entity.getWorld().spawnEntity(areaEffectCloudEntity);
            if (remove_own_effects) {
                entity.clearStatusEffects();
            }
        }
    }
}