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
package xyz.vopen.framework.mixmicro.core.event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javax.annotation.Nonnull;

/**
 * {@link ApplicationEventPublisher} interface for classes that publish events received by {@link
 * ApplicationEventListener} instance.
 *
 * <p>Note that this interface is designed for application level, non-blocking synchronous events
 * for decoupling code and is not a replacement for a message system.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/14
 */
public interface ApplicationEventPublisher {
  String EXCEPTION_MESSAGE = "";

  /**
   * Publish the given event. The event will be published synchronously and only return once all
   * listeners have consumed the event.
   *
   * @param event The event to publish.
   */
  void publishEvent(@Nonnull Object event);

  /**
   * Publish the given event. The event will be published asynchronously. A future is returned that
   * can be used to check whether the event completed successfully or not.
   *
   * @param event The event to publish.
   * @return A future that completes when the event is published.
   */
  default Future<Void> publishEventAsync(Object event) {
    CompletableFuture<Void> future = new CompletableFuture<>();
    future.completeExceptionally(new UnsupportedOperationException(EXCEPTION_MESSAGE));
    return future;
  }
}
