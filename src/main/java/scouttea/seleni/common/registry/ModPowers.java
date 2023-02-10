package scouttea.seleni.common.registry;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.registry.Registry;
import scouttea.seleni.common.Seleni;
import scouttea.seleni.common.powers.*;
import virtuoel.pehkui.api.ScaleRegistries;

import java.util.Collections;

public class ModPowers {

    //TODO: Proper power
    public static final PowerType<?> NO_AIR = new PowerTypeReference<>(Seleni.identifier("no_air"));

    public static final PowerFactory<Power> MODIFY_SIZE = new PowerFactory<>(Seleni.identifier("modify_size"), new SerializableData()
            .add("scale_types", SerializableDataTypes.IDENTIFIERS, Collections.singletonList(ScaleRegistries.getId(ScaleRegistries.SCALE_TYPES, ModScaleTypes.MODIFY_SIZE_TYPE)))
            .add("scale", SerializableDataTypes.FLOAT),  data -> (type, entity) -> new ModifySizePower(type, entity, data.get("scale_types"), data.getFloat("scale"))).allowCondition();

    public static final PowerFactory<Power> CHARGED = new PowerFactory<>(Seleni.identifier("charged"), new SerializableData(), data -> ChargedPower::new).allowCondition();

    public static final PowerFactory<Power> MODIFY_BEHAVIOR = new PowerFactory<>(Seleni.identifier("modify_behavior"), new SerializableData()
            .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null), data -> (type, entity) -> new ModifyBehaviorPower(type, entity, data.get("bientity_condition"))).allowCondition();

    public static final PowerFactory<Power> SKIN_OVERLAY_POWER = new PowerFactory<>(Seleni.identifier("skin_overlay"), new SerializableData()
            .add("texture", SerializableDataTypes.IDENTIFIER), data -> (type, entity) -> new SkinOverlayPower(type, entity, data.getId("texture"))).allowCondition();

    public static final PowerFactory<Power> END_CRYSTAL_POWER = new PowerFactory<>(Seleni.identifier("end_crystal"), new SerializableData()
            .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION, null), data -> (type, entity) -> new EndCrystalPower(type, entity, data.get("bientity_action"))).allowCondition();

    public static final PowerFactory<Power> FASTER_POTION_POWER = new PowerFactory<>(Seleni.identifier("faster_potion"), new SerializableData()
            .add("speed", SerializableDataTypes.INT, 1), data -> (type, entity) -> new FasterPotionPower(type, entity, data.getInt("speed"))).allowCondition();

    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, MODIFY_SIZE.getSerializerId(), MODIFY_SIZE);
        Registry.register(ApoliRegistries.POWER_FACTORY, CHARGED.getSerializerId(), CHARGED);
        Registry.register(ApoliRegistries.POWER_FACTORY, MODIFY_BEHAVIOR.getSerializerId(), MODIFY_BEHAVIOR);
        Registry.register(ApoliRegistries.POWER_FACTORY, SKIN_OVERLAY_POWER.getSerializerId(), SKIN_OVERLAY_POWER);
        Registry.register(ApoliRegistries.POWER_FACTORY, END_CRYSTAL_POWER.getSerializerId(), END_CRYSTAL_POWER);
        Registry.register(ApoliRegistries.POWER_FACTORY, FASTER_POTION_POWER.getSerializerId(), FASTER_POTION_POWER);
    }
}
