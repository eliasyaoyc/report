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
package xyz.vopen.framework.mixmicro.core.exceptions;

import javax.annotation.Nonnull;
import xyz.vopen.framework.mixmicro.core.context.Qualifier;

/**
 * {@link NoSuchBeanException} Thrown when no such beans exists.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/16
 */
public class NoSuchBeanException extends BeanContextException {
  private static final long serialVersionUID = 3338496150615768993L;

  /** @param beanType The bean type */
  public NoSuchBeanException(Class beanType) {
    super("No bean of type [" + beanType.getName() + "] exists." + additionalMessage());
  }

  /**
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param <T> The type
   */
  public <T> NoSuchBeanException(Class<T> beanType, Qualifier<T> qualifier) {
    super(
        "No bean of type ["
            + beanType.getName()
            + "] exists"
            + (qualifier != null ? " for the given qualifier: " + qualifier : "")
            + "."
            + additionalMessage());
  }

  /** @param message The message */
  protected NoSuchBeanException(String message) {
    super(message);
  }

  @Nonnull
  private static String additionalMessage() {
    return " Make sure the bean is not disabled by bean requirements (enable trace logging for 'io.micronaut.context.condition' to check) and if the bean is enabled then ensure the class is declared a bean and annotation processing is enabled (for Java and Kotlin the 'micronaut-inject-java' dependency should be configured as an annotation processor).";
  }
}
