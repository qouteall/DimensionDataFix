package qouteall.dimension_data_fix.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.level.storage.LevelStorage;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import qouteall.dimension_data_fix.DimensionDataFix;

@Mixin(LevelStorage.class)
public class MixinLevelStorage {
    @Shadow
    @Final
    private static Logger LOGGER;
    
    @Redirect(
        method = "readGeneratorProperties",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/datafixer/DataFixTypes;update(Lcom/mojang/datafixers/DataFixer;Lcom/mojang/serialization/Dynamic;I)Lcom/mojang/serialization/Dynamic;"
        )
    )
    private static <T> Dynamic<T> redirectUpdateToCurrentVersion(
        DataFixTypes instance, DataFixer dataFixer, Dynamic<T> dynamic, int version
    ) {
        if (!DimensionDataFix.enableUpgradeFix) {
            return instance.update(dataFixer, dynamic, version);
        }
        
        NbtCompound value = (NbtCompound) dynamic.getValue();
        DynamicOps<T> ops = dynamic.getOps();
        
        LOGGER.debug("Fixing dimension data {}", value);
        
        NbtCompound dimensions = value.getCompound("dimensions");
        
        NbtCompound vanillaDimensions = new NbtCompound();
        NbtCompound nonVanillaDimensions = new NbtCompound();
        
        for (String dimensionId : dimensions.getKeys()) {
            NbtElement data = dimensions.get(dimensionId);
            if (dimensionId.startsWith("minecraft:")) {
                vanillaDimensions.put(dimensionId, data);
            }
            else {
                nonVanillaDimensions.put(dimensionId, data);
            }
        }
        
        NbtCompound newValue = value.copy();
        newValue.put("dimensions", vanillaDimensions);
        
        Dynamic<T> dynamicOfVanillaDimensions = (Dynamic<T>) new Dynamic<>(ops, (T) newValue);
        
        Dynamic<T> updated = instance.update(dataFixer, dynamicOfVanillaDimensions, version);
        
        LOGGER.debug("Updated {}", updated.getValue());
        
        NbtCompound updatedTag = (NbtCompound) updated.getValue();
        NbtCompound updatedDimensions = updatedTag.getCompound("dimensions");
        
        for (String nonVanillaDim : nonVanillaDimensions.getKeys()) {
            NbtElement data = nonVanillaDimensions.get(nonVanillaDim);
            updatedDimensions.put(nonVanillaDim, data);
        }
        
        LOGGER.debug("Fixed dimension data {}", updatedTag);
        
        return updated;
    }
}