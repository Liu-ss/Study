package DataStructure.Tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Z字打印树
 *
 * 请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推。
 */
public class ZPrintTree {

    public static void main(String[] args) {

        // 初始数组
        int[] data0 = {2,4,7,9,56,32,1,5};
        int[] data1 = {5,2,7,1,3,6,8,4};
        int[] data2 = {5,2,8,1,3,6,9,4,7,10};

        // 构造一棵二叉搜索树
        BinarySearchTree tree = new BinarySearchTree(data2[0]);
        for (int i = 1; i < data2.length; i++){
            tree.insert(tree, data2[i]);
        }
        // 遍历二叉树
        System.out.println("中序遍历为：");
        tree.middle(tree);
        System.out.println();

        ZPrintTree printTree = new ZPrintTree();
        ArrayList<ArrayList<Integer>> arrayList = printTree.print1(tree);
        System.out.println("Z字形遍历树的结果：" + arrayList.toString());
        // Z字形遍历树的结果：[[5], [8, 2], [1, 3, 6, 9], [10, 7, 4]]

    }

    /**
     * Z字打印
     * 标记位：1-从左往右  0-从右往左
     * 借助栈：当从右往左遍历，将数据按左至右压栈，然后出栈
     *
     * This method has a problem to fix -- to be continue
     */
    public ArrayList<ArrayList<Integer>> print(BinarySearchTree tree){

        // 打印结果  将每层作为一个数组添加至最后的 打印结果数组
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        // 空树
        if (tree == null) {
            return result;
        }

        // 每层数据
        LinkedList<BinarySearchTree> queue = new LinkedList<>();
        queue.addLast(tree);
//        queue.add(node);
//        queue.offer(tree);

        // 层
        int layer = 0;
        while (!queue.isEmpty()) {
            // 层+1   下一层
            layer++;
            // 每层中节点个数
            int layerSize = queue.size();

            // 存储每层打印结果
            ArrayList<Integer> list = new ArrayList<>();

            // 从左往右打印
            if (layer % 2 != 0) {

                // 本层数据不为空
                while (layerSize > 0){
                    layerSize--;
                    BinarySearchTree temp = queue.removeFirst();

                    list.add(temp.data);

                    if (tree.left != null) {
                        queue.addLast(tree.left);
                    }
                    if (tree.right != null) {
                        queue.addLast(tree.right);
                    }
                }
            } else {
                // 从右往左打印

                // 借助 栈 存放数据
                Stack<Integer> stack = new Stack<>();

                while (layerSize > 0) {
                    layerSize--;
                    BinarySearchTree temp = queue.removeFirst();
                    stack.push(temp.data);

                    if (tree.left != null) {
                        queue.addLast(tree.left);
                    }
                    if (tree.right != null) {
                        queue.addLast(tree.right);
                    }
                }

                // 将栈中数据存入结果集
                while (!stack.isEmpty()) {
                    list.add(stack.pop());
                }
            }

            result.add(list);

        }
        return result;
    }


    /**
     * 原树
     *         5
     *    2         8
     * 1    3    6      9
     *        4       7   10
     *
     * 栈  s1  全树压栈（即存入数据）
     * 将数据取出，然后判断是否有左右子节点，将其压入栈s2【按从左至右压入栈,即 s2 中有 2 8 两棵子树】   出栈为 8 2
     * 栈 s2 即为下一层，取出数据时，再判断是否有左右子节点，此时需要将其左右子节点数据压入栈 s1 【按从右往左入栈，以便下一层遍历时，即为新方向，即新 s1 中有 9 3 6 1 四棵子树】  出栈为 1 3 6 9，
     * 此时将出栈树的子树按从左往右压入新栈 s2 为 4  7 10
     * 然后 level++ 即为下一层，此时出栈顺序 10 7 4
     * 最终 Z字遍历ok
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer> > print1(BinarySearchTree pRoot) {

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (null == pRoot) {
            return res;
        }
        // 借助栈
        Stack<BinarySearchTree> s1 = new Stack<>();
        Stack<BinarySearchTree> s2 = new Stack<>();
        s1.push(pRoot);
        int level = 1;
        while (!s1.empty() || !s2.empty()) {
            if (0 != level % 2) {
                ArrayList<Integer> nodeList = new ArrayList<>();
                while (!s1.empty()) {
                    BinarySearchTree node = s1.pop();
                    nodeList.add(node.data);
                    if (null != node.left) {
                        s2.push(node.left);
                    }
                    if (null != node.right) {
                        s2.push(node.right);
                    }
                }
                if (!nodeList.isEmpty()) {
                    res.add(nodeList);
                    level++;
                }
            } else {
                ArrayList<Integer> nodeList = new ArrayList<>();
                while (!s2.empty()) {
                    BinarySearchTree node = s2.pop();
                    nodeList.add(node.data);
                    if (null != node.right) {
                        s1.push(node.right);;
                    }
                    if (null != node.left) {
                        s1.push(node.left);
                    }
                }
                if (!nodeList.isEmpty()) {
                    res.add(nodeList);
                    level++;
                }
            }
        }
        return res;
    }






}
