package jss.bettermapscale.client;

import jss.bettermapscale.Bettermapscale;
import net.fabricmc.api.ClientModInitializer;

public class BettermapscaleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Bettermapscale.LOGGER.info("BetterMapScale client initialized");
    }
}