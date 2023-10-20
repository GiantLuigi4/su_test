package tfc.su_test.engine.steps;

import tfc.su_test.engine.Step;

public class WaitStep extends Step {
    public int delay;
    public int timer;

    public WaitStep(int delay) {
        this.timer = this.delay = delay;
    }

    @Override
    public boolean run() {
        timer--;
        if (timer == 0) {
            timer = delay;
            return true;
        }
        return false;
    }
}
