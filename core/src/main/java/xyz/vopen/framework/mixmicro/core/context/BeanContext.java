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
import com.google.common.collect.Lists;
import java.io.Closeable;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.vopen.framework.mixmicro.commons.reflect.ClassUtils;
import xyz.vopen.framework.mixmicro.commons.serviceLoader.ServiceDefinition;
import xyz.vopen.framework.mixmicro.commons.serviceLoader.SoftServiceLoader;
import xyz.vopen.framework.mixmicro.commons.utils.CollectionUtils;
import xyz.vopen.framework.mixmicro.commons.utils.StringUtils;
import xyz.vopen.framework.mixmicro.core.LifeCycle;
import xyz.vopen.framework.mixmicro.core.Named;
import xyz.vopen.framework.mixmicro.core.OrderUtil;
import xyz.vopen.framework.mixmicro.core.Provider;
import xyz.vopen.framework.mixmicro.core.annotation.Bean;
import xyz.vopen.framework.mixmicro.core.annotation.Context;
import xyz.vopen.framework.mixmicro.core.annotation.Indexes;
import xyz.vopen.framework.mixmicro.core.annotation.Parallel;
import xyz.vopen.framework.mixmicro.core.annotation.Primary;
import xyz.vopen.framework.mixmicro.core.annotation.Replaces;
import xyz.vopen.framework.mixmicro.core.context.env.ClassPathResourceLoader;
import xyz.vopen.framework.mixmicro.core.context.env.PropertyResolver;
import xyz.vopen.framework.mixmicro.core.context.env.ResourceLoader;
import xyz.vopen.framework.mixmicro.core.context.env.ValueResolver;
import xyz.vopen.framework.mixmicro.core.event.ApplicationEventListener;
import xyz.vopen.framework.mixmicro.core.event.ApplicationEventPublisher;
import xyz.vopen.framework.mixmicro.core.event.BeanCreatedEventListener;
import xyz.vopen.framework.mixmicro.core.event.BeanInitializedEventListener;
import xyz.vopen.framework.mixmicro.core.event.ShutdownEvent;
import xyz.vopen.framework.mixmicro.core.event.StartupEvent;
import xyz.vopen.framework.mixmicro.core.inject.BeanConfiguration;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.BeanDefinitionReference;
import xyz.vopen.framework.mixmicro.core.inject.BeanIdentifier;
import xyz.vopen.framework.mixmicro.core.inject.DisposableBeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.qualifiers.Qualifiers;

/**
 * {@link BeanContext} The core class which allows for dependency injection of classes annotated
 * with {@link javax.inject.Inject}.
 *
 * <p>Apart of the standard {@code javax.inject} annotations for dependency injection, additional
 * annotations within the {@code xyz.vopen.framework.mixmicro.core.annotation} package allow control
 * over configuration of the bean context.
 *
 * <p>Base class {@link BeanContext} provides rich functionality by implementing the following
 * interfaces:
 *
 * <ul>
 *   <li>LifeCycle: life cycle.
 *   <li>BeanLocator: locating and discovering the {@link Bean} instances. (core interface)
 *   <li>BeanDefinitionRegistry: register {@link Bean} instances. (core interface)
 *   <li>ApplicationEventPublisher: publisher event.
 *   <li>AnnotationMetadataResolver: Resolve the {@link AnnotationMetadata}.
 *   <li>MutableAttributeHolder: mutating attributes.
 * </ul>
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public class BeanContext
    implements LifeCycle<BeanContext>,
        BeanLocator,
        BeanDefinitionRegistry,
        ApplicationEventPublisher,
        AnnotationMetadataResolver {
  private static final Logger LOG = LoggerFactory.getLogger(BeanContext.class);

  private static final String SCOPED_PROXY_ANN = "";
  private static final String AROUND_TYPE = "";
  private static final String INTRODUCTION_TYPE = "";
  private static final String ADAPTER_TYPE = "";
  private static final String NAMED_MEMBER = "named";
  private static final String PARALLEL_TYPE = Parallel.class.getName();
  private static final String INDEXES_TYPE = Indexes.class.getName();
  private static final String REPLACES_ANN = Replaces.class.getName();

  protected final AtomicBoolean running = new AtomicBoolean(false);
  protected final AtomicBoolean initializing = new AtomicBoolean(false);
  protected final AtomicBoolean terminating = new AtomicBoolean(false);

  final Map<BeanKey, BeanRegistration> singletonObjects = new ConcurrentHashMap<>(100);
  final Map<BeanIdentifier, Object> singlesInCreation = new ConcurrentHashMap<>(5);
  final Map<BeanKey, Provider<Object>> scopedProxies = new ConcurrentHashMap<>(20);

  Set<Map.Entry<Class, List<BeanInitializedEventListener>>> beanInitializedEventListeners;

  private final Collection<BeanDefinitionReference> beanDefinitionsClasses =
      new ConcurrentLinkedQueue<>();
  private final Map<String, BeanConfiguration> beanConfigurations = new HashMap<>(10);
  private final Map<BeanKey, Boolean> containsBeanCache = new ConcurrentHashMap<>(30);
  private final Map<CharSequence, Objects> attributes =
      Collections.synchronizedMap(new HashMap<>(5));

  private final Map<BeanKey, Collection<Object>> initializedObjectsByType =
      new ConcurrentHashMap<>();
  private final Map<BeanKey, Optional<BeanDefinition>> beanConcreteCandidateCache =
      new ConcurrentHashMap<>(30);
  private final Map<Class, Collection<BeanDefinition>> beanCandidateCache =
      new ConcurrentHashMap<>(30);
  private final Map<Class, Collection<BeanDefinitionReference>> beanIndex =
      new ConcurrentHashMap<>(12);

  private final ClassLoader classLoader;
  private final Set<Class> thisInterfaces =
      CollectionUtils.setOf(
          BeanDefinitionRegistry.class,
          BeanContext.class,
          //          AnnotationMetadataResolver.class,
          BeanLocator.class,
          ApplicationEventPublisher.class,
          ApplicationContext.class,
          PropertyResolver.class,
          ValueResolver.class);

  private final Set<Class> indexedTypes =
      CollectionUtils.setOf(
          ResourceLoader.class,
          ApplicationEventListener.class,
          BeanCreatedEventListener.class,
          BeanInitializedEventListener.class);

  private final String[] eagerInitStereotypes;
  private final boolean eagerInitStereotypesPresent;
  private final boolean eagerInitSingletons;
  private Set<Map.Entry<Class, List<BeanCreatedEventListener>>> beanCreationEventListeners;

  /** Construct a new bean context using the same classloader that loaded this BeanContext class. */
  public BeanContext() {
    this(BeanContext.class.getClassLoader());
  }

  /**
   * Construct a new bean context with the given class loader.
   *
   * @param classLoader The class loader.
   */
  public BeanContext(@Nonnull ClassLoader classLoader) {
    this(
        new BeanContextConfiguration() {
          @Override
          public @Nonnull ClassLoader getClassLoader() {
            Preconditions.checkNotNull(classLoader, "ClassLoader is empty.");
            return classLoader;
          }
        });
  }

  /**
   * Construct a new bean context with the given resource loader.
   *
   * @param resourceLoader The resource loader.
   */
  public BeanContext(@Nonnull ClassPathResourceLoader resourceLoader) {
    this(
        new BeanContextConfiguration() {
          @Override
          public @Nonnull ClassLoader getClassLoader() {
            Preconditions.checkNotNull(resourceLoader, "ClassPathResourceLoader is empty.");
            return resourceLoader.getClassLoader();
          }
        });
  }

  /**
   * Construct a new bean context with the the given configuration.
   *
   * @param contextConfiguration The context configuration.
   */
  public BeanContext(@Nonnull BeanContextConfiguration contextConfiguration) {
    Preconditions.checkNotNull(contextConfiguration, "BeanContextConfiguration is empty.");
    // enable classloader logging
    System.setProperty(ClassUtils.PROPERTY_MICRONAUT_CLASSLOADER_LOGGING, "true");
    this.classLoader = contextConfiguration.getClassLoader();
    Set<Class<? extends Annotation>> eagerInitAnnotated =
        contextConfiguration.getEagerInitAnnotated();
    this.eagerInitStereotypes =
        eagerInitAnnotated.stream().map(Class::getName).toArray(String[]::new);
    this.eagerInitStereotypesPresent = eagerInitStereotypes.length > 0;
    this.eagerInitSingletons =
        eagerInitStereotypesPresent && eagerInitAnnotated.contains(Singleton.class);
  }

  public <T> Optional<T> refreshBean(BeanIdentifier identifier) {
    if (identifier != null) {
      BeanRegistration beanRegistration = singletonObjects.get(identifier);
      if (beanRegistration != null) {
        BeanDefinition beanDefinition = beanRegistration.getBeanDefinition();
        return Optional.of((T) beanDefinition.inject(this, beanRegistration.getBean()));
      }
    }
    return Optional.empty();
  }

  // =====================   LifeCycle  =====================
  @Override
  public boolean isRunning() {
    return running.get() && !initializing.get();
  }

  /**
   * The start method will read all bean definition classes found on the classpath and initialize
   * any pre-required state.
   *
   * @return current instance.
   */
  @Override
  public @Nonnull BeanContext start() {
    if (!isRunning()) {
      if (initializing.compareAndSet(false, true)) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Starting BeanContext...");
        }
        readAllBeanConfigurations();
        readAllBeanDefinitionClasses();
        if (LOG.isDebugEnabled()) {
          String activeConfigurations =
              beanConfigurations.values().stream()
                  .filter(config -> config.isEnabled(this))
                  .map(BeanConfiguration::getName)
                  .collect(Collectors.joining(","));
          if (StringUtils.isNotEmpty(activeConfigurations)) {
            LOG.debug("Loaded active configurations: {}", activeConfigurations);
          }

          LOG.debug("BeanContext Started.");
        }
        publishEvent(new StartupEvent(this));
      }
      running.set(true);
      initializing.set(false);
    }
    return this;
  }

  /**
   * The stop method will shut down the context calling {@link javax.annotation.PreDestroy} hooks on
   * loaded singletons.
   *
   * @return current instance.
   */
  @Override
  public @Nonnull BeanContext stop() {
    if (terminating.compareAndSet(false, true)) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Stopping BeanContext");
      }
      publishEvent(new ShutdownEvent(this));
      attributes.clear();

      // need to sort registered singletons so that beans with that require other beans appear first
      List<BeanRegistration> objects = topologicalSort(singletonObjects.values());

      Set<Integer> processed = new HashSet<>();
      for (BeanRegistration beanRegistration : objects) {
        BeanDefinition def = beanRegistration.beanDefinition;
        Object bean = beanRegistration.bean;
        int sysId = System.identityHashCode(bean);
        if (processed.contains(sysId)) {
          continue;
        }

        if (LOG.isDebugEnabled()) {
          LOG.debug("Destroying bean [{}] with identifier [{}]", bean, beanRegistration.identifier);
        }

        processed.add(sysId);
        if (def instanceof DisposableBeanDefinition) {
          try {
            ((DisposableBeanDefinition) def).dispose(this, bean);
          } catch (Throwable e) {
            if (LOG.isErrorEnabled()) {
              LOG.error(
                  "Error disposing of bean registration [" + def.getName() + "]: " + e.getMessage(),
                  e);
            }
          }
        }
        if (def instanceof Closeable) {
          try {
            ((Closeable) def).close();
          } catch (Throwable e) {
            if (LOG.isErrorEnabled()) {
              LOG.error(
                  "Error disposing of bean registration [" + def.getName() + "]: " + e.getMessage(),
                  e);
            }
          }
        }
        if (bean instanceof LifeCycle) {
          ((LifeCycle) bean).stop();
        }
      }
      terminating.set(false);
      running.set(false);
    }
    return this;
  }

  /**
   * Resolves the {@link BeanDefinitionReference} class instance. Default implementation uses
   * ServiceLoader pattern.
   *
   * @return The bean definition classes.
   */
  protected @Nonnull List<BeanDefinitionReference> resolveBeanDefinitionReferences() {
    final SoftServiceLoader<BeanDefinitionReference> definitions =
        SoftServiceLoader.load(BeanDefinitionReference.class, classLoader);
    List<ServiceDefinition<BeanDefinitionReference>> list = Lists.newArrayListWithCapacity(300);
    for (ServiceDefinition<BeanDefinitionReference> definition : definitions) {
      list.add(definition);
    }
    return list.parallelStream()
        .filter(ServiceDefinition::isPresent)
        .map(ServiceDefinition::load)
        .filter(BeanDefinitionReference::isPresent)
        .collect(Collectors.toList());
  }

  /**
   * Resolves the {@link BeanConfiguration} class instances. Default implementation uses
   * ServiceLoader pattern.
   *
   * @return The bean definition classes.
   */
  protected @Nonnull Iterable<BeanConfiguration> resolveBeanConfigurations() {
    final SoftServiceLoader<BeanConfiguration> definitions =
        SoftServiceLoader.load(BeanConfiguration.class, classLoader);
    List<ServiceDefinition<BeanConfiguration>> list = Lists.newArrayListWithCapacity(300);
    for (ServiceDefinition<BeanConfiguration> definition : definitions) {
      list.add(definition);
    }
    return list.parallelStream()
        .filter(ServiceDefinition::isPresent)
        .map(ServiceDefinition::load)
        .collect(Collectors.toList());
  }

  /** Initialize the event listeners. */
  protected void initializeEventListeners() {
    final Collection<BeanDefinition<BeanCreatedEventListener>> beanCreatedDefinitions =
        getBeanDefinitions(BeanCreatedEventListener.class);
    final HashMap<Class, List<BeanCreatedEventListener>> beanCreatedListeners =
        new HashMap<>(beanCreatedDefinitions.size());
  }

  /**
   * Initialize the context with the given {@link Context} scope bean.
   *
   * @param contextScopeBeans The context scope beans.
   * @param processedBeans The beans that require {@link ExecutableMethodProcessor} handling
   * @param parallelBeans The parallel bean definitions.
   */
  protected void initializeContext(
      @Nonnull List<BeanDefinitionReference> contextScopeBeans,
      @Nonnull List<BeanDefinitionReference> processedBeans,
      @Nonnull List<BeanDefinitionReference> parallelBeans) {}

  private void readAllBeanConfigurations() {
    Iterable<BeanConfiguration> beanConfigurations = resolveBeanConfigurations();
    for (BeanConfiguration beanConfiguration : beanConfigurations) {
      registerConfiguration(beanConfiguration);
    }
  }

  /**
   * Registers an active configuration.
   *
   * @param configuration The configuration to register.
   */
  protected synchronized void registerConfiguration(@Nonnull BeanConfiguration configuration) {
    Preconditions.checkNotNull(configuration, "Bean Configuration is empty.");
    beanConfigurations.put(configuration.getName(), configuration);
  }

  private <T> Collection<BeanDefinition<T>> filterExactMatch(
      final Class<T> beanType, Collection<BeanDefinition<T>> candidates) {
    Stream<BeanDefinition<T>> filteredResults =
        candidates.stream()
            .filter((BeanDefinition<T> candidate) -> candidate.getBeanType() == beanType);
    return filteredResults.collect(Collectors.toList());
  }

  private <T> void registerSingletonBean(
      BeanDefinition<T> beanDefinition,
      Class<T> beanType,
      T createdBean,
      Qualifier<T> qualifier,
      boolean singleCandidate) {}

  private void readAllBeanDefinitionClasses() {
    List<BeanDefinitionReference> contextScopeBeans = Lists.newArrayListWithCapacity(20);
    List<BeanDefinitionReference> processedBeans = Lists.newArrayListWithCapacity(10);
    List<BeanDefinitionReference> parallelBeans = Lists.newArrayListWithCapacity(10);
    List<BeanDefinitionReference> beanDefinitionReferences = resolveBeanDefinitionReferences();
    beanDefinitionsClasses.addAll(beanDefinitionReferences);

    Map<BeanConfiguration, Boolean> configurationEnabled =
        beanConfigurations.values().stream()
            .collect(Collectors.toMap(Function.identity(), bc -> bc.isEnabled(this)));
    final Set<Entry<BeanConfiguration, Boolean>> configurationEnabledEntries =
        configurationEnabled.entrySet();

    reference:
    for (BeanDefinitionReference beanDefinitionReference : beanDefinitionReferences) {
      for (Entry<BeanConfiguration, Boolean> entry : configurationEnabledEntries) {
        if (entry.getKey().isWithin(beanDefinitionReference) && !entry.getValue()) {
          beanDefinitionsClasses.remove(beanDefinitionReference);
          continue reference;
        }
      }
      //      final AnnotationMetadata annotationMetadata =
      // beanDefinitionReference.getAnnotationMetadata();
      //      Class[] indexes = annotationMetadata.classValues(INDEXES_TYPE);
      if (isEagerInit(beanDefinitionReference)) {
        contextScopeBeans.add(beanDefinitionReference);
      }
    }

    initializeEventListeners();
    initializeContext(contextScopeBeans, processedBeans, parallelBeans);
  }

  /**
   * Determine whether the {@link BeanDefinitionReference} initialize advance.
   *
   * @param beanDefinitionReference
   * @return True if it is.
   */
  private boolean isEagerInit(BeanDefinitionReference beanDefinitionReference) {
    //    return beanDefinitionReference.isContextScope()
    //        || (eagerInitSingletons || beanDefinitionReference.isSingleton())
    //        || (eagerInitStereotypesPresent
    //            ||
    // beanDefinitionReference.getAnnotationMetadata().hasStereotype(eagerInitStereotypes));
    return false;
  }

  // =====================   BeanDefinitionRegistry  =====================
  @Override
  public <T> boolean containsBean(@Nonnull Class<T> beanType, @Nullable Qualifier<T> qualifier) {
    Preconditions.checkNotNull(beanType, "Bean type is empty.");
    BeanKey<T> beanKey = new BeanKey<>(beanType, qualifier);
    if (containsBeanCache.containsKey(beanKey)) {
      return containsBeanCache.get(beanKey);
    } else {
      boolean result =
          singletonObjects.containsKey(beanKey) || isCandidatePresent(beanType, qualifier);
      containsBeanCache.put(beanKey, result);
      return result;
    }
  }

  /**
   * Find bean candidates for the given type
   *
   * @param resolutionContext The current resolution context
   * @param beanType The bean type
   * @param filter A bean definition to filter out
   * @param filterProxied Whether to filter out bean proxy targets
   * @param <T> The bean generic type
   * @return The candidates
   */
  protected @Nonnull <T> Collection<BeanDefinition<T>> findBeanCandidates(
      @Nullable BeanResolutionContext resolutionContext,
      @Nonnull Class<T> beanType,
      @Nullable BeanDefinition<?> filter,
      boolean filterProxied) {
    Preconditions.checkNotNull(beanType, "bean type is empty.");
    if (LOG.isDebugEnabled()) {
      LOG.debug("Finding candidate beans for type: {}", beanType);
    }

    return null;
  }

  private <T> boolean isCandidatePresent(Class<T> beanType, Qualifier<T> qualifier) {
    Collection<BeanDefinition<T>> beanCandidates = findBeanCandidates(null, beanType, null, true);
    return true;
  }

  private List<BeanRegistration> topologicalSort(Collection<BeanRegistration> beans) {
    return null;
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
    if (qualifier == null) {
      return Collections.emptyList();
    }

    //    List result =
    //        singletonObjects.values().stream()
    //            .filter(
    //                registration -> {
    //                  BeanDefinition beanDefinition = registration.beanDefinition;
    //                  return qualifier.reduce(
    //                      beanDefinition.getBeanType(),
    //                      Stream.of(beanDefinition).findFirst().isPresent());
    //                })
    //            .collect(Collectors.toList());
    //    return result;
    return null;
  }

  @Nonnull
  @Override
  public <T> Collection<BeanRegistration<T>> getActiveBeanRegistrations(
      @Nonnull Class<T> beanType) {
    if (beanType == null) {
      return Collections.emptyList();
    }

    List result =
        singletonObjects.values().stream()
            .filter(
                registration -> {
                  BeanDefinition beanDefinition = registration.beanDefinition;
                  return beanType.isAssignableFrom(beanDefinition.getBeanType());
                })
            .collect(Collectors.toList());
    return result;
  }

  @Nonnull
  @Override
  public <T> Collection<BeanRegistration<T>> getBeanRegistrations(@Nonnull Class<T> beanType) {
    if (beanType == null) {
      return Collections.emptyList();
    }
    // initialize the beans
    getBeansOfType(beanType);
    return getActiveBeanRegistrations(beanType);
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

  // =====================   BeanLocator  =====================
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

  // =====================   ApplicationEventPublisher  =====================
  @SuppressWarnings("unchecked")
  @Override
  public void publishEvent(@Nonnull Object event) {
    if (event != null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Publishing event: {}", event);
      }
      Collection<ApplicationEventListener> eventListeners =
          getBeansOfType(
              ApplicationEventListener.class, Qualifiers.byTypeArguments(event.getClass()));

      eventListeners =
          eventListeners.stream().sorted(OrderUtil.COMPARATOR).collect(Collectors.toList());

      notifyEventListeners(event, eventListeners);
    }
  }

  @Override
  public @Nonnull Future<Void> publishEventAsync(Object event) {
    Preconditions.checkNotNull(event, "Event is empty.");
    CompletableFuture<Void> future = new CompletableFuture();
    Collection<ApplicationEventListener> eventListeners =
        streamOfType(ApplicationEventListener.class, Qualifiers.byTypeArguments(event.getClass()))
            .sorted(OrderUtil.COMPARATOR)
            .collect(Collectors.toList());

    Executor executor =
        (Executor)
            findBean(Executor.class, Qualifiers.byName("scheduled"))
                .orElseGet(ForkJoinPool::commonPool);
    executor.execute(
        () -> {
          try {
            notifyEventListeners(event, eventListeners);
            future.complete(null);
          } catch (Exception e) {
            future.completeExceptionally(e);
          }
        });
    return future;
  }

  private void notifyEventListeners(
      @Nonnull Object event, Collection<ApplicationEventListener> eventListeners) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Established event listeners {} for event: {}", eventListeners, event);
    }

    for (ApplicationEventListener listener : eventListeners) {
      if (listener.supports(event)) {
        try {
          if (LOG.isTraceEnabled()) {
            LOG.trace("Invoking event listener [{}] for event: {}", listener, event);
          }
          listener.onApplicationEvent(event);
        } catch (ClassCastException ex) {
          String msg = ex.getMessage();
          if (msg == null || msg.startsWith(event.getClass().getName())) {
            if (LOG.isDebugEnabled()) {
              LOG.debug("Incompatible listener for event: " + listener, ex);
            }
          } else {
            throw ex;
          }
        }
      }
    }
  }

  // =====================  AnnotationMetadataResolver  =====================
  //  @Override
  //  public @Nonnull AnnotationMetadata resolveMetadata(@Nullable Class<?> type) {
  //    if (type == null) {
  //      return AnnotationMetadata.EMPTY_METADATA;
  //    }
  //    return null;
  //  }

  // =====================   Utility class   =====================

  /**
   * Class used as a bean key.
   *
   * @param <T> The bean type.
   */
  static final class BeanKey<T> implements BeanIdentifier {
    private final Class beanType;
    private final Qualifier qualifier;
    private final Class[] typeArguments;
    private final int hashCode;

    BeanKey(BeanDefinition<T> definition, Qualifier<T> qualifier) {
      this(definition.getBeanType(), qualifier, definition.getTypeParameters());
    }

    BeanKey(Class<T> beanType, Qualifier<T> qualifier, @Nullable Class... typeArguments) {
      this.beanType = beanType;
      this.qualifier = qualifier;
      this.typeArguments = typeArguments;
      this.hashCode = Objects.hash(beanType, qualifier) * 31 + Arrays.hashCode(this.typeArguments);
    }

    @Override
    public int length() {
      return toString().length();
    }

    @Override
    public char charAt(int index) {
      return toString().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
      return toString().subSequence(start, end);
    }

    @Override
    public String toString() {
      return (qualifier != null ? qualifier.toString() + " " : "") + beanType.getName();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      BeanKey<?> beanKey = (BeanKey<?>) o;
      return beanType.equals(beanKey.beanType)
          && Objects.equals(qualifier, beanKey.qualifier)
          && Arrays.equals(typeArguments, beanKey.typeArguments);
    }

    @Override
    public int hashCode() {
      return hashCode;
    }

    @Override
    public String getName() {
      if (qualifier instanceof Named) {
        return ((Named) qualifier).getName();
      }
      return Primary.SIMPLE_NAME;
    }
  }
}
