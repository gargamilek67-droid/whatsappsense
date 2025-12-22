package opm.whatsapp.features.command.commands;

import opm.whatsapp.WhatsApp;
import opm.whatsapp.features.command.Command;

public class UnloadCommand
        extends Command {
    public UnloadCommand() {
        super("unload", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        WhatsApp.unload(true);
    }
}

