package jss.bettermapscale;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bettermapscale implements ModInitializer {

    public static final String MOD_ID = "bettermapscale";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("BetterMapScale initialized");
    }
}