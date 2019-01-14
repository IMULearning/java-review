package io.imulab.review.java.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A (recursive) implementation to the binary search tree data structure.
 *
 * Problem: this tree may not be very balanced, causing problem to access time.
 *
 * Performance:
 * ------------
 * Worst case performance is N (a completely left or right leaning tree.)
 * On average, it is 1.39lgN
 *
 * @param <K> Type of the key
 * @param <V> Type of the value
 */
public class RecursiveStyleBST<K extends Comparable<K>, V> {

    private Node root;

    /**
     * Put the key value relationship into the tree.
     *
     * @param key key
     * @param value value
     */
    public void put(K key, V value) {
        root = doPut(root, key, value);
    }

    private Node doPut(Node x, K key, V value) {
        // We have hit the bottom of the tree, which means the key does not previously
        // exist in the tree. Hence, we return a new node, which the recursive caller
        // will use to attach on the tree body.
        if (x == null)
            return new Node(key, value);

        int cmp = key.compareTo(x.key);

        // Key fits on the left sub tree:
        //  attach the root returned by the recursive callee.
        if (cmp < 0)
            x.left = doPut(x.left, key, value);

        // Key fits on the right sub tree:
        //  attach the root returned by the recursive callee.
        else if (cmp > 0)
            x.right = doPut(x.right, key, value);

        // update size.
        x.count = 1 + size(x.left) + size(x.right);

        // Key is the same with current node:
        //  update the value.
        x.value = value;
        return x;
    }

    /**
     * Get the value corresponding to the key in the tree.
     *
     * The code of search, if any, is going to be 1 + depth(matched_node).
     *
     * @param key key corresponding to the value to search for.
     * @return value corresponding to the key, or null if not found.
     */
    public V get(K key) {
        // Make x be the cursor.
        Node x = root;

        // If the cursor becomes null, we are sure there's no match
        while (x != null) {
            int cmp = key.compareTo(x.key);

            // we have got a hit
            if (cmp == 0)
                return x.value;

            // key is smaller than the current node:
            //  the hit, if any, must be on the left sub tree.
            else if (cmp < 0)
                x = x.left;

            // key is larger than the current node:
            //  the hit, if any, must be on the right sub tree.
            else
                x = x.right;
        }

        return null;
    }

    /**
     * Using hibbard deletion makes performance to sqrt(N).
     * @param key
     */
    public void delete(K key) {
        root = hibbardDelete(root, key);
    }

    /**
     * Denote the node to be deleted as the root of the deletion subtree.
     *
     * When the root has no child, return null as new root.
     *
     * When the root has one child, return the other root as new root.
     *
     * When the root has two children, find the minimum node of the right sub tree (left furthermost),
     * and denote it as x. Delete x from the right subtree, and replace the root with x.
     */
    private Node hibbardDelete(Node x, K key) {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);

        // The key to be deleted is in the left subtree
        if (cmp < 0)
            x.left = hibbardDelete(x.left, key);

        // The key to be deleted is in the right subtree
        else if (cmp > 0)
            x.right = hibbardDelete(x.right, key);

        // This is the key to be deleted.
        else {
            // one child or no child case:
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;

            // two children case
            Node t = x;                                 // make a copy
            x = min(t.right);                           // assign x to the minimum of right subtree
            x.right = hibbardDelete(t.right, x.key);    // let x be new root, assign right subtree to the original right without x
            x.left = t.left;                            // assign left subtree to the original right subtree.
        }

        // update size
        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    public Iterable<V> iterator() {
        Queue<V> collector = new LinkedList<>();
        inOrder(root, collector);
        return collector;
    }

    private void inOrder(Node x, Queue<V> collector) {
        if (x == null)
            return;

        inOrder(x.left, collector);
        collector.offer(x.value);
        inOrder(x.right, collector);
    }

    /**
     * Get the minimum value in the tree.
     * It is the left furthermost node.
     *
     * @return
     */
    public V min() {
        if (root == null)
            return null;
        return min(root).value;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    /**
     * Get the maximum value in the tree.
     * It is the right furthermost node.
     *
     * @return
     */
    public V max() {
        if (root == null)
            return null;

        Node x = root;
        while (x.right != null)
            x = x.right;

        return x.value;
    }

    /**
     * The the value corresponding to the largest key that is no greater than the provided key.
     *
     * @param key key threshold.
     *
     * @return Value to the largest key that is no greater than the provided key, or
     * null if there is no such value.
     */
    public V floor(K key) {
        return doFloor(root, key);
    }

    // What we want: x <= k
    private V doFloor(Node x, K key) {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);

        // The largest key that is no greater than something is itself.
        if (cmp == 0)
            return x.value;

        // Key is smaller than cursor, so current node does not qualify.
        //  If there is any qualifying node, it must be in the left sub-tree.
        else if (cmp < 0)
            return doFloor(x.left, key);

        // Key is greater than cursor, the current node can possibly qualify, if the right sub-tree
        // provides no better alternative.
        else {
            V v = doFloor(x.right, key);

            // If the search of the right sub-tree is in vain (returned null), then current node
            // has the best value.
            return (v != null) ? v : x.value;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        return x.count;
    }

    /**
     * How many nodes are there whose key is less than the provided key.
     * @param key
     * @return
     */
    public int rank(K key) {
        return rank(root, key);
    }

    private int rank(Node x, K key) {
        if (x == null)
            return 0;

        int cmp = key.compareTo(x.key);

        // key(x) is greater than key, search left sub-tree
        if (cmp < 0)
            return rank(x.left, key);

        // key(x) is less than key:
        //  the left sub-tree of x is definitely less than key too
        //  plus the part of right sub-tree that is less than key.
        else if (cmp > 0)
            return 1 + size(x.left) + rank(x.right, key);

        // key(x) is equal to key:
        //  the left sub-tree of x is definitely less than key
        //  the right sub-tree of x is definitely larger than key
        else
            return size(x.left);
    }

    /**
     * A node in the tree.
     */
    private class Node {

        private final K key;
        private V value;
        private Node left, right;
        private int count;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
