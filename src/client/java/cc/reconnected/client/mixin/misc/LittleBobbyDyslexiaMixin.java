package cc.reconnected.client.mixin.misc;

import cc.reconnected.client.misc.FilterList;
import cc.reconnected.client.misc.ICannotTypeCorrectly;
import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AddServerScreen.class)
public abstract class LittleBobbyDyslexiaMixin {

    @Shadow
    private TextFieldWidget addressField;

    @Inject(method = "addAndClose", at = @At("HEAD"))
    private void dys$addAndClose(CallbackInfo ci) {
        if (FilterList.isFiltered(addressField.getText())) {
            var address = addressField.getText();
            var dyslexia = ICannotTypeCorrectly.dyslexia(address);
            addressField.setText(dyslexia);
        }
    }
}
