package net.minecraft.command;

import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface ICommandSender {
    String getName();

    void sendMessage(ITextComponent component);
}
