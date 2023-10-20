package tfc.su_test.engine.steps;

import net.minecraft.client.Minecraft;
import tfc.su_test.engine.Step;

public class CommandStep extends Step {
    String exec;

    public CommandStep(String exec) {
        this.exec = exec;
    }

    public boolean run() {
        // TODO: send signed
        Minecraft.getInstance().player.commandUnsigned(exec.substring(1));
        return true;
    }
}
