package qouteall.dimension_data_fix.mixin;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qouteall.dimension_data_fix.ducks.TaggedChoiceTypeAccessor;

@Mixin(value = TaggedChoice.TaggedChoiceType.class, remap = false)
public class MixinTaggedChoiceType<K> implements TaggedChoiceTypeAccessor {
    private static final Logger LOGGER = LoggerFactory.getLogger("TaggedChoiceType_DimDataFix");
    
    @Shadow(remap = false) @Final protected Object2ObjectMap<K, Type<?>> types;
    
//    private Throwable test;
    
    private boolean failSoft;
    
//    @Inject(
//        method = "<init>", at = @At("RETURN"), remap = false
//    )
//    void onInitEnd(String name, Type keyType, Object2ObjectMap types, CallbackInfo ci) {
//        test = new Throwable();
//    }
    
    @Inject(
        method = "getCodec", at = @At("HEAD"), cancellable = true, remap = false
    )
    private void onGetCodec(K k, CallbackInfoReturnable<DataResult<? extends Codec<?>>> cir) {
        if (failSoft) {
            if (!types.containsKey(k)) {
                LOGGER.warn("Not recognizing key {}. Using pass-through codec. {}", k, this);
                cir.setReturnValue(DataResult.success(Codec.PASSTHROUGH));
            }
        }
    }
    
    @Override
    public void setFailSoft(boolean cond) {
        failSoft = cond;
    }
}
