package tfc.su_test;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import tfc.smallerunits.Registry;
import tfc.smallerunits.UnitSpace;
import tfc.smallerunits.data.capability.SUCapability;
import tfc.smallerunits.data.capability.SUCapabilityManager;
import tfc.su_test.engine.Test;
import tfc.su_test.engine.steps.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Tests {
    private static final HashMap<String, Test> tests = new HashMap<>();
    private static final ArrayList<Test> allTests = new ArrayList<>();
    private static final ArrayList<String> allNames = new ArrayList<>();

    protected static void addTest(String name, Test test) {
        tests.put(name, test);
        if (name.equals("base")) return;
        allNames.add(name);
        allTests.add(test);
    }

    protected static boolean checkBlock(BlockPos parent, BlockPos small) {
        UnitSpace space = SUCapabilityManager.getCapability(
                Minecraft.getInstance().level,
                ChunkPos.ZERO
        ).getUnit(parent);
        if (space == null) return false;

        return space.getBlock(small.getX(), small.getY(), small.getZ()).getBlock().equals(Blocks.DIRT);
    }

    static {
        addTest("base", new Test(new BaseStep()));

        ItemStack stack16 = new ItemStack(Registry.UNIT_SPACE_ITEM.get());
        stack16.getOrCreateTag().putInt("upb", 16);

        addTest("place", new Test(
                new BaseStep(),
                new UseItemStep(stack16),
                new RightClickStep(),
                new UseItemStep(Items.DIRT),
                new RightClickStep(),
                new ValidateStep("Placement0", () -> checkBlock(new BlockPos(0, 0, 0), new BlockPos(7, 0, 6))),
                new RightClickStep(),
                new ValidateStep("Placement1", () -> checkBlock(new BlockPos(0, 0, 0), new BlockPos(8, 0, 6))),
                new RightClickStep(),
                new ValidateStep("Placement2", () -> checkBlock(new BlockPos(0, 0, 0), new BlockPos(8, 1, 6))),
                new RightClickStep(),
                new ValidateStep("Placement3", () -> checkBlock(new BlockPos(0, 0, 0), new BlockPos(8, 1, 7))),
                new WaitStep(1)
        ));
    }

    public static Test[] getAll() {
        return allTests.toArray(new Test[0]);
    }

    public static Test get(String name) {
        return tests.get(name);
    }

    public static String[] getNames() {
        return allNames.toArray(new String[0]);
    }
}
