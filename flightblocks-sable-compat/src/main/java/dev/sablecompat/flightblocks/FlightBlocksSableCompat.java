package dev.sablecompat.flightblocks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(FlightBlocksSableCompat.MOD_ID)
public final class FlightBlocksSableCompat {
    public static final String MOD_ID = "flightblocks_sable_compat";

    public FlightBlocksSableCompat(IEventBus modEventBus, ModContainer modContainer) {
        // All compatibility behavior is applied by the mixin declared in the mod metadata.
    }
}
