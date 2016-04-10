package com.blyang;
/**
 * 快速排序算法
 *
 */
public class QuickSort {

	// 快排算法
	private void quickSort(int[] arr, int left, int right) {
		if (left < right) {
			int middleNum = getMiddle(arr, left, right); // 获取基准值所在的指针的位置
			quickSort(arr, left, middleNum - 1); // 对基准值左边的数列进行排序
			quickSort(arr, middleNum + 1, right); // 对基准值右边的数列进行排序
		}
	}

	// 获取基准值所在的指针的位置
	private int getMiddle(int[] arr, int left, int right) {
		int tmp = arr[left]; // 数组的第一个作为中轴
		while (left < right) {
			while (left < right && arr[right] >= tmp) {
				right--;
			}
			arr[left] = arr[right]; // 比中轴小的记录移到低端
			while (left < right && arr[left] <= tmp) {
				left++;
			}
			arr[right] = arr[left]; // 比中轴大的记录移到高端
		}
		arr[left] = tmp; // 中轴记录到尾
		return left; // 返回中轴的位置
	}

	public static void main(String[] args) {
		int[] nums = new int[] { 0, 3, 1, 2, 4, 5 };
		QuickSort t = new QuickSort();
		t.quickSort(nums, 0, nums.length-1);

		for(int num : nums){
			System.out.print(num + "-->");
		}
	}

}
