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

import xyz.vopen.framework.mixmicro.core.context.BeanContext;

/**
 * {@link BeanDefinitionReference}
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
    //    AnnotationMetadata am = getAnnotationMetadata();
    //    return am.hasDeclaredStereotype(Singleton.class)
    //        || am.classValue(DefaultScope.class).map(t -> t == Singleton.class).orElse(false);
    return false;
  }

  /** @return Is this bean a configuration properties. */
  default boolean isConfigurationProperties() {
    //    return getAnnotationMetadata().hasDeclaredStereotype(ConfigurationReader.class);
    return true;
  }
}