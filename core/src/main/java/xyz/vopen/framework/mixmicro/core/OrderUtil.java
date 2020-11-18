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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * {@link OrderUtil}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/18
 */
public abstract class OrderUtil {
  /** Provide a comparator for collections. */
  public static final Comparator<Object> COMPARATOR =
      (o1, o2) -> {
        int order1 = getOrder(o1);
        int order2 = getOrder(o2);
        return Integer.compare(order1, order2);
      };

  /** Provide a comparator, in reversed order, for collections. */
  public static final Comparator<Object> REVERSE_COMPARATOR = Collections.reverseOrder(COMPARATOR);

  /**
   * Sort the given list.
   *
   * @param list The list to sort
   */
  public static void sort(List<?> list) {
    list.sort(COMPARATOR);
  }

  /**
   * Sort the given list.
   *
   * @param list The list to sort
   * @param <T> The stream generic type
   * @return The sorted stream
   */
  public static <T> Stream<T> sort(Stream<T> list) {
    return list.sorted(COMPARATOR);
  }

  /**
   * Sort the given list.
   *
   * @param list The list to sort
   */
  public static void reverseSort(List<?> list) {
    list.sort(REVERSE_COMPARATOR);
  }

  /**
   * Sort the given array in reverse order.
   *
   * @param array The array to sort
   */
  public static void reverseSort(Object[] array) {
    Arrays.sort(array, REVERSE_COMPARATOR);
  }

  /**
   * Sort the given array.
   *
   * @param objects The array to sort
   */
  public static void sort(Ordered... objects) {
    Arrays.sort(objects, COMPARATOR);
  }

  /**
   * Sort the given array.
   *
   * @param objects The array to sort
   */
  public static void sort(Object[] objects) {
    Arrays.sort(objects, COMPARATOR);
  }

  private static int getOrder(Object o) {
    if (o instanceof Ordered) {
      return getOrder((Ordered) o);
    }
    return Ordered.LOWEST_PRECEDENCE;
  }

  private static int getOrder(Ordered o) {
    return o.getOrder();
  }
}
