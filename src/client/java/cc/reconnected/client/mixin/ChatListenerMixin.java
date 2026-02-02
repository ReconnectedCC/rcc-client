package cc.reconnected.client.mixin;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.client.network.message.MessageTrustStatus;
import net.minecraft.client.network.message.MessageHandler;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;

@Mixin(MessageHandler.class)
public class ChatListenerMixin {
    /**
     * @reason Disable security evaluation, since it will not work anyways.
     * @author Aizistral (No Chat Reports)
     */
    @Inject(method = "getStatus", at = @At("HEAD"), cancellable = true)
    private void onEvaluateTrustLevel(SignedMessage message, Text decorated, Instant receptionTimestamp, CallbackInfoReturnable<MessageTrustStatus> cir) {
        cir.setReturnValue(MessageTrustStatus.SECURE);
    }
}
