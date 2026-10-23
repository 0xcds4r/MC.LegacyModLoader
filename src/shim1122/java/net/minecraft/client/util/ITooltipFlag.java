package net.minecraft.client.util;

public interface ITooltipFlag {
    boolean isAdvanced();

    enum TooltipFlags implements ITooltipFlag {
        NORMAL,
        ADVANCED;

        @Override
        public boolean isAdvanced() {
            return this == ADVANCED;
        }
    }
}
