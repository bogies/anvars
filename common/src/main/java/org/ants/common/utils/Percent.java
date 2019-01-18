package org.ants.common.utils;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月17日上午8:40:28
 */
public class Percent {
	public static String fromFloat(String value) {
		float f = Float.valueOf(value) * 100;
		f = (float)(Math.round(f*100))/100;
		Float v = f;
		
		return v.toString();
	}
}
