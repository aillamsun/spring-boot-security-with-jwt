package com.chinawiserv.utils.data;

import java.util.Random;
import java.util.UUID;

public class Uuid {
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	public static long longUuid() {
		return UUID.randomUUID().getMostSignificantBits();
	}

	public static String replaceLine(String str) {
		return str.replaceAll("-", "");
	}

	public static void main(String[] args) {
		 
		System.out.println(replaceLine(Uuid.uuid()));
	}
	
	/**
	 * @描述:java产生随机手机验证码
	 * @参数名： @return
	 * @返回类型 Integer
	 * @throws
	 */
	public static String getPhoneCode() {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += array[i]+"";
		}
		return result;
	}
	
	
	/**
	 * 
	    * @Title: getRandomPwd
	    * @Description: TODO 获取随机的6位数字密码
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String getRandomPwd() {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += array[i]+"";
		}
		return result;
	}

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}



}

