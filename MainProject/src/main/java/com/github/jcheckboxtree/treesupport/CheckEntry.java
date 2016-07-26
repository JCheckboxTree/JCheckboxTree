package com.github.jcheckboxtree.treesupport;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

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
     * Constructor, with data.
     */
    public CheckEntry(BoxVisible checkboxVisible, boolean checked, String text) {
        super();
        this.checkboxVisible = checkboxVisible;
        this.checked = checked;
        this.text = text;
    }

    /**
     * Constructor, with data and icon.
     */
    public CheckEntry(BoxVisible checkboxVisible, boolean checked, String text, Icon userIcon) {
        super();
        this.checkboxVisible = checkboxVisible;
        this.checked = checked;
        this.text = text;
    }

    /**
     * Constructor, with a user object.
     */
    public CheckEntry(Object userObject) {
        super(userObject);
    }

    /**
     * add, Adds a new child entry to this entry. More specifically, this removes newChild from its
     * parent and makes it a child of this entry by adding it to the end of this entries child
     * array.
     *
     * Note: Only instances of CheckEntry can be added to a JCheckboxTree. This overridden function
     * prevents objects of other types from being added to the tree.
     *
     * @param newChild entry to add as a child of this entry.
     *
     * @exception IllegalArgumentException if newChild is not an instance of CheckEntry, or is null.
     * @exception IllegalStateException if this entry does not allow children.
     *
     * @see #insert
     */
    @Override
    public void add(MutableTreeNode newChild) {
        if (newChild instanceof CheckEntry) {
            super.add(newChild);
        } else {
            throw new IllegalArgumentException("CheckEntry.insert(), "
                    + "Only instances of CheckEntry can be added to a JCheckboxTree.");
        }
    }

    /**
     * Returns the child at the specified index in this node's child array.
     *
     * @param index an index into this node's child array
     * @exception ArrayIndexOutOfBoundsException if <code>index</code> is out of bounds
     * @return the TreeNode in this node's child array at the specified index
     */
    @Override
    public CheckEntry getChildAt(int index) {
        if (children == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
        }
        return (CheckEntry) children.elementAt(index);
    }

    /**
     * getNextEntry, Returns the entry that follows this entry in a "preorder traversal" of this
     * entries tree. Returns null if this entry is the last entry of the traversal.
     *
     * Note: This is a low level function. The TreeIterator class can be used to conveniently loop
     * through the tree entries.
     */
    public CheckEntry getNextEntry() {
        return (CheckEntry) super.getNextNode();
    }

    /**
     * insert, Inserts a new child at a specified index. More specifically, this removes newChild
     * from its present parent (if it has a parent), sets the child's parent to this entry, and then
     * adds the child to this entry's child array at index childIndex.
     *
     * Note: Only instances of CheckEntry can be added to a JCheckboxTree. This overridden function
     * prevents objects of other types from being added to the tree.
     *
     * @param newChild The MutableTreeNode to insert under this entry.
     * @param childIndex The index in this entries child array where this entry is to be inserted.
     *
     * @exception ArrayIndexOutOfBoundsException if childIndex is out of bounds.
     * @exception IllegalArgumentException if newChild is not an instance of CheckEntry, is null, or
     * is an ancestor of this entry.
     * @exception IllegalStateException if this entry does not allow children.
     *
     * @see #isNodeDescendant
     */
    @Override
    public void insert(MutableTreeNode newChild, int childIndex) {
        if (newChild instanceof CheckEntry) {
            super.insert(newChild, childIndex);
        } else {
            throw new IllegalArgumentException("CheckEntry.insert(), "
                    + "Only instances of CheckEntry can be added to a JCheckboxTree.");
        }
    }

    /**
     * isCheckboxVisible, This returns true if the checkbox for this entry is set to be visible.
     * Otherwise returns false.
     */
    public boolean isCheckboxVisible() {
        return (checkboxVisible == BoxVisible.Show);
    }

    /**
     * hasChildren, This returns true if this entry has children. Otherwise, this returns false.
     */
    boolean hasChildren() {
        return (!isLeaf());
    }

}
