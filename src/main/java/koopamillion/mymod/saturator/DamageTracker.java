package koopamillion.mymod.saturator;

import koopamillion.mymod.generators.TileGenerator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class DamageTracker {
    public static final DamageTracker instance = new DamageTracker();

    private Map<Integer, Map<BlockPos, Set<UUID>>> tracking = new HashMap<>();

    public void reset() {
        tracking.clear();
    }


    public void register(Integer dimension, BlockPos pos, UUID entity) {
        if (!tracking.containsKey(dimension)) {
            tracking.put(dimension, new HashMap<>());
        }
        Map<BlockPos, Set<UUID>> dimensionTracking = tracking.get(dimension);
        if (!dimensionTracking.containsKey(pos)) {
            dimensionTracking.put(pos, new HashSet<>());
        }
        dimensionTracking.get(pos).add(entity);
    }

    public void remove(Integer dimension, BlockPos pos) {
        if (tracking.containsKey(dimension)) {
            tracking.get(dimension).remove(pos);
        }
    }

    public void clear(Integer dimension, BlockPos pos) {
        if (tracking.containsKey(dimension)) {
            Map<BlockPos, Set<UUID>> dimensionTracking = tracking.get(dimension);
            if (dimensionTracking.containsKey(pos)) {
                dimensionTracking.get(pos).clear();
            }
        }
    }
    public void eatfood(World world, EntityLivingBase entity){
        int dimension = world.provider.getDimension();
        if (tracking.containsKey(dimension)) {
            Map<BlockPos, Set<UUID>> dimensionTracking = tracking.get(dimension);
            for (Map.Entry<BlockPos, Set<UUID>> entry : dimensionTracking.entrySet()) {
                if (entry.getValue().contains(entity.getUniqueID())) {
                    if (world.isBlockLoaded(entry.getKey())) {
                        TileEntity tileEntity = world.getTileEntity(entry.getKey());
                        if (tileEntity instanceof TileSaturator) {
                            ((TileSaturator) tileEntity).senseDamage(entity);
                        }
                    }
                }
            }
        }

    }

}
