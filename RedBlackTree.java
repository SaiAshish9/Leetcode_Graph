public class RedBlackTree {

    static class Node {
        int val;
        Node left, right, parent;
        boolean isRed;

        Node(int val) {
            this.val = val;
            this.isRed = true; // new nodes are red
        }
    }

    private Node root;

    public void insert(int val) {
        Node node = new Node(val);
        root = bstInsert(root, node);
        fixViolation(node);
    }

    private Node bstInsert(Node root, Node node) {
        if (root == null) return node;

        if (node.val < root.val) {
            root.left = bstInsert(root.left, node);
            root.left.parent = root;
        } else {
            root.right = bstInsert(root.right, node);
            root.right.parent = root;
        }

        return root;
    }

    private void fixViolation(Node node) {
        Node parent, grandparent;

        while (node != root && node.isRed && node.parent.isRed) {
            parent = node.parent;
            grandparent = parent.parent;

            if (parent == grandparent.left) {
                Node uncle = grandparent.right;

                if (uncle != null && uncle.isRed) {
                    parent.isRed = false;
                    uncle.isRed = false;
                    grandparent.isRed = true;
                    node = grandparent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        leftRotate(node);
                    }
                    parent.isRed = false;
                    grandparent.isRed = true;
                    rightRotate(grandparent);
                }
            } else {
                Node uncle = grandparent.left;

                if (uncle != null && uncle.isRed) {
                    parent.isRed = false;
                    uncle.isRed = false;
                    grandparent.isRed = true;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rightRotate(node);
                    }
                    parent.isRed = false;
                    grandparent.isRed = true;
                    leftRotate(grandparent);
                }
            }
        }

        root.isRed = false;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;

        y.parent = x.parent;

        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;

        y.right = x;
        x.parent = y;
    }

    // 🔍 Search method
    public boolean search(int val) {
        return searchNode(root, val) != null;
    }

    private Node searchNode(Node node, int val) {
        if (node == null || node.val == val) return node;
        if (val < node.val) return searchNode(node.left, val);
        return searchNode(node.right, val);
    }

    // ❌ Delete method
    public void delete(int val) {
        Node node = searchNode(root, val);
        if (node == null) return;

        deleteNode(node);
    }

    private void deleteNode(Node node) {
        Node y = node;
        Node x;
        boolean yOriginalColor = y.isRed;

        if (node.left == null) {
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == null) {
            x = node.left;
            transplant(node, node.left);
        } else {
            y = minimum(node.right);
            yOriginalColor = y.isRed;
            x = y.right;

            if (y.parent == node) {
                if (x != null) x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.isRed = node.isRed;
        }

        if (!yOriginalColor) fixDelete(x);
    }

    private void fixDelete(Node x) {
        while (x != root && (x == null || !x.isRed)) {
            if (x == x.parent.left) {
                Node w = x.parent.right;

                if (w != null && w.isRed) {
                    w.isRed = false;
                    x.parent.isRed = true;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                if ((w.left == null || !w.left.isRed) && (w.right == null || !w.right.isRed)) {
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if (w.right == null || !w.right.isRed) {
                        if (w.left != null) w.left.isRed = false;
                        w.isRed = true;
                        rightRotate(w);
                        w = x.parent.right;
                    }

                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    if (w.right != null) w.right.isRed = false;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;

                if (w != null && w.isRed) {
                    w.isRed = false;
                    x.parent.isRed = true;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                if ((w.right == null || !w.right.isRed) && (w.left == null || !w.left.isRed)) {
                    w.isRed = true;
                    x = x.parent;
                } else {
                    if (w.left == null || !w.left.isRed) {
                        if (w.right != null) w.right.isRed = false;
                        w.isRed = true;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    w.isRed = x.parent.isRed;
                    x.parent.isRed = false;
                    if (w.left != null) w.left.isRed = false;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        if (x != null) x.isRed = false;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == null) root = v;
        else if (u == u.parent.left) u.parent.left = v;
        else u.parent.right = v;

        if (v != null) v.parent = u.parent;
    }

    private Node minimum(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }
}
