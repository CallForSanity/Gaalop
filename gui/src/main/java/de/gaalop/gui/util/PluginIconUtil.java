package de.gaalop.gui.util;

import de.gaalop.Plugin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This utility class handles the Plugin icons.
 */
public abstract class PluginIconUtil {
    private static final int SMALL_ICON_WIDTH = 16;

    /**
     * Creates a small (16x16 pixel size) Icon from the icon provided by the Plugin.
     *
     * @param plugin The plugin providing the image.
     * @return A 16x16 version of the plugins icon. If the plugin has no icon, a transparent 16x16 icon will
     *         be created.
     */
    public static Icon getSmallIcon(Plugin plugin) {
        Image image = plugin.getIcon();

        if (image == null) {
            return createTransparentIcon();
        }

        Image scaledImage = image.getScaledInstance(SMALL_ICON_WIDTH, SMALL_ICON_WIDTH, BufferedImage.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }

    private static Icon createTransparentIcon() {
        BufferedImage emptyImage = new BufferedImage(SMALL_ICON_WIDTH, SMALL_ICON_WIDTH, BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(emptyImage);
    }

}
