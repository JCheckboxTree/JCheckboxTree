package com.github.jcheckboxtree.components;

import com.github.jcheckboxtree.treesupport.BoxVisible;
import com.github.jcheckboxtree.treesupport.CheckCellRenderer;
import com.github.jcheckboxtree.treesupport.CheckEntry;
import com.github.jcheckboxtree.treesupport.Use;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * JCheckboxTree, This class implements a swing tree component that can have a checkbox on any or
 * all of its tree entries.
 *
 * Every tree entry is an instance of CheckEntry. The CheckEntry class has a "checkboxVisible" field
 * that controls whether or not any particular entry has a visible checkbox. The current checkbox
 * state (selected or unselected) is also stored in the CheckEntry class.
 *
 * The programmer should only add entries to the tree that are instances of CheckEntry, and not any
 * other class type. If you wish to store custom data with the tree entries, you can store any
 * desired data type with each entry by using the function called CheckEntry.setUserObject().
 *
 * Each tree entry may optionally have a custom icon. Custom icons are stored in the CheckEntry
 * class. Additionally, the JCheckboxTree class has settings for optionally using the default tree
 * icons for folder entries or leaf entries. When the default icons are used, they will act as
 * "fallback icons" for any entries with no custom icon.
 *
 * This tree uses a custom tree cell renderer, but does not require a custom tree cell editor class.
 * Changing the state of checkboxes is handled through the JCheckboxTree.processMouseEvent()
 * function.
 */
public class JCheckboxTree extends JTree {

    /**
     * iconFallbackFolderNodes, If this is true, "folder nodes" (nodes with children), will show the
     * default folder icons whenever a custom icon is not being displayed. If this is false, then
     * folder nodes will not have any icon when a custom icon is not being displayed. A displayed
     * custom icon always has priority over these fallback settings.
     */
    public boolean iconFallbackFolderNodes = false;

    /**
     * iconFallbackLeafNodes, If this is true, "leaf nodes" (nodes without children), will show the
     * default leaf icon whenever a custom icon is not being displayed. If this is false, then leaf
     * nodes will not have any icon when a custom icon is not being displayed. A displayed custom
     * icon always has priority over these fallback settings.
     */
    public boolean iconFallbackLeafNodes = false;

    /**
     * rowWidthAddedPixels, This determines the number of pixels that should be added to the width
     * of each tree row. Rows can only be selected by clicking on a space that is inside the total
     * row width, so this number is set to a large value by default. A large value allows the user
     * to select a row by clicking anywhere in the empty space to the right of each row.
     *
     * When this is set to a large value, "selectionsHighlightsEntireRow" should generally be set to
     * true, and the horizontal scrollbar of the tree scroll pane should generally be disabled.
     *
     * If this is set to a low value (or zero), then the user will only be able to select a row by
     * clicking on or near the row text. A possible benefit of a low value is that the horizontal
     * scrollbar would become more usable again.
     *
     * Note that this could also be set to a negative value, but that would run the risk of chopping
     * off some of the row text in certain common situations.
     */
    public int rowWidthAddedPixels = 2000;

    /**
     * selectionBackgroundColor, This is the color that will be used for the background of any
     * selected rows. This is initialized to a default color for the current look and feel, but it
     * can be set to any color that is desired. This color is is used inside this class, and inside
     * the CheckCellRenderer class.
     */
    public Color selectionBackgroundColor;

    /**
     * selectionForegroundColor, This holds the default color for selected entry foreground areas.
     */
    public Color selectionForegroundColor;

    /**
     * selectionsHighlightEntireRow, If this is true, then selecting a row will highlight the entire
     * row, from the left to the right side of the tree area. If this is false, then selecting a row
     * will only highlight the row text, plus any additional pixels that are set in the
     * "rowWidthAddedPixels" variable.
     *
     * When this is set to false, the rowWidthAddedPixels setting should generally be set to a low
     * value or zero.
     *
     * When this is set to true, the tree will automatically be set to setOpaque(false) to
     * accommodate the custom highlight rendering.
     */
    public boolean selectionsHighlightEntireRow = true;

    @Override
    public void paintComponent(Graphics g) {
        if (selectionsHighlightEntireRow) {
            setOpaque(false);
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            if (getSelectionCount() > 0) {
                for (int i : getSelectionRows()) {
                    Rectangle r = getRowBounds(i);
                    g.setColor(selectionBackgroundColor);
                    g.fillRect(0, r.y, getWidth(), r.height);
                }
            }
        }
        super.paintComponent(g);
    }

    /**
     * Constructor with default data, This creates a tree with a set of example checkbox tree data.
     * This constructor is generally used only for creating demo applications.
     */
    public JCheckboxTree() {
        this(getDefaultTreeModel());
        expandAllEntries();
    }

    /**
     * Constructor with DefaultTreeModel, This will create a tree using the supplied tree model. If
     * the model contains any nodes that are not a CheckEntry, then this will throw an exception.
     */
    public JCheckboxTree(DefaultTreeModel treemodel) {
        super(treemodel);
        checkTreeForInvalidNodes();
        initializeTreeSettings();
    }

    /**
     * Constructor with a root node, Creates a tree using the specified node as the root. This root
     * node will be used to create a DefaultTreeModel instance. If the root contains any descendents
     * that are not a CheckEntry, then this will throw an exception.
     */
    public JCheckboxTree(CheckEntry root) {
        super(root);
        checkTreeForInvalidNodes();
        initializeTreeSettings();
    }

    /**
     * checkTreeForInvalidNodes, TODO, This method confirms that the tree contains only CheckboxNode
     * instances. This will throw a RuntimeException if the tree contains any nodes that are not
     * CheckboxNodes.
     */
    private void checkTreeForInvalidNodes() {

    }

    /**
     * expandAllEntries, This will recursively expand all the entries in the tree.
     */
    public void expandAllEntries() {
        expandChildren(new TreePath(getModel().getRoot()));
    }

    /**
     * expandChildren, This will expand all the entries in the supplied tree path.
     */
    private void expandChildren(TreePath path) {
        expandPath(path);
        Object node = path.getLastPathComponent();
        int childrenNumber = getModel().getChildCount(node);
        TreePath[] childrenPath = new TreePath[childrenNumber];
        for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
            childrenPath[childIndex] = path.pathByAddingChild(getModel().getChild(node, childIndex));
            expandChildren(childrenPath[childIndex]);
        }
    }

    /**
     * getDefaultTreeModel, This creates and returns a sample DefaultTreeModel. This is used
     * primarily for demo applications, to show something interesting in the tree. This function
     * hides the protected method of the same name in JTree.
     */
    protected static DefaultTreeModel getDefaultTreeModel() {
        // Get an icon to use in the model. 
        Icon iconOriginal = javax.swing.UIManager.getIcon("OptionPane.informationIcon");
        Image imageOriginal = Use.iconToImage(iconOriginal);
        Image imageScaled = imageOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(imageScaled);
        // Create the tree model. 
        CheckEntry root = new CheckEntry(BoxVisible.Show, false, "JTree");
        CheckEntry custom = new CheckEntry(BoxVisible.Show, false, "Customized Entries");
        root.add(custom);
        custom.add(new CheckEntry(BoxVisible.Show, false, "Entry with a custom image icon.", icon));
        custom.add(new CheckEntry(BoxVisible.Show, true, "Entry that is initially checked."));
        custom.add(new CheckEntry(BoxVisible.Hide, false, "Entry without a checkbox."));
        CheckEntry colors = new CheckEntry(BoxVisible.Show, false, "Colors");
        root.add(colors);
        colors.add(new CheckEntry(BoxVisible.Show, false, "blue", Color.blue));
        colors.add(new CheckEntry(BoxVisible.Show, false, "green", Color.green));
        colors.add(new CheckEntry(BoxVisible.Show, false, "red", Color.red));
        colors.add(new CheckEntry(BoxVisible.Show, false, "orange", Color.orange));
        CheckEntry food = new CheckEntry(BoxVisible.Show, false, "Food");
        root.add(food);
        food.add(new CheckEntry(BoxVisible.Show, false, "hot dogs"));
        food.add(new CheckEntry(BoxVisible.Show, false, "pizza"));
        food.add(new CheckEntry(BoxVisible.Show, false, "ravioli"));
        food.add(new CheckEntry(BoxVisible.Show, false, "bananas"));
        CheckEntry coloredFood = new CheckEntry(BoxVisible.Show, false, "Colorized Food");
        food.add(coloredFood);
        coloredFood.add(new CheckEntry(BoxVisible.Show, false, "Green eggs and ham"));
        coloredFood.add(new CheckEntry(BoxVisible.Show, false, "Skittles bite sized candies"));
        coloredFood.add(new CheckEntry(BoxVisible.Show, false, "Painted Easter eggs"));
        CheckEntry last = new CheckEntry(BoxVisible.Show, false, "More stuff.");
        root.add(last);
        return new DefaultTreeModel(root);
    }

    /**
     * getModelAsDefaultTreeModel, Returns the tree model as a DefaultTreeModel instance.
     */
    public DefaultTreeModel getModelAsDefaultTreeModel() {
        TreeModel model = getModel();
        if (model instanceof DefaultTreeModel) {
            return (DefaultTreeModel) model;
        }
        throw new RuntimeException("JCheckboxTree.getModelAsDefaultTreeModel(), "
                + "The current model is not a DefaultTreeModel instance.");
    }

    /**
     * getRoot, Returns the root entry of this tree. If the tree has no entries, or if the tree has
     * no model, this will return null.
     */
    public CheckEntry getRoot() {
        TreeModel model = getModel();
        if (model == null) {
            return null;
        }
        CheckEntry rootEntry = (CheckEntry) model.getRoot();
        return rootEntry;
    }

    /**
     * initializeTreeSettings, This initializes the tree to have the default settings. Note that
     * this function is not responsible for creating a tree model or a root node. That task should
     * be done by the specific constructors before this function is called.
     */
    private void initializeTreeSettings() {
        setEnabled(true);
        setRootVisible(false);
        setShowsRootHandles(true);
        setRowHeight(0);
        setToggleClickCount(0);
        setCellRenderer(new CheckCellRenderer(this));
        this.selectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        selectionBackgroundColor = UIManager.getColor("Tree.selectionBackground");
        selectionForegroundColor = UIManager.getColor("Tree.selectionForeground");
        // This changes the line style for the metal look and feel.
        putClientProperty("JTree.lineStyle", "Angled");
    }

    /**
     * processMouseEvent, This catches tree mouse pressed events, for changing the state of
     * checkboxes. This overrides the processMouseEvent() function in JTree.
     *
     * Technical note: The checkbox tree was originally implemented using a custom tree cell editor,
     * but unfortunately this caused a bug in some Java "look and feel" settings, where the
     * JCheckBox component could behave erratically. The checkbox would sometimes flicker when the
     * tree selection was changed. Using the processMouseEvent() function instead fixes that bug.
     */
    @Override
    protected void processMouseEvent(MouseEvent event) {
        // This will be set to true if it is detected that the mouse is inside the inactive zone
        // surrounding the outside of the checkbox.
        boolean mouseIsInsideInactiveZone = false;
        // Check to see if the tree is enabled, if the event has not been consumed, and if this 
        // event is a mouse press event.
        if ((isEnabled()) && (!event.isConsumed()) && (event.getID() == MouseEvent.MOUSE_PRESSED)) {
            // Get the event mouse location, relative to the tree.
            Point mouseLocationRelativeToTree = event.getPoint();
            // Check to see if there is a tree row under the mouse location.
            int row = getRowForLocation(mouseLocationRelativeToTree.x,
                    mouseLocationRelativeToTree.y);
            if (row != -1) {
                // A row was found under the location.
                Rectangle rowBounds = getRowBounds(row);
                // Try to get the bounding rectangle for the row.
                if (rowBounds != null) {
                    // The row is not collapsed under a parent node.
                    // Check to see if the mouse location is inside of the row checkbox.
                    CheckCellRenderer treeRenderer = (CheckCellRenderer) cellRenderer;
                    // Check to see if the mouse location is in the inactive zone.
                    mouseIsInsideInactiveZone = treeRenderer.isPointInInactiveZone(
                            rowBounds, mouseLocationRelativeToTree);
                    if (treeRenderer.isPointInCheckboxActivationZone(rowBounds, mouseLocationRelativeToTree)) {
                        // The mouse location is inside of the row checkbox.
                        // Get the CheckEntry instance for this row.
                        TreePath path = getPathForRow(row);
                        Object possibleNode = path.getLastPathComponent();
                        if ((possibleNode != null) && (possibleNode instanceof CheckEntry)) {
                            CheckEntry entry = (CheckEntry) possibleNode;
                            // Find out if the checkbox is visible.
                            if (entry.checkboxVisible == BoxVisible.Show) {
                                // In response to the click, toggle the check state for this node.
                                entry.checked = !entry.checked;
                                getModelAsDefaultTreeModel().nodeChanged(entry);
                            }
                        }
                    }
                }
            }
        }
        // If needed, perform default JTree processing for this click event, such as changing the 
        // tree selection.
        if (!mouseIsInsideInactiveZone) {
            super.processMouseEvent(event);
        }
    }

}
