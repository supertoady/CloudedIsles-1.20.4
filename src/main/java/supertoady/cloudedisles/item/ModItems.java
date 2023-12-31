package supertoady.cloudedisles.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import supertoady.cloudedisles.CloudedIsles;
import supertoady.cloudedisles.item.custom.AstralPrismItem;
import supertoady.cloudedisles.item.custom.CloudBottleItem;
import supertoady.cloudedisles.item.custom.GuardianShieldItem;
import supertoady.cloudedisles.item.custom.StarborneCleaverItem;

public class ModItems {

    public static final Item SKYSTEEL_INGOT = registerItem("skysteel_ingot",
            new Item(new FabricItemSettings()));

    //public static final Item RAW_SKYSTEEL = registerItem("raw_skysteel",
            //new AstralPrismItem(new FabricItemSettings()));

    public static final Item ASTRAL_PRISM = registerItem("astral_prism",
            new AstralPrismItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));

    public static final Item STARBORNE_CLEAVER = registerItem("starborne_cleaver",
            new StarborneCleaverItem(ModToolMaterial.SKYSTEEL, 5, -3F
                    , new FabricItemSettings().rarity(Rarity.RARE)));

    public static final Item CLOUD_BOTTLE = registerItem("cloud_bottle",
            new CloudBottleItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));

    public static final Item GUARDIAN_SHIELD = registerItem("guardian_shield",
            new GuardianShieldItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(CloudedIsles.MOD_ID, name), item);
    }

    public static void registerModItems(){
        CloudedIsles.LOGGER.info("registering mod items for:" + CloudedIsles.MOD_ID);
    }
}
