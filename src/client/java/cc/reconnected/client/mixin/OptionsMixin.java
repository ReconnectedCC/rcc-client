package cc.reconnected.client.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

@Mixin(GameOptions.class)
public class OptionsMixin {
    @Unique
    private static final Text SECURE_CHAT_TOOLTIP = Text.of("There are no signatures to verify. RCC does not endorse chat signing!");
    @Unique
    private SimpleOption<Boolean> alternativeOption;

    /**
     * @reason Disable secure chat option, since it will not work anyways.
     * @author Aizistral (No Chat Reports)
     */

    @Inject(method = "getOnlyShowSecureChat", at = @At("RETURN"), cancellable = true)
    private void onlyShowSecureChat(CallbackInfoReturnable<SimpleOption<Boolean>> cir) {
        if (this.alternativeOption == null) {
            this.alternativeOption = new SimpleOption<>("options.onlyShowSecureChat",
                    SimpleOption.constantTooltip(SECURE_CHAT_TOOLTIP),
                    SimpleOption.BOOLEAN_TEXT_GETTER,
                    new SimpleOption.PotentialValuesBasedCallbacks<>(ImmutableList.of(Boolean.FALSE), Codec.BOOL),
                    false, value -> {});
        }

        cir.setReturnValue(this.alternativeOption);
    }

}
