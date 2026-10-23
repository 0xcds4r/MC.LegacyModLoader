package net.minecraftforge.event.entity.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlayerInteractEvent extends net.minecraftforge.fml.common.gameevent.PlayerEvent {
    protected final World world;
    protected final BlockPos pos;
    protected final EnumFacing face;

    public PlayerInteractEvent(EntityPlayer player, World world, BlockPos pos, EnumFacing face) {
        super(player);
        this.world = world;
        this.pos = pos;
        this.face = face;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getFace() {
        return face;
    }

    public static class LeftClickBlock extends PlayerInteractEvent {
        private final net.minecraft.block.state.IBlockState state;

        public LeftClickBlock(EntityPlayer player, World world, BlockPos pos, EnumFacing face,
                              net.minecraft.block.state.IBlockState state) {
            super(player, world, pos, face);
            this.state = state;
        }

        public net.minecraft.block.state.IBlockState getBlockState() {
            return state;
        }
    }

    public static class RightClickBlock extends PlayerInteractEvent {
        public RightClickBlock(EntityPlayer player, World world, BlockPos pos, EnumFacing face) {
            super(player, world, pos, face);
        }
    }

    public static class RightClickItem extends PlayerInteractEvent {
        public RightClickItem(EntityPlayer player, World world, BlockPos pos, EnumFacing face) {
            super(player, world, pos, face);
        }
    }

    public static class EntityInteract extends PlayerInteractEvent {
        private final net.minecraft.entity.Entity target;

        public EntityInteract(EntityPlayer player, World world, BlockPos pos, EnumFacing face,
                              net.minecraft.entity.Entity target) {
            super(player, world, pos, face);
            this.target = target;
        }

        public net.minecraft.entity.Entity getTarget() {
            return target;
        }
    }
}
