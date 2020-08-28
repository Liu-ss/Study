package DataStructure.Tree;



/**
 * 二叉搜索树
 */
public class BinarySearchTree {

    /**
     * 树中需要的元素
     */

    // 根节点
    int data;
    // 左子树
    BinarySearchTree left;
    // 右子树
    BinarySearchTree right;


    /**
     * 构造函数
     * 构造一棵树：根节点数据，以及左右子树
     * @param data
     */
    public BinarySearchTree(int data) {
        this.data = data;
//        this.left = null;
//        this.right = null;
    }


    /**
     * 构造二叉搜索树
     */
    public void insert(BinarySearchTree root, int data){
        // 如果插入数据据大于根节点，那么插入到右子树中
        if (root.data < data){
            if (root.right == null){
                // 如果右子树为空，则直接插入即可
                root.right = new BinarySearchTree(data);
            }else {
                // 右子树不为空,递归调用  进行插入右子树
                insert(root.right,data);
            }
        }
        // 如果插入数据小于根节点，则插入到左子树中
        if(root.data > data){
            if (root.left == null){
                // 如果左子树为空，则直接插入即可
                root.left = new BinarySearchTree(data);
            }else {
                // 左子树不为空,递归调用  进行插入右子树
                insert(root.left,data);
            }
        }
    }


    /**
     * 遍历二叉树
     * 递归调用  左右子树也按照同样的方式进行遍历
     */

    // 中序遍历  左根右
    public void middle(BinarySearchTree tree){
        if (tree != null){
            // 左子树  递归遍历
            middle(tree.left);
            // 根节点
            System.out.print(tree.data + " ");
            // 右子树  递归遍历
            middle(tree.right);
        }
    }

    // 前序遍历 根左右
    public void previous(BinarySearchTree tree){
        if (tree != null){
            // 根节点
            System.out.print(tree.data + " ");
            // 左子树  递归遍历
            previous(tree.left);
            // 右子树  递归遍历
            previous(tree.right);
        }
    }

    // 后序遍历 左右根
    public void after(BinarySearchTree tree){
        if (tree != null){
            // 左子树  递归遍历
            after(tree.left);
            // 右子树  递归遍历
            after(tree.right);
            // 根节点
            System.out.print(tree.data + " ");
        }
    }


    public static void main(String[] args) {

        // 初始数组
        int[] data = {2,4,7,9,56,32,1,5};

        // 构造一棵二叉搜索树
        BinarySearchTree binarySearchTree = new BinarySearchTree(data[0]);
        for (int i = 1; i < data.length; i++){
            binarySearchTree.insert(binarySearchTree, data[i]);
        }

        // 遍历二叉树
        System.out.println("中序遍历为：");
        binarySearchTree.middle(binarySearchTree);
        System.out.println();

        System.out.println("前序遍历为：");
        binarySearchTree.previous(binarySearchTree);
        System.out.println();

        System.out.println("后序遍历为：");
        binarySearchTree.after(binarySearchTree);




    }






}
