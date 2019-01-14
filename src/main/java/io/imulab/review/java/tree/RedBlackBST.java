package io.imulab.review.java.tree;

import io.imulab.review.java.spi.SymbolTable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackBST<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root = null;

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node h, Key key, Value value) {
        if (h == null)
            return new Node(key, value);

        int cmp = key.compareTo(h.key);
        if (cmp == 0)
            h.value = value;
        else if (cmp < 0)
            h.left = put(h.left, key, value);
        else
            h.right = put(h.right, key, value);

        if (isBlack(h.left) && isRed(h.right))
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))
            h = flipColor(h);

        h.count = count(h);

        return h;
    }

    @Override
    public Value get(Key key) {
        Node x = root;

        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                break;

            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }

        return (x == null) ? null : x.value;
    }

    @Override
    public void delete(Key key) {
        delete(root, key);
    }

    /**
     * Perform a hibbard deletion on the subtree.
     *
     * @param h     the root of the subtree to delete a node from.
     * @param key   the key corresponding to the node to be deleted.
     * @return      the new root of the subtree with the node deleted.
     */
    private Node delete(Node h, Key key) {
        if (h == null)
            return null;

        int cmp = key.compareTo(h.key);
        if (cmp < 0)
            h.left = delete(h.left, key);
        else if (cmp > 0)
            h.right = delete(h.right, key);
        else {
            if (h.left == null)
                h = h.right;
            else if (h.right == null)
                h = h.left;
            else {
                Node x = h;
                h = min(h.right);
                x.right = delete(x.right, h.key);
                h.left = x.left;
            }
        }

        h.count = count(h);

        return h;
    }

    /**
     * Get the node with minimum value in the sub tree, which is left furthermost node.
     *
     * @param h root of the subtree to search for the node with minimum value.
     * @return  the minimum node
     */
    private Node min(Node h) {
        assert h != null;
        Node x = h;

        while (x.left != null)
            x = x.left;

        return x;
    }

    @Override
    public int count() {
        if (root == null)
            return 0;
        return root.count;
    }

    private int count(Node h) {
        if (h == null)
            return 0;
        return 1 + count(h.left) + count(h.right);
    }

    @Override
    public Iterator<Value> iterator() {
        Queue<Value> collector = new LinkedList<>();
        inOrder(root, collector);
        return collector.iterator();
    }

    private void inOrder(Node h, Queue<Value> collector) {
        if (h == null)
            return;

        inOrder(h.left, collector);
        collector.offer(h.value);
        inOrder(h.right, collector);
    }

    /**
     * Perform a local left rotation on the sub tree.
     *
     * @param h the root of the sub-tree to perform a left rotation.
     * @return  the new root of the sub-tree after performing a left rotation.
     */
    private Node rotateLeft(Node h) {
        assert h != null;
        assert h.right != null && h.right.isRed();

        // rotate
        Node x = h.right;
        h.right = x.left;
        x.left = h;

        // exchange color
        x.color = h.color;
        h.color = RED;

        return x;
    }

    /**
     * Perform a local right rotation on the sub tree.
     *
     * @param h the root of the sub-tree to perform a right rotation.
     * @return  the new root of the sub-tree after performing a right rotation.
     */
    private Node rotateRight(Node h) {
        assert h != null;
        assert h.left != null && h.left.isRed();

        // rotate
        Node x = h.left;
        h.left = x.right;
        x.right = h;

        // exchange color
        x.color = h.color;
        h.color = RED;

        return x;
    }

    /**
     * Perform a color flip on the sub tree.
     *
     * @param h the root of the sub-tree to perform a color flip.
     * @return  the same root after the color flip.
     */
    private Node flipColor(Node h) {
        assert h != null && h.isBlack();
        assert h.left != null && h.left.isRed();
        assert h.right != null && h.right.isRed();

        h.left.color = BLACK;
        h.right.color = BLACK;
        h.color = RED;

        return h;
    }

    private boolean isRed(Node x) {
        return x != null && x.isRed();
    }

    private boolean isBlack(Node x) {
        return x != null && x.isBlack();
    }

    private class Node {
        private final Key key;
        private Value value = null;
        private Node left = null;
        private Node right = null;
        private boolean color = BLACK;
        private int count = 0;

        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        boolean isRed() {
            return color == RED;
        }

        boolean isBlack() {
            return color == BLACK;
        }
    }
}
