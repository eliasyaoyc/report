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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.core.LifeCycle;
import xyz.vopen.framework.mixmicro.core.event.ApplicationEventPublisher;
import xyz.vopen.framework.mixmicro.core.inject.BeanIdentifier;

/**
 * {@link BeanContext} represent the dependency injected base implementation and provides various
 * functions, as follows:
 *
 * <ul>
 *   <li>LifeCycle: Control the BeanContext life cycle include start and stop,etc.
 *   <li>BeanLocator: Obtain the bean instance through the class or type etc.
 *   <li>BeanDefinitionRegister: register the bean to context.
 *   <li>ApplicationEventPublisher: publish the event to specified listeners.
 * </ul>
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/24
 */
public interface BeanContext
    extends LifeCycle<BeanContext>, BeanLocator, BeanDefinitionRegistry, ApplicationEventPublisher {

  /**
   * Inject an existing instance.
   *
   * @param instance The instance to inject.
   * @param <T> The bean generic type
   * @return The instance to inject.
   */
  @Nonnull
  <T> T inject(@Nonnull T instance);

  /**
   * Creates a new instance of the given bean performing dependency injection and returning a new
   * instance.
   *
   * <p>Note that the instance returned is not saved as a singleton in the context.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param <T> The bean generic type
   * @return The instance
   */
  @Nonnull
  <T> T createBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Creates a new instance of the given bean performing dependency injection and returning a new
   * instance.
   *
   * <p>If the bean defines any {@code Parameter} values then the values passed in the {@code
   * argumentValues} parameter will be used
   *
   * <p>Note that the instance returned is not saved as a singleton in the context.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param argumentValues The argument values
   * @param <T> The bean generic type
   * @return The instance
   */
  @Nonnull
  <T> T createBean(
      @Nonnull Class<T> beanType,
      @Nullable Qualifier<T> qualifier,
      @Nullable Map<String, Object> argumentValues);

  /**
   * Creates a new instance of the given bean performing dependency injection and returning a new
   * instance.
   *
   * <p>If the bean defines any {@code Parameter} values then the values passed in the {@code
   * argumentValues} parameter will be used
   *
   * <p>Note that the instance returned is not saved as a singleton in the context.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param args The argument values
   * @param <T> The bean generic type
   * @return The instance
   */
  @Nonnull
  <T> T createBean(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier, @Nullable Object... args);


  /**
   * Destroys the bean for the given type causing it to be re-created. If a singleton has been loaded it will be
   * destroyed and removed from the context, otherwise null will be returned.
   *
   * @param beanType The bean type
   * @param <T>      The concrete class
   * @return The destroy instance or null if no such bean exists
   */
  @Nullable <T> T destroyBean(@Nonnull Class<T> beanType);

  /**
   * <p>Refresh the state of the given registered bean applying dependency injection and configuration wiring again.</p>
   * <p>
   * <p>Note that if the bean was produced by a {@code Factory} then this method will
   * refresh the factory too</p>
   *
   * @param identifier The {@link BeanIdentifier}
   * @param <T>        The concrete class
   * @return An {@link Optional} of the instance if it exists for the given registration
   */
  @Nonnull <T> Optional<T> refreshBean(@Nullable BeanIdentifier identifier);

  /**
   * @return The class loader used by this context
   */
  @Nonnull ClassLoader getClassLoader();

  @Override
  @Nonnull <T> BeanContext registerSingleton(@Nonnull Class<T> type, @Nonnull T singleton, @Nullable Qualifier<T> qualifier,
      boolean inject);

  /**
   * Creates a new instance of the given bean performing dependency injection and returning a new
   * instance.
   *
   * <p>Note that the instance returned is not saved as a singleton in the context.
   *
   * @param beanType The bean type
   * @param <T> The bean generic type
   * @return The instance
   */
  default @Nonnull <T> T createBean(@Nonnull Class<T> beanType) {
    return createBean(beanType, (Qualifier<T>) null);
  }

  /**
   * Creates a new instance of the given bean performing dependency injection and returning a new
   * instance.
   *
   * <p>If the bean defines any {@code Parameter} values then the values passed in the {@code
   * argumentValues} parameter will be used
   *
   * <p>Note that the instance returned is not saved as a singleton in the context.
   *
   * @param beanType The bean type
   * @param args The argument values
   * @param <T> The bean generic type
   * @return The instance
   */
  @Nonnull
  default <T> T createBean(@Nonnull Class<T> beanType, @Nullable Object... args) {
    return createBean(beanType, null, args);
  }

  /**
   * Creates a new instance of the given bean performing dependency injection and returning a new
   * instance.
   *
   * <p>
   *
   * <p>If the bean defines any {@code Parameter} values then the values passed in the {@code
   * argumentValues} parameter will be used
   *
   * <p>
   *
   * <p>Note that the instance returned is not saved as a singleton in the context.
   *
   * @param beanType The bean type
   * @param argumentValues The argument values
   * @param <T> The bean generic type
   * @return The instance
   */
  @Nonnull
  default <T> T createBean(
      @Nonnull Class<T> beanType, @Nullable Map<String, Object> argumentValues) {
    return createBean(beanType, null, argumentValues);
  }

  @Override
  default BeanContext registerSingleton(@Nonnull Object singleton) {
    Objects.requireNonNull(singleton, "Argument [singleton] must not be null");
    Class type = singleton.getClass();
    return registerSingleton(type, singleton);
  }

  @Override
  default <T> BeanContext registerSingleton(Class<T> type, T singleton, Qualifier<T> qualifier) {
    return registerSingleton(type, singleton, qualifier, true);
  }

  @Override
  default <T> BeanContext registerSingleton(Class<T> type, T singleton) {
    return registerSingleton(type, singleton, null, true);
  }

  @Nonnull
  @Override
  default BeanContext registerSingleton(@Nonnull Object singleton, boolean inject) {
    return (BeanContext) BeanDefinitionRegistry.super.registerSingleton(singleton, inject);
  }

  /**
   * Run the {@link BeanContext}. This method will instantiate a new {@link BeanContext} and call {@link #start()}.
   *
   * @return The running {@link BeanContext}
   */
  static @Nonnull BeanContext run() {
    return build().start();
  }

  /**
   * Build a {@link BeanContext}.
   *
   * @return The built, but not yet running {@link BeanContext}
   */
  static @Nonnull BeanContext build() {
    return new DefaultBeanContext();
  }

  /**
   * Run the {@link BeanContext}. This method will instantiate a new {@link BeanContext} and call {@link #start()}.
   *
   * @param classLoader The classloader to use
   * @return The running {@link BeanContext}
   */
  static @Nonnull BeanContext run(ClassLoader classLoader) {
    return build(classLoader).start();
  }

  /**
   * Build a {@link BeanContext}.
   *
   * @param classLoader The classloader to use
   * @return The built, but not yet running {@link BeanContext}
   */
  static @Nonnull BeanContext build(ClassLoader classLoader) {
    return new DefaultBeanContext(classLoader);
  }
}
