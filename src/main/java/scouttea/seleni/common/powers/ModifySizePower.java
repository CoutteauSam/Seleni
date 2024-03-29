package scouttea.seleni.common.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Source: https://github.com/MoriyaShiine/extra-origins/blob/master/src/main/java/moriyashiine/extraorigins/common/power/ModifySizePower.java

/**
 * Main power for interaction with the Pehkui mod
 */
public class ModifySizePower extends Power {
    public final float scale;
    private final Set<ScaleType> scaleTypes = new HashSet<>();

    public ModifySizePower(PowerType<?> type, LivingEntity entity, List<Identifier> identifiers, float scale) {
        super(type, entity);
        identifiers.forEach(identifier -> scaleTypes.add(ScaleRegistries.SCALE_TYPES.get(identifier)));
        this.scale = scale;
        setTicking(true);
    }

    @Override
    public void tick() {
        scaleTypes.forEach(scaleType -> {
            ScaleData data = scaleType.getScaleData(entity);
            if (isActive() && data.getScale() != scale) {
                data.setScale(scale);
            } else if (!isActive() && data.getScale() != 1) {
                data.setScale(1);
            }
        });
    }

    @Override
    public void onLost() {
        scaleTypes.forEach(scaleType -> scaleType.getScaleData(entity).setScale(1));
    }
}