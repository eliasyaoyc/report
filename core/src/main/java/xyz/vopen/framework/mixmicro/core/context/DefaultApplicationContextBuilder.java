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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.commons.cli.CommandLine;
import xyz.vopen.framework.mixmicro.commons.utils.StringUtils;
import xyz.vopen.framework.mixmicro.core.context.env.ClassPathResourceLoader;
import xyz.vopen.framework.mixmicro.core.context.env.CommandLinePropertySource;
import xyz.vopen.framework.mixmicro.core.context.env.Environment;
import xyz.vopen.framework.mixmicro.core.context.env.PropertySource;
import xyz.vopen.framework.mixmicro.core.context.env.SystemPropertiesPropertySource;

/**
 * {@link DefaultApplicationContextBuilder} Builder for {@link ApplicationContext} that used to
 * built instance.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public class DefaultApplicationContextBuilder
    implements ApplicationContextBuilder, ApplicationContextConfiguration {
  private List<Object> singletons = Lists.newArrayList();
  private List<String> environments = Lists.newArrayList();
  private List<String> packages = Lists.newArrayList();
  private Map<String, Object> properties = Maps.newLinkedHashMap();
  private List<PropertySource> propertySources = Lists.newArrayList();
  private Collection<String> configurationIncludes = Sets.newHashSet();
  private Collection<String> configurationExcludes = Sets.newHashSet();
  private Boolean deduceEnvironments = null;
  private ClassLoader classLoader = getClass().getClassLoader();
  private boolean envPropertySource = true;
  private List<String> envVarIncludes = Lists.newArrayList();
  private List<String> envVarExcludes = Lists.newArrayList();
  private String[] args = new String[0];
  private Set<Class<? extends Annotation>> eagerInitAnnotated = Sets.newHashSetWithExpectedSize(3);
  private String[] overrideConfigLocations;

  protected DefaultApplicationContextBuilder() {}

  @Nonnull
  @Override
  public ApplicationContextBuilder eagerInitAnnotated(Class<? extends Annotation>... annotations) {
    if (annotations != null) {
      eagerInitAnnotated.addAll(Arrays.asList(annotations));
    }
    return this;
  }

  @Nonnull
  @Override
  public ApplicationContextBuilder overrideConfigLocations(String... configLocations) {
    overrideConfigLocations = configLocations;
    return this;
  }

  @Override
  public @Nullable List<String> getOverrideConfigLocations() {
    return overrideConfigLocations == null ? null : Arrays.asList(overrideConfigLocations);
  }

  @Override
  public Set<Class<? extends Annotation>> getEagerInitAnnotated() {
    return Collections.unmodifiableSet(eagerInitAnnotated);
  }

  @Override
  public @Nonnull ApplicationContextBuilder singletons(Object... beans) {
    if (beans != null) {
      singletons.addAll(Arrays.asList(beans));
    }
    return this;
  }

  @Override
  public @Nonnull ClassPathResourceLoader getResourceLoader() {
    if (classLoader != null) {
      return ClassPathResourceLoader.defaultLoader(classLoader);
    } else {
      return ClassPathResourceLoader.defaultLoader(getClass().getClassLoader());
    }
  }

  @Nonnull
  @Override
  public ClassLoader getClassLoader() {
    return this.classLoader;
  }

  @Override
  public @Nonnull ApplicationContextBuilder deduceEnvironment(
      @Nullable Boolean deduceEnvironments) {
    this.deduceEnvironments = deduceEnvironments;
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder environments(@Nullable String... environments) {
    if (environments != null) {
      this.environments.addAll(Arrays.asList(environments));
    }
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder packages(@Nullable String... packages) {
    if (packages != null) {
      this.packages.addAll(Arrays.asList(packages));
    }
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder properties(@Nullable Map<String, Object> properties) {
    if (properties != null) {
      this.properties.putAll(properties);
    }
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder propertySources(
      @Nullable PropertySource... propertySources) {
    if (propertySources != null) {
      this.propertySources.addAll(Arrays.asList(propertySources));
    }
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder environmentPropertySource(
      boolean environmentPropertySource) {
    this.envPropertySource = environmentPropertySource;
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder environmentVariableIncludes(
      @Nullable String... environmentVariables) {
    if (environmentVariables != null) {
      this.envVarIncludes.addAll(Arrays.asList(environmentVariables));
    }
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder environmentVariableExcludes(
      @Nullable String... environmentVariables) {
    if (environmentVariables != null) {
      this.envVarExcludes.addAll(Arrays.asList(environmentVariables));
    }
    return this;
  }

  @Override
  public Optional<Boolean> getDeduceEnvironments() {
    return Optional.ofNullable(deduceEnvironments);
  }

  @Override
  public @Nonnull List<String> getEnvironments() {
    return environments;
  }

  @Override
  public boolean isEnvironmentPropertySource() {
    return envPropertySource;
  }

  @Override
  public @Nullable List<String> getEnvironmentVariableIncludes() {
    return envVarIncludes.isEmpty() ? null : envVarIncludes;
  }

  @Override
  public @Nullable List<String> getEnvironmentVariableExcludes() {
    return envVarExcludes.isEmpty() ? null : envVarExcludes;
  }

  @Override
  public @Nonnull ApplicationContextBuilder mainClass(Class mainClass) {
    if (mainClass != null) {
      if (this.classLoader == null) {
        this.classLoader = mainClass.getClassLoader();
      }
      String name = mainClass.getPackage().getName();
      if (StringUtils.isNotEmpty(name)) {
        packages(name);
      }
    }
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder classLoader(ClassLoader classLoader) {
    if (classLoader != null) {
      this.classLoader = classLoader;
    }
    return this;
  }

  @Override
  public @Nonnull ApplicationContextBuilder args(@Nullable String... args) {
    if (args != null) {
      this.args = args;
    }
    return this;
  }

  @Override
  @SuppressWarnings("MagicNumber")
  public @Nonnull ApplicationContext build() {
    ApplicationContext applicationContext = newApplicationContext();
    Environment environment = applicationContext.getEnvironment();
    if (!packages.isEmpty()) {
      for (String aPackage : packages) {
        environment.addPackage(aPackage);
      }
    }
    if (!properties.isEmpty()) {
      PropertySource contextProperties =
          PropertySource.of(
              PropertySource.CONTEXT, properties, SystemPropertiesPropertySource.POSITION + 100);
      environment.addPropertySource(contextProperties);
    }
    if (args.length > 0) {
      CommandLine commandLine = CommandLine.parse(args);
      environment.addPropertySource(new CommandLinePropertySource(commandLine));
    }
    if (!propertySources.isEmpty()) {
      for (PropertySource propertySource : propertySources) {
        environment.addPropertySource(propertySource);
      }
    }
    if (!singletons.isEmpty()) {
      for (Object singleton : singletons) {
        applicationContext.registerSingleton(singleton);
      }
    }

    if (!configurationIncludes.isEmpty()) {
      environment.addConfigurationIncludes(
          configurationIncludes.toArray(StringUtils.EMPTY_STRING_ARRAY));
    }
    if (!configurationExcludes.isEmpty()) {
      environment.addConfigurationExcludes(
          configurationExcludes.toArray(StringUtils.EMPTY_STRING_ARRAY));
    }

    return applicationContext;
  }

  /**
   * Creates the {@link ApplicationContext} instance.
   *
   * @return The application context
   * @since 2.0
   */
  @Nonnull
  protected ApplicationContext newApplicationContext() {
    return new DefaultApplicationContext(this);
  }

  /**
   * Allow customizing the configurations that will be loaded.
   *
   * @param configurations The configurations to include
   * @return This application
   */
  @Override
  public @Nonnull ApplicationContextBuilder include(@Nullable String... configurations) {
    if (configurations != null) {
      this.configurationIncludes.addAll(Arrays.asList(configurations));
    }
    return this;
  }

  /**
   * Allow customizing the configurations that will be loaded.
   *
   * @param configurations The configurations to exclude
   * @return This application
   */
  @Override
  public @Nonnull ApplicationContextBuilder exclude(@Nullable String... configurations) {
    if (configurations != null) {
      this.configurationExcludes.addAll(Arrays.asList(configurations));
    }
    return this;
  }
}
