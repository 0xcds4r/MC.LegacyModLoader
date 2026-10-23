package net.minecraft.block.material;

/**
 * Minimal 1.12.2 material shim. Materials are metadata for the bridge; they do
 * not map one-to-one onto modern block properties, so unknown flags are simply
 * recorded and ignored by the adapter.
 */
public class Material {
    public static final Material AIR = new Material("air");
    public static final Material GRASS = new Material("grass");
    public static final Material GROUND = new Material("ground");
    public static final Material WOOD = new Material("wood");
    public static final Material ROCK = new Material("rock");
    public static final Material IRON = new Material("iron");
    public static final Material ANVIL = new Material("anvil");
    public static final Material WATER = new Material("water");
    public static final Material LAVA = new Material("lava");
    public static final Material LEAVES = new Material("leaves");
    public static final Material PLANTS = new Material("plants");
    public static final Material VINE = new Material("vine");
    public static final Material SPONGE = new Material("sponge");
    public static final Material CLOTH = new Material("cloth");
    public static final Material FIRE = new Material("fire");
    public static final Material SAND = new Material("sand");
    public static final Material CIRCUITS = new Material("circuits");
    public static final Material CARPET = new Material("carpet");
    public static final Material GLASS = new Material("glass");
    public static final Material REDSTONE_LIGHT = new Material("redstone_light");
    public static final Material TNT = new Material("tnt");
    public static final Material CORAL = new Material("coral");
    public static final Material ICE = new Material("ice");
    public static final Material PACKED_ICE = new Material("packed_ice");
    public static final Material SNOW = new Material("snow");
    public static final Material CRAFTED_SNOW = new Material("crafted_snow");
    public static final Material CACTUS = new Material("cactus");
    public static final Material CLAY = new Material("clay");
    public static final Material GOURD = new Material("gourd");
    public static final Material DRAGON_EGG = new Material("dragon_egg");
    public static final Material PORTAL = new Material("portal");
    public static final Material CAKE = new Material("cake");
    public static final Material WEB = new Material("web");
    public static final Material PISTON = new Material("piston");
    public static final Material BARRIER = new Material("barrier");
    public static final Material STRUCTURE_VOID = new Material("structure_void");

    private final String name;
    private boolean liquid;
    private boolean solid = true;
    private boolean blocksMovement = true;
    private boolean requiresNoTool = true;
    private boolean canBurn;
    private boolean replaceable;
    private boolean opaque = true;

    public Material(String name) {
        this.name = name;
    }

    public Material(MapColor color) {
        this("map_color_" + color.colorIndex());
    }

    public Material setBoundingBox(double minX, double minY, double minZ,
                                   double maxX, double maxY, double maxZ) {
        this.solid = true;
        return this;
    }

    public Material setReplaceable() {
        this.replaceable = true;
        return this;
    }

    public Material setNoPushMobility() {
        return this;
    }

    public Material setImmovableMobility() {
        return this;
    }

    public Material setAdventureModeExempt() {
        return this;
    }

    public Material setRequiresTool() {
        this.requiresNoTool = false;
        return this;
    }

    public Material setBurning() {
        this.canBurn = true;
        return this;
    }

    public boolean isLiquid() {
        return liquid;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean blocksMovement() {
        return blocksMovement;
    }

    public boolean isReplaceable() {
        return replaceable;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public boolean getCanBurn() {
        return canBurn;
    }

    public boolean isToolNotRequired() {
        return requiresNoTool;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Material{" + name + "}";
    }
}
