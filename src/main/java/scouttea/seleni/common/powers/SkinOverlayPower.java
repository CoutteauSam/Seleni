package scouttea.seleni.common.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

/**
 * Power which allows for rendering of additional skin elements
 */
/* Disclaimer: This power isn't implemented in the best way */
public class SkinOverlayPower extends Power {
    private final Identifier texture;

    public SkinOverlayPower(PowerType<?> type, LivingEntity entity, Identifier texture) {
        super(type, entity);
        this.texture = texture;
    }

    public Identifier getTexture() {
        return texture;
    }
}
