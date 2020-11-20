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

import com.google.common.collect.Maps;
import java.nio.file.Path;
import java.util.Map;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;

/**
 * {@link AbstractBeanResolutionContext} Default implementation of the {@link BeanResolutionContext}
 * interface.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/20
 */
public abstract class AbstractBeanResolutionContext implements BeanResolutionContext {
  private final BeanContext context;
  private final BeanDefinition rootDefinition;
  private final Path path;
  private final Map<CharSequence, Object> attributes = Maps.newLinkedHashMapWithExpectedSize(2);
  private Qualifier<?> qualifier;

  public AbstractBeanResolutionContext(BeanContext context, BeanDefinition rootDefinition) {
    this.context = context;
    this.rootDefinition = rootDefinition;
    this.path = null;
  }

  @Override
  public void close() {

  }

  @Override
  public BeanContext getContext() {
    return null;
  }

  @Override
  public BeanDefinition getRootDefinition() {
    return null;
  }

  @Override
  public Path getPath() {
    return null;
  }

  @Override
  public Object setAttribute(CharSequence key, Object value) {
    return null;
  }

  @Override
  public Object getAttribute(CharSequence key) {
    return null;
  }

  @Override
  public void removeAttribute(CharSequence key) {

  }

  @Override
  public Qualifier<?> getCurrentQualifier() {
    return null;
  }

  @Override
  public void setCurrentQualifier(@Nullable Qualifier<?> qualifier) {

  }
}
