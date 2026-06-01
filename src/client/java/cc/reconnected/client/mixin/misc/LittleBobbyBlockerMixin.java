package cc.reconnected.client.mixin.misc;

import cc.reconnected.client.misc.FilterList;
import net.minecraft.client.network.Address;
import net.minecraft.client.network.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/client/network/BlockListChecker$1")
public abstract class LittleBobbyBlockerMixin {
    @Inject(method = "isAllowed(Lnet/minecraft/client/network/Address;)Z", at = @At("HEAD"), cancellable = true)
    private void troll$isAllowed(Address address, CallbackInfoReturnable<Boolean> cir) {
        var host = address.getHostName();
        var isFiltered = FilterList.isFiltered(host);
        if (isFiltered) {
            cir.setReturnValue(false);
        }
    } // address.getAddress();

    @Inject(method = "isAllowed(Lnet/minecraft/client/network/ServerAddress;)Z", at = @At("HEAD"), cancellable = true)
    private void troll$isAllowed(ServerAddress address, CallbackInfoReturnable<Boolean> cir) {
        var host = address.getAddress();
        var isFiltered = FilterList.isFiltered(host);
        if (isFiltered) {
            cir.setReturnValue(false);
        }
    }
}
