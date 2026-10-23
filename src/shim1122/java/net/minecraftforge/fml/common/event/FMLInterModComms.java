package net.minecraftforge.fml.common.event;

import dev.legacyloader.bridge.LegacyModContext;

import java.util.List;

/**
 * Inter-mod communication shim. Messages are accepted and discarded until the
 * IM bridge is implemented.
 */
public final class FMLInterModComms {
    private FMLInterModComms() {
    }

    public static boolean sendMessage(String modId, String key, Object value) {
        return true;
    }

    public static boolean sendMessage(String modId, String key, String value) {
        return true;
    }

    public static boolean sendMessage(String modId, String key, net.minecraft.item.ItemStack value) {
        return true;
    }

    public static boolean sendMessage(String modId, String key, net.minecraft.nbt.NBTTagCompound value) {
        return true;
    }

    public static class IMCEvent extends FMLEvent {
        public IMCEvent(LegacyModContext context) {
            super(context);
        }

        public List<IMCMessage> getMessages() {
            return List.of();
        }
    }

    public static class IMCMessage {
        private final String sender;
        private final String key;
        private final Object value;

        public IMCMessage(String sender, String key, Object value) {
            this.sender = sender;
            this.key = key;
            this.value = value;
        }

        public String getSender() {
            return sender;
        }

        public String key() {
            return key;
        }

        public String getStringValue() {
            return value instanceof String string ? string : "";
        }

        public Object getValue() {
            return value;
        }
    }
}
