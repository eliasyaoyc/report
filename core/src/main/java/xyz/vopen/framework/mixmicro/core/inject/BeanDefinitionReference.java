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
package xyz.vopen.framework.mixmicro.core.inject;

import javax.inject.Singleton;
import xyz.vopen.framework.mixmicro.core.annotation.AnnotationMetadata;
import xyz.vopen.framework.mixmicro.core.annotation.AnnotationMetadataProvider;
import xyz.vopen.framework.mixmicro.core.context.BeanContext;
import xyz.vopen.framework.mixmicro.core.context.annotations.ConfigurationReader;
import xyz.vopen.framework.mixmicro.core.context.annotations.DefaultScope;

/**
 * {@link BeanDefinitionReference} provides a reference to a {@link BeanDefinition} thus allowing
 * for soft loading of bean definitions without loading the actual types.
 *
 * <p>This interface implements {@link AnnotationMetadataProvider} thus allowing bean metadata to be
 * introspected safely without loading the class or the annotations themselves.
 *
 * <p>The actual bean will be loaded upon calling the {@link #load()} method. Note that consumers of
 * this interface should call {@link #isPresent()} prior to loading to ensure an error dose not
 * occur.
 *
 * <p>This class can also decide whether to abort loading the definition by returning null.
 *
 * <p>This interface extends the {@link BeanType} interface which is shared between {@link
 * BeanDefinition} and this type. In addition a reference can be enabled or disabled (see {@link
 * BeanContextConditional#isEnabled(BeanContext)}).
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/16
 */
public interface BeanDefinitionReference<T> extends BeanType {

  /** @return The class name of the backing {@link BeanDefinition}. */
  String getBeanDefinitionName();

  /**
   * Loads the bean definition.
   *
   * @return The loaded component definition or null if it shouldn't be loaded.
   */
  BeanDefinition<T> load();

  /**
   * Loads the bean definition for the current {@link BeanContext}.
   *
   * @param context The bean context
   * @return The loaded bean definition or null if it shouldn't be loaded.
   */
  BeanDefinition<T> load(BeanContext context);

  /** @return Is the underlying bean type present on the classpath. */
  boolean isPresent();

  /** @return Whether this class is context scope. */
  boolean isContextScope();

  /** @return Is this bean a singleton. */
  default boolean isSingleton() {
    AnnotationMetadata am = getAnnotationMetadata();
    return am.hasDeclaredStereotype(Singleton.class)
        || am.classValue(DefaultScope.class).map(t -> t == Singleton.class).orElse(false);
  }

  /** @return Is this bean a configuration properties. */
  default boolean isConfigurationProperties() {
    return getAnnotationMetadata().hasDeclaredStereotype(ConfigurationReader.class);
  }
}
