package tfc.su_test.engine.steps;

import tfc.su_test.engine.Step;

public class BaseStep extends Step {
    Step[] steps = new Step[]{
            new CommandStep("/fill -10 -1 -10 10 -1 10 stone"),
            new CommandStep("/fill -10 0 -10 10 10 10 air"),
            new CommandStep("/execute in minecraft:overworld run tp @s 1.44 0.00 1.25 131.83 51.29"),
            new WaitStep(1)
    };
    int execIndex = 0;

    @Override
    public boolean run() {
        if (execIndex >= steps.length) {
            execIndex = 0;
            return true;
        }

        if (steps[execIndex].run()) {
            execIndex++;
        }

        return false;
    }
}
