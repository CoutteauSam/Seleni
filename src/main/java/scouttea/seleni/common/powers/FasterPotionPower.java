package scouttea.seleni.common.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

/**
 * A power which causes potions to run out quicker
 */
public class FasterPotionPower extends Power {
    private final int speed;

    public FasterPotionPower(PowerType<?> type, LivingEntity entity, int speed) {
        super(type, entity);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}