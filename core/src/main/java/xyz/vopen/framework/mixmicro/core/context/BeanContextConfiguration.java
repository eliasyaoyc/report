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

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.inject.Singleton;
import xyz.vopen.framework.mixmicro.core.annotations.ConfigurationProperties;
import xyz.vopen.framework.mixmicro.core.annotations.ConfigurationReader;

/**
 * {@link BeanContextConfiguration} Configuration for the {@link BeanContext}.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public interface BeanContextConfiguration {

  /**
   * The class loader to use.
   *
   * @return The class loader.
   */
  default @Nonnull ClassLoader getClassLoader() {
    return ApplicationContextConfiguration.class.getClassLoader();
  }

  /**
   * Whether eager initialization of singletons is enabled.
   *
   * @return True if eager initialization of singletons is enabled.
   */
  default boolean isEagerInitSingletons() {
    return getEagerInitAnnotated().contains(Singleton.class);
  }

  /**
   * Whether eager initialization of {@link ConfigurationProperties} is enabled.
   *
   * @return True if eager initialization of configuration is enabled.
   */
  default boolean isEagerInitConfiguration() {
    return getEagerInitAnnotated().contains(ConfigurationReader.class);
  }

  /** @return A set of annotated classes that should be eagerly initialized. */
  default Set<Class<? extends Annotation>> getEagerInitAnnotated() {
    return Collections.emptySet();
  }
}
