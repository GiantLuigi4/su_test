package tfc.su_test.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.su_test.SUTests;
import tfc.su_test.engine.Test;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MCMixin {
    @Shadow
    @Nullable
    private IntegratedServer singleplayerServer;

    @Inject(at = @At("TAIL"), method = "runTick")
    public void postTick(boolean p_91384_, CallbackInfo ci) {
        if (SUTests.activeTests.isEmpty()) return;

        Test test = SUTests.activeTests.getFirst();
        if (!test.tick()) {
            SUTests.tickServer(singleplayerServer, () -> true);
            SUTests.activeTests.poll();
            if (!SUTests.testNames.isEmpty()) {
                Minecraft.getInstance().getChatListener().handleSystemMessage(
                        Component.literal("Starting " + SUTests.testNames.poll()).withStyle(ChatFormatting.GOLD),
                        false
                );
            } else {
                Minecraft.getInstance().getChatListener().handleSystemMessage(
                        Component.literal("All tests done").withStyle(ChatFormatting.GOLD),
                        false
                );
            }
        } else SUTests.tickServer(singleplayerServer, () -> true);
    }
}
