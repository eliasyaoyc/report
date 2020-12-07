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
package xyz.vopen.framework.mixmicro.commons.kits;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * {@link CollectionKit} Utility methods for working with {@link java.util.Collection} types.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public class CollectionKit {
  /**
   * Is the given type an iterable or map type.
   *
   * @param type The type
   * @return True if it is iterable or map
   * @since 2.0.0
   */
  public static boolean isIterableOrMap(Class<?> type) {
    return type != null
        && (Iterable.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type));
  }

  /**
   * Null safe empty check.
   *
   * @param map The map
   * @return True if it is empty or null
   */
  public static boolean isEmpty(@Nullable Map map) {
    return map == null || map.isEmpty();
  }

  /**
   * Null safe not empty check.
   *
   * @param map The ,ap
   * @return True if it is not null and not empty
   */
  public static boolean isNotEmpty(@Nullable Map map) {
    return map != null && !map.isEmpty();
  }

  /**
   * Null safe empty check.
   *
   * @param collection The collection
   * @return True if it is empty or null
   */
  public static boolean isEmpty(@Nullable Collection collection) {
    return collection == null || collection.isEmpty();
  }

  /**
   * Null safe not empty check.
   *
   * @param collection The collection
   * @return True if it is not null and not empty
   */
  public static boolean isNotEmpty(@Nullable Collection collection) {
    return collection != null && !collection.isEmpty();
  }

  /**
   * Attempts to convert a collection to the given iterabable type.
   *
   * @param iterableType The iterable type
   * @param collection The collection
   * @param <T> The collection generic type
   * @return An {@link Optional} of the converted type
   */
  public static <T> Optional<Iterable<T>> convertCollection(
      Class<? extends Iterable<T>> iterableType, Collection<T> collection) {
    if (iterableType.isInstance(collection)) {
      return Optional.of(collection);
    }
    if (iterableType.equals(Set.class)) {
      return Optional.of(new HashSet<>(collection));
    }
    if (iterableType.equals(Queue.class)) {
      return Optional.of(new LinkedList<>(collection));
    }
    if (iterableType.equals(List.class)) {
      return Optional.of(new ArrayList<>(collection));
    }
    if (!iterableType.isInterface()) {
      try {
        Constructor<? extends Iterable<T>> constructor =
            iterableType.getConstructor(Collection.class);
        return Optional.of(constructor.newInstance(collection));
      } catch (Throwable e) {
        return Optional.empty();
      }
    }
    return Optional.empty();
  }

  /**
   * Create a {@link LinkedHashMap} from an array of values.
   *
   * @param values The values
   * @return The created map
   */
  public static Map mapOf(Object... values) {
    int len = values.length;
    if (len % 2 != 0) {
      throw new IllegalArgumentException(
          "Number of arguments should be an even number representing the keys and values");
    }

    Map answer = new LinkedHashMap(len / 2);
    int i = 0;
    while (i < values.length - 1) {
      answer.put(values[i++], values[i++]);
    }
    return answer;
  }

  /**
   * Convert an {@link Iterator} to a {@link Set}.
   *
   * @param iterator The iterator
   * @param <T> The type
   * @return The set
   */
  public static <T> Set<T> iteratorToSet(Iterator<T> iterator) {
    Set<T> set = new HashSet<>();
    while (iterator.hasNext()) {
      set.add(iterator.next());
    }
    return set;
  }

  /**
   * Convert an {@link Enumeration} to a {@link Set}.
   *
   * @param enumeration The iterator
   * @param <T> The type
   * @return The set
   */
  public static <T> Set<T> enumerationToSet(Enumeration<T> enumeration) {
    Set<T> set = new HashSet<>();
    while (enumeration.hasMoreElements()) {
      set.add(enumeration.nextElement());
    }
    return set;
  }

  /**
   * Convert an {@link Enumeration} to a {@link Iterable}.
   *
   * @param enumeration The iterator
   * @param <T> The type
   * @return The set
   */
  public static @Nonnull <T> Iterable<T> enumerationToIterable(
      @Nullable Enumeration<T> enumeration) {
    if (enumeration == null) {
      return Collections.emptyList();
    }

    return () ->
        new Iterator<T>() {
          @Override
          public boolean hasNext() {
            return enumeration.hasMoreElements();
          }

          @Override
          public T next() {
            return enumeration.nextElement();
          }
        };
  }

  /**
   * Creates a set of the given objects.
   *
   * @param objects The objects
   * @param <T> The type
   * @return The set
   */
  public static <T> Set<T> setOf(T... objects) {
    if (objects == null || objects.length == 0) {
      return new HashSet<>(0);
    }
    return new HashSet<>(Arrays.asList(objects));
  }

  /**
   * Converts an {@link Iterable} to a {@link List}.
   *
   * @param iterable The iterable
   * @param <T> The generic type
   * @return The list
   */
  public static <T> List<T> iterableToList(Iterable<T> iterable) {
    if (iterable == null) {
      return Collections.emptyList();
    }
    if (iterable instanceof List) {
      return (List<T>) iterable;
    }
    Iterator<T> i = iterable.iterator();
    if (i.hasNext()) {
      List<T> list = new ArrayList<>();
      while (i.hasNext()) {
        list.add(i.next());
      }
      return list;
    }
    return Collections.emptyList();
  }

  /**
   * Converts an {@link Iterable} to a {@link Set}.
   *
   * @param iterable The iterable
   * @param <T> The generic type
   * @return The set
   */
  public static <T> Set<T> iterableToSet(Iterable<T> iterable) {
    if (iterable == null) {
      return Collections.emptySet();
    }
    if (iterable instanceof Set) {
      return (Set<T>) iterable;
    }
    Iterator<T> i = iterable.iterator();
    if (i.hasNext()) {
      Set<T> list = new HashSet<>();
      while (i.hasNext()) {
        list.add(i.next());
      }
      return list;
    }
    return Collections.emptySet();
  }

  /**
   * Null safe version of {@link Collections#unmodifiableList(List)}.
   *
   * @param list The list
   * @param <T> The generic type
   * @return A non-null unmodifiable list
   */
  public static @Nonnull
  <T> List<T> unmodifiableList(@Nullable List<T> list) {
    if (isEmpty(list)) {
      return Collections.emptyList();
    }
    return Collections.unmodifiableList(list);
  }

  /**
   * Returns the last element of a collection.
   *
   * @param collection The collection
   * @param <T> The generic type
   * @return The last element of a collection or null
   */
  public static @Nullable <T> T last(@Nonnull Collection<T> collection) {
    if (collection instanceof List) {
      List<T> list = (List<T>) collection;
      final int s = list.size();
      if (s > 0) {
        return list.get(s - 1);
      } else {
        return null;
      }
    } else if (collection instanceof Deque) {
      final Iterator<T> i = ((Deque<T>) collection).descendingIterator();
      if (i.hasNext()) {
        return i.next();
      }
      return null;
    } else if (collection instanceof NavigableSet) {
      final Iterator<T> i = ((NavigableSet<T>) collection).descendingIterator();
      if (i.hasNext()) {
        return i.next();
      }
      return null;
    } else {
      T result = null;
      for (T t : collection) {
        result = t;
      }
      return result;
    }
  }
}
