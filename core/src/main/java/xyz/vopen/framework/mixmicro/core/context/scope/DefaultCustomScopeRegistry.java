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
package xyz.vopen.framework.mixmicro.core.context.scope;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import xyz.vopen.framework.mixmicro.commons.reflect.ClassUtils;
import xyz.vopen.framework.mixmicro.core.context.BeanLocator;
import xyz.vopen.framework.mixmicro.core.context.Qualifier;
import xyz.vopen.framework.mixmicro.core.inject.qualifiers.Qualifiers;

/**
 * {@link DefaultCustomScopeRegistry} Default implementation of the {@link CustomScopeRegistry}
 * interface.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/18
 */
public class DefaultCustomScopeRegistry implements CustomScopeRegistry {
  private final BeanLocator beanLocator;
  private final Map<String, Optional<CustomScope>> scopes = new ConcurrentHashMap<>(2);
  private final ClassLoader classLoader;

  public DefaultCustomScopeRegistry(BeanLocator beanLocator, ClassLoader classLoader) {
    this.beanLocator = beanLocator;
    this.classLoader = classLoader;
  }

  @Override
  public Optional<CustomScope> findScope(Class<? extends Annotation> scopeAnnotation) {
    return scopes.computeIfAbsent(
        scopeAnnotation.getName(),
        s -> {
          final Qualifier qualifier = Qualifiers.byTypeArguments(scopeAnnotation);
          return beanLocator.findBean(CustomScope.class, qualifier);
        });
  }

  @Override
  public Optional<CustomScope> findScope(String scopeAnnotation) {
    return scopes.computeIfAbsent(
        scopeAnnotation,
        type -> {
          final Optional<Class> scopeClass = ClassUtils.forName(type, classLoader);
          if (scopeClass.isPresent()) {
            final Qualifier qualifier = Qualifiers.byTypeArguments(scopeClass.get());
            return beanLocator.findBean(CustomScope.class, qualifier);
          }
          return Optional.empty();
        });
  }
}
