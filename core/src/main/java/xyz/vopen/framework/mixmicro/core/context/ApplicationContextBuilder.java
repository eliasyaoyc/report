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
package xyz.vopen.framework.mixmicro.core.context;

import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import xyz.vopen.framework.mixmicro.core.LifeCycle;
import xyz.vopen.framework.mixmicro.core.annotation.ConfigurationProperties;
import xyz.vopen.framework.mixmicro.core.annotation.ConfigurationReader;
import xyz.vopen.framework.mixmicro.core.context.env.PropertySource;

/**
 * {@link ApplicationContextBuilder}
 *
 * <p>An interface for building an application context.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
public interface ApplicationContextBuilder {

  /**
   * Whether to eager initialize {@link ConfigurationProperties} beans.
   *
   * @param eagerInitConfiguration True if configuration properties should be eagerly initialized
   * @return The context builder
   * @since 2.0
   */
  default @Nonnull ApplicationContextBuilder eagerInitConfiguration(
      boolean eagerInitConfiguration) {
    if (eagerInitConfiguration) {
      return eagerInitAnnotated(ConfigurationReader.class);
    }
    return this;
  }

  /**
   * Whether to eager initialize singleton beans.
   *
   * @param eagerInitSingletons True if singletons should be eagerly initialized
   * @return The context builder
   * @since 2.0
   */
  default @Nonnull ApplicationContextBuilder eagerInitSingletons(boolean eagerInitSingletons) {
    if (eagerInitSingletons) {
      return eagerInitAnnotated(Singleton.class);
    }
    return this;
  }

  /**
   * Specifies to eager init the given annotated types.
   *
   * @param annotations The annotation stereotypes
   * @return The context builder
   * @since 2.0
   */
  @Nonnull
  ApplicationContextBuilder eagerInitAnnotated(Class<? extends Annotation>... annotations);

  /**
   * Override default config locations.
   *
   * @param configLocations The config locations
   * @return This environment
   * @since 2.0
   */
  @Nonnull
  ApplicationContextBuilder overrideConfigLocations(String... configLocations);

  /**
   * Additional singletons to register prior to startup.
   *
   * @param beans The beans
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder singletons(@Nullable Object... beans);

  /**
   * Whether to deduce environments.
   *
   * @param deduceEnvironment The boolean
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder deduceEnvironment(@Nullable Boolean deduceEnvironment);

  /**
   * The environments to use.
   *
   * @param environments The environments
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder environments(@Nullable String... environments);

  /**
   * The packages to include for package scanning.
   *
   * @param packages The packages
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder packages(@Nullable String... packages);

  /**
   * Properties to override from the environment.
   *
   * @param properties The properties
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder properties(@Nullable Map<String, Object> properties);

  /**
   * Additional property sources.
   *
   * @param propertySources The property sources to include
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder propertySources(@Nullable PropertySource... propertySources);

  /**
   * Set whether environment variables should contribute to configuration.
   *
   * @param environmentPropertySource The boolean
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder environmentPropertySource(boolean environmentPropertySource);

  /**
   * Which environment variables should contribute to configuration.
   *
   * @param environmentVariables The environment variables
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder environmentVariableIncludes(@Nullable String... environmentVariables);

  /**
   * Which environment variables should not contribute to configuration.
   *
   * @param environmentVariables The environment variables
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder environmentVariableExcludes(@Nullable String... environmentVariables);

  /**
   * The main class used by this application.
   *
   * @param mainClass The main class
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder mainClass(@Nullable Class mainClass);

  /**
   * The class loader to be used.
   *
   * @param classLoader The classloader
   * @return This builder
   */
  @Nonnull
  ApplicationContextBuilder classLoader(@Nullable ClassLoader classLoader);

  /**
   * Builds the {@link ApplicationContext}, but does not start it.
   *
   * @return The built, but not running {@link ApplicationContext}
   */
  @Nonnull
  ApplicationContext build();

  /**
   * Allow customizing the configurations that will be loaded.
   *
   * @param configurations The configurations to include
   * @return This application
   */
  @Nonnull
  ApplicationContextBuilder include(@Nullable String... configurations);

  /**
   * Allow customizing the configurations that will be loaded.
   *
   * @param configurations The configurations to exclude
   * @return This application
   */
  @Nonnull
  ApplicationContextBuilder exclude(@Nullable String... configurations);

  /**
   * Set the command line arguments.
   *
   * @param args The arguments
   * @return This application
   */
  default @Nonnull ApplicationContextBuilder args(@Nullable String... args) {
    return this;
  }

  /**
   * Starts the {@link ApplicationContext}.
   *
   * @return The running {@link ApplicationContext}
   */
  default @Nonnull ApplicationContext start() {
    return build().start();
  }

  /**
   * Run the {@link ApplicationContext} with the given type. Returning an instance of the type.
   *
   * @param type The type of the bean to run
   * @param <T> The type, a subclass of {@link AutoCloseable}. The close method of the
   *     implementation should shutdown the context.
   * @return The running bean
   */
  default @Nonnull <T extends AutoCloseable> T run(@Nonnull Class<T> type) {
    Preconditions.checkNotNull(type, "Class type is empty.");
    ApplicationContext applicationContext = start();
    T bean = applicationContext.getBean(type);
    if (bean instanceof LifeCycle) {
      LifeCycle lifeCycle = (LifeCycle) bean;
      if (!lifeCycle.isRunning()) {
        lifeCycle.start();
      }
    }
    return bean;
  }
}
