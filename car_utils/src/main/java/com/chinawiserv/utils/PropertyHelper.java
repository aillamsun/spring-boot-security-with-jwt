package com.chinawiserv.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by sungang on 2017/4/17.
 */
public class PropertyHelper {

    /**
     * 不为null的属性
     *
     * @param o
     * @param _class
     * @param map
     * @return
     */
    public static Map<String, Object> recursiveNotNull(Object o, Class<?> _class,
                                                       Map<String, Object> map) {
        if (_class == null) {
            return map;
        } else {
            Field[] field = _class.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
            // 遍历方法集合
            for (int j = 0; j < field.length; j++) { // 遍历所有属性

                String name1 = field[j].getName(); // 获取属性的名字
                // System.out.println("attribute name:"+name);
                String name2 = name1.substring(0, 1).toUpperCase()
                        + name1.substring(1); // 将属性的首字符大写，方便构造get，set方法
                Object obj = null;
                Method m;
                try {
                    m = _class.getMethod("get" + name2);
                    m.setAccessible(true);
                    obj = m.invoke(o);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (obj != null) {
                    map.put(name1, obj);
                }
            }
            return recursiveNotNull(o, _class.getSuperclass(), map);
        }
    }

    /**
     * 所有属性
     *
     * @param o
     * @param _class
     * @param map
     * @return
     */
    public static Map<String, Object> recursiveAll(Object o, Class<?> _class,
                                                   Map<String, Object> map) {
        if (_class == null) {
            return map;
        } else {
            Field[] field = _class.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
            // 遍历方法集合
            for (int j = 0; j < field.length; j++) { // 遍历所有属性

                String name1 = field[j].getName(); // 获取属性的名字
                // System.out.println("attribute name:"+name);
                String name2 = name1.substring(0, 1).toUpperCase()
                        + name1.substring(1); // 将属性的首字符大写，方便构造get，set方法
                Object obj = null;
                Method m;
                try {
                    m = _class.getMethod("get" + name2);
                    m.setAccessible(true);
                    obj = m.invoke(o);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (obj != null) {
                    map.put(name1, obj);
                }
            }
            return recursiveAll(o, _class.getSuperclass(), map);
        }
    }


    /**
     * 反射后附加对象
     *
     * @param entityClass
     * @param entityMap
     * @return
     */
    public static Object getFansheObj(Class entityClass, Map<String, Object> entityMap) {
        Object t = null;
        try {
            t = Class.forName(entityClass.getName()).newInstance();
            Field[] field = t.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String yname = field[j].getName(); // 获取属性的名字
                String name = yname.substring(0, 1).toUpperCase() + yname.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                // if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = t.getClass().getMethod("get" + name);
                String value = (String) m.invoke(t); // 调用getter方法获取属性值
                if (value == null) {
                    m = t.getClass().getMethod("set" + name, Class.forName(type.split(" ")[1]));
                    m.invoke(t, entityMap.get(yname));
                    //}
                }
                /*if (type.equals("class java.lang.Integer")) {
	                Method m = t.getClass().getMethod("get" + name);
	                Integer value = (Integer) m.invoke(t);
	                if (value == null) {
	                    m = t.getClass().getMethod("set"+name,Integer.class);
	                    m.invoke(t, 0);
	                }
	            }
	            if (type.equals("class java.lang.Boolean")) {
	                Method m = t.getClass().getMethod("get" + name);
	                Boolean value = (Boolean) m.invoke(t);
	                if (value == null) {
	                    m = t.getClass().getMethod("set"+name,Boolean.class);
	                    m.invoke(t, false);
	                }
	            }
	            if (type.equals("class java.util.Date")) {
	                Method m = t.getClass().getMethod("get" + name);
	                Date value = (Date) m.invoke(t);
	                if (value == null) {
	                    m = t.getClass().getMethod("set"+name,Date.class);
	                    m.invoke(t, new Date());
	                }
	            }*/

                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
