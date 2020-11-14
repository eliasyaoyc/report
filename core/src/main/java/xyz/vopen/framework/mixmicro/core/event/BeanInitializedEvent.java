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

/**
 * {@link BeanInitializedEvent} fired when a bean's properties have been populated but
 * initialization hooks (such as {@link javax.annotation.PostConstruct} methods) have not yet been
 * triggered.
 *
 * <p>To listen to an event for a fully initialized bean see {@link BeanCreatedEvent}.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
public class BeanInitializedEvent<T> extends BeanEvent<T> {

  /**
   * @param beanContext
   * @param beanDefinition
   * @param bean
   */
  public BeanInitializedEvent(BeanContext beanContext, BeanDefinition<T> beanDefinition, T bean) {
    super(beanContext, beanDefinition, bean);
  }
}
