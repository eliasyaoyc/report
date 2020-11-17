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
package xyz.vopen.framework.mixmicro.core;

import java.io.Closeable;
import javax.annotation.Nonnull;

/**
 * An {@link LifeCycle} interface providing a start method and extending {@link Closeable} which
 * provides a close() method for termination.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/16
 */
public interface LifeCycle<T extends LifeCycle> extends Closeable, AutoCloseable {

  /** @return Whether the component is running. */
  boolean isRunning();

  /**
   * Starts the lifecycle component.
   *
   * @return This lifecycle component.
   */
  default @Nonnull T start() {
    return (T) this;
  }

  /**
   * Stops the lifecycle component.
   *
   * @return This lifecycle component.
   */
  default @Nonnull T stop() {
    return (T) this;
  }

  /** Delegates to {@link #stop()}. */
  @Override
  default void close() {
    stop();
  }

  /**
   * Refresh the current life cycle object. Effectively this calls {@link #stop()} followed by
   * {@link #start()}/
   *
   * @return This lifecycle component.
   */
  default @Nonnull T refresh() {
    stop();
    start();
    return (T) this;
  }
}
