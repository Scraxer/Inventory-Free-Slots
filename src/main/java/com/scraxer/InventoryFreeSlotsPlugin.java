package com.scraxer;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import javax.inject.Inject;

@PluginDescriptor(
    name = "Inventory Free Slots",
    description = "Displays the number of free inventory slots on the inventory bag icon",
    tags = {"inventory", "slots", "ui"}
)
public class InventoryFreeSlotsPlugin extends Plugin
{
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private InventoryFreeSlotsOverlay overlay;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }
}

