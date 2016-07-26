package com.algorithm;
/**
 * 寻找第K大大数值
 * @author blyang
 */
public class KBig {

	public static void main(String[] args) {
		
		int K = 5;
		int[] nums = new int[]{ 0, 3, 2, 7, 8, 9, 3, 4, 6, 1, 9, 3, 7};
		int max = 10;
		int[] numArr = new int[max];
		int sum = 0;
		
		for( int i=0; i<numArr.length; i++ ){
			numArr[i] = 0;
		}
		
		for(int num : nums){
			numArr[num]++;
		}
		
		int i=0;
		for( i=numArr.length-1; i>=0; i--){
			sum += numArr[i];
			if(sum >= K){
				break;
			}
		}
		
		System.out.println(i);
	}
}
