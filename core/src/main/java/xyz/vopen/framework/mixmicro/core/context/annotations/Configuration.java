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
package xyz.vopen.framework.mixmicro.core.context.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * {@link Configuration}
 *
 * <p>A configuration is a grouping of bean definitions under a package. A configuration can have
 * requirements applied to it with {@link Requires} such that the entire configuration only loads of
 * the requirements are met. For example consider the following {@code package-info.java} file:
 *
 * <pre class="code">
 * &#064;Configuration
 * &#064;Requires(classes = Cluster.class)
 * package io.micronaut.configuration.cassandra;
 *
 * import com.datastax.driver.core.Cluster;
 * import io.micronaut.context.annotation.Configuration;
 * import io.micronaut.context.annotation.Requires;
 * </pre>
 *
 * <p>In the example above the {@link Requires} annotation ensures all beans contained within the
 * package are loaded only if the {@code Cluster} class is present on the classpath.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
@Documented
@Target(ElementType.PACKAGE)
public @interface Configuration {}
