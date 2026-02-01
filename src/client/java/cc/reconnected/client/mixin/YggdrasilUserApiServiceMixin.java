package cc.reconnected.client.mixin;

import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = YggdrasilUserApiService.class, remap = false)
public abstract class YggdrasilUserApiServiceMixin {

    // Bypassing phoning home
    @Inject(method = "fetchProperties", at = @At("HEAD"), cancellable = true)
    private void rcc$fetchProperties(CallbackInfo ci) {
        ci.cancel();
    }

    // Down with dystopian surveillance, right to self-government of Minecraft servers
    @Inject(method = "properties", at = @At("HEAD"), cancellable = true)
    private void rcc$getProperties(CallbackInfoReturnable<UserApiService.UserProperties> cir) {
        cir.setReturnValue(UserApiService.OFFLINE_PROPERTIES);
    }
}
