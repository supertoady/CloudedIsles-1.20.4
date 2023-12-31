package supertoady.cloudedisles;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import supertoady.cloudedisles.block.ModBlocks;
import supertoady.cloudedisles.item.ModItemGroup;
import supertoady.cloudedisles.item.ModItems;

public class CloudedIsles implements ModInitializer {

	public static final String MOD_ID = "cloudedisles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModItemGroup.registerItemGroups();

		ModBlocks.registerModBlocks();

		LOGGER.info("Hello supertoady world!");
	}
}