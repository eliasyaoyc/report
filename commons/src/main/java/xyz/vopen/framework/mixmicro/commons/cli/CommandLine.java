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
package xyz.vopen.framework.mixmicro.commons.cli;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * {@link CommandLine} Represent the parsed command line options
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/17
 */
public class CommandLine {
  private Properties systemProperties = new Properties();
  private LinkedHashMap<String, Object> undeclaredOptions = Maps.newLinkedHashMap();
  private LinkedHashMap<Option, Object> declaredOptions = Maps.newLinkedHashMap();
  private List<String> remainingArgs = Lists.newArrayList();
  private String[] rawArguments = new String[0];

  /**
   * Parses a new {@link CommandLine} instance that combines this instance with the given arguments.
   *
   * @param args The arguments.
   * @return The {@link CommandLine} instance.
   */
  public CommandLine parseNew(String[] args) {
    CommandLine cl = new CommandLine();
    cl.systemProperties.putAll(systemProperties);
    cl.undeclaredOptions.putAll(undeclaredOptions);
    cl.declaredOptions.putAll(declaredOptions);
    CommandLineParse commandLineParse = new CommandLineParse();
    return commandLineParse.parse(cl, args);
  }

  public Map<Option, Object> getOptions() {
    return declaredOptions;
  }

  public List<String> getRemainingArgs() {
    return remainingArgs;
  }

  public Properties getSystemProperties() {
    return systemProperties;
  }

  public boolean hasOption(String name) {
    return declaredOptions.containsKey(new Option(name, null))
        || undeclaredOptions.containsKey(name);
  }

  public Object optionValue(String name) {
    Option option = new Option(name, null);
    if (declaredOptions.containsKey(option)) {
      return declaredOptions.get(option);
    }
    if (undeclaredOptions.containsKey(name)) {
      return undeclaredOptions.get(name);
    }
    return null;
  }

  public String getRemainingArgsString() {
    return remainingArgsToString(" ", false);
  }

  public Map.Entry<String, Object> lastOption() {
    final Iterator<Entry<String, Object>> iterator = undeclaredOptions.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, Object> next = iterator.next();
      if (!iterator.hasNext()) {
        return next;
      }
    }
    return null;
  }

  public String getRemainingArgsWithOptionsString() {
    return remainingArgsToString(" ", true);
  }

  public Map<String, Object> getUndeclaredOptions() {
    return Collections.unmodifiableMap(undeclaredOptions);
  }

  public String[] getRawArguments() {
    return rawArguments;
  }

  void addDeclaredOption(Option option) {
    addDeclaredOption(option, Boolean.TRUE);
  }

  void addUndeclaredOption(String option) {
    undeclaredOptions.put(option, Boolean.TRUE);
  }

  void addUndeclaredOption(String option, Object value) {
    undeclaredOptions.put(option, value);
  }

  void addDeclaredOption(Option option, Object value) {
    declaredOptions.put(option, value);
  }

  void addRemainingArg(String arg) {
    remainingArgs.add(arg);
  }

  void addSystemProperty(String name, String value) {
    systemProperties.put(name, value);
  }

  void setRawArguments(String[] args) {
    this.rawArguments = args;
  }

  private String remainingArgsToString(String separator, boolean includeOptions) {
    StringBuilder sb = new StringBuilder();
    String sep = "";
    List<String> args = Lists.newArrayList(remainingArgs);
    if (includeOptions) {
      for (Entry<String, Object> entry : undeclaredOptions.entrySet()) {
        if (entry.getValue() instanceof Boolean && ((Boolean) entry.getValue())) {
          args.add('-' + entry.getKey());
        } else {
          args.add('-' + entry.getKey() + '=' + entry.getValue());
        }
      }
    }
    for (String arg : args) {
      sb.append(sep).append(arg);
      sep = separator;
    }
    return sb.toString();
  }

  public static Builder build() {
    return new CommandLineParse();
  }

  /**
   * Parse a new command line with the default options.
   *
   * @param args The arguments
   * @return
   */
  public static CommandLine parse(String... args) {
    if (args == null || args.length == 0) {
      return new CommandLine();
    }
    return new CommandLineParse().parse(args);
  }

  interface Builder<T extends Builder> {
    T addOption(String name, String description);

    CommandLine parseString(String string);

    CommandLine parse(String... args);
  }
  // =====================   Internal class  =====================

  /** Represent a command line option. */
  class Option {
    private String name;
    private String description;

    public Option(String name, String description) {
      Preconditions.checkNotNull(name, "Illegal option specified");
      this.name = name;
      this.description = description == null ? "" : description;
    }

    public String getName() {
      return name;
    }

    public String getDescription() {
      return description;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Option option = (Option) o;

      return name.equals(name);
    }

    @Override
    public int hashCode() {
      return name.hashCode();
    }
  }

  static class CommandLineParse implements Builder {
    CommandLine parse(CommandLine cl, String[] args) {
      return cl;
    }

    @Override
    public Builder addOption(String name, String description) {
      return null;
    }

    @Override
    public CommandLine parseString(String string) {
      return null;
    }

    @Override
    public CommandLine parse(String... args) {
      return null;
    }
  }
}
