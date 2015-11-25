package net.simonvoid.view.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stephan on 25.11.2015.
 */
public class ComponentUtils {
    public static JPanel wrapInFlowLayoutPanel(JComponent component, int align, int hgap, int vgap) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(align, hgap, vgap));
        panel.add(component);
        return panel;
    }

    public static void forceRepaint(Component component) {
        component.revalidate();
        component.repaint();
    }
}
