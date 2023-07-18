package qouteall.dimension_data_fix.mixin;

import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelStorage.Session.class)
public interface LevelStorageSessionAccessor {
    @Accessor("directory")
    LevelStorage.LevelSave ddf_getDirectory();
}
