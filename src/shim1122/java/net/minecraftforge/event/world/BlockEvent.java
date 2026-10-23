package net.minecraftforge.event.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class BlockEvent extends Event {
    private final World world;
    private final BlockPos pos;
    private final IBlockState state;

    public BlockEvent(World world, BlockPos pos, IBlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public IBlockState getState() {
        return state;
    }

    public static class HarvestDropsEvent extends BlockEvent {        private final int fortune;
        private final EntityPlayer harvester;
        private final List<ItemStack> drops;
        private float dropChance;

        public HarvestDropsEvent(World world, BlockPos pos, IBlockState state, int fortune,
                                 float dropChance, List<ItemStack> drops, EntityPlayer harvester,
                                 boolean isSilkTouching) {
            super(world, pos, state);
            this.fortune = fortune;
            this.harvester = harvester;
            this.drops = drops;
            this.dropChance = dropChance;
        }

        public int getFortuneLevel() {
            return fortune;
        }

        public EntityPlayer getHarvester() {
            return harvester;
        }

        public List<ItemStack> getDrops() {
            return drops;
        }

        public float getDropChance() {
            return dropChance;
        }

        public void setDropChance(float dropChance) {
            this.dropChance = dropChance;
        }
    }

    public static class PlaceEvent extends BlockEvent {
        private final EntityPlayer player;
        private final IBlockState placedBlock;
        private final IBlockState placedAgainst;
        private final net.minecraft.item.ItemStack itemInHand;
        private final net.minecraft.util.EnumHand hand;

        public PlaceEvent(World world, BlockPos pos, IBlockState placedBlock, IBlockState placedAgainst,
                          EntityPlayer player, net.minecraft.item.ItemStack itemInHand,
                          net.minecraft.util.EnumHand hand) {
            super(world, pos, placedBlock);
            this.placedBlock = placedBlock;
            this.placedAgainst = placedAgainst;
            this.player = player;
            this.itemInHand = itemInHand;
            this.hand = hand;
        }

        public EntityPlayer getPlayer() {
            return player;
        }

        public IBlockState getPlacedBlock() {
            return placedBlock;
        }

        public IBlockState getPlacedAgainst() {
            return placedAgainst;
        }

        public net.minecraft.item.ItemStack getItemInHand() {
            return itemInHand;
        }

        public net.minecraft.util.EnumHand getHand() {
            return hand;
        }
    }

    public static class MultiPlaceEvent extends PlaceEvent {        private final java.util.List<Object> blockSnapshots;

        public MultiPlaceEvent(World world, BlockPos pos, IBlockState placedBlock, IBlockState placedAgainst,
                               EntityPlayer player, net.minecraft.item.ItemStack itemInHand,
                               net.minecraft.util.EnumHand hand, java.util.List<Object> blockSnapshots) {
            super(world, pos, placedBlock, placedAgainst, player, itemInHand, hand);
            this.blockSnapshots = blockSnapshots;
        }

        public java.util.List<Object> getReplacedBlockSnapshots() {
            return blockSnapshots;
        }
    }

    public static class BreakEvent extends BlockEvent {
        private final EntityPlayer player;
        private int exp;

        public BreakEvent(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
            super(world, pos, state);
            this.player = player;
        }

        public EntityPlayer getPlayer() {
            return player;
        }

        public int getExpToDrop() {
            return exp;
        }

        public void setExpToDrop(int exp) {
            this.exp = exp;
        }
    }
}
