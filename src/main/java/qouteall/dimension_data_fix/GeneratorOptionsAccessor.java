package qouteall.dimension_data_fix;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

public interface GeneratorOptionsAccessor {
    // cannot be an accessor because it's final
    void _setDimensionRegistry(Registry<DimensionOptions> arg);
}
