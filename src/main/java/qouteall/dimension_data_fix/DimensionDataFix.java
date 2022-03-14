package qouteall.dimension_data_fix;

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.DataResult;
import net.fabricmc.api.ModInitializer;
import net.minecraft.nbt.NbtCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DimensionDataFix implements ModInitializer {
    @Override
    public void onInitialize() {
    
    }
    
    public static void purgeDimensionsFromWorldGenSettingsTag(NbtCompound worldGenSettings) {
        String[] vanillaDimensionIds =
            new String[]{"minecraft:overworld", "minecraft:the_nether", "minecraft:the_end"};
        
        NbtCompound dimensions = worldGenSettings.getCompound("dimensions");
        
        if (dimensions.getSize() > 3) {
            NbtCompound newDimensions = new NbtCompound();
            for (String dimId : vanillaDimensionIds) {
                if (dimensions.contains(dimId)) {
                    newDimensions.put(dimId, dimensions.getCompound(dimId));
                }
            }
            
            worldGenSettings.put("dimensions", newDimensions);
            System.out.println("Purged WorldGenSetting dimensions");
        }
    }
}
