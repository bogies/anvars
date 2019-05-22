/**@Description: TODO
 * @author: renkun
 * @date: 2018年12月17日
 */
package org.bogies.common.utils;

import java.util.Map;

import okhttp3.Response;

/**@Description: TODO Http Related Common Interface Classes
 * @author: renkun
 * @date: 2018年12月17日上午10:52:25
 */
public class HttpServiceUtil {

	/**TODO GET/POST HTTP Request
	 * @param reqUrl
	 * @param reqMethod	GET/POST
	 * @param params
	 * @return String
	 */
	public String getHttpRes(String reqUrl, String reqMethod,Map<String, Object> params) {
		Response response = null;
		try {
			OkHttpHelper http = OkHttpHelper.getInstance();
			
			switch (reqMethod.toUpperCase()) {
			case "GET":
				response = http.get(reqUrl);
				break;
			default:
				response = http.post(reqUrl, params);
			}
			
			if (response.isSuccessful()) {
				return response.body().string();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
