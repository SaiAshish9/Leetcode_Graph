class Solution {
    static class TreeNode {
        int val, height;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            this.height = 1;
        }
    }

    // Utility: Get height
    private static int height(TreeNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Utility: Get balance factor
    private static int getBalance(TreeNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Left rotation (RR)
    private static TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    // Right rotation (LL)
    private static TreeNode rightRotate(TreeNode x) {
        TreeNode y = x.left;
        TreeNode T2 = y.right;
        y.right = x;
        x.left = T2;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    // Insert key into AVL Tree
    public static TreeNode insert(TreeNode node, int key) {
        if (node == null) return new TreeNode(key);
        if (key < node.val) node.left = insert(node.left, key);
        else if (key > node.val) node.right = insert(node.right, key);
        else return node;  // No duplicates
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

//        In insertion, the rotation is determined by comparing the new value (key)
//        being inserted to the child node’s value,
//        because we’re checking the direction of the inserted node:
        // Rebalance
        if (balance > 1 && key < node.left.val) return rightRotate(node);
        if (balance < -1 && key < node.right.val) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        if (balance < -1 && key > node.right.val) return leftRotate(node);
        if (balance > 1 && key > node.left.val) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        return node;
    }

    // Search key
    public static boolean search(TreeNode root, int key) {
        if (root == null) return false;
        if (key == root.val) return true;
        return key < root.val ? search(root.left, key) : search(root.right, key);
    }

    // Utility: Find min value
    private static int findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node.val;
    }

    // Delete key from AVL Tree
    public static TreeNode delete(TreeNode root, int key) {
        if (root == null) return null;
        if (key < root.val) root.left = delete(root.left, key);
        else if (key > root.val) root.right = delete(root.right, key);
        else {
            if (root.left == null || root.right == null) {
                root = (root.left != null) ? root.left : root.right;
            } else {
                int successor = findMin(root.right);
                root.val = successor;
                root.right = delete(root.right, successor);
            }
        }
        if (root == null) return null;
        root.height = 1 + Math.max(height(root.left), height(root.right));
        int balance = getBalance(root);

//        But in deletion, the node has already been removed — so we don’t
//        have a meaningful key to guide direction anymore.
        // Rebalance
        if (balance > 1 && getBalance(root.left) >= 0) return rightRotate(root);
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) return leftRotate(root);
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Inorder Traversal (sorted)
    public static void inorder(TreeNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.val + " ");
            inorder(root.right);
        }
    }

    // Demo
    public static void main(String[] args) {
        TreeNode root = null;
        int[] values = {10, 20, 30, 40, 50, 25};
        for (int val : values) {
            root = insert(root, val);
        }
        System.out.print("Inorder after insertions: ");
        inorder(root);
        System.out.println();
        System.out.println("Search 30? " + search(root, 30));  // true
        System.out.println("Search 100? " + search(root, 100)); // false
        root = delete(root, 20);  // delete a node
        System.out.print("Inorder after deleting 20: ");
        inorder(root);
        System.out.println();
//        Inorder after insertions: 10 20 25 30 40 50
//        Search 30? true
//        Search 100? false
//        Inorder after deleting 20: 10 25 30 40 50
    }
}
