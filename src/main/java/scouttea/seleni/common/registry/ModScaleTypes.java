package scouttea.seleni.common.registry;

import net.minecraft.util.Identifier;
import scouttea.seleni.common.Seleni;
import virtuoel.pehkui.api.*;

import java.util.Map;

public class ModScaleTypes {
    public static final ScaleModifier MODIFY_SIZE_MODIFIER = register(ScaleRegistries.SCALE_MODIFIERS, new TypedScaleModifier(() -> ModScaleTypes.MODIFY_SIZE_TYPE));
    public static final ScaleType MODIFY_SIZE_TYPE = register(ScaleRegistries.SCALE_TYPES, ScaleType.Builder.create().addDependentModifier(MODIFY_SIZE_MODIFIER).affectsDimensions().build());
    private static final ScaleType[] MODIFY_SIZE_TYPES = {ScaleTypes.WIDTH, ScaleTypes.HEIGHT, ScaleTypes.VISIBILITY};

    private static <T> T register(Map<Identifier, T> registry, T entry) {
        return ScaleRegistries.register(registry, new Identifier(Seleni.MODID, "modify_size"), entry);
    }

    public static void init() {
        for (ScaleType type : MODIFY_SIZE_TYPES) {
            type.getDefaultBaseValueModifiers().add(MODIFY_SIZE_MODIFIER);
        }
    }
}