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
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.core.event.ApplicationEventListener;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.BeanIdentifier;

/**
 * {@link DefaultBeanContext} The {@link BeanContext} default implementation.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/24
 */
public class DefaultBeanContext implements BeanContext {

  private final ClassLoader classLoader;

  public DefaultBeanContext() {
    this(BeanContext.class.getClassLoader());
  }

  public DefaultBeanContext(@Nonnull ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public @Nonnull <T> T inject(@Nonnull T instance) {
    return null;
  }

  @Nonnull
  @Override
  public <T> T createBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public <T> T createBean(
      @Nonnull Class<T> beanType,
      @Nullable Qualifier<T> qualifier,
      @Nullable Map<String, Object> argumentValues) {
    return null;
  }

  @Nonnull
  @Override
  public <T> T createBean(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier, @Nullable Object... args) {
    return null;
  }

  @Nullable
  @Override
  public <T> T destroyBean(@Nonnull Class<T> beanType) {
    return null;
  }

  @Nonnull
  @Override
  public <T> Optional<T> refreshBean(@Nullable BeanIdentifier identifier) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public ClassLoader getClassLoader() {
    return null;
  }

  @Nonnull
  @Override
  public <T> BeanContext registerSingleton(
      @Nonnull Class<T> type,
      @Nonnull T singleton,
      @Nullable Qualifier<T> qualifier,
      boolean inject) {
    return null;
  }

  // =====================  LifeCycle  =====================
  @Override
  public boolean isRunning() {
    return false;
  }

  // =====================  BeanLocator   =====================
  @Override
  public @Nonnull <T> T getBean(@Nonnull BeanDefinition<T> definition) {
    return null;
  }

  @Override
  public @Nonnull <T> T getBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }

  @Override
  public @Nonnull <T> Collection<T> getBeansOfType(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return null;
  }

  @Override
  public @Nonnull <T> Optional<T> findBean(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return Optional.empty();
  }

  // =====================  BeanDefinitionRegister   =====================
  @Override
  public <T> boolean containsBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return false;
  }

  @Nonnull
  @Override
  public <T> Optional<BeanDefinition<T>> findBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return Optional.empty();
  }

  @Nonnull
  @Override
  public <T> Collection<BeanDefinition<T>> getBeanDefinitions(@Nonnull Class<T> beanType) {
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
  public Collection<BeanDefinition<?>> getBeanDefinitions(@Nonnull Qualifier<Object> qualifier) {
    return null;
  }

  @Nonnull
  @Override
  public Collection<BeanDefinition<?>> getAllBeanDefinitions() {
    return null;
  }

  // =====================  ApplicationEventPublisher  =====================
  @Override
  public void publishEvent(@Nonnull Object event) {}

  public @Nonnull Future<Void> publishEventAsync(@Nonnull Object event) {
    return null;
  }

  private void notifyEventListeners(
      @Nonnull Object event, Collection<ApplicationEventListener> eventListeners) {}
}
