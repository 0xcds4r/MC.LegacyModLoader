package net.minecraft.block.material;

public class MapColor {
    public static final MapColor AIR = new MapColor(0, 0);
    public static final MapColor GRASS = new MapColor(1, 8368696);
    public static final MapColor SAND = new MapColor(2, 16247203);
    public static final MapColor WOOL = new MapColor(3, 13092807);
    public static final MapColor FIRE = new MapColor(4, 16711680);
    public static final MapColor ICE = new MapColor(5, 10526975);
    public static final MapColor METAL = new MapColor(6, 10987431);
    public static final MapColor PLANT = new MapColor(7, 31744);
    public static final MapColor SNOW = new MapColor(8, 16777215);
    public static final MapColor CLAY = new MapColor(9, 10791096);
    public static final MapColor DIRT = new MapColor(10, 9923917);
    public static final MapColor STONE = new MapColor(11, 7368816);
    public static final MapColor WATER = new MapColor(12, 4210943);
    public static final MapColor WOOD = new MapColor(13, 9402184);
    public static final MapColor QUARTZ = new MapColor(14, 16776437);
    public static final MapColor COLOR_ORANGE = new MapColor(15, 14188339);
    public static final MapColor COLOR_MAGENTA = new MapColor(16, 11685080);
    public static final MapColor COLOR_LIGHT_BLUE = new MapColor(17, 6724056);
    public static final MapColor COLOR_YELLOW = new MapColor(18, 15066419);
    public static final MapColor COLOR_LIGHT_GREEN = new MapColor(19, 8375321);
    public static final MapColor COLOR_PINK = new MapColor(20, 15892389);
    public static final MapColor COLOR_GRAY = new MapColor(21, 5000268);
    public static final MapColor COLOR_LIGHT_GRAY = new MapColor(22, 10066329);
    public static final MapColor COLOR_CYAN = new MapColor(23, 5013401);
    public static final MapColor COLOR_PURPLE = new MapColor(24, 8339378);
    public static final MapColor COLOR_BLUE = new MapColor(25, 3361970);
    public static final MapColor COLOR_BROWN = new MapColor(26, 6704179);
    public static final MapColor COLOR_GREEN = new MapColor(27, 6717235);
    public static final MapColor COLOR_RED = new MapColor(28, 10040115);
    public static final MapColor COLOR_BLACK = new MapColor(29, 1644825);
    public static final MapColor ORANGE = COLOR_ORANGE;
    public static final MapColor MAGENTA = COLOR_MAGENTA;
    public static final MapColor LIGHT_BLUE = COLOR_LIGHT_BLUE;
    public static final MapColor YELLOW = COLOR_YELLOW;
    public static final MapColor LIME = COLOR_LIGHT_GREEN;
    public static final MapColor PINK = COLOR_PINK;
    public static final MapColor GRAY = COLOR_GRAY;
    public static final MapColor SILVER = COLOR_LIGHT_GRAY;
    public static final MapColor CYAN = COLOR_CYAN;
    public static final MapColor PURPLE = COLOR_PURPLE;
    public static final MapColor BLUE = COLOR_BLUE;
    public static final MapColor BROWN = COLOR_BROWN;
    public static final MapColor GREEN = COLOR_GREEN;
    public static final MapColor RED = COLOR_RED;
    public static final MapColor BLACK = COLOR_BLACK;

    private final int colorIndex;
    private final int colorValue;

    public MapColor(int index, int color) {
        this.colorIndex = index;
        this.colorValue = color;
    }

    public int colorIndex() {
        return colorIndex;
    }

    public int colorValue() {
        return colorValue;
    }
}
