package supertoady.cloudedisles.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarborneCleaverItem extends SwordItem {
    public StarborneCleaverItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        NbtCompound nbt = stack.getOrCreateNbt();

        if (user.getItemCooldownManager().isCoolingDown(this)) return ActionResult.PASS;
        user.getItemCooldownManager().set(this, 200);

        nbt.putBoolean("cloudedisles:cleaveractive", true);
        nbt.putInt("cloudedisles:cleaverticks", 10);
        nbt.putDouble("cloudedisles:cleaverx", entity.getX());
        nbt.putDouble("cloudedisles:cleavery", entity.getY() + 10);
        nbt.putDouble("cloudedisles:cleaverz", entity.getZ());

        return ActionResult.SUCCESS;
    }

    public void meteorExplode(NbtCompound nbt, PlayerEntity attacker){
        World world = attacker.getWorld();
        if (world.isClient) return;

        double x = nbt.getDouble("cloudedisles:cleaverx");
        double y = nbt.getDouble("cloudedisles:cleavery");
        double z = nbt.getDouble("cloudedisles:cleaverz");

        Explosion explosion = world.createExplosion(attacker, x, y, z, 1, World.ExplosionSourceType.MOB);

        nbt.putBoolean("cloudedisles:cleaveractive", false);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.getBoolean("cloudedisles:cleaveractive")){
            entity.sendMessage(Text.literal("not active"));
            return;
        }

        entity.sendMessage(Text.literal("active"));

        int ticks = nbt.getInt("cloudedisles:cleaverticks") - 1;
        if (ticks == -1) meteorExplode(nbt, (PlayerEntity) entity);

        nbt.putInt("cloudedisles:cleaverticks", ticks);
        double x = nbt.getDouble("cloudedisles:cleaverx");
        double y = nbt.getDouble("cloudedisles:cleavery") - 1;
        double z = nbt.getDouble("cloudedisles:cleaverz");

        nbt.putDouble("cloudedisles:cleavery", y);

        world.addParticle(ParticleTypes.LAVA, x, y, z, 0, -0.5, 0);

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.cloudedisles.starborne_cleaver.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
