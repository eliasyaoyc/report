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

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * {@link MapPropertySource} implementation {@link PropertySource} that uses a map.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public class MapPropertySource implements PropertySource {
  private final String name;
  private final Map map;

  public MapPropertySource(String name, Map map) {
    this.name = name;
    this.map = map;
  }

  public String getName() {
    return name;
  }

  @Override
  public Object get(String key) {
    return map.get(key);
  }

  @Override
  public Iterator<String> iterator() {
    Iterator iterator = map.keySet().iterator();
    return new Iterator<String>() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public String next() {
        return iterator.next().toString();
      }
    };
  }

  /**
   * The backing map (unmodifiable).
   *
   * @return The backing map.
   */
  public Map<String, Object> asMap() {
    return Collections.unmodifiableMap(map);
  }

  /**
   * Create a new {@link MapPropertySource} from the given map.
   *
   * @param name The name of the property source
   * @param map The map
   * @return The mao property source
   */
  public static MapPropertySource of(String name, Map<String, Object> map) {
    return new MapPropertySource(name, map);
  }

  @Override
  public String toString() {
    return getName();
  }
}
