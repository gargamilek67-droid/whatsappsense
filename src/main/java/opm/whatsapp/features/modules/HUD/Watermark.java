package opm.whatsapp.features.modules.HUD;

import opm.whatsapp.WhatsApp;
import opm.whatsapp.features.modules.Module;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import opm.whatsapp.features.setting.Setting;

public class Watermark extends Module {
    private static Watermark INSTANCE = new Watermark();
    public Watermark() {
        super("Watermark", "Godmode", Category.HUD, true, false, false);
        this.setInstance();
    }

    private final Setting<Integer> x = this.register(new Setting<Integer>("x", 3, 0, 1300));
    private final Setting<Integer> y = this.register(new Setting<Integer>("y", 3, 0, 700));

    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;

        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();


        String firstPart = "whatsappsense";
        String secondPart = ".cz";
        String thirdPart = " " + WhatsApp.MODVER+ "+" + WhatsApp.GITHASH+WhatsApp.GITREVISION;

        int darkRed = 0xFFAA0000;
        int gray = 0xFF808080;
        int white = 0xFFFFFFFF;

        mc.fontRenderer.drawString(firstPart, this.x.getValue(), this.y.getValue(), darkRed, true);

        int firstPartWidth = mc.fontRenderer.getStringWidth(firstPart);
        int secondPartWidth = mc.fontRenderer.getStringWidth(secondPart);
        mc.fontRenderer.drawString(secondPart, this.x.getValue() + firstPartWidth, this.y.getValue(), gray, true);
        mc.fontRenderer.drawString(thirdPart, this.x.getValue() + secondPartWidth + firstPartWidth, this.y.getValue(), white, true);
    }

    public static Watermark getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Watermark();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }
}
