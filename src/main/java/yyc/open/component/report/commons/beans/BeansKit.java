package yyc.open.component.report.commons.beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yyc.open.component.report.commons.reflect.ReflectKit;

/**
 * {@link BeansKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/31
 */
public class BeansKit {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeansKit.class);

    /**
     * Setup attribute
     *
     * @param bean  Object
     * @param name  The name of attribute
     * @param clazz Class that sets the value
     * @param value The value of attribute
     * @param <T>   The type corresponding to the value
     * @throws Exception
     */
    public static <T> void setProperty(Object bean, String name, Class<T> clazz, T value)
            throws Exception {
        Method method = ReflectKit.getPropertySetterMethod(bean.getClass(), name, clazz);
        if (method.isAccessible()) {
            method.invoke(bean, value);
        } else {
            try {
                method.setAccessible(true);
                method.invoke(bean, value);
            } finally {
                method.setAccessible(false);
            }
        }
    }

    /**
     * Returns attribute value.
     *
     * @param bean  Object
     * @param name  The name of attribute
     * @param clazz Class that sets the value
     * @param <T>   The type corresponding to the value
     * @throws Exception
     */
    public static <T> T getProperty(Object bean, String name, Class<T> clazz) throws Exception {
        Method method = ReflectKit.getPropertyGetterMethod(bean.getClass(), name);
        if (method.isAccessible()) {
            return (T) method.invoke(bean);
        } else {
            try {
                method.setAccessible(true);
                return (T) method.invoke(bean);
            } finally {
                method.setAccessible(false);
            }
        }
    }

    /**
     * Copy attributes to a map with custom prefixes.
     *
     * @param bean
     * @param prefix
     * @param map
     */
    public static void copyPropertiesToMap(Object bean, String prefix, Map<String, Object> map) {
        Class clazz = bean.getClass();
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            // Copy attribute.
            Class returnc = method.getReturnType();
            if (ReflectKit.isBeanPropertyReadMethod(method)) {
                String propertyName = ReflectKit.getPropertyNameFromBeanReadMethod(method);
                try {
                    if (ReflectKit.getPropertySetterMethod(clazz, propertyName, returnc) == null) {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                Object val;
                try {
                    val = method.invoke(bean);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Can't access copy " + propertyName, e.getCause());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can't access copy " + propertyName, e);
                }
                if (val != null) {
                    map.put(prefix + propertyName, val);
                }
            }
        }
        Field[] fields = bean.getClass().getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (map.containsKey(prefix + fieldName)) {
                continue;
            }
            int m = field.getModifiers();
            if (!Modifier.isStatic(m) && !Modifier.isTransient(m)) {
                Object val = null;
                try {
                    if (field.isAccessible()) {
                        val = field.get(bean);
                    } else {
                        try {
                            field.setAccessible(true);
                            val = field.get(bean);
                        } finally {
                            field.setAccessible(false);
                        }
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.warn("Can't access field" + fieldName + "when copy value to context", e);
                }
                if (val != null) {
                    map.put(prefix + fieldName, val);
                }
            }
        }
    }

    /**
     * Copy the same field from one object to another (write only values that have getter/setter methods)
     *
     * @param src
     * @param dst
     * @param ignoreFields
     */
    public static void copyProperties(Object src, Object dst, String... ignoreFields) {
        Class srcClazz = src.getClass();
        Class distClazz = dst.getClass();
        Method[] methods = distClazz.getMethods();
        List<String> ignoreFiledList = Arrays.asList(ignoreFields);
        for (Method dstMethod : methods) {
            if (Modifier.isStatic(dstMethod.getModifiers())
                    || !ReflectKit.isBeanPropertyReadMethod(dstMethod)) {
                // not static method, is getter.
                continue;
            }
            String propertyName = ReflectKit.getPropertyNameFromBeanReadMethod(dstMethod);
            if (ignoreFiledList.contains(propertyName)) {
                // ignore.
                continue;
            }
            Class dstReturnType = dstMethod.getReturnType();
            try {
                Method dstSetterMethod =
                        ReflectKit.getPropertySetterMethod(distClazz, propertyName, dstReturnType);
                if (dstSetterMethod != null) {
                    // Check the original object method again
                    Method srcGetterMethod = ReflectKit.getPropertyGetterMethod(srcClazz, propertyName);
                    // Raw fields have getter methods
                    Class srcReturnType = srcGetterMethod.getReturnType();
                    // The original and target fields return the same type
                    if (srcReturnType.equals(dstReturnType)) {
                        // Read the value from the original object
                        Object val = srcGetterMethod.invoke(src);
                        if (val != null) {
                            // Set to the target object
                            dstSetterMethod.invoke(dst, val);
                        }
                    }
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
    }

    /**
     * Copy the same field from one object to another (write only values that have getter/setter methods)
     *
     * @param src
     * @param dst
     * @param ignoreFields
     */
    public static void copyPropertiesIsNull(Object src, Object dst, String... ignoreFields) {
        Class srcClazz = src.getClass();
        Class distClazz = dst.getClass();
        Method[] methods = distClazz.getMethods();
        List<String> ignoreFiledList = Arrays.asList(ignoreFields);
        for (Method dstMethod : methods) {
            if (Modifier.isStatic(dstMethod.getModifiers())
                    || !ReflectKit.isBeanPropertyReadMethod(dstMethod)) {
                // not static method, is getter.
                continue;
            }
            String propertyName = ReflectKit.getPropertyNameFromBeanReadMethod(dstMethod);
            if (ignoreFiledList.contains(propertyName)) {
                // ignore.
                continue;
            }
            Class dstReturnType = dstMethod.getReturnType();
            try {
                Method dstSetterMethod =
                        ReflectKit.getPropertySetterMethod(distClazz, propertyName, dstReturnType);

                Object fieldValue = ReflectKit.getFieldValue(ReflectKit.getAccessibleField(dst, propertyName), dst);

                if (dstSetterMethod != null && Objects.isNull(fieldValue)) {
                    // Check the original object method again
                    Method srcGetterMethod = ReflectKit.getPropertyGetterMethod(srcClazz, propertyName);
                    // Raw fields have getter methods
                    Class srcReturnType = srcGetterMethod.getReturnType();
                    // The original and target fields return the same type
                    if (srcReturnType.equals(dstReturnType)) {
                        // Read the value from the original object
                        Object val = srcGetterMethod.invoke(src);
                        if (val != null) {
                            // Set to the target object
                            dstSetterMethod.invoke(dst, val);
                        }
                    }
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
    }

    /**
     * Check which properties of one object and another object of a class
     * have been modified (write only values that have getter/setter methods)
     *
     * @param src
     * @param dst
     * @param ignoreFields
     * @param <T>
     * @return The list of updated fields.
     */
    public static <T> List<String> getModifiedFields(T src, T dst, String... ignoreFields) {
        Class clazz = src.getClass();
        Method[] methods = clazz.getMethods();
        List<String> ignoreFiledList = Arrays.asList(ignoreFields);
        List<String> modifiedFields = new ArrayList<String>();
        // traverses the target object
        for (Method getterMethod : methods) {
            if (Modifier.isStatic(getterMethod.getModifiers())
                    || !ReflectKit.isBeanPropertyReadMethod(getterMethod)) {
                continue;
            }
            String propertyName = ReflectKit.getPropertyNameFromBeanReadMethod(getterMethod);
            if (ignoreFiledList.contains(propertyName)) {
                continue;
            }
            Class returnType = getterMethod.getReturnType();
            try {
                Method setterMethod = ReflectKit.getPropertySetterMethod(clazz, propertyName, returnType);
                if (setterMethod != null) {
                    Object srcVal = getterMethod.invoke(src);
                    Object dstVal = getterMethod.invoke(dst);
                    if (srcVal == null) {
                        if (dstVal != null) {
                            modifiedFields.add(propertyName);
                        }
                    } else {
                        if (dstVal == null) {
                            modifiedFields.add(propertyName);
                        } else {
                            if (!srcVal.equals(dstVal)) {
                                modifiedFields.add(propertyName);
                            }
                        }
                    }
                }
            } catch (Exception ignore) {
                // ignore
            }
        }
        return modifiedFields;
    }
}
