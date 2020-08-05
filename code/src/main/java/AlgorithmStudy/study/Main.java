package AlgorithmStudy.study;


import AlgorithmStudy.study.study01.SortAlgorithm;
import AlgorithmStudy.study.study01.Test01;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // 构造方法
//        Test01 test01 = new Test01("eeee");

        // 排序
        int[] arr = {5,8,3,1,4,7};
        SortAlgorithm sortAlgorithm = new SortAlgorithm();

        // Java中的排序
//        Arrays.sort(arr);
//        System.out.println("Java Array sort：" + Arrays.toString(arr));

        // 冒泡排序
//        sortAlgorithm.bubbleSort(arr);

        // 简单选择排序
//        sortAlgorithm.selectSort(arr);

        // 直接插入排序
//        sortAlgorithm.insertSort(arr);

        // 希尔排序
//        sortAlgorithm.shellSport(arr);

        // 快速排序
        sortAlgorithm.quickSort(arr,0,arr.length-1);
        System.out.println("排序后数组【快速排序】：" + Arrays.toString(arr));

//        sortAlgorithm.quickSort();





    }
}
