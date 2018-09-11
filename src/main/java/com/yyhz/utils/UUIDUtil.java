package com.yyhz.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UUIDUtil {
	private static Random random = new Random();

	public UUIDUtil() {
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.replace("-", "");
		return temp;
	}

	// 获得指定数量的UUID
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	public static String getRandomStrBySplit(int number, int position, String split) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] strs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
				'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z' };

		for (int i = 0; i < number; i++) {
			if ((i) % position == 0 && i > 0) {
				stringBuffer.append(split);
			}
			stringBuffer.append(strs[random.nextInt(61)]);
		}
		return stringBuffer.toString();
	}

	public static String getRandomStr(int number) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] strs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
				'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z' };

		for (int i = 0; i < number; i++) {
			stringBuffer.append(strs[random.nextInt(61)]);
		}
		return stringBuffer.toString();
	}
	
	public static String getRandomLowStr(int number) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] strs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		for (int i = 0; i < number; i++) {
			stringBuffer.append(strs[random.nextInt(35)]);
		}
		return stringBuffer.toString();
	}
	
	public static String getRandomStrz(int number) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] strs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		for (int i = 0; i < number; i++) {
			stringBuffer.append(strs[random.nextInt(26)]);
		}
		return stringBuffer.toString();
	}

	public static String getRandomNum(int number) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] strs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		for (int i = 0; i < number; i++) {
			stringBuffer.append(strs[random.nextInt(5)]);
		}
		return stringBuffer.toString();
	}

	public static String getConvertCode() {
		StringBuffer stringBuffer = new StringBuffer();
		String time = (new Date()).getTime() + "";
		time = time + getRandomStr(3);
		for (int i = 0; i < 3; i++) {
			stringBuffer.append(time.substring(0, 4));
			stringBuffer.append("-");
			time = time.substring(4);
		}
		stringBuffer.append(time);
		return stringBuffer.toString();
	}

	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
