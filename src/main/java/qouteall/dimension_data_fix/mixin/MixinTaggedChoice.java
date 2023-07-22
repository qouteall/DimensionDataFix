package qouteall.dimension_data_fix.mixin;

import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qouteall.dimension_data_fix.ducks.TaggedChoiceAccessor;
import qouteall.dimension_data_fix.ducks.TaggedChoiceTypeAccessor;

@Mixin(value = TaggedChoice.class, remap = false)
public class MixinTaggedChoice implements TaggedChoiceAccessor {
    boolean failSoft = false;
    
    @Override
    public void setFailSoft(boolean cond) {
        failSoft = cond;
    }
    
    @Inject(
        method = "lambda$apply$0", at = @At("RETURN"), remap = false
    )
    private void onApply(Pair key, CallbackInfoReturnable<Type> cir) {
        if (failSoft) {
            Type returnValue = cir.getReturnValue();
            if (returnValue instanceof TaggedChoice.TaggedChoiceType<?> taggedChoiceType) {
                ((TaggedChoiceTypeAccessor) (Object) taggedChoiceType).setFailSoft(true);
            }
        }
    }
}
