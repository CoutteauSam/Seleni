package scouttea.seleni.common.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

/** Simple power which causes the player to appear charged (like a creeper) when active */
public class ChargedPower extends Power {
    public ChargedPower(PowerType<Power> powerPowerType, LivingEntity livingEntity) {
        super(powerPowerType, livingEntity);
    }
}