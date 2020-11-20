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

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.naming.Name;
import xyz.vopen.framework.mixmicro.core.Named;
import xyz.vopen.framework.mixmicro.core.context.BeanContext;
import xyz.vopen.framework.mixmicro.core.context.BeanResolutionContext;
import xyz.vopen.framework.mixmicro.core.context.Qualifier;

/**
 * {@link BeanDefinition} Defines a bean definition and its requirements. A bean definition must
 * have a singled injectable constructor or a no-args constructor.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
public interface BeanDefinition<T> extends Name, BeanType<T> {

  /** Attributed used to store a dynamic bean name. */
  String NAMED_ATTRIBUTED = Named.class.getName();

  /** @return The scope of the bean. */
  Optional<Class<? extends Annotation>> getScope();

  /** @return Whether the scope is singleton. */
  boolean isSingleton();

  /** @return Is this definition provided by another bean */
  boolean isProvided();

  /** @return Whether the bean declared with */
  boolean isIterable();

  /** @return The produced bean type */
  Class<T> getBeanType();

  /** @return The type that declares this definition, null if not applicable. */
  Optional<Class<?>> getDeclaringType();

  /** @return All required components for this entity definition. */
  Collection<Class> getRequiredComponents();

  /** @return The class name */
  @Override
  String getName();

  /**
   * Inject the given bean with the context.
   *
   * @param context The context.
   * @param bean The bean
   * @return The injected bean.
   */
  T inject(BeanContext context, T bean);

  /**
   * Inject the given bean with the context.
   *
   * @param resolutionContext The resolution context
   * @param beanContext The context.
   * @param bean The bean
   * @return The injected bean.
   */
  T inject(BeanResolutionContext resolutionContext, BeanContext beanContext, T bean);

  /**
   * Whether this bean definition represents a proxy.
   *
   * @return True if it represents a proxy.
   */
  default boolean isProxy() {
    return this instanceof ProxyBeanDefinition;
  }

  /** @return Whether the bean definition is abstract. */
  default boolean isAbstract() {
    return Modifier.isAbstract(getBeanType().getModifiers());
  }

  /**
   * Resolve the declared qualifier for this bean.
   *
   * @return The qualifier or null if this isn't one.
   */
  default @Nullable Qualifier<T> getDeclaredQualifier() {
    return null;
  }

  /** @return Method that can be overridden to resolve a dynamic qualifier */
  default @Nullable Qualifier<T> resolveDynamicQualifier() {
    return null;
  }

  Class getTypeParameters();
}
