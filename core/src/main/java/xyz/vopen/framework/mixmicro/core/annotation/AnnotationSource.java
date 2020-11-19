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
package xyz.vopen.framework.mixmicro.core.annotation;

import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import javax.annotation.Nonnull;

/**
 * {@link AnnotationSource} provides an alternative to Java's {@link AnnotatedElement} that uses the
 * compile time produced data form Mixmicro. This is the parent interface of the {@link
 * AnnotationMetadata} which provides event more methods to read annotations values and compute
 * {@link java.lang.annotation.Repeatable} annotations.
 *
 * <p>Note that this interface also includes methods such as {@link #synthesize(Class)} that allows
 * materializing an instance of an annotation by producing a runtime proxy. These methods are a last
 * resort if no other option is possible and should generally be avoided as they require the use of
 * runtime reflection and proxying which hurts performance and memory consumption.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/19
 */
public interface AnnotationSource {

  /** An empty annotation source. */
  AnnotationSource EMPTY = new AnnotationSource() {};

  /**
   * Synthesizes a new annotation from the metadata for the given annotation type. This method works
   * by creating a runtime proxy of the annotation interface and should be avoided in favour of
   * direct use of the annotation metadata and only used for unique cases that require integrating
   * third party libraries.
   *
   * @param annotationClass The annotation class
   * @param <T> The annotation generic type
   * @return The annotation or null if it doesn't exist
   */
  default @Nonnull <T extends Annotation> T synthesize(@Nonnull Class<T> annotationClass) {
    Preconditions.checkNotNull(annotationClass, "Annotation class is empty.");
    return null;
  }
}
