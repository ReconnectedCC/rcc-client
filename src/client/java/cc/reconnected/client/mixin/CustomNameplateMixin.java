package cc.reconnected.client.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class CustomNameplateMixin {

    @Shadow
    protected abstract MutableText addTellClickEvent(MutableText component);

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void replaceNameplateName(CallbackInfoReturnable<Text> cir) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        Team team = (Team) self.getScoreboardTeam();
        if (!team.getName().startsWith("sol_")) {
            return;
        }

        var mutableText = Team.decorateName(team, team.getDisplayName());
        var finalText = addTellClickEvent(mutableText);

        cir.setReturnValue(finalText);
    }

}
