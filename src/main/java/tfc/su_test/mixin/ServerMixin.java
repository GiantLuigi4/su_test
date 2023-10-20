package tfc.su_test.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import tfc.su_test.SUTests;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class ServerMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;tickServer(Ljava/util/function/BooleanSupplier;)V"), method = "runServer")
    private void denyTick(MinecraftServer instance, BooleanSupplier k) {
        if (!SUTests.activeTests.isEmpty())
            return;

        SUTests.tickServer(instance, k);
    }
}
