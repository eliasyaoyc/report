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
package xyz.vopen.framework.mixmicro.core.context.env;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * {@link AnnotationScanner} Interface for classes that scan for classes with the given annotation.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public interface AnnotationScanner {

  /**
   * Scan the given packages.
   *
   * @param annotation The annotation to scan for.
   * @param pkg The package scan.
   * @return A stream of classes.
   */
  Stream<Class> scan(String annotation, String pkg);

  /**
   * Scans the given packages names.
   *
   * @param annotation The annotation name to scan.
   * @param packages The package names
   * @return A stream of classes.
   */
  default Stream<Class> scan(Class<? extends Annotation> annotation, Collection<String> packages) {
    return scan(annotation.getName(),packages.parallelStream());
  }

  /**
   * Scans the given package names.
   *
   * @param annotation The annotation name to scan
   * @param packages   The package names
   * @return A stream of classes
   */
  default Stream<Class> scan(String annotation, Stream<String> packages) {
    return packages
        .parallel()
        .flatMap(pkg -> scan(annotation, pkg));
  }
}
