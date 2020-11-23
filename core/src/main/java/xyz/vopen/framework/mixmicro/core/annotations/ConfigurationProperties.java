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
package xyz.vopen.framework.mixmicro.core.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Singleton;
import xyz.vopen.framework.mixmicro.core.context.env.Environment;

/**
 * {@link ConfigurationProperties}
 * <p>Defines a singleton bean whose property values are resolved from a {@link PropertyResolver}.</p>
 * <p>
 * <p>The {@link PropertyResolver} is typically the Micronaut {@link Environment}.</p>
 * <p>
 * <p>The {@link #value()} of the annotation is used to indicate the prefix where the configuration properties are located.
 * The class can define properties or fields which will have the configuration properties to them at runtime.
 * </p>
 * <p>
 * <p>Complex nested properties are supported via classes that are public static inner classes and are also annotated
 * with {@link ConfigurationProperties}.</p>
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
@Singleton
@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
@ConfigurationReader
public @interface ConfigurationProperties {
  /**
   * The prefix to use when resolving properties. The prefix should be defined in kebab case. Example: my-app.foo.
   *
   * @return The prefix to use to resolve the properties
   */
  @AliasFor(annotation = ConfigurationReader.class, member = "value")
  String value();

  /**
   * <p>If the properties of this configuration can also be resolved from the CLI a prefix can be specified.</p>
   * <p>
   * <p>For example given a prefix value {code server-} and a property called {code port}, Micronaut will attempt
   * to resolve the value of --server-port when specified on the command line</p>
   *
   * @return The CLI prefix of the configuration. If a blank string is used then no prefix is appended
   */
  String[] cliPrefix() default {};

  /**
   * @return The names of the properties to include
   */
  @AliasFor(annotation = ConfigurationReader.class, member = "includes")
  String[] includes() default {};

  /**
   * @return The names of the properties to exclude
   */
  @AliasFor(annotation = ConfigurationReader.class, member = "excludes")
  String[] excludes() default {};

}
