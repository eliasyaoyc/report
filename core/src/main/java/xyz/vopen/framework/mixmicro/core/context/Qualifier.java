package xyz.vopen.framework.mixmicro.core.context;

/**
 * Used to qualify which bean to select in the case of multiple possible options.
 *
 * <p>
 *
 * <p>NOTE: When implementing a custom Qualifier you MUST implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)} so that the qualifier can be used in comparisons and equality
 * checks
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/24
 */
public interface Qualifier<T> {

}
