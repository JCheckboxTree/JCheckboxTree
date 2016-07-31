package com.github.jcheckboxtree.demo;

import com.github.jcheckboxtree.components.JCheckboxTree;
import com.github.jcheckboxtree.treesupport.CheckEntry;
import com.github.jcheckboxtree.treesupport.CheckModel;
import com.github.jcheckboxtree.treesupport.Use;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.ScrollPaneConstants;

/**
 * JCheckboxTreeDemo, This executable class demonstrates basic usage of the JCheckboxTree component.
 */
public class JCheckboxTreeDemo extends JFrame {

    /**
     * checkboxTree, This holds our checkbox tree.
     */
    private JCheckboxTree checkboxTree = null;

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
        JCheckboxTreeDemo demo = new JCheckboxTreeDemo();

        // This line is only here to prevent any IDE warnings about an "unused demo variable".
        System.out.println(demo);
    }

    /**
     * Constructor, Create and display the demo application.
     */
    public JCheckboxTreeDemo() {
        // Set up the frame and content panel.
        setTitle("JCheckboxTree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Create and populate a sample tree model.
        CheckModel treeModel = zGetSampleTreeModel();

        // Create an instance of the checkbox tree.
        checkboxTree = new JCheckboxTree(treeModel);

        // Set up the default icons for the tree.
        checkboxTree.iconFallbackFolderNodes = true;
        checkboxTree.iconFallbackLeafNodes = false;

        // Add the tree to a scroll pane, and add the scroll pane to the content panel.
        JScrollPane scrollPane = new JScrollPane(checkboxTree);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(contentPanel);

        // Display the frame.
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * zGetSampleTreeModel, This creates and returns a sample CheckModel instance. The model is
     * populated with sample tree entries.
     *
     * This function was copied from the JCheckboxTree class. It shows how to add entries to a tree
     * model.
     */
    public static CheckModel zGetSampleTreeModel() {
        // Get an icon to use in the model. 
        Icon iconOriginal = javax.swing.UIManager.getIcon("OptionPane.informationIcon");
        Image imageOriginal = Use.iconToImage(iconOriginal);
        Image imageScaled = imageOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(imageScaled);
        // Create a tree entry structure.
        CheckEntry root = new CheckEntry("JTree");
        CheckEntry custom = new CheckEntry("Custom Entries");
        root.add(custom);
        custom.add(new CheckEntry("Entry without a checkbox.").withBoxHidden());
        custom.add(new CheckEntry("Entry that is initially checked.").withBoxChecked(true));
        custom.add(new CheckEntry("Entry with a custom image icon.").withIcon(icon));
        CheckEntry colors = new CheckEntry("Colors");
        root.add(colors);
        colors.add(new CheckEntry("Purple").withColor(new Color(148, 0, 211)));
        colors.add(new CheckEntry("Blue").withColor(Color.blue));
        colors.add(new CheckEntry("Green").withColor(new Color(0, 120, 0)));
        colors.add(new CheckEntry("Orange").withColor(new Color(230, 102, 0)));
        colors.add(new CheckEntry("Red").withColor(Color.red));
        CheckEntry food = new CheckEntry("Food");
        root.add(food);
        food.add(new CheckEntry("Hot dogs"));
        food.add(new CheckEntry("Pizza"));
        food.add(new CheckEntry("Ravioli"));
        food.add(new CheckEntry("Bananas"));
        CheckEntry coloredFood = new CheckEntry("Colorized Food");
        food.add(coloredFood);
        coloredFood.add(new CheckEntry("Green eggs and ham"));
        coloredFood.add(new CheckEntry("Skittles bite sized candies"));
        coloredFood.add(new CheckEntry("Painted Easter eggs"));
        CheckEntry last = new CheckEntry("More stuff.");
        root.add(last);
        // Create and return the tree model.
        CheckModel treeModel = new CheckModel(root);
        return treeModel;
    }

}
