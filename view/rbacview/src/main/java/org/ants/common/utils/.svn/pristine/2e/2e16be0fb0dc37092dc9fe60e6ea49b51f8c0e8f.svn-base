package org.ants.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName: OkHttpHelper
 * @Description: OkHttp3 封装
 * @author: Jerry
 * @date: 2018年12月18日 上午9:39:17
 */
public class OkHttpHelper {
	private static OkHttpHelper instance;
	private OkHttpClient httpClient;
	private Map<String, String> headers = new HashMap<String, String>();

	private OkHttpHelper() {
		/**
		 * 设置连接超时
		 * 设置写超时
		 * 设置读超时
		 * 是否自动重连
		 */
		httpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
				.writeTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS)
				.retryOnConnectionFailure(true)
				.build();
	}
	/**
	 * 单例实现
	 * @return
	 */
	public static OkHttpHelper getInstance() {
		if (instance == null) {
			synchronized (OkHttpHelper.class) {
				if (instance == null) {
					instance = new OkHttpHelper();
				}
			}
		}
		return instance;
	}
	/**
	 * 异步 get 请求
	 * @param url 请求的地址
	 * @throws Exception
	 */
	public void asynGet(String url) throws Exception {
        Request request = new Request.Builder()
            .url(url)
            .build();

        httpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response) throws IOException {
			}
			@Override
			public void onFailure(Call call, IOException e) {
			}
		});
	}
	/**
	 * 清空 headers已经设置的内容
	 */
	public void emptyHeader() {
		headers.clear();
	}
	/**
	 * 添加一个header内容
	 * @param key header名称
	 * @param value header 值
	 * @return
	 */
	public OkHttpHelper setHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}
	/**
	 * 设置header列表
	 * @param map 包含名称和值的列表
	 * @return
	 */
	public OkHttpHelper setHeaders(Map<String, String> map) {
		headers = map;
		return this;
	}

	/**
	 * 对外公开的手动释放内存线程池方法，在内存不足时可调用
	 */
	public void closeThreadPools() {
		// 清除并关闭线程池
		httpClient.dispatcher().executorService().shutdown();
		// 清除并关闭连接池
		httpClient.connectionPool().evictAll();
		try {
			if (httpClient.cache() != null) {
				// 清除cache
				httpClient.cache().close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建请求对象
	 * @param url 要请求的地址
	 * @param params 参数列表
	 * @param type 请求的方法 GET, POST PUT, DELETE
	 * @return 请求结果
	 */
	public Response request(String url, Map<String, Object> params, String type) {
		if (StringUtils.isBlank(type)) {
			type = "GET";
		} else {
			type = type.toUpperCase();
		}
		
		Request.Builder builder = new Request.Builder();
		builder = builder.url(url);
		for (Map.Entry<String, String> row : headers.entrySet()) {
			builder.addHeader(row.getKey(), row.getValue());
		}
		
		RequestBody body = null;
		switch (type) {
		case "POST":
			body = buildRequestBody(params);
			builder.post(body);
			try {
				long length = body.contentLength();
				builder.addHeader("Content-Length", length + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "PUT":
			body = buildRequestBody(params);
			builder.put(body);
			try {
				long length = body.contentLength();
				builder.addHeader("Content-Length", length + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "DELETE":
			builder.delete();
			break;
		case "GET":
		default:
			builder.get();
			break;
		}
		
		Request request = builder.build();
		Response response = null;
		try {
			response = httpClient.newCall(request).execute();
		} catch (Exception e) {
			e.printStackTrace();
			response = null;
		}
		
		return response;
	}
	/**
	 * get 请求
	 * @param url 请求的地址
	 * @return 请求结果
	 */
	public Response get(String url) {
		Request.Builder builder = new Request.Builder();
		builder = builder.url(url);
		for (Map.Entry<String, String> row : headers.entrySet()) {
			builder.addHeader(row.getKey(), row.getValue());
		}
		
		Request request = builder.build();
		Response response = null;
		try {
			response = httpClient.newCall(request).execute();
		} catch (Exception e) {
			e.printStackTrace();
			response = null;
		}
		
		return response;
	}
	/**
	 * post 请求
	 * @param url 请求地址
	 * @param params 数据
	 * @return
	 */
	public Response post(String url, Map<String, Object> params) {
		Request.Builder builder = new Request.Builder();
		builder = builder.url(url);
		for (Map.Entry<String, String> row : headers.entrySet()) {
			builder.addHeader(row.getKey(), row.getValue());
		}
		
		RequestBody body = null;
		body = buildRequestBody(params);
		builder.post(body);
		Response response = null;
		try {
			long length = body.contentLength();
			builder.addHeader("Content-Length", String.valueOf(length));
			Request request = builder.build();
			response = httpClient.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
			response = null;
		}
	
		return response;
	}
	/**
	 * put 请求
	 * @param url 请求地址
	 * @param params 数据
	 * @return
	 */
	public Response put(String url, Map<String, Object> params) {
		Request.Builder builder = new Request.Builder();
		builder = builder.url(url);
		for (Map.Entry<String, String> row : headers.entrySet()) {
			builder.addHeader(row.getKey(), row.getValue());
		}
		
		RequestBody body = null;
		body = buildRequestBody(params);
		builder.put(body);
		Response response = null;
		try {
			long length = body.contentLength();
			builder.addHeader("Content-Length", String.valueOf(length));
			Request request = builder.build();
			response = httpClient.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
			response = null;
		}
		
		return response;
	}
	/**
	 * delete 请求
	 * @param url 请求地址
	 * @param params 数据
	 * @return
	 */
	public Response delete(String url, Map<String, Object> params) {
		Request.Builder builder = new Request.Builder();
		builder = builder.url(url);
		for (Map.Entry<String, String> row : headers.entrySet()) {
			builder.addHeader(row.getKey(), row.getValue());
		}
		
		RequestBody body = null;
		body = buildRequestBody(params);
		builder.delete(body);
		Response response = null;
		try {
			long length = body.contentLength();
			builder.addHeader("Content-Length", String.valueOf(length));
			Request request = builder.build();
			response = httpClient.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
			response = null;
		}
		
		return response;
	}
	/**
	 * 通过Map的键值对构建post请求对象的body
	 * @param params
	 * @return
	 */
	private RequestBody buildRequestBody(final Map<String, Object> params) {
		FormBody.Builder builder = new FormBody.Builder();
		Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> next = iterator.next();
			String key = next.getKey();
			Object value = next.getValue();
			builder.add(key, value + "");
		}
		return builder.build();
	}
}
