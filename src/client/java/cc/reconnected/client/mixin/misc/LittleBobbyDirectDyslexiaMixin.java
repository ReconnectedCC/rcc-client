package cc.reconnected.client.mixin.misc;

import cc.reconnected.client.misc.FilterList;
import cc.reconnected.client.misc.ICannotTypeCorrectly;
import net.minecraft.client.gui.screen.multiplayer.DirectConnectScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DirectConnectScreen.class)
public abstract class LittleBobbyDirectDyslexiaMixin {

    @Shadow
    private TextFieldWidget addressField;

    @Shadow
    @Final
    private ServerInfo serverEntry;

    @Unique
    private void mistype() {
        if (FilterList.isFiltered(addressField.getText())) {
            var address = addressField.getText();
            var dyslexia = ICannotTypeCorrectly.dyslexia(address);
            addressField.setText(dyslexia);
            serverEntry.address = dyslexia;
        }
    }

    @Inject(method = "saveAndClose", at = @At("HEAD"))
    private void dys$saveAndClose(CallbackInfo ci) {
        mistype();
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void dys$close(CallbackInfo ci) {
        mistype();
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void dys$removed(CallbackInfo ci) {
        mistype();
    }
}
