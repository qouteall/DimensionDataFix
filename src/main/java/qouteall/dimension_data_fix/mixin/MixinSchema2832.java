package qouteall.dimension_data_fix.mixin;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.minecraft.datafixer.schema.Schema2832;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import qouteall.dimension_data_fix.ducks.TaggedChoiceAccessor;
import qouteall.dimension_data_fix.ducks.TaggedChoiceTypeAccessor;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(Schema2832.class)
public class MixinSchema2832 {
    @Redirect(
        method = {
            "method_38837", "method_38838"
        },
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/datafixers/DSL;taggedChoiceLazy(Ljava/lang/String;Lcom/mojang/datafixers/types/Type;Ljava/util/Map;)Lcom/mojang/datafixers/types/templates/TaggedChoice;"
        )
    )
    private static <K> TaggedChoice<K> redirectTaggedChoiceLazy(
        String name, Type<K> keyType, Map<K, Supplier<TypeTemplate>> templates
    ) {
        TaggedChoice<K> result = DSL.taggedChoiceLazy(name, keyType, templates);
        ((TaggedChoiceAccessor) (Object) result).setFailSoft(true);
        return result;
    }
}
