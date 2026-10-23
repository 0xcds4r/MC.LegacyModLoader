package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

import java.util.List;

public interface ICommand {
    String getName();

    String getUsage(ICommandSender sender);

    List<String> getAliases();

    void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;

    boolean checkPermission(MinecraftServer server, ICommandSender sender);

    int getRequiredPermissionLevel();
}
