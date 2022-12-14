package io.github.maheevil.ordinarytweaks.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "ordinarytweaks")
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.PrefixText
    public boolean invisibleShield = false;
    public boolean invisibleTotem = false;
    public boolean betterHorseHUD = false;
    public boolean fullBright = false;
    @ConfigEntry.Gui.PrefixText
    public boolean hideTutorialToasts = true;
    public boolean hideAdvancementToasts = false;
    public boolean hideRecipeUnlockToasts = false;
    @ConfigEntry.Gui.PrefixText
    public boolean deathCordsClipBoardButton = true;
    public boolean sendDeathCords = false;
    public boolean disablePortalGUIClosing = false;
    public boolean doNotPlantEdiblesIfHungry = false;
    public boolean noDoubleSlabPlacement = false;
    public boolean skipResourcePackDownload = false;

}