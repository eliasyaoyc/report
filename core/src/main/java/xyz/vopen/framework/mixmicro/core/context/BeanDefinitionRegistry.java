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

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.core.exceptions.NoSuchBeanException;
import xyz.vopen.framework.mixmicro.core.exceptions.NoUniqueBeanException;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;

/**
 * {@link BeanDefinitionRegistry} Core bean definition registry interface containing methods to find
 * {@link BeanDefinition} instance.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/24
 */
public interface BeanDefinitionRegistry {

  /**
   * Return whether the bean of the given type is contained within this context.
   *
   * @param beanType The bean type
   * @param qualifier The qualifier for the bean
   * @param <T> The concrete type
   * @return True if it is
   */
  <T> boolean containsBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Registers a new singleton bean at runtime. This method expects that the bean definition data
   * will have been compiled ahead of time.
   *
   * <p>If bean definition data is found the method will perform dependency injection on the
   * instance followed by invoking any {@link javax.annotation.PostConstruct} hooks.
   *
   * <p>If no bean definition data is found the bean is registered as is.
   *
   * @param type The bean type
   * @param singleton The singleton bean
   * @param qualifier The bean qualifier
   * @param inject Whether the singleton should be injected (defaults to true)
   * @param <T> The concrete type
   * @return This bean context
   */
  @Nonnull
  <T> BeanDefinitionRegistry registerSingleton(
      @Nonnull Class<T> type,
      @Nonnull T singleton,
      @Nullable Qualifier<T> qualifier,
      boolean inject);

  /**
   * Obtain a {@link BeanDefinition} for the given type.
   *
   * @param beanType The type
   * @param qualifier The qualifier
   * @param <T> The concrete type
   * @return An {@link Optional} of the bean definition
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  @Nonnull
  <T> Optional<BeanDefinition<T>> findBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Obtain a {@link BeanDefinition} for the given type.
   *
   * @param beanType The type
   * @param <T> The concrete type
   * @return An {@link Optional} of the bean definition
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  @Nonnull
  <T> Collection<BeanDefinition<T>> getBeanDefinitions(@Nonnull Class<T> beanType);

  /**
   * Obtain a {@link BeanDefinition} for the given type.
   *
   * @param beanType The type
   * @param qualifier The qualifier
   * @param <T> The concrete type
   * @return An {@link Optional} of the bean definition
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  @Nonnull
  <T> Collection<BeanDefinition<T>> getBeanDefinitions(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Get all of the {@link BeanDefinition} for the given qualifier.
   *
   * @param qualifier The qualifer
   * @return The bean definitions
   */
  @Nonnull
  Collection<BeanDefinition<?>> getBeanDefinitions(@Nonnull Qualifier<Object> qualifier);

  /**
   * Get all of the registered {@link BeanDefinition}.
   *
   * @return The bean definitions
   */
  @Nonnull
  Collection<BeanDefinition<?>> getAllBeanDefinitions();

  /**
   * Registers a new singleton bean at runtime. This method expects that the bean definition data
   * will have been compiled ahead of time.
   *
   * <p>
   *
   * <p>If bean definition data is found the method will perform dependency injection on the
   * instance followed by invoking any {@link javax.annotation.PostConstruct} hooks.
   *
   * <p>
   *
   * <p>If no bean definition data is found the bean is registered as is.
   *
   * @param type The bean type
   * @param singleton The singleton bean
   * @param qualifier The bean qualifier
   * @param <T> The concrete type
   * @return This bean context
   */
  default @Nonnull <T> BeanDefinitionRegistry registerSingleton(
      @Nonnull Class<T> type, @Nonnull T singleton, @Nullable Qualifier<T> qualifier) {
    return registerSingleton(type, singleton, qualifier, true);
  }

  /**
   * Registers a new singleton bean at runtime. This method expects that the bean definition data
   * will have been compiled ahead of time.
   *
   * <p>
   *
   * <p>If bean definition data is found the method will perform dependency injection on the
   * instance followed by invoking any {@link javax.annotation.PostConstruct} hooks.
   *
   * <p>
   *
   * <p>If no bean definition data is found the bean is registered as is.
   *
   * @param type the bean type
   * @param singleton The singleton bean
   * @param <T> The concrete type
   * @return This bean context
   */
  default <T> BeanDefinitionRegistry registerSingleton(
      @Nonnull Class<T> type, @Nonnull T singleton) {
    return registerSingleton(type, singleton, null);
  }

  /**
   * Obtain a {@link BeanDefinition} for the given type.
   *
   * @param beanType The type
   * @param qualifier The qualifier
   * @param <T> The concrete type
   * @return The {@link BeanDefinition}
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   * @throws NoSuchBeanException If the bean cannot be found
   */
  default @Nonnull <T> BeanDefinition<T> getBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return findBeanDefinition(beanType, qualifier)
        .orElseThrow(() -> new NoSuchBeanException(beanType, qualifier));
  }

  /**
   * Obtain a {@link BeanDefinition} for the given type.
   *
   * @param beanType The type
   * @param <T> The concrete type
   * @return The {@link BeanDefinition}
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   * @throws NoSuchBeanException If the bean cannot be found
   */
  default @Nonnull <T> BeanDefinition<T> getBeanDefinition(@Nonnull Class<T> beanType) {
    return findBeanDefinition(beanType, null).orElseThrow(() -> new NoSuchBeanException(beanType));
  }

  /**
   * Obtain a {@link BeanDefinition} for the given type.
   *
   * @param beanType The type
   * @param <T> The concrete type
   * @return An {@link Optional} of the bean definition
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  default @Nonnull <T> Optional<BeanDefinition<T>> findBeanDefinition(@Nonnull Class<T> beanType) {
    return findBeanDefinition(beanType, null);
  }

  /**
   * Registers a new singleton bean at runtime. This method expects that the bean definition data
   * will have been compiled ahead of time.
   *
   * <p>
   *
   * <p>If bean definition data is found the method will perform dependency injection on the
   * instance followed by invoking any {@link javax.annotation.PostConstruct} hooks.
   *
   * <p>
   *
   * <p>If no bean definition data is found the bean is registered as is.
   *
   * @param singleton The singleton bean
   * @return This bean context
   */
  default @Nonnull BeanDefinitionRegistry registerSingleton(@Nonnull Object singleton) {
    Preconditions.checkNotNull(singleton,"singleton is empty.");
    Class type = singleton.getClass();
    return registerSingleton(type, singleton);
  }

  /**
   * Registers a new singleton bean at runtime. This method expects that the bean definition data
   * will have been compiled ahead of time.
   *
   * <p>
   *
   * <p>If bean definition data is found the method will perform dependency injection on the
   * instance followed by invoking any {@link javax.annotation.PostConstruct} hooks.
   *
   * <p>
   *
   * <p>If no bean definition data is found the bean is registered as is.
   *
   * @param singleton The singleton bean
   * @param inject Whether the singleton should be injected (defaults to true)
   * @return This bean context
   */
  default @Nonnull BeanDefinitionRegistry registerSingleton(
      @Nonnull Object singleton, boolean inject) {
    Preconditions.checkNotNull(singleton,"singleton is empty.");
    Class type = singleton.getClass();
    return registerSingleton(type, singleton, null, inject);
  }

  /**
   * Return whether the bean of the given type is contained within this context.
   *
   * @param beanType The bean type
   * @return True if it is
   */
  @SuppressWarnings("ConstantConditions")
  default boolean containsBean(@Nonnull Class beanType) {
    return beanType != null && containsBean(beanType, null);
  }
}
