package scouttea.seleni.common.damage_sources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

/**
 * Custom damage source
 * to allow detection of charged creeper boom
 * to enable skull drops for example
 */
public class OriginExplosionDamageSource extends EntityDamageSource {

    private boolean charged;

    public OriginExplosionDamageSource(String name, Entity source, boolean charged) {
        super(name, source);
        this.setExplosive();
        this.charged = charged;
    }

    public boolean isCharged() {
        return charged;
    }

    public void setCharged(boolean charged) {
        this.charged = charged;
    }
}
