package supertoady.cloudedisles.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import supertoady.cloudedisles.item.ModItems;

import java.util.List;
import java.util.Random;

public class GuardianShieldItem extends ShieldItem {
    public GuardianShieldItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack){ return UseAction.BLOCK; }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.pass(user.getStackInHand(hand));
        TypedActionResult<ItemStack> result = super.use(world, user, hand);

        Box box = new Box(user.getX() - 1.5, user.getY() - 1.5, user.getZ() - 1.5,
                user.getX() + 1.5, user.getY() + 1.5, user.getZ() + 1.5);

        List<Entity> entities = world.getOtherEntities(user, box);

        if (entities.isEmpty() || !user.isSneaking()) return TypedActionResult.pass(user.getStackInHand(hand));

        Vec3d look = user.getRotationVector().normalize();

        for (Entity target : entities){
            for (int i = 15; i > -1; i--){
                Random rand = new Random();

                double x = rand.nextDouble(2) - 1;
                double y = rand.nextDouble(2) - 1;
                double z = rand.nextDouble(2) - 1;

                world.addParticle(ParticleTypes.SPLASH, x + target.getX(), y + target.getY(), z + target.getZ(), 0, 0, 0);
            }

            user.playSound(SoundEvents.ENTITY_GUARDIAN_ATTACK, 0.6f, 1.0f);

            target.addVelocity(look.x * 0.7, look.y * 0.7, look.z * 0.7);
            target.damage(target.getDamageSources().magic(), 8);
        }

        user.getItemCooldownManager().set(this, 150);

        return result;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(ModItems.SKYSTEEL_INGOT) || super.canRepair(stack, ingredient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.cloudedisles.guardian_shield.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
