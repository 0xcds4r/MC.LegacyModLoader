package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

import java.util.Collections;
import java.util.List;

public abstract class CommandBase implements ICommand {
    private final String name;

    protected CommandBase(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + name;
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    public static int parseInt(String value) throws NumberInvalidException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberInvalidException("commands.generic.num.invalid", value);
        }
    }
}
