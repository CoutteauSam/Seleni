package scouttea.seleni.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scouttea.seleni.common.registry.ModEntityActions;
import scouttea.seleni.common.registry.ModPowers;
import scouttea.seleni.common.registry.ModScaleTypes;

public class Seleni implements ModInitializer {
    public static final String MODID = "seleni";
    public static final Logger LOGGER = LogManager.getLogger("Seleni");

    public static Identifier identifier(String path) {
        return new Identifier(MODID, path);
    }

    @Override
    public void onInitialize() {
        ModScaleTypes.init();
        ModPowers.init();
        ModEntityActions.init();

        LOGGER.info("Seleni is initialized.");
    }
}
