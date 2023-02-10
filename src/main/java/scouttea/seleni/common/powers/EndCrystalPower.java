package scouttea.seleni.common.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

import java.util.function.Consumer;

/**
 * This power allows end crystals to effect the player
 */
public class EndCrystalPower extends Power {

    /**
     * Bientity action executed on the end crystal (actor) and power holder (target)
     * when the crystal is "powering" the power holder
     */
    public final Consumer<Pair<Entity, Entity>> biEntityAction;

    public EndCrystalPower(PowerType<?> type, LivingEntity entity, Consumer<Pair<Entity, Entity>> biEntityAction) {
        super(type, entity);
        this.biEntityAction = biEntityAction;
    }

    public Consumer<Pair<Entity, Entity>> getBiEntityAction() {
        return biEntityAction;
    }
}