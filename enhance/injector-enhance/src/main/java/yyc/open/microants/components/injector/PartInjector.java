package yyc.open.microants.components.injector;

import yyc.open.microants.components.injector.spi.Element;

import java.util.List;

/**
 * {@link PartInjector} A partially-initialized injector. See {@link InjectorBuilder}, which uses this to build a tree
 * of injectors in batch.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
class PartInjector {
    private final List<Element> elements;
    private final InjectorImpl injector;

    private PartInjector(List<Element> elements, InjectorImpl injector) {
        this.elements = elements;
        this.injector = injector;
    }
}
