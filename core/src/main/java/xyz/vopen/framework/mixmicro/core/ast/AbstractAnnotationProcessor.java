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
package xyz.vopen.framework.mixmicro.core.ast;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import xyz.vopen.framework.mixmicro.commons.kits.CollectionKit;

/**
 * {@link AbstractAnnotationProcessor}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/24
 */
abstract class AbstractAnnotationProcessor extends AbstractProcessor {
  private static final String JAVAX_INJECT_ANNOTATIONS = "javax.inject.*";
  private static final String MIXMICRO_ANNOTATIONS = "xyz.vopen.framework.mixmicro.*";

  private Messager messager;
  private Elements elementUtils;
  private Filer filer;
  private Types typeUtils;

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> types = CollectionKit.setOf(JAVAX_INJECT_ANNOTATIONS, MIXMICRO_ANNOTATIONS);
    return types;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    SourceVersion sourceVersion = SourceVersion.latestSupported();
    if (sourceVersion.ordinal() <= SourceVersion.RELEASE_8.ordinal()) {
      return SourceVersion.RELEASE_8;
    }
    return sourceVersion;
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    this.messager = processingEnv.getMessager();
    this.elementUtils = processingEnv.getElementUtils();
    this.filer = processingEnv.getFiler();
    this.typeUtils = processingEnv.getTypeUtils();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    return false;
  }

  protected void error(String msg) {
    messager.printMessage(Kind.ERROR, msg);
  }

  protected void error(String msg, Element e) {
    messager.printMessage(Kind.ERROR, msg, e);
  }

  protected void warn(String msg) {
    messager.printMessage(Kind.WARNING, msg);
  }

  protected void warn(String msg, Element e) {
    messager.printMessage(Kind.WARNING, msg, e);
  }
}
