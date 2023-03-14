package scouttea.seleni.common.damage_sources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

/**
 * Custom damage source to allow detection of charged creeper boom (to enable skull drops, for example)
 */
public class OriginExplosionDamageSource extends DamageSource {

    private final boolean charged;

    public OriginExplosionDamageSource(DamageSource name, Entity source, boolean charged) {
        super(name.getTypeRegistryEntry(), source);
        this.charged = charged;
    }

    public boolean isCharged() {
        return charged;
    }
}