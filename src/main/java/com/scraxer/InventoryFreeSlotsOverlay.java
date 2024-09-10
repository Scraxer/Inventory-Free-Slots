package com.scraxer;

import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

public class InventoryFreeSlotsOverlay extends Overlay
{
    private final Client client;

    @Inject
    public InventoryFreeSlotsOverlay(Client client)
    {
        this.client = client;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        int freeInventorySlots = calculateFreeInventorySlots();

        // Get the inventory icon widget
        Widget inventoryIcon = client.getWidget(WidgetInfo.FIXED_VIEWPORT_INVENTORY_TAB);
        if (inventoryIcon != null)
        {
            // Position the text over the inventory icon
            Point iconLocation = inventoryIcon.getCanvasLocation();
            graphics.setFont(new Font("Arial", Font.BOLD, 14));
            graphics.setColor(Color.WHITE);

            // Draw the number of free slots at the center of the icon
            String text = String.valueOf(freeInventorySlots);
            graphics.drawString(text, iconLocation.getX() + 10, iconLocation.getY() + 30);
        }

        return null;
    }

    private int calculateFreeInventorySlots()
    {
        return (int) client.getItemContainer(InventoryID.INVENTORY)
                .getItems().stream()
                .filter(item -> item.getId() == -1) // -1 indicates an empty slot
                .count();
    }
}
