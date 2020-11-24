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

import java.util.Iterator;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;

/**
 * {@link NoUniqueBeanException} Exception thrown when a bean is not unique and has multiple
 * possible implementations for a given bean type.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/16
 */
public class NoUniqueBeanException extends NoSuchBeanException {
  private static final long serialVersionUID = 4633760027467823025L;

  private final Class targetType;
  private final Iterator possibleCandidates;

  /**
   * @param targetType The target type
   * @param candidates The bean definition candidates
   * @param <T> The type
   */
  public <T> NoUniqueBeanException(Class targetType, Iterator<BeanDefinition<T>> candidates) {
    super(buildMessage(candidates));
    this.targetType = targetType;
    this.possibleCandidates = candidates;
  }

  public Class getTargetType() {
    return targetType;
  }

  public Iterator getPossibleCandidates() {
    return possibleCandidates;
  }

  private static <T> String buildMessage(Iterator<BeanDefinition<T>> possibleCandidates) {
    final StringBuilder message = new StringBuilder("Multiple possible bean candidate found: [");
    while (possibleCandidates.hasNext()) {
      Class<T> next = possibleCandidates.next().getBeanType();
      message.append(next.getName());
      if (possibleCandidates.hasNext()) {
        message.append(", ");
      }
    }
    message.append("]");
    return message.toString();
  }
}
