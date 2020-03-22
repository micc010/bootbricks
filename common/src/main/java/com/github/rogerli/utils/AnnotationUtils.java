
package com.github.rogerli.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 注解工具
 *
 * @author roger.li
 * @since 2018-03-30
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AnnotationUtils {

    /**
     * 读取method注解值
     *
     * @param annotationClasss 注解类
     * @param targetClass      使用注解的类
     * @param annotationFields 注解属性的名称
     * @throws Exception
     */
    public static Map<String, Map<String, Object>> getMethodValue(
            Class annotationClasss, Class targetClass,
            String... annotationFields) throws Exception {
        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>(
                20);
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClasss)) {
                Map<String, Object> methodMap = new HashMap<String, Object>(10);
                map.put(method.getName(), methodMap);
                Annotation p = method.getAnnotation(annotationClasss);
                for (String annotationField : annotationFields) {
                    Method m = p.getClass().getDeclaredMethod(annotationField);
                    if (m == null) {
                        continue;
                    }
                    Object value = m.invoke(p);
                    methodMap.put(annotationField, value);
                }
            }
        }
        return map;
    }

    /**
     * 读取method注解值
     *
     * @param annotationClasss 注解类
     * @param targetClass      使用注解的类
     * @param methodName       方法名
     * @param params           方法参数
     * @param annotationField  注解属性的名称
     * @throws Exception
     */
    public static Object getSingleMethodValue(
            Class annotationClasss, Class targetClass,
            String methodName, Object[] params,
            String annotationField) throws Exception {
        Class[] clazzes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            clazzes[i] = params[i].getClass();
        }
        Method method = targetClass.getDeclaredMethod(methodName, clazzes);
        if (method.isAnnotationPresent(annotationClasss)) {
            Annotation p = method.getAnnotation(annotationClasss);
            Method m = p.getClass().getDeclaredMethod(annotationField);
            if (m != null) {
                Object value = m.invoke(p);
                return value;
            }
        }
        return null;
    }

    /**
     * 读取field注解值
     *
     * @param annotationClasss 注解类
     * @param targetClass      使用注解的类
     * @param annotationFields 注解属性的名称
     * @return
     * @throws Exception
     */
    public static Map<String, Map<String, Object>> getFieldValue(
            Class annotationClasss, Class targetClass,
            String... annotationFields) throws Exception {
        Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>(
                20);
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClasss)) {
                Map<String, Object> fieldMap = new HashMap<String, Object>(10);
                map.put(field.getName(), fieldMap);
                Annotation p = field.getAnnotation(annotationClasss);
                for (String annotationField : annotationFields) {
                    Method m = p.getClass().getDeclaredMethod(
                            annotationField);
                    if (m == null) {
                        continue;
                    }
                    Object value = m.invoke(p);
                    fieldMap.put(annotationField, value);
                }
            }
        }
        return map;
    }

    /**
     * 读取field注解值
     *
     * @param annotationClasss 注解类
     * @param targetClass      使用注解的类
     * @param fieldName        属性名称
     * @param annotationField  注解属性的名称
     * @return
     * @throws Exception
     */
    public static Object getSingleFieldValue(
            Class annotationClasss, Class targetClass,
            String fieldName, String annotationField) throws Exception {
        Field field = targetClass.getDeclaredField(fieldName);
        if (field.isAnnotationPresent(annotationClasss)) {
            Annotation p = field.getAnnotation(annotationClasss);
            Method m = p.getClass().getDeclaredMethod(annotationField);
            if (m != null) {
                Object value = m.invoke(p);
                return value;
            }
        }
        return null;
    }

    /**
     * 读取class注解值
     *
     * @param annotationClasss 注解类
     * @param targetClass      使用注解的类
     * @param annotationFields 注解属性的名称
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getClassValue(Class annotationClasss,
                                                    Class targetClass, String... annotationFields) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
        if (targetClass.isAnnotationPresent(annotationClasss)) {
            Annotation p = targetClass.getAnnotation(annotationClasss);
            for (String annotationField : annotationFields) {
                Method m = p.getClass().getMethod(annotationField);
                if (m == null) {
                    continue;
                }
                Object value = m.invoke(p);
                map.put(annotationField, value);
            }
        }
        return map;
    }

    /**
     * 读取class注解值
     *
     * @param annotationClasss 注解类
     * @param targetClass      使用注解的类
     * @param annotationField  注解属性的名称
     * @return
     * @throws Exception
     */
    public static Object getSingleClassValue(Class annotationClasss,
                                             Class targetClass, String annotationField) throws Exception {
        if (targetClass.isAnnotationPresent(annotationClasss)) {
            Annotation p = targetClass.getAnnotation(annotationClasss);
            Method m = p.getClass().getMethod(annotationField);
            if (m != null) {
                Object value = m.invoke(p);
                return value;
            }
        }
        return null;
    }
}