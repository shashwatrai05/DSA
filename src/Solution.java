import java.util.*;

class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        Arrays.sort(nums);
        List<Integer> integerList = new ArrayList<>();
        int count = 1;

        for (int i = 0; i < nums.length; i++) {
            if (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                continue;
            }
            if (nums[i] == count) {
                count++;
            } else {
                integerList.add(count);
                count++;
            }
        }
        // Adding remaining missing numbers if any
        while (count <= nums.length) {
            integerList.add(count);
            count++;
        }

        return integerList;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example test case
        int[] nums = {4, 3, 2, 7, 8, 2, 1, 5};  // Example input
        List<Integer> result = solution.findDisappearedNumbers(nums);

        System.out.println("Disappeared Numbers: " + result);
    }
}
