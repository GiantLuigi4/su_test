package tfc.su_test.engine.steps;

import net.minecraft.client.Minecraft;
import tfc.su_test.engine.Step;

public class RightClickStep extends Step {
    @Override
    public boolean run() {
        Minecraft.getInstance().startUseItem();
        return true;
    }
}
