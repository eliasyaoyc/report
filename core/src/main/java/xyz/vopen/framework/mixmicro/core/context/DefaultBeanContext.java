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

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.vopen.framework.mixmicro.core.inject.BeanConfiguration;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinitionReference;

/**
 * {@link DefaultBeanContext} The default context implementation.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public class DefaultBeanContext implements BeanContext {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultBeanContext.class);

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public <T> boolean containsBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return false;
  }

  @Nonnull
  @Override
  public <T> BeanDefinitionRegistry registerSingleton(
      @Nonnull Class<T> type,
      @Nonnull T singleton,
      @Nullable Qualifier<T> qualifier,
      boolean inject) {
    return null;
  }

  @Nonnull
  @Override
  public Optional<BeanConfiguration> findBeanConfiguration(@Nonnull String configurationName) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public <T> Optional<BeanDefinition<T>> findBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public <T> Optional<BeanRegistration<T>> findBeanRegistration(@Nonnull T bean) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public <T> Collection<BeanDefinition<T>> getBeanDefinitions(@Nonnull Class<T> beanType) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Collection<BeanDefinition<T>> getBeanDefinitions(@Nonnull Qualifier<T> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Collection<BeanDefinition<T>> getBeanDefinitions(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public Collection<BeanDefinition<?>> getBeanDefinitions() {
    return null;
  }

  @Nonnull
  @Override
  public Collection<BeanDefinitionReference<?>> getBeanDefinitionReferences() {
    return null;
  }

  @Nonnull
  @Override
  public Collection<BeanRegistration<?>> getActiveBeanRegistrations(
      @Nonnull Qualifier<?> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Collection<BeanRegistration<T>> getActiveBeanRegistrations(
      @Nonnull Class<T> beanType) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Collection<BeanRegistration<T>> getBeanRegistrations(@Nonnull Class<T> beanType) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Optional<BeanDefinition<T>> findProxyTargetBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public <T> Optional<BeanDefinition<T>> findProxyBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public <T> T getBean(@Nonnull BeanDefinition<T> definition) {
    return null;
  }

  @Nonnull
  @Override
  public <T> T getBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Optional<T> findBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public <T> Collection<T> getBeansOfType(@Nonnull Class<T> beanType) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Collection<T> getBeansOfType(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Stream<T> streamOfType(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public <T> T getProxyTargetBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }
}
