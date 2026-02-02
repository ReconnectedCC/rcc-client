package cc.reconnected.client.mixin;

import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public class ToastComponentMixin {

    /**
     * @reason Disable unsecure server warning toast, since RCC clients will mostly connect to unsecure servers.
     * @author Aizistral (No Chat Reports)
     */

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void onAddToast(Toast toast, CallbackInfo info) {
            if (toast instanceof SystemToast sys && sys.getType() == SystemToast.Type.UNSECURE_SERVER_WARNING) {
                info.cancel();
            }
    }

}
