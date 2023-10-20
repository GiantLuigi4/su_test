package tfc.su_test.engine.steps;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tfc.su_test.engine.Step;

public class UseItemStep extends Step {
    ItemStack stack;

    public UseItemStep(ItemStack stack) {
        this.stack = stack;
    }

    public UseItemStep(Item stack) {
        this.stack = new ItemStack(stack);
    }

    @Override
    public boolean run() {
        String end = "";
        if (stack.hasTag()) {
            end += stack.getTag().toString();
        }
        end += " " + stack.getCount();
        return new CommandStep(
                "/item replace entity @s weapon.mainhand with " + Registry.ITEM.getKey(stack.getItem()) + end
        ).run();
    }
}
