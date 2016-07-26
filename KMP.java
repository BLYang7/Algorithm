package com.algorithm;

import java.util.Arrays;

/**
 * KMP算法的Java实现
 */
public class KMP {

	private int[] nextArr = null;
	private String originStr = null;
	private String moduleStr = null;

	public KMP(String originStr, String moduleStr) {
		this.originStr = originStr;
		this.moduleStr = moduleStr;
		this.nextArr = caculate_nextArr(moduleStr);
	}

	/**
	 * 计算next数组的值(部分匹配值)
	 * */
	private int[] caculate_nextArr(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		int[] theNextArr = new int[str.length()];

		for (int i = 0; i < str.length(); i++) {
			if (i == 0) {
				theNextArr[i] = 0;
			} else if (i == 1) {
				if (str.charAt(0) == str.charAt(1)) {
					theNextArr[i] = 1;
				} else {
					theNextArr[i] = 0;
				}
			} else {
				int theLength2 = i;
				boolean hasEqual = false;

				for (int j = theLength2 - 1; j >= 0; j--) {
					String prefix_str = str.substring(0, j + 1);
					String suffix_str = str.substring(theLength2 - j,
							theLength2 + 1);
					if (prefix_str.equals(suffix_str)) {
						hasEqual = true;
						theNextArr[i] = prefix_str.length();
						break;
					}
				}
				if (hasEqual == false) {
					theNextArr[i] = 0;
				}
			}
		}
		return theNextArr;
	}

	/**
	 * 获取子字符串在父字符串中的位置
	 */
	public int getIndexOfStr() {

		if (moduleStr == null || moduleStr.length() <= 0) {
			return -1;
		}
		if (originStr == null || originStr.length() <= 0) {
			return -1;
		}
		if (originStr.length() < moduleStr.length()) {
			return -1;
		}
		int res = -1;
		int totalLength = originStr.length();

		int origin_loc = 0;
		int module_loc = 0;
		while (true) {

			char c_origin = originStr.charAt(origin_loc);
			char c_module = moduleStr.charAt(module_loc);

			if (c_origin == c_module) {
				if (module_loc == moduleStr.length() - 1) {
					res = origin_loc - module_loc;
					break;
				} else {
					origin_loc++;
					module_loc++;
				}
			} 
			else {
				if (module_loc == 0) {
					origin_loc++;
				} else {
					module_loc=nextArr[module_loc-1];  
				}
			}
			
			if (origin_loc >= totalLength) {
				break;
			}
		}
		return res;
	}
	
	// 测试
	public static void main(String[] args) {
		KMP ktest = new KMP("BBC ABCDAB ABCDABCDABDE", "ABDE");
		System.out.println("部分匹配值的next数组：");
		System.out.println(Arrays.toString(ktest.nextArr));
		int theLoc = ktest.getIndexOfStr();
		System.out.println();
		System.out.println("匹配位置在：" + theLoc);
	}
}






