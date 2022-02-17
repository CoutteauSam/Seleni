package scouttea.seleni.common.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

import java.util.function.Predicate;

//Source (modified): https://github.com/Latokike/lato-origins/blob/1.18.x/src/main/java/latokike/latoorigins/common/power/ModifyBehaviorPower.java
/** Causes entities to lose interest in power holders based on a condition */
public class ModifyBehaviorPower extends Power {

    /** some entity (actor) and power holder (target) */
    private final Predicate<Pair<Entity, Entity>> bientity_condition;

    public ModifyBehaviorPower(PowerType<?> type, LivingEntity entity, Predicate<Pair<Entity, Entity>> bientity_condition) {
        super(type, entity);
        this.bientity_condition = bientity_condition;
    }

    public boolean apply(Entity actor, Entity target) {
        Pair<Entity, Entity> actorTargetPair = new Pair<>(actor, target);
        return actor instanceof LivingEntity
                && target instanceof LivingEntity
                && (bientity_condition == null || bientity_condition.test(actorTargetPair));
    }
}