package yyc.open.framework.microants.components.kit.common.release;

import java.io.Closeable;

/**
 * {@link Releasable} Specialization of {@link AutoCloseable} that may only throw an {@link Exception}.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/2
 */
public interface Releasable extends Closeable {
}
