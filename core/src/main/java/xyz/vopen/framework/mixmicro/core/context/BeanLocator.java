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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.core.annotations.Bean;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;

/**
 * {@link BeanLocator} Core interface for the locating and discovering the {@link Bean} instance.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/24
 */
public interface BeanLocator {

  /**
   * Obtains a bean for the given definition.
   *
   * @param definition The bean type.
   * @param <T> The bean type parameter.
   * @return bean.
   */
  @Nonnull
  <T> T getBean(@Nonnull BeanDefinition<T> definition);

  /**
   * Obtains a bean for the given definition.
   *
   * @param beanType The bean type.
   * @param qualifier The qualifier.
   * @param <T> The bean type parameter.
   * @return bean.
   */
  @Nonnull
  <T> T getBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Get all beans of the given type.
   *
   * @param beanType The bean type.
   * @param qualifier The qualifier.
   * @param <T> The bean type parameter
   * @return bean.
   */
  @Nonnull
  <T> Collection<T> getBeansOfType(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Finds a bean for the given type.
   *
   * @param beanType The bean type.
   * @param qualifier The qualifier.
   * @param <T> The bean type parameter.
   * @return An optional obj.
   */
  @Nonnull
  <T> Optional<T> findBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Obtains a bean for the given definition.
   *
   * @param beanType The bean type.
   * @param <T> The bean type parameter.
   * @return bean.
   */
  default @Nonnull <T> T getBean(@Nonnull Class<T> beanType) {
    return getBean(beanType, null);
  }

  /**
   * Finds a bean for the given type.
   *
   * @param beanType The bean type.
   * @param <T> The bean type parameter.
   * @return An optional obj.
   */
  default @Nonnull <T> Optional<T> findBean(@Nonnull Class<T> beanType) {
    return findBean(beanType, null);
  }
}
