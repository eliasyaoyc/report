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
package xyz.vopen.framework.mixmicro.core.context.env;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.commons.converter.ConversionService;
import xyz.vopen.framework.mixmicro.commons.reflect.ClassUtils;
import xyz.vopen.framework.mixmicro.commons.utils.CollectionUtils;
import xyz.vopen.framework.mixmicro.commons.utils.StringUtils;
import xyz.vopen.framework.mixmicro.core.LifeCycle;
import xyz.vopen.framework.mixmicro.core.context.ApplicationContext;
import xyz.vopen.framework.mixmicro.core.inject.BeanConfiguration;

/**
 * The current application {@link Environment}. The environment represents the loaded configuration
 * of the application for a current list of active environment name.
 *
 * <p>The active environment names can be obtained from the {@link #getActiveNames()} method and are
 * established from one of the following sources:
 *
 * <ul>
 *   <li>Environment names passed to the {@link ApplicationContext#run(String...)} method
 *   <li>The value of the {@link #ENVIRONMENTS_PROPERTY} system properties
 *   <li>The value of the {@link #ENVIRONMENTS_ENV} environment variable
 *   <li>The class that started the application
 * </ul>
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/16
 */
public interface Environment
    extends PropertyResolver,
        LifeCycle<Environment>,
        ConversionService<Environment>,
        ResourceLoader {

  String MIXMICRO = "mixmicro";

  /** The test environment. */
  String TEST = "test";

  /** The development environment. */
  String DEVELOPMENT = "dev";

  /** The android environment. */
  String ANDROID = "android";

  /** The cli environment. */
  String CLI = "cli";

  /** The cloud environment. */
  String CLOUD = "cloud";

  /** The application is executing as a function. */
  String FUNCTION = "function";

  /** The default bootstrap name. */
  String BOOTSTRAP_NAME_PROPERTY = "micronaut.bootstrap.name";
  /** Whether the bootstrap context is enabled. */
  String BOOTSTRAP_CONTEXT_PROPERTY = "micronaut.bootstrap.context";
  /** The default bootstrap name. */
  String CLOUD_PLATFORM_PROPERTY = "micronaut.cloud.platform";

  /** The property that stores additional environments. */
  String ENVIRONMENTS_PROPERTY = "micronaut.environments";

  /** The environment key that stores additional environments. */
  String ENVIRONMENTS_ENV = "MICRONAUT_ENVIRONMENTS";

  /** The default bootstrap config name. */
  String BOOTSTRAP_NAME = "bootstrap";

  /** The default application name. */
  String DEFAULT_NAME = "application";

  /** Cloud provider google compute instance. */
  String GOOGLE_COMPUTE = "gcp";

  /** Cloud provider for google app engine. */
  String GAE = "gae";

  /** Cloud provider amazon ec2. */
  String AMAZON_EC2 = "ec2";

  /** Cloud provider Microsoft Azure. */
  String AZURE = "azure";

  /** Cloud provider Oracle Cloud. */
  String ORACLE_CLOUD = "oraclecloud";

  /** Cloud provider Digital Ocean. */
  String DIGITAL_OCEAN = "digitalocean";
  /** Cloud or non cloud provider on bare metal (unknown). */
  String BARE_METAL = "baremetal";

  /** Cloud provider IBM cloud. */
  String IBM = "ibm";

  /** Running on Kubernetes. */
  String KUBERNETES = "k8s";

  /** Running on Cloud Foundry. */
  String CLOUD_FOUNDRY = "pcf";

  /** Running on Heroku. */
  String HEROKU = "heroku";

  /** The key used to load additional property sources. */
  String PROPERTY_SOURCES_KEY = "mixmicro.config.files";

  /** The host name environment variable. */
  String HOSTNAME = "HOSTNAME";

  /** Property for whether to deduce environments. */
  String DEDUCE_ENVIRONMENT_PROPERTY = "mixmicro.env.deduction";

  /** Environment key for whether to deduce environments. */
  String DEDUCE_ENVIRONMENT_ENV = "MIXMICRO_ENV_DEDUCTION";

  /**
   * Should respect the order as provided.
   *
   * @return The active environment names
   */
  Set<String> getActiveNames();

  /** @return The active property sources */
  Collection<PropertySource> getPropertySources();

  /**
   * Adds a property source to this environment.
   *
   * @param propertySource The property source
   * @return This environment
   */
  Environment addPropertySource(PropertySource propertySource);

  /**
   * Removes a property source from this environment.
   *
   * @param propertySource The property source
   * @return This environment
   */
  Environment removePropertySource(PropertySource propertySource);

  /**
   * Add an application package. Application packages are candidates for scanning for tools that
   * need it (such as JPA or GORM).
   *
   * @param pkg The package to add
   * @return This environment
   */
  Environment addPackage(String pkg);

  /**
   * Exclude configurations by name.
   *
   * @param names The names of the configuration
   * @return This environment
   */
  Environment addConfigurationExcludes(String... names);

  /**
   * Exclude configurations by name.
   *
   * @param names The names of the configuration
   * @return This environment
   */
  Environment addConfigurationIncludes(String... names);

  /** @return The application packages */
  Collection<String> getPackages();

  /** @return The placeholder resolver */
  PropertyPlaceholderResolver getPlaceholderResolver();

  /**
   * Refresh the environment from the list of {@link PropertySource} instances and return a diff of
   * the changes.
   *
   * @return The values that changed
   */
  Map<String, Object> refreshAndDiff();

  /**
   * Add a property source for the given map.
   *
   * @param name The name
   * @param values The values
   * @return This environment
   */
  default Environment addPropertySource(String name, @Nullable Map<String, ? super Object> values) {
    if (StringUtils.isNotEmpty(name) && CollectionUtils.isNotEmpty(values)) {
      return addPropertySource(PropertySource.of(name, values));
    }
    return this;
  }

  /**
   * Add an application package. Application packages are candidates for scanning for tools that
   * need it (such as JPA or GORM).
   *
   * @param pkg The package to add
   * @return This environment
   */
  default Environment addPackage(Package pkg) {
    addPackage(pkg.getName());
    return this;
  }

  /**
   * Scan the current environment for classes annotated with the given annotation. Use with care,
   * repeated invocations should be avoided for performance reasons.
   *
   * @param annotation The annotation to scan
   * @return The classes
   */
  default Stream<Class> scan(Class<? extends Annotation> annotation) {
    ClassPathAnnotationScanner scanner = new ClassPathAnnotationScanner(getClassLoader());
    return scanner.scan(annotation, getPackages());
  }

  /**
   * Scan the current environment for classes annotated with the given annotation. Use with care,
   * repeated invocations should be avoided for performance reasons.
   *
   * @param annotation The annotation to scan
   * @param packages The packages to scan
   * @return The classes
   */
  default Stream<Class> scan(Class<? extends Annotation> annotation, String... packages) {
    ClassPathAnnotationScanner scanner = new ClassPathAnnotationScanner(getClassLoader());
    return scanner.scan(annotation, Arrays.asList(packages));
  }

  /** @return The class loader for the environment */
  default ClassLoader getClassLoader() {
    return Environment.class.getClassLoader();
  }

  /**
   * Check whether the given class is present within this environment.
   *
   * @param className The class name
   * @return True if it is
   */
  default boolean isPresent(String className) {
    return ClassUtils.isPresent(className, getClassLoader());
  }

  /**
   * Whether the current environment includes the given configuration.
   *
   * @param configuration The configuration
   * @return True if it does
   */
  boolean isActive(BeanConfiguration configuration);

  /**
   * Obtains the {@link PropertySourceLoader} instances.
   *
   * @return A collection of {@link PropertySourceLoader}
   */
  Collection<PropertySourceLoader> getPropertySourceLoaders();
}
