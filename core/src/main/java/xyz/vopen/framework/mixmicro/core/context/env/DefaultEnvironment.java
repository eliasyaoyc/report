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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import xyz.vopen.framework.mixmicro.commons.converter.ConversionContext;
import xyz.vopen.framework.mixmicro.core.inject.BeanConfiguration;

/**
 * {@link DefaultEnvironment}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/16
 */
public class DefaultEnvironment implements Environment {

  @Override
  public Set<String> getActiveNames() {
    return null;
  }

  @Override
  public Collection<PropertySource> getPropertySources() {
    return null;
  }

  @Override
  public Environment addPropertySource(PropertySource propertySource) {
    return null;
  }

  @Override
  public Environment removePropertySource(PropertySource propertySource) {
    return null;
  }

  @Override
  public Environment addPackage(String pkg) {
    return null;
  }

  @Override
  public Environment addConfigurationExcludes(String... names) {
    return null;
  }

  @Override
  public Environment addConfigurationIncludes(String... names) {
    return null;
  }

  @Override
  public Collection<String> getPackages() {
    return null;
  }

  @Override
  public PropertyPlaceholderResolver getPlaceholderResolver() {
    return null;
  }

  @Override
  public Map<String, Object> refreshAndDiff() {
    return null;
  }

  @Override
  public boolean isActive(BeanConfiguration configuration) {
    return false;
  }

  @Override
  public Collection<PropertySourceLoader> getPropertySourceLoaders() {
    return null;
  }

  @Override
  public <T> Optional<T> convert(Object object, Class<T> targetType, ConversionContext context) {
    return Optional.empty();
  }

  @Override
  public boolean isRunning() {
    return false;
  }
}