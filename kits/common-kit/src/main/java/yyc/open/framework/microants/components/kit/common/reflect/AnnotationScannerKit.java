package yyc.open.framework.microants.components.kit.common.reflect;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * {@link AnnotationScannerKit}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/7/30
 */
public class AnnotationScannerKit {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationScannerKit.class);
    private static final String EXT = "class";

    /**
     * Returns package url through package name.
     *
     * @param packageName
     * @return
     */
    public static String getPackagePath(String packageName) {
        String pkgDirName = packageName.replace('.', File.separatorChar);
        URL url = Thread.currentThread().getContextClassLoader().getResource(pkgDirName);

        return url == null ? null : url.getFile();
    }

    /**
     * Returns all object collections under the specified package.
     *
     * @param packageName name of package.
     * @param packagePath path of package.
     * @param recursive   whether recursive.
     * @return
     */
    public static Set<Class<?>> scanClasses(String packageName, String packagePath, final boolean recursive) {
        Set<Class<?>> sets = Sets.newHashSet();
        Collection<File> allClassFile = getAllClassFile(packagePath, recursive);

        for (File curFile : allClassFile) {
            try {
                sets.add(getClassObj(curFile, packagePath, packageName));
            } catch (ClassNotFoundException e) {
                logger.error("[Annotation Scanner] scanClasses load class fail", e);
            }
        }
        return sets;
    }

    /**
     * Returns the collection of all class objects under the specified package that contain the specified annotation.
     *
     * @param packageName       name of package.
     * @param recursive         whether recursive.
     * @param targetAnnotations target annotation collection.
     * @return
     */
    public static Map<Class<? extends Annotation>, Set<Class<?>>> scanClassesByAnnotations(String packageName, final boolean recursive, List<Class<? extends Annotation>> targetAnnotations) {
        return scanClassesByAnnotations(packageName, getPackagePath(packageName), recursive, targetAnnotations);
    }

    /**
     * Returns the collection of all class objects under the specified package that contain the specified annotation.
     *
     * @param packageName      name of package.
     * @param targetAnnotation target annotation.
     * @return
     */
    public static Map<Class<? extends Annotation>, Set<Class<?>>> scanClassesByAnnotations(String packageName, Class<? extends Annotation> targetAnnotation) {
        return scanClassesByAnnotations(packageName, getPackagePath(packageName), true, Arrays.asList(targetAnnotation));
    }

    /**
     * Returns the collection of all class objects under the specified package that contain the specified annotation.
     *
     * @param packageName       name of package.
     * @param packagePath       path of package.
     * @param recursive         whether recursive.
     * @param targetAnnotations target annotation collection.
     * @return
     */
    public static Map<Class<? extends Annotation>, Set<Class<?>>> scanClassesByAnnotations(String packageName, String packagePath, final boolean recursive, List<Class<? extends Annotation>> targetAnnotations) {
        Map<Class<? extends Annotation>, Set<Class<?>>> resultMap = Maps.newHashMap();

        Collection<File> allClassFile = getAllClassFile(packagePath, recursive);

        for (File file : allClassFile) {
            try {
                Class<?> curClass = getClassObj(file, packagePath, packageName);
                for (Class<? extends Annotation> annotation : targetAnnotations) {
                    if (curClass.isAnnotationPresent(annotation)) {
                        if (!resultMap.containsKey(annotation)) {
                            resultMap.put(annotation, Sets.newHashSet());
                        }
                        resultMap.get(annotation).add(curClass);
                    }
                }
            } catch (ClassNotFoundException e) {
                logger.error("[Annotation Scanner] scanClassesByAnnotations load class fail", e);
            }
        }
        return resultMap;
    }

    /**
     * Returns the methods for the specified annotation.
     *
     * @param classes
     * @param targetAnnotations
     * @return
     */
    public static Map<Class<? extends Annotation>, Set<Method>> scanMethodsByAnnotations(Set<Class<?>> classes,
                                                                                         List<Class<? extends Annotation>> targetAnnotations) {
        Map<Class<? extends Annotation>, Set<Method>> resultMap = Maps.newHashMap();

        classes.stream().forEach(clazz -> {
            Method[] methods = clazz.getMethods();

            targetAnnotations.stream().forEach(annotation -> {

                Arrays.stream(methods).forEach(method -> {
                    if (method.isAnnotationPresent(annotation)) {
                        if (!resultMap.containsKey(annotation)) {
                            resultMap.put(annotation, Sets.newHashSet());
                        }
                        resultMap.get(annotation).add(method);
                    }
                });
            });
        });
        return resultMap;
    }

    /**
     * Load class.
     *
     * @param file
     * @param packageName name of package.
     * @param packagePath path of package.
     * @return
     * @throws ClassNotFoundException
     */
    private static Class<?> getClassObj(File file, String packagePath, String packageName) throws ClassNotFoundException {
        String absPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - EXT.length() - 1);
        String className = absPath.substring(packagePath.length()).replace(File.separatorChar, '.');
        className = className.startsWith(".") ? packageName + className : packageName + "." + className;

        return Thread.currentThread().getContextClassLoader().loadClass(className);
    }

    /**
     * Recursive all files with the class extension in the specified directory.
     *
     * @param packagePath
     * @param recursive
     * @return
     */
    private static Collection<File> getAllClassFile(String packagePath, boolean recursive) {
        File packageDir = new File(packagePath);

        if (!(packageDir.exists() && packageDir.isDirectory())) {
            logger.error("[Annotation Scanner] getAllClassFile the directory to package is empty:{}", packagePath);
            return Collections.emptyList();
        }
        return FileUtils.listFiles(packageDir, new String[]{EXT}, recursive);
    }
}
