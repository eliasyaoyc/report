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

/**
 * {@link Mixmicro} The entry point to the Mixmicro framework.
 *
 * <p>Mixmicro supports a model of development that draws clear boundaries between APIs,
 * Implementations of these APIs,Modules which configure these implementations,and finally
 * Application which consist of a collection of Modules. It is the Application,which typically
 * defines your {@code main()} method, that bootstraps the Mixmicro Injector using the {@link
 * Mixmicro} class, as in this example:
 *
 * <pre>
 *   public class FooApplication{
 *     public static void main(String[] args){
 *        Injector injector = Mixmicro.createInjector(
 *           new ModuleA(),
 *           new ModuleB(),
 *           ...
 *           new FooApplicationFlagsModule(args)
 *        );
 *
 *        // now just bootstrap the application and you're done.
 *        FooStarter starter = injector.getInstance(FootStart.class);
 *        starter.runApplication();
 *     }
 *   }
 * </pre>
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/12
 */
public final class Mixmicro {
  private Mixmicro() {}

  // =====================   Builder  =====================
  public MixmicroBuilder builder() {
    return new MixmicroBuilder();
  }

  public static class MixmicroBuilder {



    public Mixmicro build() {
      return new Mixmicro();
    }
  }
}
