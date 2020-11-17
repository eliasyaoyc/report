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
import xyz.vopen.framework.mixmicro.core.context.exceptions.NoSuchBeanException;
import xyz.vopen.framework.mixmicro.core.context.exceptions.NoUniqueBeanException;
import xyz.vopen.framework.mixmicro.core.inject.BeanConfiguration;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinitionReference;
import xyz.vopen.framework.mixmicro.core.inject.ProxyBeanDefinition;

/**
 * {@link BeanDefinitionRegistry} Core bean definition registry interface containing methods to find
 * {@link BeanDefinition} instance.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/16
 */
public interface BeanDefinitionRegistry {

  /**
   * Return whether the bean of the given type and qualifier is contained within this context.
   *
   * @param beanType The type of bean.
   * @param qualifier The qualifier for the bean.
   * @param <T> The concrete type.
   * @return True if it is, otherwise false.
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
   * @param type of the bean
   * @param singleton of bean
   * @param qualifier of bean
   * @param inject Whether the singleton should be injected (default is true)
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
   * Obtain a bean configuration by name.
   *
   * @param configurationName of configuration.
   * @return An optional with the configuration either present or not.
   */
  @Nonnull
  Optional<BeanConfiguration> findBeanConfiguration(@Nonnull String configurationName);

  /**
   * Obtain a {@link BeanDefinition} by the type of bean and qualifier.
   *
   * @param beanType of bean
   * @param qualifier of bean
   * @param <T> The concrete bean.
   * @return An optional with {@link BeanDefinition} either present or not.
   */
  @Nonnull
  <T> Optional<BeanDefinition<T>> findBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Obtain a {@link BeanRegistration} for the given bean.
   *
   * @param bean The bean
   * @param <T> The concrete type.
   * @return An optional of the {@link BeanRegistration}.
   */
  @Nonnull
  <T> Optional<BeanRegistration<T>> findBeanRegistration(@Nonnull T bean);

  /**
   * Obtain the collection of {@link BeanDefinition}'s for the given bean type.
   *
   * @param beanType of bean
   * @param <T> The concrete bean.
   * @return Collection of {@link BeanDefinition}'s
   */
  @Nonnull
  <T> Collection<BeanDefinition<T>> getBeanDefinitions(@Nonnull Class<T> beanType);

  /**
   * Obtain the collection of {@link BeanDefinition}'s for the qualifier.
   *
   * @param qualifier The qualifier
   * @param <T> The concrete bean.
   * @return Collection of {@link BeanDefinition}'s
   */
  @Nonnull
  <T> Collection<BeanDefinition<T>> getBeanDefinitions(@Nonnull Qualifier<T> qualifier);

  /**
   * Obtain the collection of {@link BeanDefinition}'s for the given bean type and qualifier.
   *
   * @param beanType of bean
   * @param qualifier The qualifier
   * @param <T> The concrete bean.
   * @return Collection of {@link BeanDefinition}'s
   */
  @Nonnull
  <T> Collection<BeanDefinition<T>> getBeanDefinitions(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /** @return All the registered {@link BeanDefinition}'s */
  @Nonnull
  Collection<BeanDefinition<?>> getBeanDefinitions();

  /** @return All of the enabled {@link BeanDefinitionReference}'s. */
  @Nonnull
  Collection<BeanDefinitionReference<?>> getBeanDefinitionReferences();

  /**
   * Find active {@link javax.inject.Singleton} beans for the given qualifier. Note that this method
   * can return multiple registrations for a given singleton bean instance since each bean may have
   * multiple qualifiers.
   *
   * @param qualifier The qualifier
   * @return The beans
   */
  @Nonnull
  Collection<BeanRegistration<?>> getActiveBeanRegistrations(@Nonnull Qualifier<?> qualifier);

  /**
   * Find active {@link javax.inject.Singleton} beans for the given bean type. Note that this method
   * can return multiple registrations for a given singleton bean instance since each bean may have
   * multiple qualifiers.
   *
   * @param beanType The bean type
   * @param <T> The concrete type
   * @return The beans
   */
  @Nonnull
  <T> Collection<BeanRegistration<T>> getActiveBeanRegistrations(@Nonnull Class<T> beanType);

  /**
   * Find and if necessary initialize {@link javax.inject.Singleton} beans for the given bean type,
   * returning all the active registrations. Note that this method can return multiple registrations
   * for a given singleton bean instance since each bean may have multiple qualifiers.
   *
   * @param beanType The bean type
   * @param <T> The concrete type
   * @return The beans
   */
  @Nonnull
  <T> Collection<BeanRegistration<T>> getBeanRegistrations(@Nonnull Class<T> beanType);

  /**
   * Obtain the original {@link BeanDefinition} for a {@link ProxyBeanDefinition}.
   *
   * @param beanType The type
   * @param qualifier The qualifier
   * @param <T> The concrete type
   * @return An {@link Optional} of the bean definition
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  @Nonnull
  <T> Optional<BeanDefinition<T>> findProxyTargetBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

  /**
   * Obtain the original {@link BeanDefinition} for a {@link ProxyBeanDefinition}.
   *
   * @param beanType The type
   * @param qualifier The qualifier
   * @param <T> The concrete type
   * @return An {@link Optional} of the bean definition
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   */
  @Nonnull
  <T> Optional<BeanDefinition<T>> findProxyBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier);

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
   * Obtain the original {@link BeanDefinition} for a {@link ProxyBeanDefinition}.
   *
   * @param beanType The type
   * @param qualifier The qualifier
   * @param <T> The concrete type
   * @return The {@link BeanDefinition}
   * @throws NoUniqueBeanException When multiple possible bean definitions exist for the given type
   * @throws NoSuchBeanException If the bean cannot be found
   */
  default @Nonnull <T> BeanDefinition<T> getProxyTargetBeanDefinition(
      @Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    return findProxyTargetBeanDefinition(beanType, qualifier)
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
    Preconditions.checkNotNull(singleton, "Singleton object is empty.");
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
    Preconditions.checkNotNull(singleton, "Singleton object is empty.");
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
