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

import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ClassPathAnnotationScanner}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public class ClassPathAnnotationScanner implements AnnotationScanner {
  private static final Logger LOG = LoggerFactory.getLogger(ClassPathAnnotationScanner.class);

  private final ClassLoader classLoader;
  private boolean includeJars;

  public ClassPathAnnotationScanner(ClassLoader classLoader) {
    this.classLoader = classLoader;
    this.includeJars = true;
  }

  /**
   * Scan the given packages.
   *
   * @param annotation The annotation to scan for
   * @param pkg        The package to scan
   * @return A stream of classes
   */
  @Override
  public Stream<Class> scan(String annotation, String pkg) {
    if (pkg == null) {
      return Stream.empty();
    }
    List<Class> classes = doScan(annotation, pkg);
    return classes.stream();
  }

  protected List<Class> doScan(String annotation,String pkg){
    return null;
  }
}
