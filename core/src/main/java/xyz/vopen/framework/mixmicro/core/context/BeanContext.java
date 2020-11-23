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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import xyz.vopen.framework.mixmicro.core.annotation.AnnotationMetadata;
import xyz.vopen.framework.mixmicro.api.annotations.Bean;
import xyz.vopen.framework.mixmicro.api.annotations.Context;
import xyz.vopen.framework.mixmicro.api.annotations.Indexes;
import xyz.vopen.framework.mixmicro.api.annotations.Parallel;
import xyz.vopen.framework.mixmicro.api.annotations.Primary;
import xyz.vopen.framework.mixmicro.api.annotations.Replaces;
import xyz.vopen.framework.mixmicro.core.context.env.ClassPathResourceLoader;
import xyz.vopen.framework.mixmicro.core.context.env.PropertyResolver;
import xyz.vopen.framework.mixmicro.core.context.env.ResourceLoader;
import xyz.vopen.framework.mixmicro.core.context.env.ValueResolver;
import xyz.vopen.framework.mixmicro.core.context.exceptions.BeanContextException;
import xyz.vopen.framework.mixmicro.core.context.processor.AnnotationProcessor;
import xyz.vopen.framework.mixmicro.core.context.processor.ExecutableMethodProcessor;
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
import xyz.vopen.framework.mixmicro.core.inject.BeanType;
import xyz.vopen.framework.mixmicro.core.inject.DisposableBeanDefinition;
import xyz.vopen.framework.mixmicro.core.inject.ProxyBeanDefinition;
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

  // =====================   LifeCycle  =====================
  protected final AtomicBoolean running = new AtomicBoolean(false);
  protected final AtomicBoolean initializing = new AtomicBoolean(false);
  protected final AtomicBoolean terminating = new AtomicBoolean(false);

  /** Store the scope of singleton object. */
  final Map<BeanKey, BeanRegistration /* Bean unique key, BeanRegistration */> singletonObjects =
      new ConcurrentHashMap<>(100);

  /** Store the scope of singleton object that in creation */
  final Map<BeanIdentifier, Object> singlesInCreation = new ConcurrentHashMap<>(5);

  /** Store the bean that need to proxied. */
  final Map<BeanKey, Provider<Object> /* Bean unique key, Provider */> scopedProxies =
      new ConcurrentHashMap<>(20);

  /**
   * Store all listener.
   *
   * @see ApplicationEventListener
   */
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
          AnnotationMetadataResolver.class,
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

  private void readAllBeanConfigurations() {
    Iterable<BeanConfiguration> beanConfigurations = resolveBeanConfigurations();
    for (BeanConfiguration beanConfiguration : beanConfigurations) {
      registerConfiguration(beanConfiguration);
    }
  }

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

    // all the reference of beans.
    for (BeanDefinitionReference beanDefinitionReference : beanDefinitionReferences) {
      // 1. exclude disabled bean.
      for (Entry<BeanConfiguration, Boolean> entry : configurationEnabledEntries) {
        if (entry.getKey().isWithin(beanDefinitionReference) && !entry.getValue()) {
          beanDefinitionsClasses.remove(beanDefinitionReference);
          continue reference;
        }
      }

      final AnnotationMetadata annotationMetadata = beanDefinitionReference.getAnnotationMetadata();
      Class[] indexes = annotationMetadata.classValues(INDEXES_TYPE);
      if (indexes.length > 0) {
        for (int i = 0; i < indexes.length; i++) {
          Class indexedType = indexes[i];
          resolveTypeIndex(indexedType).add(beanDefinitionReference);
        }
      } else {
        if (annotationMetadata.hasStereotype(ADAPTER_TYPE)) {
          final Class aClass =
              annotationMetadata
                  .classValue(ADAPTER_TYPE, AnnotationMetadata.VALUE_MEMBER)
                  .orElse(null);
          if (indexedTypes.contains(aClass)) {
            resolveTypeIndex(aClass).add(beanDefinitionReference);
          }
        }
      }

      // context scope.
      if (isEagerInit(beanDefinitionReference)) {
        contextScopeBeans.add(beanDefinitionReference);
        // parallel
      } else if (annotationMetadata.hasDeclaredStereotype(PARALLEL_TYPE)) {
        parallelBeans.add(beanDefinitionReference);
      }

      // the bean whether needed to process.
      if (beanDefinitionReference.requiresMethodProcessing()) {
        processedBeans.add(beanDefinitionReference);
      }
    }

    initializeEventListeners();
    initializeContext(contextScopeBeans, processedBeans, parallelBeans);
  }

  /** Initialize the event listeners. */
  protected void initializeEventListeners() {
    final Collection<BeanDefinition<BeanCreatedEventListener>> beanCreatedDefinitions =
        getBeanDefinitions(BeanCreatedEventListener.class);

    // process created event listener.
    final HashMap<Class, List<BeanCreatedEventListener>> beanCreatedListeners =
        Maps.newHashMapWithExpectedSize(beanCreatedDefinitions.size());

    beanCreatedListeners.put(
        AnnotationProcessor.class, Arrays.asList(new AnnotationProcessorListener()));
    for (BeanDefinition<BeanCreatedEventListener> beanCreatedDefinition : beanCreatedDefinitions) {
      try (BeanResolutionContext context = newResolutionContext(beanCreatedDefinition, null)) {
        final BeanCreatedEventListener listener;
        final Qualifier qualifier = beanCreatedDefinition.getDeclaredQualifier();
        if (beanCreatedDefinition.isSingleton()) {
          listener =
              createAndRegisterSingleton(
                  context, beanCreatedDefinition, beanCreatedDefinition.getBeanType(), qualifier);
        } else {
          listener =
              doCreateBean(
                  context, beanCreatedDefinition, BeanCreatedEventListener.class, qualifier);
        }
        beanCreatedDefinition.getTypeParameters();
        beanCreatedListeners
            .computeIfAbsent(null, aClass -> Lists.newArrayListWithCapacity(10))
            .add(listener);
      }
    }

    for (List<BeanCreatedEventListener> listenerList : beanCreatedListeners.values()) {
      OrderUtil.sort(listenerList);
    }

    // process initialized event listeners.
    final Collection<BeanDefinition<BeanInitializedEventListener>> beanInitializedDefinitions =
        getBeanDefinitions(BeanInitializedEventListener.class);

    final Map<Class, List<BeanInitializedEventListener>> beanInitializedListeners =
        Maps.newHashMapWithExpectedSize(beanInitializedDefinitions.size());

    for (BeanDefinition<BeanInitializedEventListener> beanInitializedDefinition :
        beanInitializedDefinitions) {
      try (BeanResolutionContext context = newResolutionContext(beanInitializedDefinition, null)) {
        final Qualifier qualifier = beanInitializedDefinition.getDeclaredQualifier();
        final BeanInitializedEventListener listener;
        if (beanInitializedDefinition.isSingleton()) {
          listener =
              createAndRegisterSingleton(
                  context,
                  beanInitializedDefinition,
                  beanInitializedDefinition.getBeanType(),
                  qualifier);
        } else {
          listener =
              doCreateBean(
                  context,
                  beanInitializedDefinition,
                  BeanInitializedEventListener.class,
                  qualifier);
        }

        beanInitializedListeners
            .computeIfAbsent(null, aClass -> Lists.newArrayListWithCapacity(10))
            .add(listener);
      }
    }

    for (List<BeanInitializedEventListener> listenerList : beanInitializedListeners.values()) {
      OrderUtil.sort(listenerList);
    }

    this.beanCreationEventListeners = beanCreatedListeners.entrySet();
    this.beanInitializedEventListeners = beanInitializedListeners.entrySet();
  }

  /**
   * Initialize the context with the given {@link Context} scope bean.
   *
   * @param contextScopeBeans The context scope beans that need to initialized with Bean context.
   * @param processedBeans The beans that require {@link ExecutableMethodProcessor} handling
   *     (scheduling related)
   * @param parallelBeans The parallel bean definitions that loaded in parallelism
   */
  protected void initializeContext(
      @Nonnull List<BeanDefinitionReference> contextScopeBeans,
      @Nonnull List<BeanDefinitionReference> processedBeans,
      @Nonnull List<BeanDefinitionReference> parallelBeans) {}

  private <T> T createAndRegisterSingleton(
      BeanResolutionContext resolutionContext,
      BeanDefinition<T> definition,
      Class<T> beanType,
      Qualifier<T> qualifier) {
    synchronized (singletonObjects) {
      return createAndRegisterSingletonInternal(resolutionContext, definition, beanType, qualifier);
    }
  }

  private <T> T createAndRegisterSingletonInternal(
      BeanResolutionContext resolutionContext,
      BeanDefinition<T> definition,
      Class<T> beanType,
      Qualifier<T> qualifier) {
    return null;
  }

  /**
   * @param resolutionContext The bean resolution context
   * @param definition The bean definition
   * @param beanType The bean type
   * @param qualifier The qualifier
   * @param args The argument values
   * @param <T> The bean generic type.
   * @return
   */
  protected @Nonnull <T> T doCreateBean(
      @Nonnull BeanResolutionContext resolutionContext,
      @Nonnull BeanDefinition<T> definition,
      @Nonnull Class<T> beanType,
      @Nullable Qualifier<T> qualifier,
      @Nullable Object... args) {
    return null;
  }

  /**
   * Resolves the {@link BeanDefinitionReference} class instance. Default implementation uses
   * ServiceLoader pattern.
   *
   * @return The bean definition classes.
   */
  private @Nonnull List<BeanDefinitionReference> resolveBeanDefinitionReferences() {
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

  private @Nonnull BeanResolutionContext newResolutionContext(
      BeanDefinition<?> beanDefinition, @Nullable BeanResolutionContext currentContext) {
    if (currentContext == null) {
      return new AbstractBeanResolutionContext(this, beanDefinition) {

        @Override
        public <T> void addInFlightBean(BeanIdentifier beanIdentifier, T instance) {
          singlesInCreation.put(beanIdentifier, instance);
        }

        @Override
        public void removeInFlightBean(BeanIdentifier beanIdentifier) {
          singlesInCreation.remove(beanIdentifier);
        }

        @Nullable
        @Override
        public <T> T getInFlightBean(BeanIdentifier beanIdentifier) {
          return (T) singlesInCreation.get(beanIdentifier);
        }
      };
    } else {
      return currentContext;
    }
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

  private @Nonnull Collection<BeanDefinitionReference> resolveTypeIndex(Class<?> indexedType) {
    return beanIndex.computeIfAbsent(
        indexedType,
        aClass -> {
          indexedTypes.add(indexedType);
          return Lists.newArrayListWithCapacity(20);
        });
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
   * @param beanType The bean type
   * @param filter A bean definition to filter out
   * @param <T> The bean generic type
   * @return The candidates
   */
  protected @Nonnull <T> Collection<BeanDefinition<T>> findBeanCandidates(
      @Nonnull Class<T> beanType, @Nullable BeanDefinition<?> filter) {
    return findBeanCandidates(null, beanType, filter, true);
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

    // first traverse component definition classes and loa candidates.
    Collection<BeanDefinitionReference> beanDefinitionsClasses;

    if (indexedTypes.contains(beanType)) {
      beanDefinitionsClasses = beanIndex.get(beanType);
      if (beanDefinitionsClasses == null) {
        beanDefinitionsClasses = Collections.emptyList();
      }
    } else {
      beanDefinitionsClasses = this.beanDefinitionsClasses;
    }

    if (!beanDefinitionsClasses.isEmpty()) {
      Stream<BeanDefinition<T>> candidateStream =
          beanDefinitionsClasses.stream()
              .filter(
                  reference -> {
                    Class<?> candidateType = reference.getBeanType();
                    final boolean isCandidate =
                        candidateType != null
                            && (beanType.isAssignableFrom(candidateType)
                                || beanType == candidateType);
                    return isCandidate && reference.isEnabled(this, resolutionContext);
                  })
              .map(
                  ref -> {
                    BeanDefinition<T> loadedBean;
                    try {
                      loadedBean = ref.load(this);
                    } catch (Throwable e) {
                      throw new BeanContextException(
                          "Error loading bean [" + ref.getName() + "]: " + e.getMessage(), e);
                    }
                    return loadedBean;
                  });

      if (filter != null) {
        candidateStream = candidateStream.filter(candidate -> !candidate.equals(filter));
      }

      Set<BeanDefinition<T>> candidates =
          candidateStream
              .filter(candidate -> candidate.isEnabled(this, resolutionContext))
              .collect(Collectors.toSet());

      if (!candidates.isEmpty()) {
        if (filterProxied) {
          filterProxiedTypes(candidates, true, false);
        }
        filterReplacedBeans(resolutionContext, candidates);
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("Resolved bean candidates {} for type: {}", candidates, beanType);
      }
      return candidates;
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("No bean candidates found for type: {}", beanType);
      }
      return Collections.emptyList();
    }
  }

  @SuppressWarnings("unchecked")
  private <T> Collection<BeanDefinition<T>> findBeanCandidatesInternal(
      BeanResolutionContext resolutionContext, Class<T> beanType) {
    Collection beanDefinitions = beanCandidateCache.get(beanType);
    if (beanDefinitions == null) {
      beanDefinitions = findBeanCandidates(resolutionContext, beanType, null, true);
      beanCandidateCache.put(beanType, beanDefinitions);
    }
    return beanDefinitions;
  }

  private <T> void filterProxiedTypes(
      Collection<BeanDefinition<T>> candidates, boolean filterProxied, boolean filterDelegates) {
    int count = candidates.size();
    Set<Class> proxiedTypes = Sets.newHashSetWithExpectedSize(count);
    Iterator<BeanDefinition<T>> i = candidates.iterator();
    Collection<BeanDefinition<T>> delegates =
        filterDelegates ? Lists.newArrayListWithCapacity(count) : Collections.emptyList();
    while (i.hasNext()) {
      BeanDefinition<T> candidadte = i.next();
      if (candidadte instanceof ProxyBeanDefinition) {

      } else if (candidadte instanceof BeanDefinitionDelegate) {

      }
    }

    if (filterDelegates) {
      candidates.addAll(delegates);
    }

    if (!proxiedTypes.isEmpty()) {
      candidates.removeIf(
          candidate -> {
            if (candidate instanceof BeanDefinitionDelegate) {
              return true;
            } else {
              return proxiedTypes.contains(candidate.getClass());
            }
          });
    }
  }

  private <T> void filterReplacedBeans(
      BeanResolutionContext resolutionContext, Collection<? extends BeanType<T>> candidates) {}

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
    Collection<BeanDefinition<T>> candidates = findBeanCandidatesInternal(null, beanType);
    return Collections.unmodifiableCollection(candidates);
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
