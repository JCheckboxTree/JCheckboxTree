package com.github.jcheckboxtree.treesupport;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * CheckModel, This class provides a light extension of the DefaultTreeModel class. The primary
 * purpose of this class is to ensure that only CheckEntry instances are added to the tree model.
 */
public class CheckModel extends DefaultTreeModel {

    /**
     * Constructor, with root entry.
     */
    public CheckModel(CheckEntry root) {
        super(root);
        verifyRootEntry();
    }

    /**
     * Constructor, with root entry and asksAllowsChildren. See also, the function:
     * DefaultTreeModel.setAsksAllowsChildren()
     */
    public CheckModel(CheckEntry root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
        verifyRootEntry();
    }

    /**
     * addEntryInto, Invoked this to add newChild to the parent. This will then message
     * nodesWereInserted with the correct child index to create the notification event. This is the
     * preferred way to add children as it will create the appropriate event.
     */
    public void addEntryInto(CheckEntry newChild, CheckEntry parent) {
        insertNodeInto(newChild, parent, parent.getChildCount());
    }

    /**
     * getPathToRoot, Builds the parents of the entry up to and including the root entry. The
     * original entry is the last element in the returned array.
     *
     * @param entry The CheckEntry to get the path for.
     */
    public CheckEntry[] getPathToRoot(CheckEntry entry) {
        return (CheckEntry[]) super.getPathToRoot(entry);
    }

    /**
     * getRoot, Returns the root of the tree. Returns null only if the tree has no nodes.
     *
     * @return the root of the tree
     */
    @Override
    public CheckEntry getRoot() {
        return (CheckEntry) root;
    }

    /**
     * insertEntryInto, Invoked this to insert newChild at location index in parents children. This
     * will then message nodesWereInserted to create the notification event. This is the preferred
     * way to insert children as it will create the appropriate event.
     */
    public void insertEntryInto(CheckEntry newChild, CheckEntry parent, int index) {
        super.insertNodeInto(newChild, parent, index);
    }

    /**
     * setRoot, Sets the root entry. A root entry must either be an instance of CheckEntry, or be
     * null.
     */
    @Override
    public void setRoot(TreeNode rootEntry) {
        super.setRoot(root);
        verifyRootEntry();
    }

    /**
     * removeEntryFromParent, Message this to remove an entry from its parent. This will message
     * nodesWereRemoved to create the appropriate event. This is the preferred way to remove an
     * entry as it handles the event creation for you.
     */
    public void removeEntryFromParent(CheckEntry entry) {
        super.removeNodeFromParent(entry);
    }

    /**
     * verifyRootEntry, This verifies is either an instance or descendant of CheckEntry, or is null.
     * If the root entry does not meet these criteria, then this function will throw an exception.
     */
    private void verifyRootEntry() {
        Object rootEntry = getRoot();
        if (rootEntry == null) {
            // Null is allowed for the root entry. 
            return;
        }
        if (!(rootEntry instanceof CheckEntry)) {
            throw new RuntimeException("CheckModel.setRoot(), "
                    + "The root entry must be an instance of CheckEntry.");
        }
    }
}
