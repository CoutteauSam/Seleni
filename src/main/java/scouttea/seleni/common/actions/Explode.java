package scouttea.seleni.common.actions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import scouttea.seleni.common.Strings;
import scouttea.seleni.common.damage_sources.OriginExplosionDamageSource;

// Source: net.minecraft.entity.mob.CreeperEntity
public class Explode {
    public static void explode(LivingEntity entity, float radius, boolean skull, boolean create_fire, World.ExplosionSourceType explosionSourceType) {
        if (entity.world.isClient()) return;
        DamageSource source = new OriginExplosionDamageSource(Strings.CREEPER_BOOM_DAMAGE_SOURCE, entity, skull);
        entity.world.createExplosion(entity, source, null, entity.getX(), entity.getY(), entity.getZ(), radius, create_fire, explosionSourceType);
    }
}
