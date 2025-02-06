package cc.reconnected.client.mixin;

import cc.reconnected.client.Utils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    // Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 0), index = 4)
    public int rcc$colorMessage(int bgColor, @Local ChatHudLine.Visible visible) {
        var builder = new StringBuilder();
        visible.content().accept((index, style, codePoint) -> {
            builder.appendCodePoint(codePoint);
            return true;
        });
        var bg = 0x0;
        if (Utils.isMentioning(builder.toString())) {
            bg = 0xAE7322;
        }

        return bg | bgColor;
    }
}
