package opm.whatsapp.features.command.commands;

import opm.whatsapp.WhatsApp;
import opm.whatsapp.features.command.Command;

public class ReloadCommand
        extends Command {
    public ReloadCommand() {
        super("reload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        WhatsApp.reload();
    }
}

