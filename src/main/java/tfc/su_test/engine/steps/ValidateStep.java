package tfc.su_test.engine.steps;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import tfc.su_test.engine.Step;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class ValidateStep extends Step {
    public Supplier<Boolean> validator;
    public String text;

    public ValidateStep(String text, Supplier<Boolean> validator) {
        this.validator = validator;
        this.text = text;
    }

    @Override
    public boolean run() {
        if (!validator.get()) {
            Minecraft.getInstance().getChatListener().handleSystemMessage(
                    Component.literal(text).withStyle(ChatFormatting.RED),
                    false
            );
        } else {
            Minecraft.getInstance().getChatListener().handleSystemMessage(
                    Component.literal(text).withStyle(ChatFormatting.GREEN),
                    false
            );
        }
        return true;
    }
}
