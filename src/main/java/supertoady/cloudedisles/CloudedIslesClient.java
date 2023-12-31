package supertoady.cloudedisles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import supertoady.cloudedisles.block.ModBlocks;
import supertoady.cloudedisles.item.ModItems;

public class CloudedIslesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOUD, RenderLayer.getCutout());

        ModelPredicateProviderRegistry.register(ModItems.GUARDIAN_SHIELD, new Identifier("blocking"), (itemStack, clientWorld, livingEntity, integer) -> {
           if (livingEntity == null){
               return 0.0f;
           }
           return livingEntity.getActiveItem() == itemStack ? 1.0f : 0.0f;
        });
    }
}
