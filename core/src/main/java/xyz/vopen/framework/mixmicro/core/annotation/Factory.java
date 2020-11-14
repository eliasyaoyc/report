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
package xyz.vopen.framework.mixmicro.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Singleton;

/**
 * {@link Factory}
 *
 * <p>A factory is a {@link Singleton} that produces one or many other bean implementations.
 *
 * <p>Each produced bean is defined by method that is annotated with {@link Bean}
 *
 * <pre class="code">
 * &#064;Factory
 * public class MyFactory {
 *
 *     &#064;Bean
 *     public MyBean myBean() {
 *         // create the bean
 *     }
 * }</pre>
 *
 * <p>Methods defined within the body of the class that are annotated with {@link Bean} will be
 * exposed as beans.
 *
 * <p>You can use a {@link javax.inject.Scope} annotation to control the scope the bean is exposed
 * within. For example for a singleton instance you can annotation the method with {@link
 * Singleton}.
 *
 * <p>Methods annotated with {@link Bean} can accept arguments and Micronaut will attempt to inject
 * those arguments from existing beans or values. For example:
 *
 * <pre class="code">
 * &#064;Factory
 * public class MyFactory {
 *
 *     &#064;Bean
 *     public MyBean myBean(&#064;Value("foo.bar") String myValue) {
 *         // create the bean
 *     }
 * }</pre>
 *
 * @see Bean
 * @see Configuration
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
@Singleton
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Factory {}
