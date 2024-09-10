package net.runelite.client.plugins.inventoryfreeslots;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("inventoryfreeslots")
public interface InventoryFreeSlotsConfig extends Config
{
    @ConfigItem(
            keyName = "fontSize",
            name = "Font Size",
            description = "Set the font size for the inventory free slots display"
    )
    default int fontSize()
    {
        return 14;
    }

    @ConfigItem(
            keyName = "enableColorChange",
            name = "Enable Color Change",
            description = "Enable or disable the color change based on free slots percentage"
    )
    default boolean enableColorChange()
    {
        return true;
    }

    @ConfigItem(
            keyName = "enableBorder",
            name = "Enable Border",
            description = "Enable or disable the border around the text"
    )
    default boolean enableBorder()
    {
        return true;
    }
}
