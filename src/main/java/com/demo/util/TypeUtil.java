package com.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeUtil {
	private static final Logger logger = LoggerFactory.getLogger(TypeUtil.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void ClassToClass(Object object, Object goalObject) {
		Class fatherClass = object.getClass();
		Field fatherFields[] =  object.getClass().getDeclaredFields();
		Field objectFields[] = goalObject.getClass().getDeclaredFields();
		Map<String, Field> fields = new HashMap<>();
		for (Field field : objectFields) {
			fields.put(field.getName(),field);
		}
		for (int i = 0; i < fatherFields.length; i++) {
			Field f = fatherFields[i];// 取出每一个属性，如deleteDate
			if (!(f.getName().indexOf("$") == 0)) {
				f.setAccessible(true);
				Method m;
				try {
					m = fatherClass.getMethod("get" + upperHeadChar(f.getName()));
					m.setAccessible(true);
					Object obj = m.invoke(object);// 取出属性值
					Field field = fields.get(f.getName());
					if (field!=null) {
						field.setAccessible(true);
						field.set(goalObject, obj);
					}
				} catch (NoSuchMethodException | SecurityException e) {
					logger.info(f.getName()+"属性不存在");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static String upperHeadChar(String in) {
		if (Character.isUpperCase(in.charAt(1))) {
			return in;
		}
		String head = in.substring(0, 1);
		String out = head.toUpperCase() + in.substring(1, in.length());
		return out;
	}
}
