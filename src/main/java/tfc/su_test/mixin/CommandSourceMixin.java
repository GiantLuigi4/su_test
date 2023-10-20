package tfc.su_test.mixin;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.su_test.SUTests;

@Mixin(CommandSourceStack.class)
public class CommandSourceMixin {
    @Inject(at = @At("HEAD"), method = "sendFailure", cancellable = true)
    public void preSendFail(Component p_81353_, CallbackInfo ci) {
        if (!SUTests.activeTests.isEmpty())
            ci.cancel();
    }
}
