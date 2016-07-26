package com.github.jcheckboxtree.ysandbox;

import com.github.jcheckboxtree.components.JCheckboxTree;
import com.github.jcheckboxtree.treesupport.CheckEntry;
import com.github.jcheckboxtree.treesupport.TreeIterator;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

/**
 *
 */
public class TestStart extends JFrame {

    /**
     * checkboxTree, This holds our checkbox tree.
     */
    private JCheckboxTree tree = null;

    /**
     * main, The entry point for the demo.
     */
    public static void main(String[] args) {
        // Set the desired look and feel.
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
            // You could also set any other desired look and feel, or use the default look and feel.
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

        // Create and run the demo.
        TestStart demo = new TestStart();

        // This line is only here to prevent any IDE warnings about an "unused demo variable".
        // System.out.println(demo);
    }

    /**
     * Constructor, Create and display the demo application.
     */
    public TestStart() {
        // Set up the frame and content panel.
        setTitle("JCheckboxTree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Create an instance of the checkbox tree.
        tree = new JCheckboxTree();

        // Turn on default icons for the tree.
        tree.iconFallbackFolderNodes = true;
        tree.iconFallbackLeafNodes = false;
        
        tree.setRootVisible(true);

        // Add the tree to a scroll pane, and add the scroll pane to the content panel.
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(contentPanel);

        // Display the frame.
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);

        /* Unused previous example code.
            checkboxTree._addNode(BoxVisible.Show, false, "Node 1");
            CheckNode node2 = checkboxTree._addNode(BoxVisible.Show, true, "Node 2, starts checked");
            checkboxTree._addNode(BoxVisible.Show, false, "Node 2a", node2);
            checkboxTree._addNode(BoxVisible.Show, false, "Node 2b", node2);
            checkboxTree._addNode(BoxVisible.Show, false, "Node 2first", node2, 0);
            CheckNode node3 = checkboxTree._addNode(BoxVisible.Show, false, "Node 3");
            checkboxTree._addNode(BoxVisible.Show, false, "Node 3a", node3);
            checkboxTree._addNode(BoxVisible.Show, false, "Node 3b", node3);
            checkboxTree._addNode(BoxVisible.Hide, false, "Node 4, no checkbox");
            checkboxTree.expandAll();
         */
        loopAndPrint();
    }

    private void loopAndPrint() {
        TreeIterator treeIterator = new TreeIterator(tree, tree.getRoot());
        while (treeIterator.hasNext()) {
            CheckEntry currentEntry = treeIterator.next();
            System.out.println(currentEntry.text);
        }
    }

}
