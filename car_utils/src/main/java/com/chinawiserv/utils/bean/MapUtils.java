package com.chinawiserv.utils.bean;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapUtils {
	/**
	 * map 转 Bean
	 * 
	 * @param map
	 * @param cls
	 * @return
	 */
	public static Object map2Bean(Map map, Class cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取出bean里的所有方法
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取方法名
			String method = methods[i].getName();
			// 取出方法的类型
			Class[] cc = methods[i].getParameterTypes();
			if (cc.length != 1)
				continue;

			// 如果方法名没有以set开头的则退出本次for
			if (method.indexOf("set") < 0)
				continue;
			// 类型
			String type = cc[0].getSimpleName();

			try {
				// 转成小写
				// Object value = method.substring(3).toLowerCase();
				Object value = method.substring(3, 4).toLowerCase()
						+ method.substring(4);
				System.out.println("value == " + value);
				// 如果map里有该key
				if (map.containsKey(value) && map.get(value) != null) {
					// 调用其底层方法
					setValue(type, map.get(value), i, methods, obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/***************************************************************************
	 * 调用底层方法设置值
	 */
	private static void setValue(String type, Object value, int i,
			Method[] method, Object bean) {
		if (value != null && !value.equals("")) {
			try {
				if (type.equals("String")) {
					// 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数
					method[i].invoke(bean, new Object[] { value });
				} else if (type.equals("int") || type.equals("Integer")) {
					method[i].invoke(bean, new Object[] { new Integer(""
							+ value) });
				} else if (type.equals("double") || type.equals("Double")) {
					method[i].invoke(bean,
							new Object[] { new Double("" + value) });
				} else if (type.equals("float") || type.equals("Float")) {
					method[i].invoke(bean,
							new Object[] { new Float("" + value) });
				} else if (type.equals("long") || type.equals("Long")) {
					method[i].invoke(bean,
							new Object[] { new Long("" + value) });
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					method[i].invoke(bean,
							new Object[] { Boolean.valueOf("" + value) });
				} else if (type.equals("BigDecimal")) {
					method[i].invoke(bean, new Object[] { new BigDecimal(""
							+ value) });
				} else if (type.equals("Date")) {
					Date date = null;
					if (value.getClass().getName().equals("java.util.Date")) {
						date = (Date) value;
					} else {
						String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm:ss"
								: "yyyy-MM-dd";
						SimpleDateFormat sf = new SimpleDateFormat();
						sf.applyPattern(format);
						date = sf.parse((String) (value));
					}
					if (date != null) {
						method[i].invoke(bean, new Object[] { date });
					}
				} else if (type.equals("byte[]")) {
					method[i].invoke(bean,
							new Object[] { new String(value + "").getBytes() });
				}
			} catch (Exception e) {
				System.out
						.println("将linkHashMap 或 HashTable 里的值填充到javabean时出错,请检查!");
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 简单的 string  转map
	 * @param singInfo
	 * @return
	 */
	public static Map<String, String> str2Map(String singInfo ){
		//将map.toString后的串反转成map
		String str1 = singInfo.replaceAll("\\{|\\}", "");//singInfo是一个map  toString后的字符串。
    		String str2 = str1.replaceAll(" ", "");
    		String str3 = str2.replaceAll(",", "&");


		Map<String, String> map = null;
		if ((null != str3) && (!"".equals(str3.trim())))
		{
		  String[] resArray = str3.split("&");
		  if (0 != resArray.length)
		  {
		    map = new HashMap(resArray.length);
		    for (String arrayStr : resArray) {
		      if ((null != arrayStr) && (!"".equals(arrayStr.trim())))
		      {
		        int index = arrayStr.indexOf("=");
		        if (-1 != index) {
		          map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
		        }
		      }
		    }
		  }
		}
		return map;
	}
	
	
	/**
	 * 用户API 接口的 mapToBean 转换  对null 或 空串做了处理
	 * @param map
	 * @param cls
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Object mapToBeanApi(Map map, Class cls) throws  Exception {
		Object obj = null;
		obj = cls.newInstance();
		// 取出bean里的所有方法
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取方法名
			String method = methods[i].getName();
			// 取出方法的类型
			Class[] cc = methods[i].getParameterTypes();
			if (cc.length != 1)
				continue;

			// 如果方法名没有以set开头的则退出本次for
			if (method.indexOf("set") < 0)
				continue;
			// 类型
			String type = cc[0].getSimpleName();
			Object key = method.substring(3, 4).toLowerCase()
					+ method.substring(4);
//			System.out.println("key == " + key);
			// 如果map里有该key
			if (map.containsKey(key)  ) {
				Object value = map.get(key);
				// 调用其底层方法
				if (type.equals("String")) {
					// 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数
					value= (value==null||value.equals("")? "" : value); 
					methods[i].invoke(obj, new Object[] { value });
				} else if (type.equals("int") || type.equals("Integer")) {
					value= (value==null||value.equals("")? 0 : value); 
					methods[i].invoke(obj, new Object[] { new Integer(""
							+ value) });
				} else if (type.equals("double") || type.equals("Double")) {
					value= (value==null||value.equals("")? 0.0 : value); 
					methods[i].invoke(obj,
							new Object[] { new Double("" + value) });
				} else if (type.equals("float") || type.equals("Float")) {
					value= (value==null||value.equals("")? 0.0f : value); 
					methods[i].invoke(obj,
							new Object[] { new Float("" + value) });
				} else if (type.equals("long") || type.equals("Long")) {
					value= (value==null||value.equals("")? 0l : value); 
					methods[i].invoke(obj,
							new Object[] { new Long("" + value) });
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					value= (value==null||value.equals("")? false : value); 
					value = ("1".equals(value.toString()) ? true:false);
					methods[i].invoke(obj,
							new Object[] { Boolean.valueOf("" + value) });
				} else if (type.equals("BigDecimal")) {
					value= (value==null||value.equals("")? 0 : value); 
					methods[i].invoke(obj, new Object[] { new BigDecimal(""
							+ value) });
				} else if (type.equals("Date")) {
					Date date = null;
					if (value.getClass().getName().equals("java.util.Date")) {
						date = (Date) value;
					} else {
						String format = ((String) value).indexOf(":") > 0 ? "yyyy-MM-dd hh:mm:ss"
								: "yyyy-MM-dd";
						SimpleDateFormat sf = new SimpleDateFormat();
						sf.applyPattern(format);
						date = sf.parse((String) (value));
					}
					if (date != null) {
						methods[i].invoke(obj, new Object[] { date });
					}
				} else if (type.equals("byte[]")) {
					methods[i].invoke(obj,
							new Object[] { new String(value + "").getBytes() });
				}
			}
		}
		return obj;
	}
}
