package tfc.su_test.engine;

public class Test {
    Step[] steps;
    int execIndex = 0;

    public Test(Step... steps) {
        this.steps = steps;
    }

    /**
     * runs every tick
     *
     * @return false if there are no more steps
     */
    public boolean tick() {
        if (execIndex >= steps.length) {
            execIndex = 0;
            return false;
        }

        if (steps[execIndex].run()) {
            execIndex++;
        }

        return true;
    }
}
