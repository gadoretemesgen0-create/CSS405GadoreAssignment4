/**
 * Author: Temesgen Gadore
 * Date: 11/24/2026
 * Class: CSS405
 * Assignment: Assignment 4 - AVL Word Frequency
 * File: Node.java
 * Description: Stores one word, its frequency, AVL height, and child links.
 * These fields support searching and keeping the tree balanced.
 * References: CSS405 course notes; zyBooks, Chapter on AVL trees.
 */
public class Node {
    String key;
    int count;
    int height;
    Node left;
    Node right;

    /** Creates one tree node with an initial frequency of one. */
    public Node(String key) {
        this.key = key;
        this.count = 1;
        this.height = 1;
    }
}
