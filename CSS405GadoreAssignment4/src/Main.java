/**
 * Author: Temesgen Gadore
 * Date: 11/24/2026
 * Class: CSS405
 * Assignment: Assignment 4 - AVL Word Frequency
 * File: Main.java
 * Description: Starts the word-frequency program using a file path.
 * It reads words into an AVL tree and displays three traversal orders.
 * Optional search and deletion examples can be requested with flags.
 * References: CSS405 course notes; zyBooks, Chapter on AVL trees.
 */
import java.io.IOException;

public class Main {
    /** Runs the program; expects a file and optional demonstration flags. */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main <file> [--demo]");
            return;
        }
        WordFrequencyAnalyzer analyzer = new WordFrequencyAnalyzer();
        try {
            analyzer.readFile(args[0]);
        } catch (IOException error) {
            System.out.println("Could not read file: " + args[0]);
            return;
        }
        AVLTree tree = analyzer.getTree();
        tree.printInOrder();
        System.out.println();
        tree.printLevelOrder();
        System.out.println();
        tree.printPreOrder();
        System.out.println("AVL balanced: " + tree.isBalanced());

        // This optional example demonstrates searching and deleting a word.
        if (args.length > 1 && args[1].equals("--demo")) {
            System.out.println();
            System.out.println("Search 'river': " + tree.search("river"));
            tree.delete("river");
            System.out.println("After deleting 'river':");
            tree.printInOrder();
            System.out.println("AVL balanced: " + tree.isBalanced());
        }
    }
}
