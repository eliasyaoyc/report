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
package xyz.vopen.framework.mixmicro.core.context.condition;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import xyz.vopen.framework.mixmicro.core.context.AnnotationMetadataProvider;
import xyz.vopen.framework.mixmicro.core.context.BeanContext;
import xyz.vopen.framework.mixmicro.core.context.BeanLocator;
import xyz.vopen.framework.mixmicro.core.context.env.PropertySource;
import xyz.vopen.framework.mixmicro.core.inject.BeanConfiguration;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;

/**
 * {@link ConditionContext} pass to a {@link Condition}.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/12/7
 */
public interface ConditionContext<T extends AnnotationMetadataProvider>
    extends BeanLocator, PropertySource {

  /**
   * The component for which the condition is being evaluated.
   *
   * @return Either a {@link BeanDefinition} or a {@link BeanConfiguration}
   */
  T getComponent();

  /** @return The bean contxt. */
  BeanContext getBeanContext();

  /**
   * Fail the condition with the given message.
   *
   * @param failure The failure.
   * @return
   */
  ConditionContext fail(ConditionFailure failure);

  default ConditionContext<T> fail(@Nonnull String failure) {
    return fail(ConditionFailure.simple(failure));
  }

  default List<ConditionFailure> getFailures() {
    return Collections.emptyList();
  }

  /** @return Whether there are any failures */
  default boolean isFailing() {
    return !getFailures().isEmpty();
  }
}
