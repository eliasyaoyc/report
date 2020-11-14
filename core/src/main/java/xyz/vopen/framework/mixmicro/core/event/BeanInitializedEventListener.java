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

import java.util.EventListener;
import javax.inject.Provider;
import xyz.vopen.framework.mixmicro.core.annotation.Indexed;

/**
 * {@link BeanInitializedEventListener}
 *
 * <p>Allows hooking into bean instantiation at the point prior to when {@link
 * javax.annotation.PostConstruct} initialization hooks have been called and in the case of bean
 * {@link javax.inject.Provider} instances the {@link javax.inject.Provider#get()} method has not
 * yet been invoked.
 *
 * <p>
 *
 * <p>This allows (for example) customization of bean properties prior to any initialization logic
 * or factory logic.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
@Indexed(BeanInitializedEventListener.class)
public interface BeanInitializedEventListener<T> extends EventListener {

  /**
   * Fired when a bean is instantiated but the {@link javax.annotation.PostConstruct} initialization
   * hooks have not yet been called and in this case of bean {@link javax.inject.Provider} instances
   * the {@link Provider#get()} method has not yet been invoked.
   *
   * @param event The bean initializing event.
   * @return The bean or a replacement been of the same type.
   */
  T onInitialized(BeanInitializedEvent<T> event);
}
