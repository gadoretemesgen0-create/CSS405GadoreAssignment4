/**
 * Author: Temesgen Gadore
 * Date: 11/24/2026
 * Class: CSS405
 * Assignment: Assignment 4 - AVL Word Frequency
 * File: WordFrequencyAnalyzer.java
 * Description: Reads a text file one line at a time using Scanner.
 * It changes words to lowercase and ignores punctuation marks.
 * The AVL tree keeps track of how often each word appears.
 * References: CSS405 course notes; zyBooks, Chapter on AVL trees.
 */
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class WordFrequencyAnalyzer {
    private final AVLTree tree = new AVLTree();

    /** Reads a text file one line at a time and counts its words. */
    public void readFile(String filename) throws IOException {
        Scanner input = new Scanner(new File(filename));

        // Read each line until we reach the end of the file.
        while (input.hasNextLine()) {
            String line = input.nextLine().toLowerCase();

            // Split words where there are spaces or punctuation marks.
            String[] words = line.split("[^a-z]+");

            // Add every real word to the AVL tree.
            for (String word : words) {
                if (!word.isEmpty()) {
                    tree.insert(word);
                }
            }
        }

        input.close();
    }

    /** Returns the tree so callers can search or display its contents. */
    public AVLTree getTree() {
        return tree;
    }
}
