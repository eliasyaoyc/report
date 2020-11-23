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
package xyz.vopen.framework.mixmicro.core.inject;


import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import xyz.vopen.framework.mixmicro.commons.reflect.ReflectionUtils;
import xyz.vopen.framework.mixmicro.commons.utils.ArrayUtils;

/**
 * {@link AnnotationMetadata} implemented at compile time by Mixmicro that allows the inspection of
 * annotation metadata and stereotypes (meta-annotations).
 *
 * <p>This interface exposes fast and efficient means to expose annotation data at runtime without
 * requiring reflective tricks to read the annotation metadata.
 *
 * <p>Uses of Mixmicro should in general avoid the methods of the {@link
 * java.lang.reflect.AnnotatedElement} interface and use this interface instead to obtain maximum
 * efficiency.
 *
 * <p>Core framework types such as {@link xyz.vopen.framework.mixmicro.core.inject.BeanDefinition}
 * and {@link ExecutableMethod}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2020/11/18
 */
public interface AnnotationMetadata extends AnnotationSource {

  /** A constant for representing empty metadata. */
  AnnotationMetadata EMPTY_METADATA = new EmptyAnnotationMetadata();

  /** The default value() member. */
  String VALUE_MEMBER = "value";

  /** The suffix used when saving compiled metadata to classes. */
  String CLASS_NAME_SUFFIX = "$$AnnotationMetadata";

  /**
   * Resolve all of the annotation names that feature the given stereotype.
   *
   * @param stereotype The annotation names.
   * @return A set of annotation names.
   */
  default @Nonnull
  List<String> getAnnotationNamesByStereotypes(@Nullable String stereotype) {
    return Collections.emptyList();
  }

  /** @return All the annotation names this metadata declares. */
  default @Nonnull
  Set<String> getAnnotationNames() {
    return Collections.emptySet();
  }

  /** @return All the declared annotation names this metadata declares */
  default @Nonnull Set<String> getDeclaredAnnotationNames() {
    return Collections.emptySet();
  }

  /**
   * Resolve all of the annotations names for the given stereotype that are declared annotations.
   *
   * @param stereotype The stereotype
   * @return The declared annotations
   */
  default @Nonnull List<String> getDeclaredAnnotationNamesByStereotype(
      @Nullable String stereotype) {
    return Collections.emptyList();
  }

  /**
   * Get all of the values for the given annotation and type of the underlying values.
   *
   * @param annotation The annotation name
   * @param valueType valueType
   * @param <T> Generic type
   * @return The {@link Optional}
   */
  default @Nonnull <T> Optional<T> getValues(
      @Nonnull String annotation, @Nonnull Class<T> valueType) {
    return Optional.empty();
  }

  /**
   * Get the value of the given annotation member.
   *
   * @param annotation The annotation class
   * @param member The annotation member
   * @param requiredType The required type
   * @param <T> The value
   * @return An {@link Optional} of the value
   */
  default <T> Optional<T> getValue(
      @Nonnull String annotation, @Nonnull String member, @Nonnull Class<T> requiredType) {
    Preconditions.checkNotNull(annotation, "annotation is empty.");
    Preconditions.checkNotNull(member, "member is empty.");
    Preconditions.checkNotNull(requiredType, "requiredType is empty.");

    //    return getValue(annotation, member, Argument.of(requiredType));
    return null;
  }

  /**
   * Gets all the annotation values by the given repeatable type.
   *
   * @param annotationType The annotation type
   * @param <T> The annotation type
   * @return A list of values
   */
  default @Nonnull <T extends Annotation> List<Optional<T>> getAnnotationValuesByType(
      @Nonnull Class<T> annotationType) {
    return Collections.emptyList();
  }

  /**
   * The value of the annotation as a Class.
   *
   * @param annotation The annotation
   * @return An {@link Optional} class
   */
  default Optional<Class> classValue(@Nonnull Class<? extends Annotation> annotation) {
    Preconditions.checkNotNull(annotation, "annotation is empty");
    return classValue(annotation, VALUE_MEMBER);
  }

  /**
   * The value of the annotation as a Class.
   *
   * @param annotation The annotation
   * @param member The annotation member
   * @return An {@link Optional} class
   */
  default Optional<Class> classValue(
      @Nonnull Class<? extends Annotation> annotation, @Nonnull String member) {
    Preconditions.checkNotNull(annotation, "annotation is empty");
    Preconditions.checkNotNull(member, "member is empty.");
    return classValue(annotation.getName(), member);
  }

  /**
   * The value of the annotation as a Class.
   *
   * @param annotation The annotation
   * @param member The annotation member
   * @return An {@link Optional} class
   */
  default Optional<Class> classValue(@Nonnull String annotation, @Nonnull String member) {
    Preconditions.checkNotNull(annotation, "annotation is empty");
    Preconditions.checkNotNull(member, "member is empty.");

    return getValue(annotation, member, Class.class);
  }

  /**
   * The value of the annotation as a Class.
   *
   * @param annotation The annotation
   * @param <T> The concrete class type.
   * @return An {@link Optional} class
   */
  default @Nonnull <T> Class<T>[] classValues(@Nonnull String annotation) {
    Preconditions.checkNotNull(annotation, "annotation is empty");
    return classValues(annotation, VALUE_MEMBER);
  }

  /**
   * The value of the annotation as a Class.
   *
   * @param annotation The annotation
   * @return An {@link Optional} class
   * @param <T> The type of the class
   */
  default @Nonnull <T> Class<T>[] classValues(@Nonnull Class<? extends Annotation> annotation) {
    Preconditions.checkNotNull(annotation, "annotation is empty");
    return classValues(annotation, VALUE_MEMBER);
  }

  /**
   * The value of the annotation as a Class.
   *
   * @param annotation The annotation
   * @param member The annotation member
   * @return An {@link Optional} class
   * @param <T> The type of the class
   */
  default @Nonnull <T> Class<T>[] classValues(
      @Nonnull Class<? extends Annotation> annotation, @Nonnull String member) {
    Preconditions.checkNotNull(annotation, "annotation is empty");
    Preconditions.checkNotNull(member, "member is empty.");

    return classValues(annotation.getName(), member);
  }

  /**
   * The value of the annotation as a Class.
   *
   * @param annotation The annotation
   * @param member The annotation member
   * @return An {@link Optional} class
   * @param <T> The type of the class
   */
  default @Nonnull <T> Class<T>[] classValues(@Nonnull String annotation, @Nonnull String member) {
    Preconditions.checkNotNull(annotation, "annotation is empty");
    Preconditions.checkNotNull(member, "member is empty.");

    return getValue(annotation, member, Class[].class).orElse(ReflectionUtils.EMPTY_CLASS_ARRAY);
  }

  /**
   * Checks whether this object has the given annotation stereotype on the object itself or
   * inherited from a parent.
   *
   * <p>An annotation stereotype is a meta annotation potentially applied to another annotation
   *
   * @param annotation The annotation
   * @return True if the annotation is present
   */
  default boolean hasStereotype(@Nullable String annotation) {
    return false;
  }
  /**
   * Checks whether this object has the given annotation stereotype on the object itself and not
   * inherited from a parent.
   *
   * <p>An annotation stereotype is a meta annotation potentially applied to another annotation.
   *
   * @param annotation The annotation.
   * @return True if the annotation is present.
   */
  default boolean hasDeclaredStereotype(@Nullable String annotation) {
    return false;
  }

  /**
   * Checks whether this object has the given stereotype directly declared on the object.
   *
   * @param stereotype The annotation
   * @return True if the annotation is present
   */
  default boolean hasDeclaredStereotype(@Nullable Class<? extends Annotation> stereotype) {
    if (stereotype == null) {
      Repeatable repeatable = stereotype.getAnnotation(Repeatable.class);
      if (repeatable != null) {
        return hasDeclaredStereotype(repeatable.value().getName());
      } else {
        return hasDeclaredStereotype(stereotype.getName());
      }
    }
    return false;
  }

  /**
   * Checks whether this object has any of the given stereotype directly declared on the object.
   *
   * @param annotations The annotations
   * @return True if any of the given stereotypes are present
   */
  default boolean hasDeclaredStereotype(@Nullable Class<? extends Annotation>... annotations) {
    if (ArrayUtils.isEmpty(annotations)) {
      return false;
    }
    for (Class<? extends Annotation> annotation : annotations) {
      if (hasDeclaredStereotype(annotation)) {
        return true;
      }
    }
    return false;
  }

  /** @return Whether the annotation metadata is empty. */
  default boolean isEmpty() {
    return this == AnnotationMetadata.EMPTY_METADATA;
  }
}
