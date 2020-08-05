package leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class code {

    public static void main(String[] args) {
        System.out.println("test");
    }


}


/**
 * 1.三数之和
 */
// 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
// 注意：答案中不可以包含重复的三元组。

// 示例
// 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
// 满足要求的三元组集合为：
// [
//   [-1, 0, 1],
//   [-1, -1, 2]
// ]

//    解：
//    先对整数数组进行排序: sort nums[] = [-4, -1, -1, 0, 1, 2]
//    遍历枚举 a b c
//    a - 从数组 0下标值开始； c - 从数组尾起； b - 从a数组下标+1 起 【其中需要判断a/b/c的值  不能和之前相同，即：a[i]==a[i-1],跳过】
//    遍历过程中需保持 second < third  至指针重合，即：second == third 退出循环
//    遍历过程符合 a + b + c == 0  为一组符合的三元组

class Solution1 {
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        // 枚举 a
        for (int first = 0; first < n; ++first) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            int third = n - 1;
            int target = -nums[first];
            // 枚举 b
            for (int second = first + 1; second < n; ++second) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 需要保证 b 的指针在 c 的指针的左侧
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }
}

/**
 * 2.无重复字符的最长子串
 */

// 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度

// 解：
// class Solution {
//     public int lengthOfLongestSubstring(String s) {
//         // 哈希集合，记录每个字符是否出现过
//         Set<Character> occ = new HashSet<Character>();
//         int n = s.length();
//         // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
//         int rk = -1, ans = 0;
//         for (int i = 0; i < n; ++i) {
//             if (i != 0) {
//                 // 左指针向右移动一格，移除一个字符
//                 occ.remove(s.charAt(i - 1));
//             }
//             while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
//                 // 不断地移动右指针
//                 occ.add(s.charAt(rk + 1));
//                 ++rk;
//             }
//             // 第 i 到 rk 个字符是一个极长的无重复字符子串
//             ans = Math.max(ans, rk - i + 1);
//         }
//         return ans;
//     }
// }


// this one might be better
class Solution2 {
    public int lengthOfLongestSubstring(String s) {
        if (s.length()==0) return 0;
        // map<字符,下标值>
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        // 无重复字串最大长度
        int max = 0;
        // 起始下标
        int left = 0;
        //丛下标0起 遍历
        for(int i = 0; i < s.length(); i ++){
            // 已map中已存在该字符，得到该字符下标 + 1， 浮动窗口从该字符后一位为起始继续进行，则起始下标变化
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i)) + 1);
            }
            // 将字符放入map
            map.put(s.charAt(i),i);
            // 长度 = 下标i - 起始下标 + 1
            max = Math.max(max,i-left+1);
        }
        return max;

    }
}


/**
 * 3.爬楼梯
 */

// 数学规律 一级台阶 1； 二级台阶 2 ； 三级台阶 3 ； 四级台阶 5
// 斐波那契数列 1 1 2 3 5 8 ...

class Solution3 {
    public int climbStairs(int n) {
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; ++i) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }
}


// class Solution {
//     public int climbStairs(int n) {
//         int[] memo = new int[n+1];
//         memo[0] = 1;
//         memo[1] = 1;
//         for (int i = 2; i <= n; i++){
//             memo[i] = memo[i-1] + memo[i-2];
//         }
//         return memo[n];
//     }
// }


/**
 * 4.最佳观光组合
 */

// 给定正整数数组 A，A[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的距离为 j - i。

// 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i - j）：景点的评分之和减去它们两者之间的距离【  A[i] + A[j] - (j - i) ===> A[i] + A[j] + i - j  】。

// 返回一对观光景点能取得的最高分。

//  

// 示例：

// 输入：[8,1,5,2,6]
// 输出：11
// 解释：i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11


// 解：
// A[i] + A[j] + i - j ==> (A[i] + i) + (A[j] - j)
// 初始 mx = A[0] + 0  ans = 0
// 边遍历边维护
// 遍历过程 计算 mx + (A[j] - j) & A[j] + j
// 维护 mx : 将 A[j] + j 与 mx 比较更新  后续使用较大值继续计算 mx + (A[j] - j)


class Solution4 {
    public int maxScoreSightseeingPair(int[] A) {
        int ans = 0, mx = A[0] + 0;
        for (int j = 1; j < A.length; ++j) {
            ans = Math.max(ans, mx + A[j] - j);
            // 边遍历边维护
            mx = Math.max(mx, A[j] + j);
        }
        return ans;
    }
}
