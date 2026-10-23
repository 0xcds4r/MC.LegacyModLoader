package net.minecraft.network.datasync;

public class DataSerializers {
    public static final DataSerializer<Byte> BYTE = new DataSerializer<>("byte");
    public static final DataSerializer<Integer> VARINT = new DataSerializer<>("varint");
    public static final DataSerializer<Float> FLOAT = new DataSerializer<>("float");
    public static final DataSerializer<String> STRING = new DataSerializer<>("string");
    public static final DataSerializer<net.minecraft.util.text.ITextComponent> TEXT_COMPONENT = new DataSerializer<>("text");
    public static final DataSerializer<net.minecraft.item.ItemStack> ITEM_STACK = new DataSerializer<>("item_stack");
    public static final DataSerializer<Boolean> BOOLEAN = new DataSerializer<>("boolean");
    public static final DataSerializer<net.minecraft.util.math.BlockPos> BLOCK_POS = new DataSerializer<>("block_pos");
    public static final DataSerializer<net.minecraft.util.EnumFacing> FACING = new DataSerializer<>("facing");
    public static final DataSerializer<java.util.UUID> UNIQUE_ID = new DataSerializer<>("uuid");
    public static final DataSerializer<java.util.Optional<java.util.UUID>> OPTIONAL_UNIQUE_ID =
            new DataSerializer<>("optional_uuid");
    public static final DataSerializer<net.minecraft.util.math.BlockPos> OPTIONAL_BLOCK_POS =
            new DataSerializer<>("optional_block_pos");
    public static final DataSerializer<net.minecraft.nbt.NBTTagCompound> NBT = new DataSerializer<>("nbt");
}
