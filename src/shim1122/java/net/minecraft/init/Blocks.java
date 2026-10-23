package net.minecraft.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * Vanilla block constant stubs. They are valid block objects but are not bound
 * to modern registry entries; code that merely compares identities or reads
 * properties during class initialization works.
 */
public class Blocks {
    public static final Block AIR = block("air", Material.AIR);
    public static final Block STONE = block("stone", Material.ROCK);
    public static final BlockGrass GRASS = createGrass();
    public static final Block DIRT = block("dirt", Material.GROUND);
    public static final Block COBBLESTONE = block("cobblestone", Material.ROCK);
    public static final Block PLANKS = block("planks", Material.WOOD);
    public static final Block SAPLING = block("sapling", Material.PLANTS);
    public static final Block BEDROCK = block("bedrock", Material.ROCK);
    public static final Block FLOWING_WATER = block("flowing_water", Material.WATER);
    public static final Block WATER = block("water", Material.WATER);
    public static final Block FLOWING_LAVA = block("flowing_lava", Material.LAVA);
    public static final Block LAVA = block("lava", Material.LAVA);
    public static final BlockSand SAND = createSand();
    public static final Block GRAVEL = block("gravel", Material.SAND);
    public static final Block GOLD_ORE = block("gold_ore", Material.ROCK);
    public static final Block IRON_ORE = block("iron_ore", Material.ROCK);
    public static final Block COAL_ORE = block("coal_ore", Material.ROCK);
    public static final BlockLog LOG = (BlockLog) createLog("log");
    public static final BlockLog LOG2 = (BlockLog) createLog("log2");
    public static final BlockLeaves LEAVES = createLeaves("leaves");
    public static final BlockLeaves LEAVES2 = createLeaves("leaves2");
    public static final Block SPONGE = block("sponge", Material.SPONGE);
    public static final Block GLASS = block("glass", Material.GLASS);
    public static final Block LAPIS_ORE = block("lapis_ore", Material.ROCK);
    public static final Block LAPIS_BLOCK = block("lapis_block", Material.ROCK);
    public static final Block DISPENSER = block("dispenser", Material.ROCK);
    public static final Block SANDSTONE = block("sandstone", Material.ROCK);
    public static final Block NOTEBLOCK = block("noteblock", Material.WOOD);
    public static final Block BED = block("bed", Material.CLOTH);
    public static final Block GOLDEN_RAIL = block("golden_rail", Material.CIRCUITS);
    public static final Block DETECTOR_RAIL = block("detector_rail", Material.CIRCUITS);
    public static final BlockPistonBase STICKY_PISTON = createPiston(true, "sticky_piston");
    public static final Block WEB = block("web", Material.WEB);
    public static final BlockTallGrass TALLGRASS = createTallGrass();
    public static final BlockDeadBush DEADBUSH = createDeadBush();
    public static final BlockPistonBase PISTON = createPiston(false, "piston");
    public static final Block WOOL = block("wool", Material.CLOTH);
    public static final BlockFlower YELLOW_FLOWER = createFlower("yellow_flower");
    public static final BlockFlower RED_FLOWER = createFlower("red_flower");
    public static final BlockBush BROWN_MUSHROOM = createBush("brown_mushroom");
    public static final BlockBush RED_MUSHROOM = createBush("red_mushroom");
    public static final Block GOLD_BLOCK = block("gold_block", Material.IRON);
    public static final Block IRON_BLOCK = block("iron_block", Material.IRON);
    public static final Block BRICK_BLOCK = block("brick_block", Material.ROCK);
    public static final Block TNT = block("tnt", Material.TNT);
    public static final Block BOOKSHELF = block("bookshelf", Material.WOOD);
    public static final Block MOSSY_COBBLESTONE = block("mossy_cobblestone", Material.ROCK);
    public static final Block OBSIDIAN = block("obsidian", Material.ROCK);
    public static final Block TORCH = block("torch", Material.CIRCUITS);
    public static final BlockFire FIRE = createFire();
    public static final Block MOB_SPAWNER = block("mob_spawner", Material.ROCK);
    public static final BlockChest CHEST = createChest("chest", BlockChest.Type.BASIC);
    public static final Block REDSTONE_WIRE = block("redstone_wire", Material.CIRCUITS);
    public static final Block DIAMOND_ORE = block("diamond_ore", Material.ROCK);
    public static final Block DIAMOND_BLOCK = block("diamond_block", Material.IRON);
    public static final Block CRAFTING_TABLE = block("crafting_table", Material.WOOD);
    public static final Block WHEAT = block("wheat", Material.PLANTS);
    public static final Block FARMLAND = block("farmland", Material.GROUND);
    public static final Block FURNACE = block("furnace", Material.ROCK);
    public static final Block LIT_FURNACE = block("lit_furnace", Material.ROCK);
    public static final Block STANDING_SIGN = block("standing_sign", Material.WOOD);
    public static final Block WOODEN_DOOR = block("wooden_door", Material.WOOD);
    public static final Block LADDER = block("ladder", Material.WOOD);
    public static final Block RAIL = block("rail", Material.CIRCUITS);
    public static final Block STONE_STAIRS = block("stone_stairs", Material.ROCK);
    public static final Block WALL_SIGN = block("wall_sign", Material.WOOD);
    public static final Block LEVER = block("lever", Material.CIRCUITS);
    public static final Block STONE_PRESSURE_PLATE = block("stone_pressure_plate", Material.ROCK);
    public static final Block IRON_DOOR = block("iron_door", Material.IRON);
    public static final Block WOODEN_PRESSURE_PLATE = block("wooden_pressure_plate", Material.WOOD);
    public static final Block REDSTONE_ORE = block("redstone_ore", Material.ROCK);
    public static final Block LIT_REDSTONE_ORE = block("lit_redstone_ore", Material.ROCK);
    public static final Block UNLIT_REDSTONE_TORCH = block("unlit_redstone_torch", Material.CIRCUITS);
    public static final Block REDSTONE_TORCH = block("redstone_torch", Material.CIRCUITS);
    public static final Block STONE_BUTTON = block("stone_button", Material.CIRCUITS);
    public static final Block SNOW_LAYER = block("snow_layer", Material.SNOW);
    public static final Block ICE = block("ice", Material.ICE);
    public static final Block SNOW = block("snow", Material.SNOW);
    public static final BlockCactus CACTUS = createCactus();
    public static final Block CLAY = block("clay", Material.CLAY);
    public static final BlockReed REEDS = createReed();
    public static final Block JUKEBOX = block("jukebox", Material.WOOD);
    public static final Block FENCE = block("fence", Material.WOOD);
    public static final Block PUMPKIN = block("pumpkin", Material.GOURD);
    public static final Block NETHERRACK = block("netherrack", Material.ROCK);
    public static final Block SOUL_SAND = block("soul_sand", Material.SAND);
    public static final Block GLOWSTONE = block("glowstone", Material.GLASS);
    public static final BlockPortal PORTAL = createPortal();
    public static final Block LIT_PUMPKIN = block("lit_pumpkin", Material.GOURD);
    public static final Block CAKE = block("cake", Material.CAKE);
    public static final Block UNPOWERED_REPEATER = block("unpowered_repeater", Material.CIRCUITS);
    public static final Block POWERED_REPEATER = block("powered_repeater", Material.CIRCUITS);
    public static final Block STAINED_GLASS = block("stained_glass", Material.GLASS);
    public static final Block TRAPDOOR = block("trapdoor", Material.WOOD);
    public static final Block MONSTER_EGG = block("monster_egg", Material.ROCK);
    public static final Block STONEBRICK = block("stonebrick", Material.ROCK);
    public static final Block BROWN_MUSHROOM_BLOCK = block("brown_mushroom_block", Material.WOOD);
    public static final Block RED_MUSHROOM_BLOCK = block("red_mushroom_block", Material.WOOD);
    public static final Block IRON_BARS = block("iron_bars", Material.IRON);
    public static final Block GLASS_PANE = block("glass_pane", Material.GLASS);
    public static final Block MELON_BLOCK = block("melon_block", Material.GOURD);
    public static final Block PUMPKIN_STEM = block("pumpkin_stem", Material.PLANTS);
    public static final Block MELON_STEM = block("melon_stem", Material.PLANTS);
    public static final Block VINE = block("vine", Material.VINE);
    public static final Block FENCE_GATE = block("fence_gate", Material.WOOD);
    public static final Block OAK_FENCE = block("oak_fence", Material.WOOD);
    public static final Block SPRUCE_FENCE = block("spruce_fence", Material.WOOD);
    public static final Block BIRCH_FENCE = block("birch_fence", Material.WOOD);
    public static final Block JUNGLE_FENCE = block("jungle_fence", Material.WOOD);
    public static final Block DARK_OAK_FENCE = block("dark_oak_fence", Material.WOOD);
    public static final Block ACACIA_FENCE = block("acacia_fence", Material.WOOD);
    public static final Block OAK_FENCE_GATE = block("oak_fence_gate", Material.WOOD);
    public static final Block SPRUCE_FENCE_GATE = block("spruce_fence_gate", Material.WOOD);
    public static final Block BIRCH_FENCE_GATE = block("birch_fence_gate", Material.WOOD);
    public static final Block JUNGLE_FENCE_GATE = block("jungle_fence_gate", Material.WOOD);
    public static final Block DARK_OAK_FENCE_GATE = block("dark_oak_fence_gate", Material.WOOD);
    public static final Block ACACIA_FENCE_GATE = block("acacia_fence_gate", Material.WOOD);
    public static final Block WOODEN_SLAB = block("wooden_slab", Material.WOOD);
    public static final Block DOUBLE_WOODEN_SLAB = block("double_wooden_slab", Material.WOOD);
    public static final Block ACACIA_STAIRS = block("acacia_stairs", Material.WOOD);
    public static final Block DARK_OAK_STAIRS = block("dark_oak_stairs", Material.WOOD);
    public static final Block BRICK_STAIRS = block("brick_stairs", Material.ROCK);
    public static final Block STONE_BRICK_STAIRS = block("stone_brick_stairs", Material.ROCK);
    public static final BlockMycelium MYCELIUM = createMycelium();
    public static final Block WATERLILY = block("waterlily", Material.PLANTS);
    public static final Block NETHER_WART = block("nether_wart", Material.PLANTS);
    public static final Block NETHER_BRICK_FENCE = block("nether_brick_fence", Material.ROCK);
    public static final Block NETHER_BRICK_STAIRS = block("nether_brick_stairs", Material.ROCK);
    public static final Block ENCHANTING_TABLE = block("enchanting_table", Material.ROCK);
    public static final Block END_PORTAL = block("end_portal", Material.PORTAL);
    public static final Block END_PORTAL_FRAME = block("end_portal_frame", Material.ROCK);
    public static final Block END_STONE = block("end_stone", Material.ROCK);
    public static final Block DRAGON_EGG = block("dragon_egg", Material.DRAGON_EGG);
    public static final Block REDSTONE_LAMP = block("redstone_lamp", Material.REDSTONE_LIGHT);
    public static final Block LIT_REDSTONE_LAMP = block("lit_redstone_lamp", Material.REDSTONE_LIGHT);
    public static final BlockSlab DOUBLE_STONE_SLAB = createSlab(true, "double_stone_slab");
    public static final BlockSlab STONE_SLAB = createSlab(false, "stone_slab");
    public static final Block COCOA = block("cocoa", Material.PLANTS);
    public static final Block SANDSTONE_STAIRS = block("sandstone_stairs", Material.ROCK);
    public static final Block EMERALD_ORE = block("emerald_ore", Material.ROCK);
    public static final Block ENDER_CHEST = block("ender_chest", Material.ROCK);
    public static final BlockTripWireHook TRIPWIRE_HOOK = createTripWireHook();
    public static final Block TRIPWIRE = block("tripwire", Material.CIRCUITS);
    public static final Block EMERALD_BLOCK = block("emerald_block", Material.IRON);
    public static final Block SPRUCE_STAIRS = block("spruce_stairs", Material.WOOD);
    public static final Block BIRCH_STAIRS = block("birch_stairs", Material.WOOD);
    public static final Block JUNGLE_STAIRS = block("jungle_stairs", Material.WOOD);
    public static final Block COMMAND_BLOCK = block("command_block", Material.IRON);
    public static final BlockBeacon BEACON = createBeacon();
    public static final Block COBBLESTONE_WALL = block("cobblestone_wall", Material.ROCK);
    public static final Block FLOWER_POT = block("flower_pot", Material.CIRCUITS);
    public static final Block CARROTS = block("carrots", Material.PLANTS);
    public static final Block POTATOES = block("potatoes", Material.PLANTS);
    public static final Block WOODEN_BUTTON = block("wooden_button", Material.WOOD);
    public static final Block SKULL = block("skull", Material.CIRCUITS);
    public static final Block ANVIL = block("anvil", Material.ANVIL);
    public static final Block TRAPPED_CHEST = createChest("trapped_chest", BlockChest.Type.TRAP);
    public static final Block LIGHT_WEIGHTED_PRESSURE_PLATE = block("light_weighted_pressure_plate", Material.IRON);
    public static final Block HEAVY_WEIGHTED_PRESSURE_PLATE = block("heavy_weighted_pressure_plate", Material.IRON);
    public static final Block UNPOWERED_COMPARATOR = block("unpowered_comparator", Material.CIRCUITS);
    public static final Block POWERED_COMPARATOR = block("powered_comparator", Material.CIRCUITS);
    public static final BlockDaylightDetector DAYLIGHT_DETECTOR = createDaylightDetector();
    public static final Block REDSTONE_BLOCK = block("redstone_block", Material.ROCK);
    public static final Block QUARTZ_ORE = block("quartz_ore", Material.ROCK);
    public static final BlockHopper HOPPER = createHopper();
    public static final Block QUARTZ_BLOCK = block("quartz_block", Material.ROCK);
    public static final Block QUARTZ_STAIRS = block("quartz_stairs", Material.ROCK);
    public static final Block ACTIVATOR_RAIL = block("activator_rail", Material.CIRCUITS);
    public static final Block DROPPER = block("dropper", Material.ROCK);
    public static final Block STAINED_HARDENED_CLAY = block("stained_hardened_clay", Material.ROCK);
    public static final Block STAINED_GLASS_PANE = block("stained_glass_pane", Material.GLASS);
    public static final Block HAY_BLOCK = block("hay_block", Material.GRASS);
    public static final Block CARPET = block("carpet", Material.CLOTH);
    public static final Block HARDENED_CLAY = block("hardened_clay", Material.ROCK);
    public static final Block CONCRETE = block("concrete", Material.ROCK);
    public static final Block CONCRETE_POWDER = block("concrete_powder", Material.SAND);
    public static final Block WHITE_GLAZED_TERRACOTTA = block("white_glazed_terracotta", Material.ROCK);
    public static final Block ORANGE_GLAZED_TERRACOTTA = block("orange_glazed_terracotta", Material.ROCK);
    public static final Block MAGENTA_GLAZED_TERRACOTTA = block("magenta_glazed_terracotta", Material.ROCK);
    public static final Block LIGHT_BLUE_GLAZED_TERRACOTTA = block("light_blue_glazed_terracotta", Material.ROCK);
    public static final Block YELLOW_GLAZED_TERRACOTTA = block("yellow_glazed_terracotta", Material.ROCK);
    public static final Block LIME_GLAZED_TERRACOTTA = block("lime_glazed_terracotta", Material.ROCK);
    public static final Block PINK_GLAZED_TERRACOTTA = block("pink_glazed_terracotta", Material.ROCK);
    public static final Block GRAY_GLAZED_TERRACOTTA = block("gray_glazed_terracotta", Material.ROCK);
    public static final Block SILVER_GLAZED_TERRACOTTA = block("silver_glazed_terracotta", Material.ROCK);
    public static final Block CYAN_GLAZED_TERRACOTTA = block("cyan_glazed_terracotta", Material.ROCK);
    public static final Block PURPLE_GLAZED_TERRACOTTA = block("purple_glazed_terracotta", Material.ROCK);
    public static final Block BLUE_GLAZED_TERRACOTTA = block("blue_glazed_terracotta", Material.ROCK);
    public static final Block BROWN_GLAZED_TERRACOTTA = block("brown_glazed_terracotta", Material.ROCK);
    public static final Block GREEN_GLAZED_TERRACOTTA = block("green_glazed_terracotta", Material.ROCK);
    public static final Block RED_GLAZED_TERRACOTTA = block("red_glazed_terracotta", Material.ROCK);
    public static final Block BLACK_GLAZED_TERRACOTTA = block("black_glazed_terracotta", Material.ROCK);
    public static final Block COAL_BLOCK = block("coal_block", Material.ROCK);
    public static final Block PACKED_ICE = block("packed_ice", Material.PACKED_ICE);
    public static final BlockDoublePlant DOUBLE_PLANT = createDoublePlant();
    public static final Block BANNER = block("banner", Material.CLOTH);
    public static final Block END_ROD = block("end_rod", Material.CIRCUITS);
    public static final Block CHORUS_PLANT = block("chorus_plant", Material.PLANTS);
    public static final Block CHORUS_FLOWER = block("chorus_flower", Material.PLANTS);
    public static final Block PURPUR_BLOCK = block("purpur_block", Material.ROCK);
    public static final Block PURPUR_PILLAR = block("purpur_pillar", Material.ROCK);
    public static final Block PURPUR_STAIRS = block("purpur_stairs", Material.ROCK);
    public static final Block PURPUR_DOUBLE_SLAB = block("purpur_double_slab", Material.ROCK);
    public static final Block PURPUR_SLAB = block("purpur_slab", Material.ROCK);
    public static final Block END_BRICKS = block("end_bricks", Material.ROCK);
    public static final Block BEETROOTS = block("beetroots", Material.PLANTS);
    public static final Block GRASS_PATH = block("grass_path", Material.GROUND);
    public static final Block END_GATEWAY = block("end_gateway", Material.PORTAL);
    public static final Block FROSTED_ICE = block("frosted_ice", Material.ICE);
    public static final Block MAGMA = block("magma", Material.ROCK);
    public static final Block NETHER_WART_BLOCK = block("nether_wart_block", Material.GRASS);
    public static final Block RED_NETHER_BRICK = block("red_nether_brick", Material.ROCK);
    public static final Block BONE_BLOCK = block("bone_block", Material.ROCK);
    public static final Block STRUCTURE_VOID = block("structure_void", Material.STRUCTURE_VOID);
    public static final Block OBSERVER = block("observer", Material.ROCK);
    public static final Block WHITE_SHULKER_BOX = block("white_shulker_box", Material.ROCK);
    public static final Block ORANGE_SHULKER_BOX = block("orange_shulker_box", Material.ROCK);
    public static final Block MAGENTA_SHULKER_BOX = block("magenta_shulker_box", Material.ROCK);
    public static final Block LIGHT_BLUE_SHULKER_BOX = block("light_blue_shulker_box", Material.ROCK);
    public static final Block YELLOW_SHULKER_BOX = block("yellow_shulker_box", Material.ROCK);
    public static final Block LIME_SHULKER_BOX = block("lime_shulker_box", Material.ROCK);
    public static final Block PINK_SHULKER_BOX = block("pink_shulker_box", Material.ROCK);
    public static final Block GRAY_SHULKER_BOX = block("gray_shulker_box", Material.ROCK);
    public static final Block SILVER_SHULKER_BOX = block("silver_shulker_box", Material.ROCK);
    public static final Block CYAN_SHULKER_BOX = block("cyan_shulker_box", Material.ROCK);
    public static final Block PURPLE_SHULKER_BOX = block("purple_shulker_box", Material.ROCK);
    public static final Block BLUE_SHULKER_BOX = block("blue_shulker_box", Material.ROCK);
    public static final Block BROWN_SHULKER_BOX = block("brown_shulker_box", Material.ROCK);
    public static final Block GREEN_SHULKER_BOX = block("green_shulker_box", Material.ROCK);
    public static final Block RED_SHULKER_BOX = block("red_shulker_box", Material.ROCK);
    public static final Block BLACK_SHULKER_BOX = block("black_shulker_box", Material.ROCK);

    private static Block block(String name, Material material) {
        return new Block(material).setRegistryName(new ResourceLocation("minecraft", name));
    }

    private static BlockGrass createGrass() {
        BlockGrass grass = new BlockGrass();
        grass.setRegistryName(new ResourceLocation("minecraft", "grass"));
        return grass;
    }

    private static BlockSand createSand() {
        BlockSand sand = new BlockSand();
        sand.setRegistryName(new ResourceLocation("minecraft", "sand"));
        return sand;
    }

    private static BlockPortal createPortal() {
        BlockPortal portal = new BlockPortal();
        portal.setRegistryName(new ResourceLocation("minecraft", "portal"));
        return portal;
    }

    private static BlockMycelium createMycelium() {
        BlockMycelium mycelium = new BlockMycelium();
        mycelium.setRegistryName(new ResourceLocation("minecraft", "mycelium"));
        return mycelium;
    }

    private static BlockStoneSlab createSlab(boolean isDouble, String name) {
        BlockStoneSlab slab = new BlockStoneSlab(isDouble);
        slab.setRegistryName(new ResourceLocation("minecraft", name));
        return slab;
    }

    private static BlockTallGrass createTallGrass() {
        BlockTallGrass grass = new BlockTallGrass();
        grass.setRegistryName(new ResourceLocation("minecraft", "tallgrass"));
        return grass;
    }

    private static BlockDeadBush createDeadBush() {
        BlockDeadBush bush = new BlockDeadBush();
        bush.setRegistryName(new ResourceLocation("minecraft", "deadbush"));
        return bush;
    }

    private static BlockDoublePlant createDoublePlant() {
        BlockDoublePlant plant = new BlockDoublePlant();
        plant.setRegistryName(new ResourceLocation("minecraft", "double_plant"));
        return plant;
    }

    private static BlockFlower createFlower(String name) {
        BlockFlower flower = new BlockFlower(Material.PLANTS);
        flower.setRegistryName(new ResourceLocation("minecraft", name));
        return flower;
    }

    private static BlockBush createBush(String name) {
        BlockBush bush = new BlockBush();
        bush.setRegistryName(new ResourceLocation("minecraft", name));
        return bush;
    }

    private static BlockFire createFire() {
        BlockFire fire = new BlockFire();
        fire.setRegistryName(new ResourceLocation("minecraft", "fire"));
        return fire;
    }

    private static BlockChest createChest(String name, BlockChest.Type type) {
        BlockChest chest = new BlockChest(type);
        chest.setRegistryName(new ResourceLocation("minecraft", name));
        return chest;
    }

    private static BlockPistonBase createPiston(boolean sticky, String name) {
        BlockPistonBase piston = new BlockPistonBase(sticky);
        piston.setRegistryName(new ResourceLocation("minecraft", name));
        return piston;
    }


    private static BlockLog createLog(String name) {
        BlockLog log = new BlockLog();
        log.setRegistryName(new ResourceLocation("minecraft", name));
        return log;
    }

    private static BlockLeaves createLeaves(String name) {
        BlockLeaves leaves = new BlockLeaves();
        leaves.setRegistryName(new ResourceLocation("minecraft", name));
        return leaves;
    }

    private static BlockCactus createCactus() {
        BlockCactus cactus = new BlockCactus();
        cactus.setRegistryName(new ResourceLocation("minecraft", "cactus"));
        return cactus;
    }

    private static BlockReed createReed() {
        BlockReed reed = new BlockReed();
        reed.setRegistryName(new ResourceLocation("minecraft", "reeds"));
        return reed;
    }

    private static BlockDaylightDetector createDaylightDetector() {
        BlockDaylightDetector detector = new BlockDaylightDetector();
        detector.setRegistryName(new ResourceLocation("minecraft", "daylight_detector"));
        return detector;
    }

    private static BlockHopper createHopper() {
        BlockHopper hopper = new BlockHopper();
        hopper.setRegistryName(new ResourceLocation("minecraft", "hopper"));
        return hopper;
    }

    private static BlockTripWireHook createTripWireHook() {
        BlockTripWireHook hook = new BlockTripWireHook();
        hook.setRegistryName(new ResourceLocation("minecraft", "tripwire_hook"));
        return hook;
    }
    private static BlockBeacon createBeacon() {
        BlockBeacon beacon = new BlockBeacon();
        beacon.setRegistryName(new ResourceLocation("minecraft", "beacon"));
        return beacon;
    }
}
