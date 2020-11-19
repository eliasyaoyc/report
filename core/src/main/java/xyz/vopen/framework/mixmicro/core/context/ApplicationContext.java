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
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.core.context.converter.ConversionService;
import xyz.vopen.framework.mixmicro.core.context.env.ClassPathResourceLoader;
import xyz.vopen.framework.mixmicro.core.context.env.Environment;
import xyz.vopen.framework.mixmicro.core.context.env.RuntimeConfiguredEnvironment;
import xyz.vopen.framework.mixmicro.core.inject.BeanConfiguration;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinitionReference;

/**
 * An {@link ApplicationContext} extends a {@link BeanContext} and adds the concepts of
 * configuration, environments and runtimes.
 *
 * <p>
 *
 * <p>The {@link ApplicationContext} is the main entry point for starting and running Micronaut
 * applications. It can be thought of as a container object for all dependency injected objects.
 *
 * <p>
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
 * @version ${project.version} - 2020/11/17
 */
public class ApplicationContext extends BeanContext {
  private final ConversionService conversionService;
  private final ClassPathResourceLoader resourceLoader;
  private Environment environment;

  private Iterable<BeanConfiguration> resolvedConfigurations;
  private List<BeanDefinitionReference> resolvedBeanReferences;

  /**
   * Construct a new ApplicationContext for the given environment name.
   *
   * @param environmentNames of environment.
   */
  public ApplicationContext(@Nonnull String... environmentNames) {
    this(
        new ApplicationContextConfiguration() {
          @Override
          public @Nonnull List<String> getEnvironments() {
            Preconditions.checkNotNull(environmentNames, "EnvironmentNames is empty.");
            return Arrays.asList(environmentNames);
          }
        });
  }

  /**
   * Construct a new ApplicationContext for the given environment name and classloader.
   *
   * @param environmentNames of environment.
   * @param resourceLoader class loader
   */
  public ApplicationContext(
      @Nonnull ClassPathResourceLoader resourceLoader, @Nonnull String... environmentNames) {
    this(
        new ApplicationContextConfiguration() {
          @Override
          public @Nonnull ClassPathResourceLoader getResourceLoader() {
            Preconditions.checkNotNull(resourceLoader, "ClassPathResourceLoader is empty.");
            return resourceLoader;
          }

          @Override
          public @Nonnull ClassLoader getClassLoader() {
            return getResourceLoader().getClassLoader();
          }

          @Override
          public List<String> getEnvironments() {
            Preconditions.checkNotNull(environmentNames, "EnvironmentNames is empty.");
            return Arrays.asList(environmentNames);
          }
        });
  }

  /**
   * Construct a new ApplicationContext for the given applicationContextConfiguration.
   *
   * @param configuration The application context configuration.
   */
  public ApplicationContext(@Nonnull ApplicationContextConfiguration configuration) {
    super(configuration);
    this.conversionService = createConversionService();
    this.resourceLoader = configuration.getResourceLoader();
    this.environment = createEnvironment(configuration);
  }

  public ApplicationContext run() {
    return null;
  }

  /**
   * Registers a new singleton bean at runtime. This method expects that the bean definition data
   * will have been compiled ahead of time.
   *
   * <p>
   *
   * <p>If bean definition data is found the method will perform dependency injection on the
   * instance followed by invoking any {@link javax.annotation.PostConstruct} hooks.
   *
   * <p>
   *
   * <p>If no bean definition data is found the bean is registered as is.
   *
   * @param type The bean type
   * @param singleton The singleton bean
   * @param qualifier The bean qualifier
   * @param inject Whether the singleton should be injected (defaults to true)
   * @param <T> The concrete type
   * @return This bean context
   */
  public @Nonnull <T> ApplicationContext registerSingleton(
      @Nonnull Class<T> type,
      @Nonnull T singleton,
      @Nullable Qualifier<T> qualifier,
      boolean inject) {
    return (ApplicationContext) super.registerSingleton(type, singleton, qualifier, inject);
  }

  public @Nonnull <T> ApplicationContext registerSingleton(
      @Nonnull Class<T> type, @Nonnull T singleton, @Nullable Qualifier<T> qualifier) {
    return registerSingleton(type, singleton, qualifier, true);
  }

  public @Nonnull <T> ApplicationContext registerSingleton(
      @Nonnull Class<T> type, @Nonnull T singleton) {
    return registerSingleton(type, singleton, null, true);
  }

  public @Nonnull ApplicationContext registerSingleton(@Nonnull Object singleton, boolean inject) {
    return (ApplicationContext) super.registerSingleton(singleton, inject);
  }

  protected @Nonnull Iterable<BeanConfiguration> resolveBeanConfigurations() {
    if (resolvedConfigurations != null) {
      return resolvedConfigurations;
    }
    return super.resolveBeanConfigurations();
  }

  protected @Nonnull List<BeanDefinitionReference> resolveBeanDefinitionReferences() {
    if (resolvedBeanReferences != null) {
      return resolvedBeanReferences;
    }
    return super.resolveBeanDefinitionReferences();
  }

  /**
   * Creates the default environment for the given environment name.
   *
   * @param configuration The application context configuration
   * @return The environment instance
   */
  protected @Nonnull Environment createEnvironment(
      @Nonnull ApplicationContextConfiguration configuration) {
    return new RuntimeConfiguredEnvironment(configuration);
  }

  /**
   * Creates the default conversion service.
   *
   * @return The conversion service
   */
  protected @Nonnull ConversionService createConversionService() {
    return ConversionService.SHARED;
  }

  public @Nonnull ConversionService<?> getConversionService() {
    return conversionService;
  }

  public @Nonnull Environment getEnvironment() {
    return environment;
  }

  public synchronized @Nonnull ApplicationContext start() {
    startEnvironment();
    return (ApplicationContext) super.start();
  }

  public synchronized @Nonnull ApplicationContext stop() {
    return (ApplicationContext) super.stop();
  }

  //  public boolean containsProperty(String name) {
  //    return getEnvironment().containsProperty(name);
  //  }

  //  public boolean containsProperties(String name) {
  //    return getEnvironment().containsProperties(name);
  //  }

  //  public <T> Optional<T> getProperty(String name, ArgumentConversionContext<T>
  // conversionContext) {
  //    return getEnvironment().getProperty(name, conversionContext);
  //  }

  //  public @Nonnull Collection<String> getPropertyEntries(@Nonnull String name) {
  //    return environment.getPropertyEntries(name);
  //  }

  //  public @Nonnull Map<String, Object> getProperties(
  //      @Nullable String name, @Nonnull StringConvention keyFormat) {
  //    return getEnvironment().getProperties(name, keyFormat);
  //  }

  protected void registerConfiguration(BeanConfiguration configuration) {
    if (getEnvironment().isActive(configuration)) {
      super.registerConfiguration(configuration);
    }
  }

  /** Start the Environment */
  protected void startEnvironment() {
    Environment environment = getEnvironment();
    environment.start();
    registerSingleton(Environment.class, environment);
  }

  public static ApplicationContextBuilder builder() {
    return new ApplicationContextBuilder();
  }
  // =====================   Builder  =====================
  public static class ApplicationContextBuilder {}
}
