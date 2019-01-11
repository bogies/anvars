package org.ants.common.utils;

import java.text.DecimalFormat;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月17日上午8:40:16
 */
public class NumberUtils {
	/**
	 * 格式化数字为千分位显示；
	 * @param 要格式化的数字；
	 * @return
	 */
	public static String fmtMicrometer(String text)
	{
	    DecimalFormat df = null;
	    String dot = ".";
	    if(text.indexOf(dot) > 0)
	    {
	        if(text.length() - text.indexOf(dot)-1 == 0)
	        {
	            df = new DecimalFormat("###,##0.");
	        }else if(text.length() - text.indexOf(dot)-1 == 1)
	        {
	            df = new DecimalFormat("###,##0.0");
	        }else
	        {
	            df = new DecimalFormat("###,##0.00");
	        }
	    }else
	    {
	        df = new DecimalFormat("###,##0");
	    }
	    double number = 0.0;
	    try {
	         number = Double.parseDouble(text);
	    } catch (Exception e) {
	        number = 0.0;
	    }
	    return df.format(number);
	}
}
