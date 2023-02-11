package scouttea.seleni.common.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

/**
 * Simple power which allows the player to breath underwater when active
 */
public class NoAirPower extends Power {
    public NoAirPower(PowerType<Power> powerPowerType, LivingEntity livingEntity) {
        super(powerPowerType, livingEntity);
    }
}