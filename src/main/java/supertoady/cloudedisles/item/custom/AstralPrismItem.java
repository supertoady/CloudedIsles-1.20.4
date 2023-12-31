package supertoady.cloudedisles.item.custom;

import net.minecraft.block.BedBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2d;

import java.util.List;

public class AstralPrismItem extends Item {
    public AstralPrismItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;

        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.getBoolean("prism.isactivated")){
            Vec3d dVector = new Vec3d(entity.getX() - nbt.getDouble("prism.tposx"), 0, entity.getZ() - nbt.getDouble("prism.tposz"));

            //calculate yaw in degrees
            double yaw = Math.atan2(dVector.z, dVector.x);
            yaw *= 180 / Math.PI;
            yaw += 90;

            //calculate difference between current player yaw and target yaw
            double dist = entity.getYaw() - yaw;

            String message = null;
            if (withinDistance(dist, 135)) message = "⬋";
            if (withinDistance(dist, 90)) message = "←";
            if (withinDistance(dist, 45)) message = "⬉";
            if (withinDistance(dist, 0)) message = "↑";
            if (withinDistance(dist, -45)) message = "⬈";
            if (withinDistance(dist, -90)) message = "→";
            if (withinDistance(dist, -135)) message = "⬊";
            if (withinDistance(dist, -180) || withinDistance(dist, 180)) message = "↓";

            message = "no structure found";

            PlayerEntity player = (PlayerEntity) entity;
            player.sendMessage(Text.literal("§b< " + message + " >"), true);
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NbtCompound nbt = user.getStackInHand(hand).getOrCreateNbt();
        nbt.putBoolean("prism.isactivated", !nbt.getBoolean("prism.isactivated"));

        if (nbt.getBoolean("prism.isactivated")) user.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 1f, 1f);
        else user.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE, 1f, 1f);
        nbt.putDouble("prism.tposx", 0);
        nbt.putDouble("prism.tposz", 0);

        return super.use(world, user, hand);
    }

    public static boolean withinDistance(double yaw, double rotation) {
        return yaw > (rotation - 22.5) && yaw < (rotation + 22.5);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean("prism.isactivated");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.cloudedisles.astral_prism.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
