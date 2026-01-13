package yud0o0.main.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static yud0o0.main.ChosablePacifismClient.CONFIG;


@Mixin(MinecraftClient.class)
public abstract class ChoosablePacifismClientMixin {
    @Inject(
            method = "doAttack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isRiding()Z"
            ),
            cancellable = true
    )
    private void dontAttackTeammates(CallbackInfoReturnable<Boolean> cir) {
        Entity targetedentity=MinecraftClient.getInstance().targetedEntity;
        if (
                CONFIG.enabled &&
                    targetedentity != null
                        && CONFIG.friends.contains(targetedentity.getName().getString())
        ) {
            cir.setReturnValue(false);
        }
    }
}
