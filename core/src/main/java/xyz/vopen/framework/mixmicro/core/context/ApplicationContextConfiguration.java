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

import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.commons.converter.ConversionService;
import xyz.vopen.framework.mixmicro.core.context.env.ClassPathResourceLoader;

/**
 * {@link ApplicationContextConfiguration} An interface for configuring an application context.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public interface ApplicationContextConfiguration extends BeanContextConfiguration {

  /** @return The environment names. */
  @Nonnull
  List<String> getEnvironments();

  /**
   *
   * @return True if the environments should be deduced.
   */
  default Optional<Boolean> getDeduceEnvironments(){
    return Optional.empty();
  }

  /**
   * @return True if environment variable should contribute to configurtation.
   */
  default boolean isEnvironmentPropertySource(){
    return true;
  }

  /**
   * @return The environment variables to include in configuration
   */
  default @Nullable
  List<String> getEnvironmentVariableIncludes() {
    return null;
  }

  /**
   * @return The environment variables to exclude from configuration
   */
  default @Nullable List<String> getEnvironmentVariableExcludes() {
    return null;
  }

  /**
   * The default conversion service to use.
   *
   * @return The conversion service
   */
  default @Nonnull ConversionService<?> getConversionService() {
    return ConversionService.SHARED;
  }

  /**
   * The class path resource loader to use.
   *
   * @return The classpath resource loader
   */
  default @Nonnull
  ClassPathResourceLoader getResourceLoader() {
    return ClassPathResourceLoader.defaultLoader(getClassLoader());
  }

  /**
   * The config locations.
   *
   * @return The config locations
   */
  default @Nullable List<String> getOverrideConfigLocations() {
    return null;
  }
}
