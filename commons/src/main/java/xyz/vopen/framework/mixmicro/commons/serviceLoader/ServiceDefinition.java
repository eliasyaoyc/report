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
package xyz.vopen.framework.mixmicro.commons.serviceLoader;

import java.util.Optional;
import java.util.ServiceConfigurationError;
import java.util.function.Supplier;

/**
 * {@link ServiceDefinition}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/18
 */
public class ServiceDefinition<S> {
  private final String name;
  private final Optional<Class<S>> loadedClass;

  ServiceDefinition(String name, Optional<Class<S>> loadedClass) {
    this.name = name;
    this.loadedClass = loadedClass;
  }

  public String getName() {
    return name;
  }

  public boolean isPresent() {
    return loadedClass.isPresent();
  }

  public <X extends Throwable> S orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
    final Class<S> type = loadedClass.orElseThrow(exceptionSupplier);
    try {
      return type.getDeclaredConstructor().newInstance();
    } catch (Throwable e) {
      throw exceptionSupplier.get();
    }
  }

  public S load() {
    return loadedClass
        .map(
            aClass -> {
              try {
                return aClass.getDeclaredConstructor().newInstance();
              } catch (Throwable e) {
                throw new ServiceConfigurationError(
                    "Error loading service [" + aClass.getName() + "]" + e.getMessage(), e);
              }
            })
        .orElseThrow(
            () ->
                new ServiceConfigurationError(
                    "Call to load() when class '" + name + "'is not present"));
  }
}
