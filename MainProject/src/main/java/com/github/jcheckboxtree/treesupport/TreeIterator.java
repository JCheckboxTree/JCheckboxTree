package com.github.jcheckboxtree.treesupport;

import com.github.jcheckboxtree.components.JCheckboxTree;
import javax.swing.tree.TreePath;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TreeIterator, This is an iterator that can be used to loop through the CheckEntry instances in a
 * JCheckboxTree. The iterator has settings for including or excluding: the root entry, checked or
 * unchecked entries, and expanded or unexpanded entries. The settings can be controlled using the
 * TreeIterator constructor parameters.
 *
 * If includeRoot is not supplied, the default behavior is to include the root entry only if the
 * root entry is set to be visible in the tree. (If tree.isRootVisible() == true).
 *
 * If a CheckedSpecifier is not supplied, the default behavior is to ignore the checked state and
 * include all entries.
 *
 * If an ExpandedSpecifier is not supplied, the default behavior is to ignore the expanded state and
 * include all entries.
 *
 * Any constructor that only takes a tree parameter (and has no startingEntry), will attempt to loop
 * through the whole tree. Any constructor that takes a startingEntry will attempt to loop through
 * the subtree that begins with the startingEntry. The startingEntry will be included in the
 * iteration unless it does not match one of the other specified parameters.
 */
public class TreeIterator implements Iterator<CheckEntry> {

    /**
     * CheckedSpecifier, This enumeration can optionally be used to specify whether or not to
     * include checked or unchecked entries.
     */
    public enum CheckedSpecifier {
        DoesNotMatter, CheckedOnly, UncheckedOnly
    }

    /**
     * ExpandedSpecifier, This enumeration can optionally be used to specify whether or not to
     * include expanded or collapsed entries.
     */
    public enum ExpandedSpecifier {
        DoesNotMatter, ExpandedOnly, CollapsedOnly
    }

    /**
     * tree, This holds the tree that is being iterated.
     */
    private JCheckboxTree tree;

    /**
     * root, This holds the root entry of the tree that is being iterated.
     */
    private CheckEntry root;

    /**
     * includeRoot, If this is true, then the root entry will be included in an iteration.
     * Otherwise, the root entry will be excluded from the iteration.
     */
    private boolean includeRoot = true;

    /**
     * checkedSpecifier, This controls whether checked or unchecked entries will be included or
     * excluded from the iteration. A value of "DoesNotMatter" means that the setting will have no
     * effect.
     */
    private CheckedSpecifier checkedSpecifier = CheckedSpecifier.DoesNotMatter;

    /**
     * expandedSpecifier, This controls whether expanded or collapsed entries will be included or
     * excluded from the iteration. A value of "DoesNotMatter" means that the setting will have no
     * effect.
     */
    private ExpandedSpecifier expandedSpecifier = ExpandedSpecifier.DoesNotMatter;

    /**
     * previousEntry, This holds the previous entry during the iteration process, or null if the
     * iteration process has not begun.
     */
    private CheckEntry previousEntry = null;

    /**
     * futureEntry, This holds the future entry (the "next" entry) during the iteration process, or
     * null if there are no more entries in the tree that match the supplied parameters. This is
     * initialized from the iterator constructor, and this will not contain null until the last
     * matching entry has been returned from the function called next().
     */
    private CheckEntry futureEntry = null;

    /**
     * Constructor, for the whole tree with default root behavior. This will iterate through the
     * entire tree, with the possible exception of the root entry.
     *
     * If the root entry is visible in the tree (if tree.isRootVisible()), then it will be included
     * in the iteration. Otherwise, the root entry will be excluded from the iteration.
     */
    public TreeIterator(JCheckboxTree tree) {
        this(tree, tree.getRoot(), tree.isRootVisible(), CheckedSpecifier.DoesNotMatter,
                ExpandedSpecifier.DoesNotMatter);
    }

    /**
     * Constructor, for the whole tree with includeRoot. This will iterate through the entire tree.
     * If specified, this will skip over the root entry.
     */
    public TreeIterator(JCheckboxTree tree, boolean includeRoot) {
        this(tree, tree.getRoot(), includeRoot, CheckedSpecifier.DoesNotMatter,
                ExpandedSpecifier.DoesNotMatter);
    }

    /**
     * Constructor, for the whole tree with all options. This will iterate through the entire tree.
     * This will skip over any entries that do not meet all of the specified parameters.
     */
    public TreeIterator(JCheckboxTree tree, boolean includeRoot,
            CheckedSpecifier checkedSpecifier, ExpandedSpecifier expandedSpecifier) {
        this(tree, tree.getRoot(), includeRoot, checkedSpecifier, expandedSpecifier);
    }

    /**
     * Constructor, with a starting entry and default root behavior. This will iterate through the
     * starting entry and any children, with the possible exception of the root entry.
     *
     * If the root entry is visible in the tree (if tree.isRootVisible()), then it will be included
     * in the iteration. Otherwise, the root entry will be excluded from the iteration.
     */
    public TreeIterator(JCheckboxTree tree, CheckEntry startingEntry) {
        this(tree, startingEntry, tree.isRootVisible(), CheckedSpecifier.DoesNotMatter,
                ExpandedSpecifier.DoesNotMatter);
    }

    /**
     * Constructor, with a starting entry and includeRoot. This will iterate through the starting
     * entry and any children. If specified, this will skip over the root entry.
     */
    public TreeIterator(JCheckboxTree tree, CheckEntry startingEntry, boolean includeRoot) {
        this(tree, startingEntry, includeRoot, CheckedSpecifier.DoesNotMatter,
                ExpandedSpecifier.DoesNotMatter);
    }

    /**
     * Constructor, with starting entry and all options. This will iterate through the starting
     * entry and any children. This will skip over any entries that do not meet all of the specified
     * parameters.
     */
    public TreeIterator(JCheckboxTree tree, CheckEntry startingEntry, boolean includeRoot,
            CheckedSpecifier checkedSpecifier, ExpandedSpecifier expandedSpecifier) {
        this.tree = tree;
        this.root = tree.getRoot();
        this.includeRoot = includeRoot;
        this.checkedSpecifier = checkedSpecifier;
        this.expandedSpecifier = expandedSpecifier;
        futureEntry = getFutureEntryOrNull(true, startingEntry, null);
    }

    /**
     * hasNext, This returns true if the iterator has any more entries that match the supplied
     * parameters. Otherwise, this returns false. This function is required by the Iterator
     * interface.
     */
    @Override
    public boolean hasNext() {
        return (futureEntry != null);
    }

    /**
     * next, This returns the next entry that matches the supplied parameters. If there are no more
     * matching entries, this will throw the appropriate NoSuchElementException. Note: This will
     * never throw an exception as long as (hasNext() == true). This function is required by the
     * Iterator interface.
     */
    @Override
    public CheckEntry next() {
        // If we have no more elements, throw the appropriate exception.
        if (futureEntry == null) {
            throw new NoSuchElementException("TreeIterator.next(), "
                    + "This iterator has no more elements.");
        }
        // Save the future entry as the current entry.
        CheckEntry currentEntry = futureEntry;
        // The current entry becomes the previousEntry relative to the next futureEntry.
        // (Say that three times fast.)
        previousEntry = currentEntry;
        // Try to get the next futureEntry.
        futureEntry = getFutureEntryOrNull(false, null, previousEntry);
        // Return the current entry.
        return currentEntry;
    }

    /**
     * getFutureEntryOrNull, This will retrieve the next entry that matches the supplied parameters,
     * or null if no more matching entries exist. This is called one iteration step -before- each
     * entry is needed, so that the hasNext() function will always have the information it needs to
     * indicate whether or not there is a next entry.
     *
     * @param firstCall This should be set to true when this is the first call to this function in
     * an iteration cycle. This should only be the first call when being called from a constructor.
     * @param firstCandidateOrNull This is set to the entry that should be used as the first
     * candidate during the first iteration loop. The first candidate entry may or may not be
     * returned from this function, depending on whether or not it matches the supplied parameters.
     * This parameter is only needed when (firstCall == true).
     * @param previousEntryOrNull This is set to the previous entry. The previous entry will never
     * be returned from this function. This parameter is only needed when (firstCall != true).
     */
    private CheckEntry getFutureEntryOrNull(
            boolean firstCall, CheckEntry firstCandidateOrNull, CheckEntry previousEntryOrNull) {
        if (firstCall && (firstCandidateOrNull == null)) {
            throw new RuntimeException("TreeIterator.getFutureEntryOrNull(), "
                    + "The 'startingEntry' cannot be null during the first call.");
        }
        if (!firstCall && (previousEntryOrNull == null)) {
            throw new RuntimeException("TreeIterator.getFutureEntryOrNull(), "
                    + "The 'previousEntry' cannot be null, except during the first call.");
        }
        boolean firstLoop = true;
        CheckEntry previous = previousEntryOrNull;
        // Loop through the candidates until we find the next entry that matches all the desired 
        // parameters, or until we reached the end of the tree.
        while (true) {
            // Get the candidate entry.
            // We use the firstEntry during the first loop of the first call.
            // Otherwise, we use the next entry returned by the "preorder traversal" methodology.
            CheckEntry candidate = (firstLoop && firstCall)
                    ? firstCandidateOrNull : previous.getNextEntry();
            // Set the firstLoop indicator to false. 
            // Note, we will not need the firstloop variable below this line.
            firstLoop = false;
            // Check to see if we have reached the end of the tree.
            if (candidate == null) {
                // We have reached the end of the tree, so return null.
                return null;
            }
            // Check to see if the candidate matches all the desired parameters.
            // Enforce: (includeRoot == false).
            if ((!includeRoot) && (candidate == root)) {
                previous = candidate;
                continue;
            }
            // If indicated, we can skip the checkedSpecifier.
            if (checkedSpecifier != CheckedSpecifier.DoesNotMatter) {
                // Enforce: CheckedSpecifier.SelectedOnly.
                if ((checkedSpecifier == CheckedSpecifier.CheckedOnly)
                        && (candidate.checked == false)) {
                    previous = candidate;
                    continue;
                }
                // Enforce: CheckedSpecifier.NotSelectedOnly.
                if ((checkedSpecifier == CheckedSpecifier.UncheckedOnly)
                        && (candidate.checked == true)) {
                    previous = candidate;
                    continue;
                }
            }
            // If indicated, we can skip the expandedSpecifier.
            if (expandedSpecifier != ExpandedSpecifier.DoesNotMatter) {
                TreePath path = new TreePath(candidate.getPath());
                // Enforce: ExpandedSpecifier.ExpandedOnly.
                if (expandedSpecifier == ExpandedSpecifier.ExpandedOnly) {
                    if (!(tree.isExpanded(path))) {
                        previous = candidate;
                        continue;
                    }
                }
                // Enforce: ExpandedSpecifier.CollapsedOnly.
                if (expandedSpecifier == ExpandedSpecifier.CollapsedOnly) {
                    if (tree.isExpanded(path)) {
                        previous = candidate;
                        continue;
                    }
                }
            }
            // This candidate matches all the desired parameters, so return the candidate.
            return candidate;
        }
        // End: getFutureEntryOrNull().
    }

}
