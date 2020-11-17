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
package xyz.vopen.framework.mixmicro.core.context.env;

import java.util.LinkedHashMap;
import java.util.Map;
import xyz.vopen.framework.mixmicro.core.Ordered;

/**
 * A {@link PropertySource} is a location to resolve property values from. The property keys are
 * available via the {@link #iterator()} method.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public interface PropertySource extends Iterable<String>, Ordered {

  /** The name of the property source with value supplied directly from context. */
  String CONTEXT = "context";

  /** @return The name of the property source */
  String getName();

  /**
   * Get a property value of the given key.
   *
   * @param key
   * @return The value.
   */
  Object get(String key);

  /** @return Whether the property source has upper case under score separated keys. */
  default PropertyConvention getConvention() {
    return PropertyConvention.JAVA_PROPERTIES;
  }

  /**
   * Create a {@link PropertySource} from the given map.
   *
   * @param name of the property source
   * @param map The map
   * @return The {@link PropertySource}.
   */
  static PropertySource of(String name, Map<String, Object> map) {
    return new MapPropertySource(name, map);
  }

  /**
   * Create a {@link PropertySource} from the given map
   *
   * @param name of the property source
   * @param map The map
   * @param propertyConvention The convention type of the property source
   * @return The {@link PropertySource}.
   */
  static PropertySource of(
      String name, Map<String, Object> map, PropertyConvention propertyConvention) {
    return new MapPropertySource(name, map) {
      @Override
      public PropertyConvention getConvention() {
        return propertyConvention;
      }
    };
  }

  /**
   * Create a {@link PropertySource} from the given map.
   *
   * @param name of the property source
   * @param values The values as an array of alternating key/value entries
   * @return The {@link PropertySource}.
   */
  static PropertySource of(String name, Object... values) {
    return new MapPropertySource(name, mapOf(values));
  }

  /**
   * Create a {@link PropertySource} from the given map.
   *
   * @param name of the property source
   * @param map The map
   * @param priority The priority to order by.
   * @return The {@link PropertySource}.
   */
  static PropertySource of(String name, Map<String, Object> map, int priority) {
    return new MapPropertySource(name, map) {
      @Override
      public int getOrder() {
        return priority;
      }
    };
  }

  /**
   * Create a {@link java.util.LinkedHashMap} of configuration from an array of values.
   *
   * @param values The values.
   * @return
   */
  static Map<String, Object> mapOf(Object... values) {
    int len = values.length;
    if (len % 2 != 0) {
      throw new IllegalArgumentException("");
    }
    Map<String, Object> ret = new LinkedHashMap<>(len / 2);
    int i = 0;
    while (i < values.length - 1) {
      Object k = values[i++];
      if (k != null) {
        ret.put(k.toString(), values[i++]);
      }
    }
    return ret;
  }

  /** Property convention. */
  enum PropertyConvention {

    /** Upper case separated by under scores (environment variable style). */
    ENVIRONMENT_VARIABLE,

    /** Lower case separated by dots (java properties file style). */
    JAVA_PROPERTIES
  }
}
