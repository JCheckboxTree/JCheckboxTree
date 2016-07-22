package com.github.jcheckboxtree.treesupport;

import com.privatejgoodiesjcheckboxtree.forms.factories.CC;
import com.privatejgoodiesjcheckboxtree.forms.layout.FormLayout;
import com.github.jcheckboxtree.components.JCheckboxTree;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.TreeCellRenderer;

/**
 * CheckCellRenderer, This class is used for displaying checkbox tree entries. This implements the
 * TreeCellRenderer interface.
 *
 * This class has a panel, a checkbox, and a customized DefaultTreeCellRenderer. The default
 * renderer does a significant portion of the work for drawing the tree entries.
 */
public class CheckCellRenderer extends JPanel
        implements TreeCellRenderer {

    /**
     * defaultRendererLabel, This holds our instance of the custom default tree cell renderer. This
     * is used for rendering the label portion of the tree entries. (This renders the right hand
     * side of the tree entries.)
     */
    private CustomDefaultTreeCellRenderer defaultRendererLabel;

    /**
     * iconClosedFolder, This holds the default icon for closed folder entries.
     */
    private transient Icon iconClosedFolder;

    /**
     * iconLeaf, This holds the default icon for leaf entries.
     */
    private transient Icon iconLeaf;

    /**
     * iconOpenFolder, This holds the default icon for open folder entries.
     */
    private transient Icon iconOpenFolder;

    /**
     * selectionBackgroundColor, This holds the default color for selected entry background areas.
     */
    private final Color selectionBackgroundColor;

    /**
     * selectionForegroundColor, This holds the default color for selected entry foreground areas.
     */
    private final Color selectionForegroundColor;

    /**
     * textBackgroundColor, This holds the default color for unselected entry background areas.
     */
    private final Color textBackgroundColor;

    /**
     * textForegroundColor, This holds the default color for unselected entry foreground areas.
     */
    private final Color textForegroundColor;

    /**
     * tree, This holds the tree associated with this renderer.
     */
    private final JCheckboxTree tree;

    /**
     * Constructor.
     */
    public CheckCellRenderer(JCheckboxTree tree) {
        this.tree = tree;
        initComponents();

        // Create and initialize the defaultRendererLabel.
        defaultRendererLabel = new CustomDefaultTreeCellRenderer();
        defaultRendererLabel.drawsFocusBorderAroundIcon = false;
        defaultRendererLabel.drawDashedFocusIndicator = false;
        defaultRendererLabel.drawSolidFocusIndicator = false;
        defaultRendererPanel.add(defaultRendererLabel, CC.xy(1, 1));

        // Initialize the default fallback icons.
        iconOpenFolder = defaultRendererLabel.getDefaultOpenIcon();
        iconClosedFolder = defaultRendererLabel.getDefaultClosedIcon();
        iconLeaf = defaultRendererLabel.getDefaultLeafIcon();

        final Font fontValue = UIManager.getFont("Tree.font");
        if (fontValue != null) {
            defaultRendererLabel.setFont(fontValue);
        }

        final Boolean focusPaintedObject
                = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
        boolean focusPainted = (focusPaintedObject != null && focusPaintedObject);
        checkbox.setFocusPainted(focusPainted);

        selectionBackgroundColor = UIManager.getColor("Tree.selectionBackground");
        selectionForegroundColor = UIManager.getColor("Tree.selectionForeground");
        textBackgroundColor = UIManager.getColor("Tree.textBackground");
        textForegroundColor = UIManager.getColor("Tree.textForeground");
    }

    /**
     * getCheckbox, This is an accessor for the specified rendering component.
     */
    public JCheckBox getCheckbox() {
        return checkbox;
    }

    /**
     * getCheckboxActivationZonePanel, This is an accessor for the specified rendering component.
     */
    public JPanel getComponentCheckboxActivationZonePanel() {
        return checkboxActivationZonePanel;
    }

    /**
     * getDefaultRendererLabel, This is an accessor for the specified rendering component.
     */
    public CustomDefaultTreeCellRenderer getComponentDefaultRendererLabel() {
        return defaultRendererLabel;
    }

    /**
     * getDefaultRendererPanel, This is an accessor for the specified rendering component.
     */
    public JPanel getComponentDefaultRendererPanel() {
        return defaultRendererPanel;
    }

    /**
     * getLeftSideBackgroundPanel, This is an accessor for the specified rendering component.
     */
    public JPanel getComponentLeftSideBackgroundPanel() {
        return leftSideBackgroundPanel;
    }

    /**
     * getRightSideBackgroundPanel, This is an accessor for the specified rendering component.
     */
    public JPanel getComponentRightSideBackgroundPanel() {
        return rightSideBackgroundPanel;
    }

    /**
     * getPreferredSize, This returns the preferred size of the cell renderer.
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension checkboxPreferredSize = checkbox.getPreferredSize();
        Dimension defaultRendererPreferredSize = defaultRendererLabel.getPreferredSize();
        int treeWidth = tree.getBounds().width;
        int estimatedRowWidth = leftSideBackgroundPanel.getPreferredSize().width
                + defaultRendererPreferredSize.width + 800;
        int width = Math.max(treeWidth, estimatedRowWidth);
        width += 0;
        int height = Math.max(checkboxPreferredSize.height, defaultRendererPreferredSize.height);
        height += 1;
        return new Dimension(width, height);
    }

    /**
     * getTreeCellRendererComponent, This function is required by the TreeCellRenderer interface.
     * This configures and returns the rendering components based on the passed in values.
     */
    @Override
    public Component getTreeCellRendererComponent(JTree possibleTree, Object possibleEntry,
            boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        // Configure the default renderer based on the passed in components.
        defaultRendererLabel.getTreeCellRendererComponent(
                possibleTree, possibleEntry, selected, expanded, leaf, row, hasFocus);

        // If we don't receive the expected class types, then return the default renderer by itself.
        if ((!(possibleEntry instanceof CheckEntry))
                || (!(possibleTree instanceof JCheckboxTree))) {
            return defaultRendererLabel;
        }
        // All received instances are the expected class type.
        JCheckboxTree checkTree = (JCheckboxTree) possibleTree;
        CheckEntry entry = (CheckEntry) possibleEntry;
        // Configure the checkbox state.
        checkbox.setVisible(entry.isCheckboxVisible());
        checkbox.setSelected(entry.checked);

        // Enable or disable the visual components based on the tree enabled setting.
        boolean treeEnabled = checkTree.isEnabled();
        this.setEnabled(treeEnabled);
        defaultRendererPanel.setEnabled(treeEnabled);
        checkbox.setEnabled(treeEnabled);
        defaultRendererLabel.setEnabled(treeEnabled);

        // Configure the areas to match the appropriate selection colors.
        if (selected) {
            // Change the areas to match the selection colors.
            leftSideBackgroundPanel.setForeground(selectionForegroundColor);
            leftSideBackgroundPanel.setBackground(selectionBackgroundColor);
            rightSideBackgroundPanel.setForeground(selectionForegroundColor);
            rightSideBackgroundPanel.setBackground(selectionBackgroundColor);
            defaultRendererPanel.setForeground(selectionForegroundColor);
            defaultRendererPanel.setBackground(selectionBackgroundColor);
        } else {
            // Change all areas to match the "normal" (unselected) colors.
            leftSideBackgroundPanel.setForeground(textForegroundColor);
            leftSideBackgroundPanel.setBackground(textBackgroundColor);
            rightSideBackgroundPanel.setForeground(textForegroundColor);
            rightSideBackgroundPanel.setBackground(textBackgroundColor);
            defaultRendererPanel.setForeground(textForegroundColor);
            defaultRendererPanel.setBackground(textBackgroundColor);
        }

        // Set the appropriate icon for this entry.
        // This can also hide the icon, when no icon is desired.
        boolean rowIsFolder = (entry.getChildCount() > 0);
        boolean rowIsExpanded = checkTree.isExpanded(row);
        if (entry.userIconVisible && entry.userIcon != null) {
            defaultRendererLabel.setIcon(entry.userIcon);
        } else if (checkTree.iconFallbackFolderNodes && rowIsFolder && rowIsExpanded) {
            defaultRendererLabel.setIcon(iconOpenFolder);
        } else if (checkTree.iconFallbackFolderNodes && rowIsFolder && (!rowIsExpanded)) {
            defaultRendererLabel.setIcon(iconClosedFolder);
        } else if (checkTree.iconFallbackLeafNodes && (!rowIsFolder)) {
            defaultRendererLabel.setIcon(iconLeaf);
        } else {
            defaultRendererLabel.setIcon(null);
        }
        defaultRendererLabel.revalidate();
        defaultRendererLabel.repaint();

        // Return this class instance. This class is a panel that contains the checkbox and the 
        // default renderer.
        return this;
    }

    /**
     * isPointInCheckbox, Returns true if the supplied point is inside the checkbox for this cell.
     */
    public boolean isPointInCheckboxActivationZone(
            Rectangle rowBounds, Point mouseLocationRelativeToTree) {
        int locationRelativeToRow_X = mouseLocationRelativeToTree.x - rowBounds.x;
        int locationRelativeToRow_Y = mouseLocationRelativeToTree.y - rowBounds.y;
        // We do not have to adjust for the left side panel, because origin of the left side panel 
        // is the same as the upper left corner of the row.
        Rectangle activationZoneBounds = checkboxActivationZonePanel.getBounds();
        int locationRelativeToActivationZone_X = locationRelativeToRow_X - activationZoneBounds.x;
        int locationRelativeToActivationZone_Y = locationRelativeToRow_Y - activationZoneBounds.y;
        boolean inActivationZone = checkboxActivationZonePanel.contains(
                locationRelativeToActivationZone_X, locationRelativeToActivationZone_Y);
        return inActivationZone;
    }

    /**
     * isPointInInactiveZone, Returns true only if the supplied point is in the inactive area
     * outside the checkbox activation zone. This will never return true if the point is inside the
     * checkbox activation zone.
     */
    public boolean isPointInInactiveZone(Rectangle rowBounds, Point mouseLocationRelativeToTree) {
        int locationRelativeToRow_X = mouseLocationRelativeToTree.x - rowBounds.x;
        int locationRelativeToRow_Y = mouseLocationRelativeToTree.y - rowBounds.y;
        // We do not have to adjust for the left side panel, because origin of the left side panel 
        // is the same as the upper left corner of the row.
        return leftSideBackgroundPanel.contains(locationRelativeToRow_X, locationRelativeToRow_Y)
                && !isPointInCheckboxActivationZone(rowBounds, mouseLocationRelativeToTree);
    }

    public JPanel getLeftSideBackgroundPanel() {
        return leftSideBackgroundPanel;
    }

    public JPanel getCheckboxActivationZonePanel() {
        return checkboxActivationZonePanel;
    }

    public JPanel getRightSideBackgroundPanel() {
        return rightSideBackgroundPanel;
    }

    public JPanel getDefaultRendererPanel() {
        return defaultRendererPanel;
    }

    /**
     * initComponents, This function is generated by JFormDesigner, and should not be modified by
     * hand. This creates and initializes the rendering components and panels.
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        leftSideBackgroundPanel = new JPanel();
        checkboxActivationZonePanel = new JPanel();
        checkbox = new JCheckBox();
        rightSideBackgroundPanel = new JPanel();
        defaultRendererPanel = new JPanel();

        //======== this ========
        setLayout(new FormLayout(
                "pref, pref:grow",
                "fill:pref:grow"));

        //======== leftSideBackgroundPanel ========
        {
            leftSideBackgroundPanel.setLayout(new FormLayout(
                    "0px, pref:grow, 0px",
                    "pref:grow, fill:pref, pref:grow"));

            //======== checkboxActivationZonePanel ========
            {
                checkboxActivationZonePanel.setBackground(new Color(102, 255, 102));
                checkboxActivationZonePanel.setOpaque(false);
                checkboxActivationZonePanel.setBorder(new EmptyBorder(2, 2, 2, 2));
                checkboxActivationZonePanel.setLayout(new FormLayout(
                        "pref:grow",
                        "fill:pref"));

                //---- checkbox ----
                checkbox.setMargin(new Insets(0, 0, 0, 0));
                checkboxActivationZonePanel.add(checkbox, CC.xy(1, 1));
            }
            leftSideBackgroundPanel.add(checkboxActivationZonePanel, CC.xy(2, 2));
        }
        add(leftSideBackgroundPanel, CC.xy(1, 1));

        //======== rightSideBackgroundPanel ========
        {
            rightSideBackgroundPanel.setLayout(new FormLayout(
                    "3px, pref:grow, 1px",
                    "pref:grow, fill:pref, pref:grow"));

            //======== defaultRendererPanel ========
            {
                defaultRendererPanel.setBackground(Color.white);
                defaultRendererPanel.setLayout(new FormLayout(
                        "pref:grow",
                        "fill:pref:grow"));
            }
            rightSideBackgroundPanel.add(defaultRendererPanel, CC.xy(2, 2));
        }
        add(rightSideBackgroundPanel, CC.xy(2, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel leftSideBackgroundPanel;
    private JPanel checkboxActivationZonePanel;
    private JCheckBox checkbox;
    private JPanel rightSideBackgroundPanel;
    private JPanel defaultRendererPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
