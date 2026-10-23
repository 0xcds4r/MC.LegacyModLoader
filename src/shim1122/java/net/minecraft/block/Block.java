package net.minecraft.block;

import dev.legacyloader.bridge.LegacyBlockView;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;
import java.util.Random;

/**
 * 1.12.2 block shim.
 *
 * <p>This class is intentionally a plain POJO. It does not extend the modern
 * block class: old overrides have different signatures, and inheriting modern
 * types would leak the modern API into legacy code. The loader registers a
 * modern {@code LegacyBlockAdapter} that holds this object instead.
 */
public class Block implements IForgeRegistryEntry<Block>, LegacyBlockView {
    public static final net.minecraft.util.registry.RegistryNamespaced<ResourceLocation, Block> REGISTRY =
            new net.minecraft.util.registry.RegistryNamespaced<>();
    protected final Material material;
    protected float hardness = 1.0F;
    protected float resistance = 5.0F;
    protected float blockResistance = 5.0F;
    protected float slipperiness = 0.6F;
    protected int lightLevel;
    protected boolean opaqueCube = true;
    protected boolean fullCube = true;
    protected boolean hasTileEntity;
    protected boolean ticksRandomly;
    protected String harvestTool = "";
    protected int harvestLevel;
    protected String unlocalizedName;
    protected ResourceLocation registryName;
    protected CreativeTabs creativeTab;
    protected SoundType soundType = SoundType.STONE;
    protected BlockStateContainer blockState;
    protected boolean useNeighborBrightness;

    public Block(Material material) {
        this.material = material;
        this.blockState = createBlockState();
    }

    public Material getMaterial() {
        return material;
    }

    public Block setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public Block setResistance(float resistance) {
        this.resistance = resistance;
        this.blockResistance = resistance;
        return this;
    }

    public Block setLightLevel(float value) {
        this.lightLevel = (int) (15.0F * value);
        return this;
    }

    public Block setLightOpacity(int opacity) {
        return this;
    }

    public Block setBlockUnbreakable() {
        this.hardness = -1.0F;
        return this;
    }

    public enum EnumOffsetType {
        NONE,
        XZ,
        XYZ
    }

    public Block setTickRandomly(boolean ticks) {
        this.ticksRandomly = ticks;
        return this;
    }

    public Block disableStats() {
        return this;
    }

    public Block setUnlocalizedName(String name) {
        this.unlocalizedName = name;
        return this;
    }

    public Block setTranslationKey(String key) {
        return setUnlocalizedName(key);
    }

    public Block setCreativeTab(CreativeTabs tab) {
        this.creativeTab = tab;
        return this;
    }

    public Block setSoundType(SoundType soundType) {
        this.soundType = soundType;
        return this;
    }

    public void setHarvestLevel(String toolClass, int level) {
        this.harvestTool = toolClass == null ? "" : toolClass;
        this.harvestLevel = level;
    }

    public Block setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
        return this;
    }

    public Block setOpaqueCube(boolean opaque) {
        this.opaqueCube = opaque;
        return this;
    }

    public Block setFullCube(boolean full) {
        this.fullCube = full;
        return this;
    }

    public boolean isOpaqueCube() {
        return opaqueCube;
    }

    public boolean isFullCube(IBlockState state) {
        return fullCube;
    }

    public boolean isFullCube() {
        return fullCube;
    }

    public int getLightValue(IBlockState state) {
        return lightLevel;
    }

    public boolean hasTileEntity(IBlockState state) {
        return hasTileEntity;
    }

    public CreativeTabs getCreativeTab() {
        return creativeTab;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public String getTranslationKey() {
        return unlocalizedName;
    }

    public String getLocalizedName() {
        return unlocalizedName;
    }

    public SoundType getSoundType() {
        return soundType;
    }

    @Override
    public Block setRegistryName(ResourceLocation name) {
        this.registryName = name;
        return this;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public Class<Block> getRegistrySuperType() {
        return Block.class;
    }

    // --- LegacyBlockView -------------------------------------------------

    @Override
    public String legacyTranslationKey() {
        String key = unlocalizedName == null ? "unnamed" : unlocalizedName;
        return key.startsWith("tile.") ? key : "tile." + key;
    }

    @Override
    public String legacyRegistryPath() {
        if (registryName != null) {
            return registryName.getPath();
        }
        String key = legacyTranslationKey();
        int dot = key.lastIndexOf('.');
        return dot >= 0 ? key.substring(dot + 1) : key;
    }

    @Override
    public float legacyHardness() {
        return hardness;
    }

    @Override
    public float legacyResistance() {
        return resistance;
    }

    @Override
    public int legacyLightLevel() {
        return lightLevel;
    }

    @Override
    public float legacyFriction() {
        return slipperiness;
    }

    @Override
    public boolean legacyOpaqueCube() {
        return opaqueCube;
    }

    @Override
    public boolean legacyFullCube() {
        return fullCube;
    }

    @Override
    public boolean legacyHasTileEntity() {
        return hasTileEntity;
    }

    @Override
    public String legacyHarvestTool() {
        return harvestTool;
    }

    @Override
    public int legacyHarvestLevel() {
        return harvestLevel;
    }

    // --- 1.12 block behaviour surface ------------------------------------

    public IBlockState getDefaultState() {
        if (blockState == null) {
            blockState = createBlockState();
        }
        return blockState.getBaseState();
    }

    public void setDefaultState(IBlockState state) {
        // legacy callers only use this to force a base state; the shim keeps
        // its own container and treats the default state as immutable for now
    }

    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta,
                                            EntityLivingBase placer, EnumHand hand) {
        return getStateFromMeta(meta);
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state;
    }

    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state;
    }

    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
                                EntityLivingBase placer, ItemStack stack) {
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        return false;
    }

    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
    }

    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    }

    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    }

    public void neighborChanged(IBlockState state, World world, BlockPos pos,
                                Block block, BlockPos fromPos) {
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
    }

    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
    }

    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
    }

    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos,
                                   EntityPlayer player, boolean willHarvest) {
        return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), 3);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getBoundingBox(state, world, pos);
    }

    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return !material.blocksMovement();
    }

    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return material.isReplaceable();
    }

    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return true;
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
        return canPlaceBlockAt(world, pos);
    }

    public boolean canProvidePower(IBlockState state) {
        return false;
    }

    public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return 0;
    }

    public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return 0;
    }

    public boolean hasComparatorInputOverride(IBlockState state) {
        return false;
    }

    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        return 0;
    }

    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    public boolean isNormalCube(IBlockState state) {
        return opaqueCube && fullCube;
    }

    public boolean isBlockNormalCube(IBlockState state) {
        return isNormalCube(state);
    }

    public boolean isFullBlock(IBlockState state) {
        return fullCube;
    }

    public boolean isTopSolid(IBlockState state) {
        return fullCube;
    }

    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return isNormalCube(state);
    }

    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return opaqueCube ? 255 : 0;
    }

    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return lightLevel;
    }

    public float getExplosionResistance(Entity exploder) {
        return resistance;
    }

    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return hardness;
    }

    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player,
                                                World world, BlockPos pos) {
        return 0.0F;
    }

    public String getHarvestTool(IBlockState state) {
        return harvestTool;
    }

    public int getHarvestLevel(IBlockState state) {
        return harvestLevel;
    }

    public boolean isToolEffective(String type, IBlockState state) {
        return harvestTool.equals(type);
    }

    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
        return soundType;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state,
                                            BlockPos pos, EnumFacing face) {
        return isFullCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
    }

    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
    }

    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    }

    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state,
                             TileEntity tileEntity, ItemStack tool) {
    }

    public void dropBlockAsItem(World world, BlockPos pos, IBlockState state, int fortune) {
    }

    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public int damageDropped(IBlockState state) {
        return 0;
    }

    public int quantityDropped(Random random) {
        return 1;
    }

    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return List.of();
    }

    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
        return false;
    }
}
