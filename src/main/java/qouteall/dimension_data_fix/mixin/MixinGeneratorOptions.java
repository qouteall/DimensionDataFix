package qouteall.dimension_data_fix.mixin;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import qouteall.dimension_data_fix.GeneratorOptionsAccessor;

@Mixin(GeneratorOptions.class)
public class MixinGeneratorOptions implements GeneratorOptionsAccessor {
    @Shadow
    @Final
    @Mutable
    private Registry<DimensionOptions> options;
    
    @Override
    public void _setDimensionRegistry(Registry<DimensionOptions> arg) {
        options = arg;
    }
}
