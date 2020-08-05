package AlgorithmStudy.study.study01;


import java.util.Arrays;

/**
 * 排序算法
 */
public class SortAlgorithm<T extends Comparable<T>> {


    /**
     * 比较
     *
     * @param a
     * @param b
     * @return
     */
    private boolean less(T a, T b) {
        // Comparable接口中compareTo方法，
        // 若a < b 返回 -1；
        // 若a = b 返回 0；
        // 若a > b 返回1
        return a.compareTo(b) < 0;
    }


    /**
     * 交换 数组中下标为 i,j 的元素位置
     *
     * @param arr 原始数组
     * @param i   下标i
     * @param j   下标j
     */
    public void swap(int[] arr, int i, int j) {


        // 借助临时变量
//        int temp;
//        temp = arr[i];
//        arr[i] = arr[j];
//        arr[j] = temp;

        // 不借助临时变量
        arr[j] = arr[i] + arr[j];
        arr[i] = arr[j] - arr[i];
        arr[j] = arr[j] - arr[i];

        // 哈哈哈哈哈  编译后代码变成这个样子， 简化代码编写
//        arr[j] += arr[i];
//        arr[i] = arr[j] - arr[i];
//        arr[j] -= arr[i];
    }


    protected void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


    /**
     * ============================================ 1.冒泡排序===========================================
     */
    public void bubbleSort(int[] arrays) {


        // 外层循环 i只是用来决定j需要比较到什么位置（即：一次比较后，会将最大或最小的数置于尾，下一次就不必在比较这个位置，j只需要 length-1-i 就可以了）
//        for (int i = 0; i < arrays.length - 1; i++) {
//            // 内层循环
//            for (int j = 0; j< arrays.length -1 -i; j++) {
//                // 进行比较 交换
//                if (arrays[j] > arrays[j+1]) {
////                    arrays[j+1] = arrays[j] + arrays[j+1];
////                    arrays[j] = arrays[j+1] - arrays[j];
////                    arrays[j+1] = arrays[j+1] - arrays[j];
//
//                    int temp;
//                    temp = arrays[j];
//                    arrays[j] = arrays[j+1];
//                    arrays[j+1] = temp;
//
//
//                }
//            }
//        }
//        System.out.println("排序后数组：" + Arrays.toString(arrays));


        /**
         * 优化
         *
         * 例： 原始数组 {5,8,3,1,4,7}
         * 经第一次排序：{5,3,1,4,7,8}
         * 经第二次排序：{3,1,4,5,7,8}
         * 经第三次排序：{1,3,4,5,7,8}
         *
         * 即 经过三次排序后这个数组就变为有序数组了
         *
         * 对其进行优化，即经过n次排序整个数组已经有序不需要在进行后续，需借助一个标记位
         */
        boolean flag = true; //(不设置初始值时，默认 false)
        // 外层循环
        for (int i = 0; i < arrays.length - 1 && flag; i++) {
            flag = false;
            // 内层循环
            for (int j = 0; j < arrays.length - 1 - i; j++) {
                // 进行比较 交换
                if (arrays[j] > arrays[j + 1]) {
//                    arrays[j+1] = arrays[j] + arrays[j+1];
//                    arrays[j] = arrays[j+1] - arrays[j];
//                    arrays[j+1] = arrays[j+1] - arrays[j];
                    swap(arrays, j, j + 1);
                    flag = true;
                }
            }
        }
        System.out.println("排序后数组【冒泡排序】：" + Arrays.toString(arrays));


    }


    /**============================================ 2.简单选择排序===========================================*/

    /**
     * 即通过n-i次关键字间的比较，从n-i+1（包括自己）个记录中选出关键字最小的记录，并和第i个记录交换
     */
    public void selectSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[minIndex];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    minIndex = j;
                    min = arr[j];
                }
            }

            if (i != minIndex) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }

        System.out.println("排序后数组【简单选择排序】：" + Arrays.toString(arr));

    }


    /**============================================ 2.直接插入排序===========================================*/

    /**
     * 概念：
     * 把n个待排序的元素看成一个有序列表和一个无序表，
     * 开始时有序表只包含一个元素，无序表中包含n-1个元素，
     * 排序过程中每次从无序表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，
     * 将它插入到有序表中的适当位置，使之成为新的有序表。
     */
    public void insertSort(int[] arr) {
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
                swap(arr, j, j - 1);
            }
        }
        System.out.println("排序后数组【直接插入排序】：" + Arrays.toString(arr));
    }


    /**============================================ 2.希尔排序===========================================*/

    /* 参考文章： https://gitee.com/Langyk/DataStructureAndAlgorithm?_from=gitee_search#2%E6%A0%88%E7%9A%84%E5%BA%94%E7%94%A8%E5%9C%BA%E6%99%AF
     *
     *   其中讲到  交换法 和 位移法   进行元素换位
     *   交换法 即：借助临时变量 进行交换两元素位置
     *   位移法（如下例子）
     *
     *   例解：
     *       数组：{5,8,3,1,4,7}
     *           增量/步长 increment = 6/2 = 3
     *           i = 3 ; i<6 ; i++ (即i从3到6)
     *           将arr[j] 赋给临时变量 temp
     *           若arr[j] < arr[j - increment] ,交换两元素，将较大 arr[j - increment] 的值赋给 arr[j]
     *           然后将下标 j 的值减少相应步长
     *           再把 temp 的值赋给新下标 j 的位置
     *
     */

    /**
     * 对于大规模的数组，插入排序很慢，因为它只能交换相邻的元素，每次只能将逆序数量减少 1。
     * 希尔排序的出现就是为了解决插入排序的这种局限性，它通过交换不相邻的元素，每次可以将逆序数量减少大于 1。
     * <p>
     * 希尔排序使用插入排序对间隔 h 的序列进行排序。通过不断减小 h，最后令 h=1，就可以使得整个数组是有序的。
     */
    public void shellSport(int[] arr) {

        for (int increment = arr.length / 2; increment > 0; increment = increment / 2) {

            for (int i = increment; i < arr.length; i++) {
                // 将i的值赋给j
                int j = i;
                // 将arr[j]的值赋给temp(即创建一个变量，值为arr[j]的值)
                int temp = arr[j];
                // 如果arr[j] < arr[j - increment] ,交换两元素
                if (arr[j] < arr[j - increment]) {
                    // 要保证 j - increment >= 0 && temp < arr[j - increment] 即 arr[j] < arr[j - increment]
                    while (j - increment >= 0 && temp < arr[j - increment]) {
                        // 将较大arr[j - increment]的值赋给arr[j]
                        arr[j] = arr[j - increment];
                        // 然后将 下标j 的值减少相应步长
                        j -= increment;
                    }
                    // 将temp的值赋给arr[j] 此时的 j 是已经减少相应步长后的下标，即要交换的两元素中的较小下标值
                    arr[j] = temp;
                }
            }
        }

        System.out.println("排序后数组【希尔排序】：" + Arrays.toString(arr));
    }


    /**============================================ 2.归并排序===========================================*/

    /**
     * 归并排序是利用归并的思想进行排序的方法，采用经典的分治策略（分治是将问题分解成一些小问题然后递归求解，然后就将各个阶段的答案修补在一起）
     */


    /**============================================ 2.快速排序===========================================*/

    /**
     * 归并排序  将数组分为两个子数组分别排序，并将有序的子数组归并使得整个数组排序；
     * 快速排序  通过一个切分元素将数组分为两个子数组，左子数组小于等于切分元素，右子数组大于等于切分元素，将这两个子数组排序也就将整个数组排序了。
     */


    public void quickSort(int[] arr, int left, int right) {


        // 左边界
        int l = left;
        // 右边界
        int r = right;

        int temp = 0;

        // 找出一个基数
        int privot = arr[(right + left) / 2];

        // 左边界小于右边界
        while (l < r) {

            // 将数组中值 与 基数值 进行比较
            // 如果值小于 privot 则不变位置
            while (arr[l] < privot) {
                l++;
            }
            // 如果值大于 privot 则不变位置
            while (arr[r] > privot) {
                r--;
            }
            // 左边界值增加到 大于等于 右边界值 则退出（过程：左边界值增加，右边界值减小）
            if (l >= r) {
                break;
            }

            // 若arr[l] > privot && arr[r] < privot 则交换它们的值
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            // 若左半域中值与privot相等  则左边界值下标停止增加，仅右边界下标值减小
            if (arr[l] == privot) {
                r--;
            }
            // 若右半域中值与privot相等  则右边界值下标停止减小，仅左边界下标值增加
            if (arr[r] == privot) {
                l++;
            }


        }

        // 左右下标增减至 相等
        if (l == r) {
            l += 1;
            r -= 1;
        }

        // privot = 3
        // l = 2   r = 1
        // left = 0  right = 5
        // {1,3,8,5,4,7}

        //左递归
        if (left < r) {
            quickSort(arr, left, r);
        }
        //向右递归
        if (right > l) {
            quickSort(arr, l, right);
        }

    }


//    public void quickSort(T[] arr) {
//        shuffle(arr);
//        sort(arr, 0, arr.length - 1);
//    }
//
//    public void shuffle(T[] arr) {
//        List<Comparable> list = Arrays.asList(arr);
//        Collections.shuffle(list);
//        list.toArray(arr);
//    }
//
//    public void sort(T[] arr, int l, int h) {
//
//        if (h <= l) {
//            return;
//        }
//        int j = partition(arr, l, h);
//        sort(arr, l, j - 1);
//        sort(arr, j + 1, h);
//
//    }
//
//    private int partition(T[] arr, int l, int h) {
//
//        int i = l, j = h;
//        T v = arr[l];
//        while (true) {
//            while (arr[++i].compareTo(v) == -1 && i != h) ;
//            while (v.compareTo(arr[--j]) == -1 && j != l) ;
//            if (i >= j) {
//                break;
//            }
//            swap(arr, i, j);
//        }
//        swap(arr, l, j);
//        return j;
//
//    }


}
