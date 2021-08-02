package yyc.open.microants.components.injector;

import yyc.open.microants.components.injector.exceptions.ErrorsException;

/**
 * {@link Initializable} Holds a reference that requires initialization to be performed before it can be used.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
interface Initializable<T> {
    /**
     * Ensures the reference is initialized, when returns it.
     *
     * @param errors
     * @return
     * @throws ErrorsException
     */
    T get(Errors errors) throws ErrorsException;
}
