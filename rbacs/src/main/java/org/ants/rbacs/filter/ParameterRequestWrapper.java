package org.ants.rbacs.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.WordUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ParameterRequestWrapper extends HttpServletRequestWrapper {
    private Map<String , String[]> params = new HashMap<String, String[]>();
    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());
    }
    
    /**
     * 复写获取key的方法
     */
	@Override
    public Enumeration<String> getParameterNames() {
        Vector<String> names = new Vector<String>(params.keySet());
        return names.elements();
    }

    /**
     * 复写获取值value的方法
     */
    @Override
    public String getParameter(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                return strArr[0];
            } else {
                return null;
            }
        } else if (v instanceof String) {
            return (String) v;
        } else {
            return v.toString();
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        Object v = params.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            return (String[]) v;
        } else if (v instanceof String) {
            return new String[] { (String) v };
        } else {
            return new String[] { v.toString() };
        }
    }

    public void addAllParameters(Map<String , Object>otherParams) {//增加多个参数
        for(Map.Entry<String , Object>entry : otherParams.entrySet()) {
            addParameter(entry.getKey() , entry.getValue());
        }
    }

    public void addString(String name, String value) {
    	params.put(name , new String[] {(String)value});
    }

    public void addParameter(String name , Object value) {//增加参数
        if(value != null) {
            if(value instanceof String[]) {
                params.put(name , (String[])value);
            }else if(value instanceof String) {
                params.put(name , new String[] {(String)value});
            }else {
                params.put(name , new String[] {String.valueOf(value)});
            }
        }
    }

    /** 简单封装，请根据需求改进 */
    public void addObject(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        try {
            for (Method method : methods) {
                if (!method.getName().startsWith("get")) {
                    continue;
                }
                Object invoke = method.invoke(obj);
                if (invoke == null || "".equals(invoke)) {
                    continue;
                }

                String filedName = method.getName().replace("get", "");
                filedName = WordUtils.uncapitalize(filedName);

                if (invoke instanceof Collection) {
                    Collection<?> collections = (Collection<?>) invoke;
                    if (collections != null && collections.size() > 0) {
                        String[] strings = (String[]) collections.toArray();
                        addParameter(filedName, strings);
                        return;
                    }
                }

                addParameter(filedName, invoke);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
