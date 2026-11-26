/**
 * Author: Temesgen Gadore
 * Date: 11/24/2026
 * Class: CSS405
 * Assignment: Assignment 4 - AVL Word Frequency
 * File: AVLTree.java
 * Description: Implements an AVL search tree for words and frequencies.
 * It inserts, searches, deletes, and rebalances nodes using rotations.
 * It also prints in-order, level-order, and stack-based pre-order views.
 * References: CSS405 course notes; zyBooks, Chapter on AVL trees.
 */
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class AVLTree {
    private Node root;

    /** Returns node height, treating an empty child as height zero. */
    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /** Updates a node's height after its children may have changed. */
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    /** Calculates the difference between left and right heights. */
    private int balance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    /** Rotates a left-heavy subtree right and returns its new root. */
    private Node rotateRight(Node top) {
        Node newTop = top.left;
        Node middle = newTop.right;
        newTop.right = top;
        top.left = middle;
        updateHeight(top);
        updateHeight(newTop);
        return newTop;
    }

    /** Rotates a right-heavy subtree left and returns its new root. */
    private Node rotateLeft(Node top) {
        Node newTop = top.right;
        Node middle = newTop.left;
        newTop.left = top;
        top.right = middle;
        updateHeight(top);
        updateHeight(newTop);
        return newTop;
    }

    /** Makes a subtree balanced again when one side gets too tall. */
    private Node rebalance(Node node) {
        updateHeight(node);
        // Positive means the left side is taller.
        // Negative means the right side is taller.
        int difference = balance(node);
        if (difference > 1) {
            if (balance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (difference < -1) {
            if (balance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    /** Inserts a word or increments its existing count. */
    public void insert(String key) {
        root = insert(root, key);
    }

    /** Recursively inserts into the correct alphabetical subtree. */
    private Node insert(Node node, String key) {
        if (node == null) {
            return new Node(key);
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = insert(node.left, key);
        } else if (comparison > 0) {
            node.right = insert(node.right, key);
        } else {
            node.count++;
            return node;
        }
        return rebalance(node);
    }

    /** Returns a word's count, or zero when the word is missing. */
    public int search(String key) {
        Node current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison == 0) {
                return current.count;
            }
            if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return 0;
    }

    /** Deletes an entire word entry and rebalances the remaining tree. */
    public void delete(String key) {
        root = delete(root, key);
    }

    /** Removes a word. For two children, it uses the next word. */
    private Node delete(Node node, String key) {
        if (node == null) {
            return null;
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = delete(node.left, key);
        } else if (comparison > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // Find the next alphabetical word to replace this node.
            Node successor = smallest(node.right);
            node.key = successor.key;
            node.count = successor.count;
            node.right = delete(node.right, successor.key);
        }
        return rebalance(node);
    }

    /** Finds the smallest node in a nonempty subtree. */
    private Node smallest(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /** Prints all words in alphabetical order. */
    public void printInOrder() {
        System.out.println("=== In-Order (Alphabetical) ===");
        inOrder(root);
    }

    /** Visits left child, node, then right child. */
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println(node.key + ": " + node.count);
        inOrder(node.right);
    }

    /** Prints the tree level by level using a queue. */
    public void printLevelOrder() {
        System.out.println("=== Level-Order (Queue) ===");
        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            System.out.println(node.key + ": " + node.count);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /** Prints pre-order traversal using an explicit stack. */
    public void printPreOrder() {
        System.out.println("=== Pre-Order (Stack) ===");
        if (root == null) {
            System.out.println("(empty tree)");
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.key + ": " + node.count);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /** Returns whether all subtrees have legal AVL balance and heights. */
    public boolean isBalanced() {
        return checkHeight(root) != -1;
    }

    /** Checks node heights bottom-up; -1 indicates an invalid subtree. */
    private int checkHeight(Node node) {
        if (node == null) {
            return 0;
        }
        int left = checkHeight(node.left);
        int right = checkHeight(node.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }
        int actualHeight = 1 + Math.max(left, right);
        if (node.height != actualHeight) {
            return -1;
        }
        return actualHeight;
    }
}
