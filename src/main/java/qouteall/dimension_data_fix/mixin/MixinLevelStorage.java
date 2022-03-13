package qouteall.dimension_data_fix.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qouteall.dimension_data_fix.DimensionDataFix;

@Mixin(LevelStorage.class)
public class MixinLevelStorage {
    
    @Inject(
        method = "readGeneratorProperties",
        at = @At("HEAD")
    )
    private static <T> void onReadGeneratorProperties(
        Dynamic<T> nbt, DataFixer dataFixer, int version,
        CallbackInfoReturnable<Pair<GeneratorOptions, Lifecycle>> cir
    ) {
        
        NbtElement nbtTag = ((Dynamic<NbtElement>) nbt).getValue();
        
        if (nbtTag instanceof NbtCompound compoundTag) {
            NbtCompound worldGenSettings = ((NbtCompound) nbtTag).getCompound("WorldGenSettings");
            
            DimensionDataFix.purgeDimensionsFromWorldGenSettingsTag(worldGenSettings);
        }
        
        
    }
    
    
}
