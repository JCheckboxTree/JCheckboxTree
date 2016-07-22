package com.github.jcheckboxtree.treesupport;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * CheckEntry, Instances of this class are used as the tree entries (the "nodes") of the checkbox
 * tree. This is the only class type that should be used for checkbox tree entries.
 *
 * If you wish to store custom data types with your tree entries, you can use the function called
 * called CheckEntry.setUserObject(), which is inherited from DefaultMutableTreeNode.
 */
public class CheckEntry extends DefaultMutableTreeNode {

    /**
     * checkboxVisible, This indicates whether the checkbox should be shown or hidden.
     */
    public BoxVisible checkboxVisible = BoxVisible.Show;

    /**
     * checked, This indicates if the checkbox is selected or unselected. (Is checked or unchecked).
     */
    public boolean checked = false;

    /**
     * text, This holds the text that should be displayed for this entry.
     */
    public String text = "";

    /**
     * userIcon, This holds an optional user icon for this tree entry. Note that default tree icons
     * can also be displayed, by using the settings "JCheckBoxTree.iconFallbackFolderNodes" and
     * "JCheckBoxTree.iconFallbackLeafNodes".
     */
    public Icon userIcon = null;

    /**
     * userIconVisible, This indicates if the user icon should be displayed or hidden. The icon will
     * only be displayed if an icon has been supplied in the userIcon variable.
     */
    public boolean userIconVisible = true;

    /**
     * Constructor, default.
     */
    public CheckEntry() {
        super();
    }

    /**
     * Constructor, with a user object.
     */
    public CheckEntry(Object userObject) {
        super(userObject);
    }

    /**
     * isCheckboxVisible, This returns true if the checkbox for this entry is set to be visible.
     * Otherwise returns false.
     */
    public boolean isCheckboxVisible() {
        return (checkboxVisible == BoxVisible.Show);
    }

}
