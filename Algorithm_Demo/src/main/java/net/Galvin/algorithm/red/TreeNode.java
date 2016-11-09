package net.Galvin.algorithm.red;


public class TreeNode<K, V> {

    TreeNode parent;
    TreeNode left;
    TreeNode right;
    boolean red;
    K k;
    V v;

    public TreeNode(K k, V v) {
        this.k = k;
        this.v = v;
    }

}
