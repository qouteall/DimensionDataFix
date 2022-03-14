package qouteall.dimension_data_fix.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import qouteall.dimension_data_fix.FailSoftMapCodec;

import java.util.Map;

@Mixin(RegistryCodecs.class)
public class MixinRegistryCodecs {
    
//    /**
//     * @author qouteall
//     * @reason make dimension deserialization fail soft
//     * In MC 1.18.2, this is only used in {@link GeneratorOptions}
//     */
//    @Overwrite
//    private static <T> Codec<Map<RegistryKey<T>, T>> registryMap(
//        RegistryKey<? extends Registry<T>> registryRef, Codec<T> elementCodec
//    ) {
//        return new FailSoftMapCodec<>(
//            Identifier.CODEC.xmap(RegistryKey.createKeyFactory(registryRef), RegistryKey::getValue),
//            elementCodec
//        );
//    }
}
