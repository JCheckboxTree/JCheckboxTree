package com.github.jcheckboxtree.treesupport;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * CheckEntry, Instances of this class are used as the tree entries (the "nodes") of the checkbox
 * tree. This is the only class type that should be used for checkbox tree entries.
 *
 * If you wish to store custom data types with your tree entries, you can use the function called
 * called CheckEntry.setUserObject(), (which is inherited from the DefaultMutableTreeNode class).
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
     * textBackgroundColor, This holds an optional custom background color for the entry text. If
     * this is null, then a default color will be used.
     */
    public Color textBackgroundColor = null;

    /**
     * textForegroundColor, This holds an optional custom foreground color for the entry text. If
     * this is null, then a default color will be used.
     */
    public Color textForegroundColor = null;

    /**
     * userIcon, This holds an optional user icon for this tree entry.
     *
     * Note: Default tree icons can also be displayed, by using the settings
     * "JCheckBoxTree.iconFallbackFolderNodes" and "JCheckBoxTree.iconFallbackLeafNodes".
     *
     * Note: The minimum row height of the tree will automatically be adjusted to accommodate the
     * maximum height of any visible row icons. See also, JTree.setRowHeight().
     */
    public Icon userIcon = null;

    /**
     * userIconVisible, This indicates if the user icon should be displayed or hidden. The icon will
     * only be displayed if an icon has been supplied in the userIcon variable.
     */
    public boolean userIconVisible = true;

    /**
     * Constructor, default.
     *
     * Usage note, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry() {
        this("", null);
    }

    /**
     * Constructor, with text.
     *
     * Usage note, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry(String text) {
        this(text, null);
    }

    /**
     * Constructor, with text and userObject.
     *
     * Usage note, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry(String text, Object userObject) {
        super(userObject);
        this.text = text;
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
        } else if (newChild == null) {
            throw new IllegalArgumentException("CheckEntry.add(), "
                    + "Added entries can not be null.");
        } else {
            throw new IllegalArgumentException("CheckEntry.add(), "
                    + "Added entries must be an instance or descendant of the CheckEntry class.");
        }
    }

    /**
     * getChildAfter, Returns the child in this node's child array that immediately follows
     * <code>aChild</code>, which must be a child of this node. If <code>aChild</code> is the last
     * child, returns null. This method performs a linear search of this node's children for
     * <code>aChild</code> and is O(n) where n is the number of children; to traverse the entire
     * array of children, use an enumeration instead.
     *
     * @see #children
     * @exception IllegalArgumentException if <code>aChild</code> is null or is not a child of this
     * node
     * @return the child of this node that immediately follows <code>aChild</code>
     */
    public CheckEntry getChildAfter(CheckEntry aChild) {
        return (CheckEntry) super.getChildAfter(aChild);
    }

    /**
     * getChildAt, Returns the child at the specified index in this node's child array.
     *
     * @param index an index into this node's child array
     * @exception ArrayIndexOutOfBoundsException if <code>index</code> is out of bounds
     * @return the TreeNode in this node's child array at the specified index
     */
    @Override
    public CheckEntry getChildAt(int index) {
        return (CheckEntry) super.getChildAt(index);
    }

    /**
     * getChildBefore, Returns the child in this node's child array that immediately precedes
     * <code>aChild</code>, which must be a child of this node. If <code>aChild</code> is the first
     * child, returns null. This method performs a linear search of this node's children for
     * <code>aChild</code> and is O(n) where n is the number of children.
     *
     * @exception IllegalArgumentException if <code>aChild</code> is null or is not a child of this
     * node
     * @return the child of this node that immediately precedes <code>aChild</code>
     */
    public CheckEntry getChildBefore(CheckEntry aChild) {
        return (CheckEntry) super.getChildBefore(aChild);
    }

    /**
     * getFirstChild, Returns this node's first child. If this node has no children, throws
     * NoSuchElementException.
     *
     * @return the first child of this node
     * @exception NoSuchElementException if this node has no children
     */
    @Override
    public CheckEntry getFirstChild() {
        return (CheckEntry) super.getFirstChild();
    }

    /**
     * getFirstLeaf, Finds and returns the first leaf that is a descendant of this node -- either
     * this node or its first child's first leaf. Returns this node if it is a leaf.
     *
     * @see #isLeaf
     * @see #isNodeDescendant
     * @return the first leaf in the subtree rooted at this node
     */
    @Override
    public CheckEntry getFirstLeaf() {
        return (CheckEntry) super.getFirstLeaf();
    }

    /**
     * getLastChild, Returns this node's last child. If this node has no children, throws
     * NoSuchElementException.
     *
     * @return the last child of this node
     * @exception NoSuchElementException if this node has no children
     */
    @Override
    public CheckEntry getLastChild() {
        return (CheckEntry) super.getLastChild();
    }

    /**
     * getLastLeaf, Finds and returns the last leaf that is a descendant of this node -- either this
     * node or its last child's last leaf. Returns this node if it is a leaf.
     *
     * @see #isLeaf
     * @see #isNodeDescendant
     * @return the last leaf in the subtree rooted at this node
     */
    @Override
    public CheckEntry getLastLeaf() {
        return (CheckEntry) super.getLastLeaf();
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
     * getNextLeaf, Returns the leaf after this node or null if this node is the last leaf in the
     * tree.
     * <p>
     * In this implementation of the <code>MutableNode</code> interface, this operation is very
     * inefficient. In order to determine the next node, this method first performs a linear search
     * in the parent's child-list in order to find the current node.
     * <p>
     * That implementation makes the operation suitable for short traversals from a known position.
     * But to traverse all of the leaves in the tree, you should use
     * <code>depthFirstEnumeration</code> to enumerate the nodes in the tree and use
     * <code>isLeaf</code> on each node to determine which are leaves.
     *
     * @see #depthFirstEnumeration
     * @see #isLeaf
     * @return returns the next leaf past this node
     */
    @Override
    public CheckEntry getNextLeaf() {
        return (CheckEntry) super.getNextLeaf();
    }

    /**
     * getPath, Returns the path from the root, to get to this node. The last element in the path is
     * this node.
     *
     * @return an array of TreeNode objects giving the path, where the first element in the path is
     * the root and the last element is this node.
     */
    @Override
    public CheckEntry[] getPath() {
        return (CheckEntry[]) super.getPath();
    }

    /**
     * getPreviousEntry, Returns the node that precedes this node in a preorder traversal of this
     * node's tree. Returns <code>null</code> if this node is the first node of the traversal -- the
     * root of the tree. This is an inefficient way to traverse the entire tree; use an enumeration,
     * instead.
     *
     * @see #preorderEnumeration
     * @return the node that precedes this node in a preorder traversal, or null if this node is the
     * first
     */
    public CheckEntry getPreviousEntry() {
        return (CheckEntry) super.getPreviousNode();
    }

    /**
     * getPreviousLeaf, Returns the leaf before this node or null if this node is the first leaf in
     * the tree.
     * <p>
     * In this implementation of the <code>MutableNode</code> interface, this operation is very
     * inefficient. In order to determine the previous node, this method first performs a linear
     * search in the parent's child-list in order to find the current node.
     * <p>
     * That implementation makes the operation suitable for short traversals from a known position.
     * But to traverse all of the leaves in the tree, you should use
     * <code>depthFirstEnumeration</code> to enumerate the nodes in the tree and use
     * <code>isLeaf</code> on each node to determine which are leaves.
     *
     * @see #depthFirstEnumeration
     * @see #isLeaf
     * @return returns the leaf before this node
     */
    @Override
    public CheckEntry getPreviousLeaf() {
        return (CheckEntry) super.getPreviousLeaf();
    }

    /**
     * getPreviousSibling, Returns the previous sibling of this node in the parent's children array.
     * Returns null if this node has no parent or is the parent's first child. This method performs
     * a linear search that is O(n) where n is the number of children.
     *
     * @return the sibling of this node that immediately precedes this node
     */
    @Override
    public CheckEntry getPreviousSibling() {
        return (CheckEntry) super.getPreviousSibling();
    }

    /**
     * getRoot, Returns the root of the tree that contains this node. The root is the ancestor with
     * a null parent.
     *
     * @see #isNodeAncestor
     * @return the root of the tree that contains this node
     */
    @Override
    public CheckEntry getRoot() {
        return (CheckEntry) super.getRoot();
    }

    /**
     * getSharedAncestor, Returns the nearest common ancestor to this node and <code>aNode</code>.
     * Returns null, if no such ancestor exists -- if this node and <code>aNode</code> are in
     * different trees or if <code>aNode</code> is null. A node is considered an ancestor of itself.
     *
     * @see #isNodeAncestor
     * @see #isNodeDescendant
     * @param aNode node to find common ancestor with
     * @return nearest ancestor common to this node and <code>aNode</code>, or null if none
     */
    public CheckEntry getSharedAncestor(CheckEntry aNode) {
        return (CheckEntry) super.getSharedAncestor(aNode);
    }

    /**
     * hasChildren, This returns true if this entry has children. Otherwise, this returns false.
     */
    boolean hasChildren() {
        return (getChildCount() != 0);
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
        } else if (newChild == null) {
            throw new IllegalArgumentException("CheckEntry.insert(), "
                    + "Inserted entries can not be null.");
        } else {
            throw new IllegalArgumentException("CheckEntry.insert(), "
                    + "Inserted entries must be an instance or descendant of the CheckEntry class.");
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
     * setParent, Sets this node's parent to newParent but does not change the parent's child array.
     * This method is called from insert() and remove() to reassign a child's parent, it should not
     * be called from anywhere else.
     *
     * @param newParent this node's new parent
     */
    @Override
    public void setParent(MutableTreeNode newParent) {
        if ((newParent == null) || (newParent instanceof CheckEntry)) {
            super.setParent(newParent);
        } else {
            throw new IllegalArgumentException("CheckEntry.setParent(), "
                    + "Entries must be an instance or descendant of the CheckEntry class.");
        }
    }

    /**
     * withBoxChecked, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withBoxChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    /**
     * withBoxHidden, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withBoxHidden() {
        this.checkboxVisible = BoxVisible.Hide;
        return this;
    }

    /**
     * withBoxState, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withBoxState(BoxVisible checkboxVisible, boolean checked) {
        this.checkboxVisible = checkboxVisible;
        this.checked = checked;
        return this;
    }

    /**
     * withBoxVisible, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withBoxVisible(BoxVisible checkboxVisible) {
        this.checkboxVisible = checkboxVisible;
        return this;
    }

    /**
     * withColor, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withColor(Color textForegroundColor) {
        this.textForegroundColor = textForegroundColor;
        return this;
    }

    /**
     * withColors, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withColors(Color textBackgroundColor, Color textForegroundColor) {
        this.textBackgroundColor = textBackgroundColor;
        this.textForegroundColor = textForegroundColor;
        return this;
    }

    /**
     * withIcon, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withIcon(Icon userIcon) {
        this.userIcon = userIcon;
        return this;
    }

    /**
     * withIconState, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withIconState(Icon userIcon, boolean userIconVisible) {
        this.userIcon = userIcon;
        this.userIconVisible = userIconVisible;
        return this;
    }

    /**
     * withIconVisible, The withX() functions can be chained to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withIconVisible(boolean userIconVisible) {
        this.userIconVisible = userIconVisible;
        return this;
    }

    /**
     * withObject, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withObject(Object userObject) {
        this.userObject = userObject;
        return this;
    }

    /**
     * withText, The withX() functions can be chained together to set construction parameters.
     * Example: CheckEntry entry = new CheckEntry("Hello").withBoxHidden().withIcon(icon);
     */
    public CheckEntry withText(String text) {
        this.text = text;
        return this;
    }

}
