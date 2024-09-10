package com.scraxer;

import com.google.inject.Provides;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Inventory Free Slots",
        description = "Displays the number of free inventory slots on the inventory bag icon",
        tags = {"inventory", "free", "slots", "display", "ui"}
)
public class InventoryFreeSlotsPlugin extends Plugin
{
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private InventoryFreeSlotsOverlay inventoryFreeSlotsOverlay;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(inventoryFreeSlotsOverlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(inventoryFreeSlotsOverlay);
    }

    @Provides
    InventoryFreeSlotsConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(InventoryFreeSlotsConfig.class);
    }

    // Subscribe to inventory changes to update free slots
    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event)
    {
        // Trigger an update when the inventory changes
        if (event.getContainerId() == net.runelite.api.InventoryID.INVENTORY.getId())
        {
            inventoryFreeSlotsOverlay.updateFreeSlots();
        }
    }
}
