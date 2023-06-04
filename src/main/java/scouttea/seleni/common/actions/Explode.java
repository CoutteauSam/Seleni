package scouttea.seleni.common.actions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import scouttea.seleni.common.damage_sources.OriginExplosionDamageSource;

// Source: net.minecraft.entity.mob.CreeperEntity

public class Explode {
    public static void explode(LivingEntity entity, float radius, boolean dropSkulls, boolean create_fire, World.ExplosionSourceType explosionSourceType) {
        if (entity.getWorld().isClient()) return;
        DamageSource source = new OriginExplosionDamageSource(entity.getWorld().getDamageSources().explosion(null), entity, dropSkulls);
        entity.getWorld().createExplosion(entity, source, null, entity.getX(), entity.getY(), entity.getZ(), radius, create_fire, explosionSourceType);
    }
}