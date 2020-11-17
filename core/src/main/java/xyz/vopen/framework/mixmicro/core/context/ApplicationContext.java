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

import static xyz.vopen.framework.mixmicro.core.context.env.SystemPropertiesPropertySource.POSITION;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.commons.utils.StringUtils;
import xyz.vopen.framework.mixmicro.core.LifeCycle;
import xyz.vopen.framework.mixmicro.core.context.env.Environment;
import xyz.vopen.framework.mixmicro.core.context.env.PropertySource;
import xyz.vopen.framework.mixmicro.core.context.env.SystemPropertiesPropertySource;

/**
 * An {@link ApplicationContext} a {@link BeanContext} and adds the concepts of configuration,
 * environments and runtimes.
 *
 * <p>The {@link ApplicationContext} is the main entry point for starting and running Micronaut
 * applications. It can be thought of as a container object for all dependency injected objects.
 *
 * <p>The {@link ApplicationContext} can be started via the {@link #run()} method. For example:
 *
 * <pre class="code">
 *     ApplicationContext context = ApplicationContext.run();
 * </pre>
 *
 * <p>Alternatively, the {@link #builder()} method can be used to customize the {@code
 * ApplicationContext} using the {@link ApplicationContextBuilder} interface prior to running. For
 * example:
 *
 * <pre class="code">
 *     ApplicationContext context = ApplicationContext.builder().environments("test").start();
 * </pre>
 *
 * <p>The {@link #getEnvironment()} method can be used to obtain a reference to the application
 * {@link Environment}, which contains the loaded configuration and active environment names.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
public interface ApplicationContext extends BeanContext {

  /** @return The application environment. */
  @Nonnull
  Environment getEnvironment();

  /**
   * Starts the application context.
   *
   * @return The ApplicationContext.
   */
  @Override
  @Nonnull
  ApplicationContext start();

  /**
   * Stops the application context;
   *
   * @return The ApplicationContext.
   */
  @Override
  @Nonnull
  BeanContext stop();

  @Override
  @Nonnull
  <T> BeanDefinitionRegistry registerSingleton(
      @Nonnull Class<T> type,
      @Nonnull T singleton,
      @Nullable Qualifier<T> qualifier,
      boolean inject);

  @Override
  @Nonnull
  default <T> BeanDefinitionRegistry registerSingleton(
      @Nonnull Class<T> type, @Nonnull T singleton, @Nullable Qualifier<T> qualifier) {
    return registerSingleton(type, singleton, qualifier, true);
  }

  @Override
  default @Nonnull <T> BeanDefinitionRegistry registerSingleton(
      @Nonnull Class<T> type, @Nonnull T singleton) {
    return registerSingleton(type, singleton, null, true);
  }

  @Override
  default @Nonnull ApplicationContext registerSingleton(@Nonnull Object singleton, boolean inject) {
    return (ApplicationContext) BeanContext.super.registerSingleton(singleton, inject);
  }

  @Override
  default @Nonnull ApplicationContext registerSingleton(@Nonnull Object singleton) {
    Preconditions.checkNotNull(singleton, "Singleton object is empty.");
    Class type = singleton.getClass();
    return (ApplicationContext) registerSingleton(type, singleton);
  }

  /**
   * Allow configuration the {@link Environment}.
   *
   * @param consumer The consumer.
   * @return This context.
   */
  default @Nonnull ApplicationContext environment(@Nonnull Consumer<Environment> consumer) {
    Preconditions.checkNotNull(consumer, "Consumer object is empty.");
    consumer.accept(getEnvironment());
    return this;
  }

  /**
   * Run the ApplicationContext. This method will instantiate a new {@link ApplicationContext} and
   * call {@link #start()}.
   *
   * @param environments The environments to use.
   * @return The running {@link ApplicationContext}.
   */
  static @Nonnull ApplicationContext run(@Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    return build(environments).start();
  }

  /**
   * Run the {@link ApplicationContext}. This method will instantiate a new {@link
   * ApplicationContext} and call {@link #start()}.
   *
   * @return The running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContext run() {
    return run(StringUtils.EMPTY_STRING_ARRAY);
  }

  /**
   * Runt the {@link ApplicationContext} with the given type.Returning an instance of the type. Note
   * this method should not be used.
   *
   * <p>If the {@link ApplicationContext} requires graceful shutdown unless the returned bean takes
   * responsibility for shutting down the context.
   *
   * @param properties Additional properties.
   * @param environments The environment names.
   * @return The running {@link ApplicationContext}.
   */
  static @Nonnull ApplicationContext run(
      @Nonnull Map<String, Object> properties, @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(properties, "Properties is empty.");
    PropertySource propertySource =
        PropertySource.of(PropertySource.CONTEXT, properties, POSITION + 100);
    return run(propertySource, environments);
  }

  /**
   * Run the {@link ApplicationContext} with the given type. Returning an instance of the type. Note
   * this method should not be used.
   *
   * <p>If the {@link ApplicationContext} requires graceful shutdown unless the returned bean takes
   * responsibility for shutting down the context.
   *
   * @param properties Additional properties
   * @param environments The environment names
   * @return The running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContext run(
      @Nonnull PropertySource properties, @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(properties, "Properties is empty.");
    return build(environments).propertySources(properties).start();
  }

  /**
   * Run the {@link ApplicationContext} with the given type. Returning an instance of the type. Note
   * this method should not be used.
   *
   * <p>If the {@link ApplicationContext} requires graceful shutdown unless the returned bean takes
   * responsibility for shutting down the context.
   *
   * @param type The type of the bean to run
   * @param environments The environments to use
   * @param <T> The type
   * @return The running bean
   */
  static @Nonnull <T extends AutoCloseable> T run(
      @Nonnull Class<T> type, @Nonnull String... environments) {
    Preconditions.checkNotNull(type, "Class type is empty.");
    Preconditions.checkNotNull(environments, "Environments is empty.");
    return run(type, Collections.emptyMap(), environments);
  }

  /**
   * Run the {@link ApplicationContext} with the given type. Returning an instance of the type. Note
   * this method should not be used.
   *
   * <p>If the {@link ApplicationContext} requires graceful shutdown unless the returned bean takes
   * responsibility for shutting down the context.
   *
   * @param type The type of the bean to run
   * @param properties Additional properties
   * @param environments The environment names
   * @param <T> The type
   * @return The running bean
   */
  static @Nonnull <T extends AutoCloseable> T run(
      @Nonnull Class<T> type,
      @Nonnull Map<String, Object> properties,
      @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(properties, "Properties is empty.");
    Preconditions.checkNotNull(type, "Class type is empty.");
    PropertySource propertySource =
        PropertySource.of(
            PropertySource.CONTEXT, properties, SystemPropertiesPropertySource.POSITION + 100);
    return run(type, propertySource, environments);
  }

  /**
   * Run the {@link ApplicationContext} with the given type. Returning an instance of the type. Note
   * this method should not be used.
   *
   * <p>If the {@link ApplicationContext} requires graceful shutdown unless the returned bean takes
   * responsibility for shutting down the context.
   *
   * @param type The environment to use
   * @param propertySource Additional properties
   * @param environments The environment names
   * @param <T> The type
   * @return The running {@link BeanContext}
   */
  static @Nonnull <T extends AutoCloseable> T run(
      @Nonnull Class<T> type,
      @Nonnull PropertySource propertySource,
      @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(propertySource, "Properties is empty.");
    Preconditions.checkNotNull(type, "Class type is empty.");

    T bean =
        build(environments).mainClass(type).propertySources(propertySource).start().getBean(type);
    if (bean instanceof LifeCycle) {
      LifeCycle lifeCycle = (LifeCycle) bean;
      if (!lifeCycle.isRunning()) {
        lifeCycle.start();
      }
    }
    return bean;
  }

  /**
   * Run the {@link BeanContext}. This method will instantiate a new {@link BeanContext} and call
   * {@link #start()}
   *
   * @param classLoader The classloader to use
   * @param environments The environments to use
   * @return The running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContext run(
      @Nonnull ClassLoader classLoader, @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(classLoader, "ClassLoader is empty.");
    return builder(classLoader, environments).start();
  }

  // =====================   Builder  =====================

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param environments The environments to use
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder build(@Nonnull String... environments) {
    return builder(environments);
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param properties The properties
   * @param environments The environments to use
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder build(
      @Nonnull Map<String, Object> properties, @Nonnull String... environments) {
    return builder(properties, environments);
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param environments The environments to use
   * @return The built, but not yet running {@link ApplicationContext}.
   */
  static @Nonnull ApplicationContextBuilder builder(@Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    return new DefaultApplicationContextBuilder().environments(environments);
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param properties The properties
   * @param environments The environments to use
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder builder(
      @Nonnull Map<String, Object> properties, @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(properties, "Properties is empty.");
    return new DefaultApplicationContextBuilder().properties(properties).environments(environments);
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder build() {
    return builder();
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder builder() {
    return new DefaultApplicationContextBuilder();
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param classLoader The classloader to use
   * @param environments The environment to use
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder build(
      @Nonnull ClassLoader classLoader, @Nonnull String... environments) {
    return builder(classLoader, environments);
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param mainClass The main class of the application
   * @param environments The environment to use
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder build(
      @Nonnull Class mainClass, @Nonnull String... environments) {
    return builder(mainClass, environments);
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param classLoader The classloader to use
   * @param environments The environment to use
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder builder(
      @Nonnull ClassLoader classLoader, @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(classLoader, "ClassLoader is empty.");
    return builder(environments).classLoader(classLoader);
  }

  /**
   * Build a {@link ApplicationContext}.
   *
   * @param mainClass The main class of the application
   * @param environments The environment to use
   * @return The built, but not yet running {@link ApplicationContext}
   */
  static @Nonnull ApplicationContextBuilder builder(
      @Nonnull Class mainClass, @Nonnull String... environments) {
    Preconditions.checkNotNull(environments, "Environments is empty.");
    Preconditions.checkNotNull(mainClass, "MainClass is empty.");

    return builder(environments).mainClass(mainClass);
  }
}
