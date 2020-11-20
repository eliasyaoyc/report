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

import java.nio.file.Path;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.BeanIdentifier;

/**
 * {@link BeanResolutionContext} Represent the resolution context for a current resolve of a give
 * bean.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public interface BeanResolutionContext extends AutoCloseable {
  @Override
  default void close() {}

  BeanContext getContext();

  BeanDefinition getRootDefinition();

  Path getPath();

  Object setAttribute(CharSequence key, Object value);

  Object getAttribute(CharSequence key);

  void removeAttribute(CharSequence key);

  /**
   * Adds a bean that is created as part of the resolution. This is used to store references to instances passed to {@link BeanContext#inject(Object)}
   * @param beanIdentifier The bean identifier
   * @param instance The instance
   * @param <T> THe instance type
   */
  <T> void addInFlightBean(BeanIdentifier beanIdentifier, T instance);

  /**
   * Removes a bean that is in the process of being created. This is used to store references to instances passed to {@link BeanContext#inject(Object)}
   * @param beanIdentifier The bean identifier
   */
  void removeInFlightBean(BeanIdentifier beanIdentifier);

  /**
   * Obtains an inflight bean for the given identifier. An "In Flight" bean is one that is currently being
   * created but has not finished construction and been registered as a singleton just yet. For example
   * in the case whereby a bean as a {@code PostConstruct} method that also triggers bean resolution of the same bean.
   *
   * @param beanIdentifier The bean identifier
   * @param <T> The bean type
   * @return The bean
   */
  @Nullable <T> T getInFlightBean(BeanIdentifier beanIdentifier);

  @Nullable
  Qualifier<?> getCurrentQualifier();

  void setCurrentQualifier(@Nullable Qualifier<?> qualifier);
}
