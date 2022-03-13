package qouteall.dimension_data_fix.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.dimension_data_fix.DimensionDataFix;

@Mixin(LevelProperties.class)
public class MixinLevelProperties {
    @Shadow
    @Final
    private GeneratorOptions generatorOptions;
    
    @Inject(method = "updateProperties", at = @At("RETURN"))
    private void onSetTagData(
        DynamicRegistryManager registryManager, NbtCompound levelNbt,
        NbtCompound playerNbt, CallbackInfo ci
    ) {
        NbtCompound worldGenSettings = levelNbt.getCompound("WorldGenSettings");
        DimensionDataFix.purgeDimensionsFromWorldGenSettingsTag(worldGenSettings);
    }
}
