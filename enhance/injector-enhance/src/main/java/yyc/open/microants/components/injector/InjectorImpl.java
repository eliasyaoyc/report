package yyc.open.microants.components.injector;

/**
 * {@link InjectorImpl}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
class InjectorImpl implements Injector, Lookups {
    final State state;
    boolean readOnly;

    InjectorImpl(State state) {
        this.state = state;
    }
}
