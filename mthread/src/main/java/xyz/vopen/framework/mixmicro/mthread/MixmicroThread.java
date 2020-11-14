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
package xyz.vopen.framework.mixmicro.mthread;

/**
 * {@link MixmicroThread} A lightweight thread.
 *
 * <p>There are two ways to create a new mthread: either subclass the {@code MixmicroThread} class
 * and override th {@code run} method,or pass the code to be executed in the mixmicroThread as the
 * {@code target} parameter to the constructor.All in all, the MixmicroThread API resembles the
 * {@link Thread} class in many ways.
 *
 * <p>A mixmicro runs inside a ForkJoinPool.
 *
 * <p>A Mixmicro can be serialized if it's not running and all involved classes and data types are
 * alse {@link java.io.Serializable}.
 *
 * <p>A new MixmicroThread occupies under 1024 bytes of memory (when using the default stack size,
 * and compressed OOPs are turned on, as they are by default).
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/12
 */
public class MixmicroThread {

}
