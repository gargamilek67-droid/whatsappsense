package opm.whatsapp.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import opm.whatsapp.WhatsApp;
import opm.whatsapp.features.command.Command;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("Commands: ");
        for (Command command : WhatsApp.commandManager.getCommands()) {
            HelpCommand.sendMessage(ChatFormatting.GRAY + WhatsApp.commandManager.getPrefix() + command.getName());
        }
    }
}

