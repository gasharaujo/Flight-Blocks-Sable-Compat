package dev.sablecompat.flightblocks.mixin;

import com.benbenlaw.flightblocks.block.entity.FlightBlockEntity;
import com.benbenlaw.flightblocks.event.FlightBlockEventHandler;
import dev.ryanhcode.sable.Sable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FlightBlockEventHandler.class, remap = false)
abstract class FlightBlockEventHandlerMixin {
    private static final double BLOCK_CENTER_OFFSET = 0.5D;

    @Inject(
            method = "isPlayerNearFlightBlock",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void flightblocksSableCompat$checkSublevels(
            Player player,
            ServerLevel level,
            CallbackInfoReturnable<Boolean> callback
    ) {
        for (FlightBlockEntity block : FlightBlockEntity.ACTIVE_BLOCKS) {
            if (block == null || block.isRemoved() || block.getLevel() != level) {
                continue;
            }

            // Vanilla Flight Blocks already handles ordinary world blocks. Only supplement
            // the check when the block is stored in one of Sable's internal sublevel plots.
            if (Sable.HELPER.getContaining(block) == null) {
                continue;
            }

            Vec3 blockCenter = Vec3.atCenterOf(block.getBlockPos());
            double distance = Sable.HELPER.rectilinearDistanceWithSubLevels(
                    level,
                    blockCenter,
                    player.position()
            );

            // Flight Blocks uses an AABB inflated by RANGE. Measuring from the block center
            // with a half-block allowance preserves the same effective boundary.
            if (distance <= FlightBlockEntity.RANGE + BLOCK_CENTER_OFFSET) {
                callback.setReturnValue(true);
                return;
            }
        }
    }
}
