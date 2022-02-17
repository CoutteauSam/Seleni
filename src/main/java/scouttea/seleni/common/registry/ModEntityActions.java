package scouttea.seleni.common.registry;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.explosion.Explosion;
import scouttea.seleni.common.Seleni;
import scouttea.seleni.common.actions.AreaEffectFromActiveStatusEffects;
import scouttea.seleni.common.actions.Explode;


public class ModEntityActions {

    public static final ActionFactory<Entity> CREEPER_BOOM = new ActionFactory<>(
            Seleni.identifier("creeper_boom"),
            new SerializableData()
                    .add("radius", SerializableDataTypes.FLOAT, 3.0F)
                    .add("drop_skulls", SerializableDataTypes.BOOLEAN, false)
                    .add("create_fire", SerializableDataTypes.BOOLEAN, false)
                    .add("destruction_type", SerializableDataType.enumValue(Explosion.DestructionType.class), Explosion.DestructionType.NONE),
            (data, entity) -> Explode.explode(
                    (LivingEntity) entity,
                    data.getFloat("radius"),
                    data.getBoolean("drop_skulls"),
                    data.getBoolean("create_fire"),
                    data.get("destruction_type"))
    );

    public static final ActionFactory<Entity> AREA_EFFECT_FROM_ACTIVE_STATUS_EFFECTS = new ActionFactory<>(
            Seleni.identifier("area_effect_from_active_status_effects"),
            new SerializableData()
                    .add("remove_own_effect", SerializableDataTypes.BOOLEAN, false)
                    .add("radius", SerializableDataTypes.FLOAT, 2.5f)
                    .add("radius_on_use", SerializableDataTypes.FLOAT, -0.5f),
            (data, entity) -> AreaEffectFromActiveStatusEffects.spawnEffectsCloud(
                    (LivingEntity) entity,
                    data.getBoolean("remove_own_effect"),
                    data.getFloat("radius"),
                    data.getFloat("radius_on_use")
            )
    );


    public static void init() {
        Registry.register(ApoliRegistries.ENTITY_ACTION, CREEPER_BOOM.getSerializerId(), CREEPER_BOOM);
        Registry.register(ApoliRegistries.ENTITY_ACTION, AREA_EFFECT_FROM_ACTIVE_STATUS_EFFECTS.getSerializerId(), AREA_EFFECT_FROM_ACTIVE_STATUS_EFFECTS);
    }
}
