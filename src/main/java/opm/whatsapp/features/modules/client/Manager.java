package opm.whatsapp.features.modules.client;


import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import opm.whatsapp.WhatsApp;
import opm.whatsapp.api.event.events.PacketEvent;
import opm.whatsapp.features.command.Command;
import opm.whatsapp.features.modules.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import opm.whatsapp.features.setting.Setting;
import opm.whatsapp.api.manager.ChatManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;

import java.util.HashMap;

public class Manager extends Module {
    public static HashMap<String, Integer> TotemPopContainer = new HashMap<>();
    public static Manager INSTANCE = new Manager();
    public Setting<Boolean> flag = register(new Setting("Rubberband", Boolean.valueOf(false), "NoCheatPlus config issue"));
    public Setting<Boolean> notifyToggles = register(new Setting("Toggle Modules", Boolean.valueOf(true), "notifys in chat"));
//   public Setting<Boolean> notifyPearl = register(new Setting("Pearl Notify", Boolean.valueOf(false), "notifys in chat"));

    public enum ClientName {whatsapp, LGBT, Skeet, Neverlose}

    public final Setting<ClientName> clientname = register(new Setting<>("PopMode", ClientName.LGBT));

    private static final int SENDER_ID = 1;

    public Manager() {
        super("Manager", "Notify you about any shit", Category.CLIENT, true, false, false);
        setInstance();
    }

    public static Manager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Manager();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        TotemPopContainer.clear();
    }

    public void onTotemPop(EntityPlayer player) {
        if (Manager.fullNullCheck()) return;
        if (Manager.mc.player.equals(player)) return;

        int count = TotemPopContainer.getOrDefault(player.getName(), 0) + 1;
        TotemPopContainer.put(player.getName(), count);

        ChatManager chatManager = getChatManager();
        if (chatManager == null) return;

        String message = getPopMessage(player.getName(), count);
        ITextComponent component = new TextComponentString(message);
        chatManager.replace(component, player.getName() + "_pop", SENDER_ID, true);
    }

    public void onDeath(EntityPlayer player) {
        if (player == null) return;
        TotemPopContainer.remove(player.getName());
        ChatManager chatManager = getChatManager();
    }

    private String getPopMessage(String player, int count) {
        switch (clientname.getValue()) {
            case whatsapp:
                return "" +
                        ChatFormatting.DARK_RED + ChatFormatting.BOLD + "whatsapp" +
                        ChatFormatting.GRAY + ChatFormatting.BOLD + ".cz " +
                        ChatFormatting.WHITE + player + " popped " +
                        ChatFormatting.DARK_RED + ChatFormatting.BOLD + count +
                        ChatFormatting.DARK_RED + ChatFormatting.BOLD + this.getPopString(count) +
                        ChatFormatting.WHITE + " totem" + (count == 1 ? "" : "s");
            case LGBT:
                return ChatFormatting.GRAY + "[" +
                        ChatFormatting.RED + "P" +
                        ChatFormatting.GOLD + "o" +
                        ChatFormatting.YELLOW + "r" +
                        ChatFormatting.GREEN + "n" +
                        ChatFormatting.AQUA + "h" +
                        ChatFormatting.BLUE + "u" +
                        ChatFormatting.LIGHT_PURPLE + "b" +
                        ChatFormatting.DARK_PURPLE + ".com" +
                        ChatFormatting.GRAY + "] " +
                        ChatFormatting.WHITE + player + " popped " +
                        ChatFormatting.LIGHT_PURPLE + count +
                        ChatFormatting.WHITE + " totem" + (count == 1 ? "" : "s") + " mhh";
            case Skeet:
                return ChatFormatting.GRAY + "[" +
                        ChatFormatting.WHITE + "game" +
                        ChatFormatting.GREEN + "sense" +
                        ChatFormatting.GRAY + "] " +
                        ChatFormatting.WHITE + player + " popped " +
                        ChatFormatting.DARK_PURPLE + count +
                        ChatFormatting.WHITE + " totem" + (count == 1 ? "" : "s") + " hhhh";
            case Neverlose:
                return ChatFormatting.GRAY + "[" +
                        ChatFormatting.BLUE + "never" +
                        ChatFormatting.BLUE + "lose" +
                        ChatFormatting.WHITE + ".cc" +
                        ChatFormatting.GRAY + "] " +
                        ChatFormatting.WHITE + player + " popped " +
                        ChatFormatting.LIGHT_PURPLE + count +
                        ChatFormatting.WHITE + " totem" + (count == 1 ? "" : "s") + " hc: 100%";
            default:
                return player + " popped " + count + " totem" + (count == 1 ? "" : "s") + "!";
        }
    }

    public String getPopString(int pops) {
        if (pops == 1) {
            return "st";
        } else if (pops == 2) {
            return "nd";
        } else if (pops == 3) {
            return "rd";
        } else if (pops >= 4 && pops < 21) {
            return "th";
        } else {
            int lastDigit = pops % 10;
            if (lastDigit == 1) {
                return "st";
            } else if (lastDigit == 2) {
                return "nd";
            } else {
                return lastDigit == 3 ? "rd" : "th";
            }
        }
    }

    public static boolean spawnCheck() {
        return (mc.player.ticksExisted > 15);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (flag.getValue().booleanValue()) {
            if (!fullNullCheck() && spawnCheck()) {

                if (event.getPacket() instanceof SPacketPlayerPosLook) {
                    Command.sendSilentMessage("" + ChatFormatting.DARK_RED + ChatFormatting.BOLD + "whatsapp" + ChatFormatting.GRAY + ChatFormatting.BOLD + ".cz" + " " + ChatFormatting.RED + "Detected Lagback");
                }
            }
        }
    }
        private ChatManager getChatManager () {
            return WhatsApp.chatManager;
        }
    }

