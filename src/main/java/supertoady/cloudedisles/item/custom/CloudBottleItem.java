package supertoady.cloudedisles.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CloudBottleItem extends Item {
    public CloudBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(this)){
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

        Vec3d look = user.getRotationVector().normalize();

        for (int i = 15; i > -1; i--){
            Random rand = new Random();

            double x = rand.nextDouble(2) - 1;
            double y = rand.nextDouble(1) - 1.5;
            double z = rand.nextDouble(2) - 1;

            world.addParticle(ParticleTypes.CLOUD, x + user.getX(), y + user.getY(), z + user.getZ(), 0, 0, 0);
            user.playSound(SoundEvents.BLOCK_SAND_BREAK, 0.6f, 1.0f);
        }

        user.addVelocity(look.x, look.y, look.z);
        if (!user.isCreative()) user.getItemCooldownManager().set(this, 150);
        user.fallDistance = 0;

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.cloudedisles.cloud_bottle.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
