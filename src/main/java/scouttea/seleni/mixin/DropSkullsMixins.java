package scouttea.seleni.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import scouttea.seleni.common.damage_sources.OriginExplosionDamageSource;

import java.util.HashMap;

public class DropSkullsMixins {
    @Mixin(MobEntity.class)
    public abstract static class MobEntityMixin extends LivingEntity {
        private static final HashMap<Class<? extends LivingEntity>, Item> SkullMap = new HashMap<>() {{
            put(SkeletonEntity.class, Items.SKELETON_SKULL);
            put(ZombieEntity.class, Items.ZOMBIE_HEAD);
            put(WitherSkeletonEntity.class, Items.WITHER_SKELETON_SKULL);
            put(CreeperEntity.class, Items.CREEPER_HEAD);
        }};

        protected MobEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
            super(entityType, world);
        }

        @Inject(method = "dropEquipment", at = @At("RETURN"))
        protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
            if (this.shouldDropLoot() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && source instanceof OriginExplosionDamageSource && ((OriginExplosionDamageSource) source).isCharged()) {
                Class<? extends LivingEntity> aClass = this.getClass();
                if (SkullMap.containsKey(aClass)) {
                    this.dropItem(SkullMap.get(aClass));
                }
            }
        }
    }
}