package com.scraxer;

import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class InventoryFreeSlotsOverlay extends Overlay
{
    private final Client client;
    private final InventoryFreeSlotsConfig config;
    private static final int TOTAL_INVENTORY_SLOTS = 28;
    private int freeSlots = TOTAL_INVENTORY_SLOTS; // Store free slots as a variable

    // Define lighter orange color
    private static final Color LIGHT_ORANGE = new Color(255, 165, 0); // Lighter shade of orange

    @Inject
    public InventoryFreeSlotsOverlay(Client client, InventoryFreeSlotsConfig config)
    {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // Get the font size from the configuration
        int fontSize = config.fontSize();

        // Get the inventory icon widget
        Widget inventoryIcon = client.getWidget(WidgetInfo.RESIZABLE_VIEWPORT_INVENTORY_TAB);
        if (inventoryIcon != null)
        {
            net.runelite.api.Point apiPoint = inventoryIcon.getCanvasLocation();
            java.awt.Point iconLocation = new java.awt.Point(apiPoint.getX(), apiPoint.getY());

            // Set the font based on the user-selected size
            graphics.setFont(new Font("Arial", Font.BOLD, fontSize));

            // Get the text color based on the number of free slots
            Color textColor = Color.WHITE; // Default color
            if (config.enableColorChange())
            {
                double freePercentage = ((double) freeSlots / TOTAL_INVENTORY_SLOTS) * 100;
                if (freePercentage >= 50)
                {
                    textColor = Color.GREEN; // 50% or more free slots
                }
                else if (freePercentage >= 30)
                {
                    textColor = Color.YELLOW; // 30-49% free slots
                }
                else if (freePercentage >= 10)
                {
                    textColor = LIGHT_ORANGE; // Lighter orange for 10-29% free slots
                }
                else
                {
                    textColor = Color.RED; // Less than 10% free slots
                }
            }

            // Draw text with or without border based on the user configuration
            if (config.enableBorder()) // If enableBorder is true, we want the border
            {
                // Draw text with an outline (border effect)
                drawTextWithOutline(graphics, String.valueOf(freeSlots), iconLocation.x + 10, iconLocation.y + 30, textColor, Color.BLACK);
            }
            else
            {
                // Draw plain text without an outline
                graphics.setColor(textColor);
                graphics.drawString(String.valueOf(freeSlots), iconLocation.x + 10, iconLocation.y + 30);
            }
        }

        return null;
    }

    // Method to draw text with an outline/border
    private void drawTextWithOutline(Graphics2D graphics, String text, int x, int y, Color textColor, Color outlineColor)
    {
        // Draw the outline by offsetting the text
        graphics.setColor(outlineColor);
        graphics.drawString(text, x - 1, y - 1); // Top-left
        graphics.drawString(text, x + 1, y - 1); // Top-right
        graphics.drawString(text, x - 1, y + 1); // Bottom-left
        graphics.drawString(text, x + 1, y + 1); // Bottom-right

        // Draw the main text on top
        graphics.setColor(textColor);
        graphics.drawString(text, x, y);
    }

    // Method to calculate and update free slots
    public void updateFreeSlots()
    {
        freeSlots = calculateFreeInventorySlots();
    }

    private int calculateFreeInventorySlots()
    {
        // Get the inventory container
        if (client.getItemContainer(InventoryID.INVENTORY) != null)
        {
            Item[] items = client.getItemContainer(InventoryID.INVENTORY).getItems();

            if (items != null) {
                // Count non-empty slots (i.e., items with ID != -1)
                int occupiedSlots = 0;
                for (Item item : items)
                {
                    if (item.getId() != -1) // Item exists in this slot
                    {
                        occupiedSlots++;
                    }
                }
                return TOTAL_INVENTORY_SLOTS - occupiedSlots; // Free slots are total slots minus occupied slots
            }
        }
        return TOTAL_INVENTORY_SLOTS; // If inventory is null, assume all slots are free
    }
}
