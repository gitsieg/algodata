package oblig6;

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);

        tree.printInOrder();

//        tree.printInOrder();
//        tree.printPostOrder();
//        tree.printPreOrder();
//        tree.printInOrder();
//        System.out.println(tree.size());
//        System.out.println(tree.calcLevels());
//        System.out.println("Height: " + tree.getHeight());
    }

    BinaryNode<AnyType> root;
    int size;
    int levels;

    public BinaryNode<AnyType> findMin(BinaryNode<AnyType> node) {
        if (node != null) {
            while (node.left != null) {
                node = node.left;
            }
        }
        return node;
    }

    public int getHeight() {
        return root.height;
    }

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public BinaryNode<AnyType> getRoot() {
        return root;
    }

    public int size() {
        return size;
    }

    public void insert(AnyType element) {
        root = insert(element, root);
    }

    public int calcLevels() {
        if (root == null) {
            levels = -1;
        } else {
            levels = 0;
            root.calcLevels(0);
        }
        return levels;
    }

    private BinaryNode insert(AnyType element, BinaryNode<AnyType> node) {
        if (node == null) {
            node = new BinaryNode<AnyType>(element);
            size++;
            return node;
        }
        int cmp = element.compareTo(node.element);
        if (cmp < 0) {
            node.left = insert(element, node.left);
            node.calcHeight();
            if (!node.balanced) {
                BinaryNode theNode = node.left;
                if (theNode.left != null && theNode.right != null) {
                    node = (theNode.left.height > theNode.right.height) ? rotateWithLeftChild(node) : doubleRotateWithLeftChild(node);
                } else {
                    node = (theNode.left == null) ? doubleRotateWithLeftChild(node) : rotateWithLeftChild(node);
                }

                /*
                if (node.left.left == null) {
                    node = doubleRotateWithLeftChild(node);
                } else {
                    node = rotateWithLeftChild(node);
                }
                 */
            }
        } else if (cmp > 0) {
            node.right = insert(element, node.right);
            node.calcHeight();
            if (!node.balanced) {
                BinaryNode theNode = node.right;
                if (theNode.left != null && theNode.right != null) {
                    node = (theNode.right.height > theNode.left.height) ? rotateWithRightChild(node) : doubleRotateWithRightChild(node);
                } else {
                    node = (theNode.right == null) ? doubleRotateWithRightChild(node) : rotateWithRightChild(node);
                }
                
                /*
                if (node.right.right == null) {
                    node = doubleRotateWithRightChild(node);
                } else {
                    node = rotateWithRightChild(node);
                }
                */
            }
        } else {
            throw new IllegalArgumentException(element.toString());
        }
        return node;
    }

    public void printInOrder() {
        if (root == null) {
            return;
        }
        System.out.println("Inorder:");
        root.printInOrder();
    }

    public void printPostOrder() {
        if (root == null) {
            return;
        }
        System.out.println("PostOrder:");
        root.printPostOrder();
    }

    public void printPreOrder() {
        if (root == null) {
            return;
        }
        System.out.println("PreOrder:");
        root.printPreOrder();

    }

    private BinaryNode rotateWithLeftChild(BinaryNode<AnyType> k2) {
        BinaryNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.calcHeight();
        return k1;
    }

    private BinaryNode rotateWithRightChild(BinaryNode<AnyType> k1) {
        BinaryNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.calcHeight();
        return k2;
    }

    private BinaryNode<AnyType> doubleRotateWithRightChild(BinaryNode<AnyType> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        k3 = rotateWithRightChild(k3);
        k3.calcHeight();
        return k3;
    }

    private BinaryNode<AnyType> doubleRotateWithLeftChild(BinaryNode<AnyType> k3) {
        k3.left = rotateWithRightChild(k3.left);
        k3 = rotateWithLeftChild(k3);
        k3.calcHeight();
        return k3;
    }

    class BinaryNode<AnyType> {

        BinaryNode<AnyType> left, right;
        AnyType element;
        int height;
        boolean balanced;

        BinaryNode(AnyType element) {
            height = 0;
            left = right = null;
            this.element = element;
            balanced = true;
        }

        /**
         * Utskrift av treet i Inorder- rekkefølge
         */
        private void printInOrder() {
            if (left != null) {
                left.printInOrder();
            }
            print();
            if (right != null) {
                right.printInOrder();
            }
        }

        /**
         * Utskrift av treet i Preorder- rekkefølge
         */
        private void printPreOrder() {
            print();
            if (left != null) {
                left.printPreOrder();
            }
            if (right != null) {
                right.printPreOrder();
            }
        }

        /**
         * Utskrift at treet i Postorder-rekkefølge
         */
        private void printPostOrder() {
            if (left != null) {
                left.printPostOrder();
            }
            if (right != null) {
                right.printPostOrder();
            }
            print();
        }

        private void calcLevels(int nivå) {
            if (left != null) {
                left.calcLevels(nivå);
            }
            nivå++;
            if (nivå > levels) {
                levels = nivå;
            }
            if (right != null) {
                right.calcLevels(nivå);
            }
        }

        /**
         * Kalkulerer en nodes høyde og setter sjekker om en node er i balanse
         */
        private void calcHeight() {
            int diff;
            if (left != null && right != null) {
                height = 1 + Math.max(left.height, right.height);
                diff = Math.abs(left.height - right.height);
                balanced = (diff <= 1);
            } else if (left != null && right == null) {
                height = (left.height + 1);
                balanced = height <= 1;
            } else if (left == null && right != null) {
                height = right.height + 1;
                balanced = height <= 1;
            } else {
                height = 0;
                balanced = true;
            }
        }

        private void print() {
            System.out.println("Element: " + element
                    + "\nSize: " + size
                    + "\nHeight: " + height
                    + "\nBalanced: " + balanced
                    + "\n----------------");
        }

        private void setBalanced() {
        }
    }
}
