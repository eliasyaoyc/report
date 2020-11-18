/**
 * MIT License
 *
 * <p>Copyright (c) 2020 mixmicro
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package xyz.vopen.framework.mixmicro.commons.serviceLoader;

import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import xyz.vopen.framework.mixmicro.commons.reflect.ClassUtils;

/**
 * {@link SoftServiceLoader} Variation of {@link java.util.ServiceLoader} that allows soft loading
 * and conditional loading of META-INF/services classes.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/18
 */
public final class SoftServiceLoader<S> implements Iterable<ServiceDefinition<S>> {
  private static final String META_INF_SERVICES = "META-INF/services";

  private final Class<S> serviceType;
  private final ClassLoader classLoader;
  private final Map<String, ServiceDefinition<S>> loadServices = Maps.newLinkedHashMap();
  private final Iterator<ServiceDefinition<S>> unloadedServices;
  private final Predicate<String> condition;

  private SoftServiceLoader(Class<S> serviceType, ClassLoader classLoader) {
    this(serviceType, classLoader, (String name) -> true);
  }

  private SoftServiceLoader(
      Class<S> serviceType, ClassLoader classLoader, Predicate<String> condition) {
    this.serviceType = serviceType;
    this.classLoader = classLoader;
    this.unloadedServices = new ServiceLoaderIterator();
    this.condition = condition;
  }

  /**
   * Creates a new {@link SoftServiceLoader} using the thread context loader bt default.
   *
   * @param service The service type.
   * @param <S> The service generic type
   * @return A new service loader
   */
  public static <S> SoftServiceLoader<S> load(Class<S> service) {
    return load(service, SoftServiceLoader.class.getClassLoader());
  }

  /**
   * Creates a new {@link SoftServiceLoader} using the given type and class loader.
   *
   * @param service The service type
   * @param loader The class loader
   * @param <S> The service generic type
   * @return A new service loader
   */
  public static <S> SoftServiceLoader<S> load(Class<S> service, ClassLoader loader) {
    return new SoftServiceLoader<>(service, loader);
  }

  /**
   * Creates a new {@link SoftServiceLoader} using the given type and class loader.
   *
   * @param service The service type
   * @param loader The class loader to use
   * @param condition A {@link Predicate} to use to conditionally load the service. The predicate is
   *     passed the service class name
   * @param <S> The service generic type
   * @return A new service loader
   */
  public static <S> SoftServiceLoader<S> load(
      Class<S> service, ClassLoader loader, Predicate<String> condition) {
    return new SoftServiceLoader<>(service, loader, condition);
  }

  /** @return Return the first such instance. */
  public Optional<ServiceDefinition<S>> first() {
    Iterator<ServiceDefinition<S>> iterator = iterator();
    if (iterator.hasNext()) {
      return Optional.of(iterator.next());
    }
    return Optional.empty();
  }

  /**
   * @param alternative An alternative type to use if the this type is not present.
   * @param classLoader The classloader
   * @return Return the first such instance.
   */
  public Optional<ServiceDefinition<S>> firstOr(String alternative, ClassLoader classLoader) {
    Iterator<ServiceDefinition<S>> iterator = iterator();
    if (iterator.hasNext()) {
      return Optional.of(iterator.next());
    }

    Optional<Class> alternativeClass = ClassUtils.forName(alternative, classLoader);
    if (alternativeClass.isPresent()) {
      return Optional.of(newService(alternative, alternativeClass));
    }
    return Optional.empty();
  }

  protected ServiceDefinition<S> newService(String alterNative, Optional<Class> loadClass) {
    return new ServiceDefinition(alterNative, loadClass);
  }

  @Override
  public Iterator<ServiceDefinition<S>> iterator() {
    return new Iterator<ServiceDefinition<S>>() {
      Iterator<ServiceDefinition<S>> loaded = loadServices.values().iterator();

      @Override
      public boolean hasNext() {
        if (loaded.hasNext()) {
          return true;
        }
        return unloadedServices.hasNext();
      }

      @Override
      public ServiceDefinition<S> next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }

        if (loaded.hasNext()) {
          return loaded.next();
        }

        if (unloadedServices.hasNext()) {
          ServiceDefinition<S> nextService = unloadedServices.next();
          loadServices.put(nextService.getName(), nextService);
          return nextService;
        }
        throw new Error("Bug in iterator");
      }
    };
  }

  /** A service loader iterator implementation. */
  private final class ServiceLoaderIterator implements Iterator<ServiceDefinition<S>> {
    private Enumeration<URL> serviceConfigs = null;
    private Iterator<String> unprocessed = null;

    @Override
    public boolean hasNext() {
      if (serviceConfigs == null) {
        String name = serviceType.getName();
        try {
          serviceConfigs = classLoader.getResources(META_INF_SERVICES + '/' + name);
        } catch (IOException e) {
          throw new ServiceConfigurationError("Failed to load resources for service: " + name, e);
        }
      }

      while (unprocessed == null || !unprocessed.hasNext()) {
        if (!serviceConfigs.hasMoreElements()) {
          return false;
        }

        URL url = serviceConfigs.nextElement();
        try {
          try (BufferedReader reader =
              new BufferedReader(new InputStreamReader(url.openStream()))) {
            List<String> lines =
                reader
                    .lines()
                    .filter(line -> line.length() != 0 && line.charAt(0) != '#')
                    .filter(condition)
                    .map(
                        line -> {
                          int i = line.indexOf('#');
                          if (i > -1) {
                            line = line.substring(0, i);
                          }
                          return line;
                        })
                    .collect(Collectors.toList());
            unprocessed = lines.iterator();
          }
        } catch (IOException e) {
          // ignore
        }
      }
      return unprocessed.hasNext();
    }

    @Override
    public ServiceDefinition<S> next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      String nextName = unprocessed.next();
      try {
        final Class<?> loadedClass = Class.forName(nextName, false, classLoader);
        return newService(nextName, Optional.of(loadedClass));
      } catch (NoClassDefFoundError | ClassNotFoundException e) {
        return newService(nextName, Optional.empty());
      }
    }
  }
}
