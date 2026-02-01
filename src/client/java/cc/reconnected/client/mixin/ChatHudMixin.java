package cc.reconnected.client.mixin;

import cc.reconnected.client.Utils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    @Shadow @Final private List<ChatHudLine.Visible> visibleMessages;

    // Maps addedTime -> whether any line with that addedTime contains a mention
    @Unique
    private final Set<Integer> rcc$mentioningMessages = new HashSet<>();

    @Inject(method = "render", at = @At("HEAD"))
    private void rcc$scanMentions(CallbackInfo ci) {
        // Clear and rescan which messages contain mentions
        rcc$mentioningMessages.clear();

        // Group visible messages by addedTime and check if the combined text contains a mention
        Map<Integer, StringBuilder> messageTexts = new HashMap<>();

        // Iterate in reverse order since newer lines are added to the front
        // but we want to concatenate text in reading order
        for (int i = visibleMessages.size() - 1; i >= 0; i--) {
            ChatHudLine.Visible visible = visibleMessages.get(i);
            int addedTime = visible.addedTime();

            StringBuilder builder = messageTexts.computeIfAbsent(addedTime, k -> new StringBuilder());
            visible.content().accept((index, style, codePoint) -> {
                builder.appendCodePoint(codePoint);
                return true;
            });
        }

        // Check each complete message for mentions
        for (Map.Entry<Integer, StringBuilder> entry : messageTexts.entrySet()) {
            if (Utils.isMentioning(entry.getValue().toString())) {
                rcc$mentioningMessages.add(entry.getKey());
            }
        }
    }

    @WrapOperation(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I")
            )
    )
    public void rcc$colorMessage(DrawContext context, int x1, int y1, int x2, int y2, int color, Operation<Void> original, @Local(name = "visible") ChatHudLine.Visible visible) {
        int finalColor = color;
        if (visible != null && rcc$mentioningMessages.contains(visible.addedTime())) {
            finalColor = 0xAE7322 | color;
        }
        original.call(context, x1, y1, x2, y2, finalColor);
    }

    @Redirect(
            method = {"logChatMessage"},
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"
            )
    )
    public void cleanLogLines(Logger logger, String format, Object arg) {
        logger.info(format, arg.toString().replaceAll("[\ue002-\ue009]",""));
    }


}
