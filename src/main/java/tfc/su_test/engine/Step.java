package tfc.su_test.engine;

public abstract class Step {
    /**
     * runs every game tick while the step is running
     *
     * @return true if the action should end
     */
    public abstract boolean run();
}
