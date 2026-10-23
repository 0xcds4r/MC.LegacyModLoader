package net.minecraft.creativetab;

/**
 * Creative tab handle. In 1.12 a tab is a mutable object that mods pass to
 * {@code Block#setCreativeTab}; modern tabs are registry-driven, so the bridge
 * records this value and currently does not add entries to a tab (use /give in
 * the MVP).
 */
public class CreativeTabs {
    private static int nextId;
    public static final CreativeTabs TAB_BUILDING_BLOCKS = new CreativeTabs(0, "buildingBlocks");
    public static final CreativeTabs TAB_DECORATIONS = new CreativeTabs(1, "decorations");
    public static final CreativeTabs TAB_REDSTONE = new CreativeTabs(2, "redstone");
    public static final CreativeTabs TAB_TRANSPORTATION = new CreativeTabs(3, "transportation");
    public static final CreativeTabs TAB_MISC = new CreativeTabs(4, "misc");
    public static final CreativeTabs TAB_SEARCH = new CreativeTabs(5, "search");
    public static final CreativeTabs TAB_COMBAT = new CreativeTabs(6, "combat");
    public static final CreativeTabs TAB_FOOD = new CreativeTabs(7, "food");
    public static final CreativeTabs TAB_TOOLS = new CreativeTabs(8, "tools");
    public static final CreativeTabs TAB_COMBAT_ALT = TAB_COMBAT;
    public static final CreativeTabs TAB_BREWING = new CreativeTabs(9, "brewing");
    public static final CreativeTabs TAB_MATERIALS = new CreativeTabs(10, "materials");
    public static final CreativeTabs TAB_INVENTORY = new CreativeTabs(11, "inventory");

    private final int index;
    private final String label;

    public CreativeTabs(int index, String label) {
        this.index = index;
        this.label = label;
    }

    public int getTabIndex() {
        return index;
    }

    public static int getNextID() {
        return nextId++;
    }

    public String getTabLabel() {
        return label;
    }

    public String getTranslatedTabLabel() {
        return "itemGroup." + label;
    }

    public boolean hasSearchBar() {
        return this == TAB_SEARCH;
    }
}
