package supertoady.cloudedisles.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import supertoady.cloudedisles.CloudedIsles;
import supertoady.cloudedisles.block.ModBlocks;

public class ModItemGroup {
    public static final ItemGroup CLOUDED_ISLES = Registry.register(Registries.ITEM_GROUP,
            new Identifier(CloudedIsles.MOD_ID, "clouded-isles"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.clouded-isles"))
                    .icon(() -> new ItemStack(ModItems.ASTRAL_PRISM)).entries((displayContext, entries) -> {
                        entries.add(ModItems.ASTRAL_PRISM);
                        entries.add(ModItems.STARBORNE_CLEAVER);
                        entries.add(ModItems.GUARDIAN_SHIELD);
                        entries.add(ModItems.CLOUD_BOTTLE);

                        entries.add(ModItems.SKYSTEEL_INGOT);
                        entries.add(ModBlocks.SKYSTEEL_BLOCK);

                        entries.add(ModBlocks.SKYSTONE);
                        entries.add(ModBlocks.CLOUD);

                    }).build());


    public static void registerItemGroups() {
        CloudedIsles.LOGGER.info("Registering Item Groups for " + CloudedIsles.MOD_ID);
    }
}
