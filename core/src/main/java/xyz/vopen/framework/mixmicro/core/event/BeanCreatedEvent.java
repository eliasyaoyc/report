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
package xyz.vopen.framework.mixmicro.core.event;

import xyz.vopen.framework.mixmicro.core.context.BeanContext;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.BeanIdentifier;

/**
 * {@link BeanCreatedEvent} fired when a bean is created and fully initialized.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
public class BeanCreatedEvent<T> extends BeanEvent<T> {
  private final BeanIdentifier beanIdentifier;
  /**
   * @param beanContext The bean context.
   * @param beanDefinition The bean definition.
   * @param bean The bean.
   */
  public BeanCreatedEvent(
      BeanContext beanContext,
      BeanDefinition<T> beanDefinition,
      BeanIdentifier beanIdentifier,
      T bean) {
    super(beanContext, beanDefinition, bean);
    this.beanIdentifier = beanIdentifier;
  }

  /**
   * Return the bean identifier used to create the bean.
   *
   * @return The bean identifier used to create the bean.
   */
  public BeanIdentifier getBeanIdentifier() {
    return beanIdentifier;
  }
}
