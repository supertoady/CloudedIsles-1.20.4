package supertoady.cloudedisles.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import supertoady.cloudedisles.CloudedIsles;

public class ModBlocks {

    public static Block SKYSTONE = registerBlock("skystone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE)));

    public static Block SKYSTEEL_BLOCK = registerBlock("skysteel_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

    public static Block RAW_SKYSTEEL_BLOCK = registerBlock("raw_skysteel_block",
            new Block(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK)));

    public static Block CLOUD = registerBlock("cloud",
            new Block(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(CloudedIsles.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(CloudedIsles.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks(){
        CloudedIsles.LOGGER.info("Registering mod blocks for" + CloudedIsles.MOD_ID);
    }


}
