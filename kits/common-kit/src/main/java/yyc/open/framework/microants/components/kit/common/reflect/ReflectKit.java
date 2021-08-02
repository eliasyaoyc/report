package yyc.open.framework.microants.components.kit.common.reflect;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.*;

/**
 * {@link ReflectKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/10
 */
public class ReflectKit {

    public static Method getAccessibleMethod(
            final Object obj, final String methodName, final Class<?>... parameterTypes) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(methodName, "methodName can't be blank");

        for (Class<?> searchType = obj.getClass();
             searchType != Object.class;
             searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
            }
        }
        return null;
    }

    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Validate.notNull(obj, "object can't be null");
        Validate.notBlank(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = obj.getClass();
             superClass != Object.class;
             superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) { // NOSONAR

            }
        }
        return null;
    }

    public static Object getFieldValue(Field field, Object target) {
        try {
            makeAccessible(field);
            return field.get(target);
        } catch (IllegalAccessException var3) {
            handleReflectionException(var3);
            throw new IllegalStateException("Unexpected reflection exception - " + var3.getClass().getName() + ": " + var3.getMessage());
        }
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers())
                || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static Method getPropertySetterMethod(Class clazz, String property, Class propertyClazz) {
        String methodName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
        try {
            return clazz.getMethod(methodName, propertyClazz);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No setter method for " + clazz.getName() + "#" + property, e);
        }
    }

    public static Method getPropertyGetterMethod(Class clazz, String property) {
        String methodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        Method method;
        try {
            method = clazz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            try {
                methodName = "is" + property.substring(0, 1).toUpperCase() + property.substring(1);
                method = clazz.getMethod(methodName);
            } catch (NoSuchMethodException e1) {
                throw new RuntimeException("No getter method for " + clazz.getName() + "#" + property, e);
            }
        }
        return method;
    }

    public static boolean isBeanPropertyReadMethod(Method method) {
        return method != null
                && Modifier.isPublic(method.getModifiers())
                && !Modifier.isStatic(method.getModifiers())
                && method.getReturnType() != void.class
                && method.getDeclaringClass() != Object.class
                && method.getParameterTypes().length == 0
                && (method.getName().startsWith("get") || method.getName().startsWith("is"))
                && (!"get".equals(method.getName()) && !"is".equals(method.getName()));
    }

    public static String getPropertyNameFromBeanReadMethod(Method method) {
        if (isBeanPropertyReadMethod(method)) {
            if (method.getName().startsWith("get")) {
                return method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
            }
            if (method.getName().startsWith("is")) {
                return method.getName().substring(2, 3).toLowerCase() + method.getName().substring(3);
            }
        }
        return null;
    }

    static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        } else if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        } else {
            if (ex instanceof InvocationTargetException) {
                handleInvocationTargetException((InvocationTargetException) ex);
            }

            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }

    static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else if (ex instanceof Error) {
            throw (Error) ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }
}
