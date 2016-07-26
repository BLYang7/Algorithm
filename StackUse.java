package com.algorithm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class StackUse {

	/**
	 * 栈的典型应用其一：字符串匹配
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isValid(String str) {

		HashMap<Character, Character> map = new HashMap<Character, Character>();
		map.put('{', '}');
		map.put('[', ']');
		map.put('(', ')');

		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < str.length(); i++) {
			char curr = str.charAt(i);
			if (map.keySet().contains(curr)) {
				stack.add(curr);
			} else if (map.values().contains(curr)) {
				if (stack != null && map.get(stack.peek()) == curr) {
					stack.pop();
				} else {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}

	/**
	 * 栈的典型应用其二：表达式后缀栈
	 */
	private static int[] operatPriority = new int[] { 0, 3, 2, 1, -1, 1, 0, 2 }; // 操作符优先级，依次是（
																					// ）
																					// ＊
																					// ＋
																					// ，
																					// －
																					// .
																					// /

	private static Stack<String> prepare(String expression) {

		Stack<String> postfixStack = new Stack<String>();
		Stack<Character> opStack = new Stack<Character>();

		opStack.push(',');// 运算符放入栈底元素逗号，此符号优先级最低

		char[] arr = expression.toCharArray();
		int currentIndex = 0; // 当前字符的位置
		int count = 0; // 上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
		char currentOp, peekOp; // 当前操作符和栈顶操作符

		for (int i = 0; i < arr.length; i++) {
			currentOp = arr[i];
			if (isOperator(currentOp)) {// 如果当前字符是运算符

				if (count > 0) {
					postfixStack.push(new String(arr, currentIndex, count));// 取两个运算符之间的数字
				}
				peekOp = opStack.peek();
				if (currentOp == ')') {// 遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
					while (opStack.peek() != '(') {
						postfixStack.push(String.valueOf(opStack.pop()));
					}
					opStack.pop(); // 将'('弹出扔掉
				} else {
					// 当前字符优先级不大于字符栈顶的字符优先级时，字符栈顶元素弹出并放入输出栈
					while (currentOp != '(' && peekOp != ','
							&& compare(currentOp, peekOp)) {
						postfixStack.push(String.valueOf(opStack.pop()));
						peekOp = opStack.peek();
					}
					// 讲比较之后的读入的字符放到字符栈
					opStack.push(currentOp);
				}
				count = 0;
				currentIndex = i + 1; // 当前index加一，继续向下读取
			} else {
				count++;
			}
		}

		// 最后一个字符不是括号或者其他运算符的则加入后缀式栈中
		if (count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {
			postfixStack.push(new String(arr, currentIndex, count));
		}

		// 将字符栈中的符合条件的元素都添加到输出栈中
		while (opStack.peek() != ',') {
			postfixStack.push(String.valueOf(opStack.pop()));// 将操作符栈中的剩余的元素添加到后缀式栈中
		}

		return postfixStack;
	}

	// 判断是否为运算符
	private static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '('
				|| c == ')';
	}

	// 比较运算符优先级。如果是peek优先级高于cur，返回true，默认都是peek优先级要低
	public static boolean compare(char cur, char peek) {
		boolean result = false;
		if (operatPriority[(peek) - 40] >= operatPriority[(cur) - 40]) {
			result = true;
		}
		return result;
	}

	/**
	 * 栈的典型应用其三：后缀表达式的计算
	 */
	public String calculate(String expression) {
		Stack<String> resultStack = new Stack<String>();

		// 获取后缀表达式
		Stack<String> postfixStack = prepare(expression);
		Collections.reverse(postfixStack); // 后缀表达式的栈是按顺序存储的，但是计算的时候需要从第一个字符开始(存放在栈的最低了)，所以要反转一下
		String firstValue, secondValue, currentValue;// 参与计算的第一个值，第二个值和算术运算符

		while (!postfixStack.isEmpty()) {
			currentValue = postfixStack.pop();
			if (!isOperator(currentValue.charAt(0))) {// 如果不是运算符则存入操作数栈中
				resultStack.push(currentValue);
			} else {// 如果是运算符则从操作数栈中取两个值和该数值一起参与运算
				secondValue = resultStack.pop();
				firstValue = resultStack.pop();
				String tempResult = calculate(firstValue, secondValue,
						currentValue.charAt(0));
				resultStack.push(tempResult);
			}
		}

		return resultStack.peek();
	}
	// 按照给定的算术运算符做计算
	private String calculate(String firstValue, String secondValue, char currentOp) {
		String result = "";
		switch (currentOp) {
		
		case '+':{
			result = String.valueOf(Double.parseDouble(firstValue) + Double.parseDouble(secondValue));
			break;
		}
		case '-':{
			result = String.valueOf(Double.parseDouble(firstValue) - Double.parseDouble(secondValue));
			break;
		}
		case '*':{
			result = String.valueOf(Double.parseDouble(firstValue) * Double.parseDouble(secondValue));
			break;
		}
		case '/':{
			result = String.valueOf(Double.parseDouble(firstValue) / Double.parseDouble(secondValue));
			break;
		}
		
		}
		return result;
	}
	
	/**
	 * 栈的典型应用其四：数制转换
	 * 这里举个例子：八进制转换
	 */
	private String numChange(int number) {
		Stack<String> stack = new Stack<String>();
		while (number > 0) {
			stack.push(number % 8 + "");
			number = number / 8;
		}
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			sb.append(stack.pop());
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 栈的典型应用其五：迷宫求解
	 */
	private class Point {
		int x = 0;
		int y = 0;
		public Point() {
			this(0, 0);
		}
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public boolean equals(Point p) {
			return (x == p.x) && (y == p.y);
		}
		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	private int[][] maze = null; // 迷宫图
	private Stack<Point> stack = new Stack<Point>(); // 栈区，用来保存路径信息

	// 寻路方法
	public void go() {
		Point out = new Point(maze.length - 1, maze[0].length - 1); // 出口
		Point in = new Point(0, 0); // 入口
		Point curNode = in; // 当前点为入口
		Point nextNode = null; // 下一个访问点(目标点)

		while (!curNode.equals(out)) {
			nextNode = new Point(curNode.x, curNode.y); // 设置目标点为当前点，便于下面偏移

			// 如果下方是空的，则目标点向下偏移,优先向下走
			if ((curNode.x + 1) < maze.length
					&& maze[curNode.x + 1][curNode.y] == 0) {
				nextNode.x++;
			}

			// 如果右边是空的，则目标点向右偏移
			else if ((curNode.y + 1) < maze[0].length
					&& maze[curNode.x][curNode.y + 1] == 0) {
				nextNode.y++;
			}

			// 如果上方是空的，则目标点向上偏移
			else if ((curNode.x - 1) >= 0
					&& maze[curNode.x - 1][curNode.y] == 0) {
				nextNode.x--;
			}

			// 如果左边是空的，则目标点向左偏移
			else if ((curNode.y - 1) >= 0
					&& maze[curNode.x][curNode.y - 1] == 0) {
				nextNode.y--;
			}

			// 这里是没有路的状态
			else {
				maze[curNode.x][curNode.y] = 3; // 标记为死路
				if (stack.isEmpty()) { // 判断栈是否为空
					System.out.println("Non solution");
					return;
				}
				curNode = stack.pop(); // 弹出上一次的点
				continue; // 继续循环
			}

			// 如果有路的话会执行到这里
			stack.push(curNode); // 当前点压入栈中
			maze[curNode.x][curNode.y] = 2; // 标记为已走
			curNode = nextNode; // 移动当前点
		}

		if (nextNode.equals(out)) {
			stack.push(nextNode); // 将出口点添加到当前路劲中
			maze[nextNode.x][nextNode.y] = 2; // 标记为已走
		}

		System.out.println("\n该迷宫的一条可行路劲为：");
		for (int i = 0; i < stack.size(); i++) {
			System.out.println(stack.elementAt(i));
		}
	}
	
	// 测试
	public static void main(String[] args) {
		
		// 字符平衡的判断
		System.out.println(isValid("{{}[]()00}0"));

		// 中缀转后缀
		System.out.println(prepare("1+2+3*4"));

		StackUse t = new StackUse();
		
		// 后缀表达式的计算
		System.out.println(t.calculate("1+2+3*4"));

		// 数制转换的计算
		System.out.println(t.numChange(8));

		// 迷宫求解
		t.maze = new int[][] { { 0, 0, 0, 1, 0 }, { 0, 1, 0, 0, 0 },
				{ 1, 0, 0, 0, 0 }, { 0, 1, 1, 1, 0 }, { 0, 0, 0, 1, 0 } };
		t.go();

	}

}






