package net.infinite1274.helldivers.block;

import net.infinite1274.helldivers.HelldiversMod;
import net.infinite1274.helldivers.block.custom.HellbombBlock;
import net.infinite1274.helldivers.item.ModItems;
import net.infinite1274.helldivers.worldgen.tree.TestTreeGrower;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.example.registry.BlockRegistry;
import software.bernie.geckolib.GeckoLib;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, HelldiversMod.MOD_ID);

    public static final RegistryObject<Block> HELLBOMB = registerBlock("hellbomb",
            () -> new HellbombBlock());

    public static final RegistryObject<Block> TEST_SAPLING = registerBlock("test_sapling",
            () -> new SaplingBlock(new TestTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> toReturn) {
        ModItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
