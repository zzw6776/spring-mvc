package com.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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

	public static String getErrorInfoFromException(Exception e) {
		String result = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw =  new PrintWriter(sw);
		try {
			e.printStackTrace(pw);
			result = "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e2) {
			return "ErrorInfoFromException";
		}finally {
			try {
				sw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			pw.close();
		}
		//微信推送
		//WeChatPushUtil.weChatPush(WeChatPushUtil.MY_SCKEY,"系统出错",result);
		return result;
	}
}
