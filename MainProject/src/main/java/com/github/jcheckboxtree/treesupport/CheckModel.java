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
    public CheckModel(TreeNode root) {
        super(root);
        verifyRootEntry();
    }

    /**
     * Constructor, with root entry and asksAllowsChildren. See also, the function:
     * DefaultTreeModel.setAsksAllowsChildren()
     */
    public CheckModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
        verifyRootEntry();
    }

    /**
     * setRoot, Sets the root entry. A root entry must be an instance of CheckEntry, and cannot be
     * null.
     */
    @Override
    public void setRoot(TreeNode rootEntry) {
        super.setRoot(root);
        verifyRootEntry();
    }

    /**
     * verifyRootEntry, This verifies that the root entry is not null, and is an instance of
     * CheckEntry. If the root entry does not meet these criteria, then this function will throw an
     * exception.
     */
    private void verifyRootEntry() {
        Object rootEntry = getRoot();
        if (rootEntry == null) {
            throw new RuntimeException("CheckModel.setRoot(), "
                    + "The root entry cannot be null.");
        }
        if (!(rootEntry instanceof CheckEntry)) {
            throw new RuntimeException("CheckModel.setRoot(), "
                    + "The root entry must be an instance of CheckEntry.");
        }
    }
}
