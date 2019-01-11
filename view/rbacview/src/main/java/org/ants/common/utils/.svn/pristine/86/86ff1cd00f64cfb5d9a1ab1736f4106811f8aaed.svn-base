package org.ants.common.utils;

import java.util.Map;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月17日上午8:40:07
 */
public class MapData {
	/**
	 * 从一个map中获取字符串值，未找到则返回设置的值
	 * @param map 数据源
	 * @param field key 名
	 * @param def 未找到field时返回的默认值
	 * @return String 值
	 */
	public static String getMapData(Map<String, Object> map, String field, String def) {
		if (null == def) {
			def = "";
		}
		Object o = map.get(field);
		if (null == o) {
			return def;
		} else {
			return o.toString();
		}
	}
}
