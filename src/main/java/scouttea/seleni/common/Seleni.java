package scouttea.seleni.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scouttea.seleni.common.registry.ModEntityActions;
import scouttea.seleni.common.registry.ModPowers;

public class Seleni implements ModInitializer {
    public static Identifier identifier(String path) {
        return new Identifier("Seleni", path);
    }

    @Override
    public void onInitialize() {
        ModPowers.init();
        ModEntityActions.init();
    }
}