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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.commons.reflect.InstantiationUtils;
import xyz.vopen.framework.mixmicro.core.annotation.Bean;
import xyz.vopen.framework.mixmicro.core.context.exceptions.NoSuchBeanException;
import xyz.vopen.framework.mixmicro.core.context.exceptions.NoUniqueBeanException;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;

/**
 * {@link BeanLocator} Core interface for locating and discovering {@link Bean} instance.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public interface BeanLocator {
  /**
   * Obtains a Bean for the given bean definition.
   *
   * @param definition The bean type
   * @param <T> The bean type parameter
   * @return An instanceof said bean
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   * @see Qualifier
   */
  @Nonnull
  <T> T getBean(@Nonnull BeanDefinition<T> definition);

  /**
   * Obtains a Bean for the given type and qualifier.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param <T> The bean type parameter
   * @return An instanceof said bean
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   * @see Qualifier
   */
  @Nonnull
  <T> T getBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Finds a Bean for the given type and qualifier.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param <T> The bean type parameter
   * @return An instance of {@link Optional} that is either empty or containing the specified bean
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   * @see Qualifier
   */
  @Nonnull
  <T> Optional<T> findBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Get all beans of the given type.
   *
   * @param beanType The bean type
   * @param <T> The bean type parameter
   * @return The found beans
   */
  @Nonnull
  <T> Collection<T> getBeansOfType(@Nonnull Class<T> beanType);

  /**
   * Get all beans of the given type.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param <T> The bean type parameter
   * @return The found beans
   */
  @Nonnull
  <T> Collection<T> getBeansOfType(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Obtain a stream of beans of the given type.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param <T> The bean concrete type
   * @return A stream of instances
   * @see Qualifier
   */
  @Nonnull
  <T> Stream<T> streamOfType(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Resolves the proxy target for a given bean type. If the bean has no proxy then the original
   * bean is returned.
   *
   * @param beanType The bean type
   * @param qualifier The bean qualifier
   * @param <T> The generic type
   * @return The proxied instance
   */
  @Nonnull
  <T> T getProxyTargetBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Obtain a stream of beans of the given type.
   *
   * @param beanType The bean type
   * @param <T> The bean concrete type
   * @return A stream
   */
  default @Nonnull <T> Stream<T> streamOfType(@Nonnull Class<T> beanType) {
    return streamOfType(beanType, null);
  }

  /**
   * Obtains a Bean for the given type.
   *
   * @param beanType The bean type
   * @param <T> The bean type parameter
   * @return An instanceof said bean
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   * @throws NoSuchBeanException If the bean doesn't exist
   */
  default @Nonnull <T> T getBean(@Nonnull Class<T> beanType) {
    return getBean(beanType, null);
  }

  /**
   * Finds a Bean for the given type.
   *
   * @param beanType The bean type
   * @param <T> The bean type parameter
   * @return An instance of {@link Optional} that is either empty or containing the specified bean
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  default @Nonnull <T> Optional<T> findBean(@Nonnull Class<T> beanType) {
    return findBean(beanType, null);
  }

  /**
   * Finds a Bean for the given type or attempts to instantiate the given instance.
   *
   * @param beanType The bean type
   * @param <T> The bean type parameter
   * @return An instance of {@link Optional} that is either empty or containing the specified bean
   *     if it could not be found or instantiated
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  default @Nonnull <T> Optional<T> findOrInstantiateBean(@Nonnull Class<T> beanType) {
    Optional<T> bean = findBean(beanType, null);
    if (bean.isPresent()) {
      return bean;
    } else {
      return InstantiationUtils.tryInstantiate(beanType);
    }
  }
}
