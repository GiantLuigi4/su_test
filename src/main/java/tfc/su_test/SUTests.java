package tfc.su_test;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import tfc.su_test.engine.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("sutests")
public class SUTests {
    public static final ArrayDeque<String> testNames = new ArrayDeque<>();
    public static final ArrayDeque<Test> activeTests = new ArrayDeque<>();

    private static final Object syncLoc = new Object();

    public static void tickServer(MinecraftServer server, BooleanSupplier k) {
        synchronized (syncLoc) {
            if (Thread.currentThread().equals(server.getRunningThread())) {
                server.tickServer(k);
            } else {
                try {
                    boolean[] canGo = new boolean[]{false};
                    server.submit(() -> {
                        server.tickServer(k);
                        canGo[0] = true;
                    });
                    while (!canGo[0]) {
                        Thread.sleep(0);
                    }
                    Thread.sleep(2);
                } catch (Throwable err) {
                    err.printStackTrace();
                }
            }
        }
    }

    public SUTests() {
        MinecraftForge.EVENT_BUS.addListener(SUTests::onChat);
    }

    public static void onChat(ClientChatEvent event) {
        if (event.getMessage().startsWith(".test")) {
            boolean first = testNames.isEmpty();
            String c = event.getMessage().substring(".test".length()).trim();
            if (c.isEmpty()) {
                testNames.addAll(Arrays.asList(Tests.getNames()));
                activeTests.addAll(Arrays.asList(Tests.getAll()));
            } else {
                Test t = Tests.get(c);
                if (t == null) {
                    Minecraft.getInstance().getChatListener()
                            .handleSystemMessage(Component.literal("Unknown test " + c), false);
                } else {
                    testNames.add(c);
                    activeTests.add(t);
                }
            }
            if (first && !testNames.isEmpty()) {
                Minecraft.getInstance().getChatListener().handleSystemMessage(
                        Component.literal("Starting " + SUTests.testNames.poll()).withStyle(ChatFormatting.GOLD),
                        false
                );
            }
            if (event.isCancelable())
                event.setCanceled(true);
        }
    }
}
