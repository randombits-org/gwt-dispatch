package net.customware.gwt.dispatch.server.guice;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jessewilson@google.com (Jesse Wilson)
 */
public class UniqueAnnotations {
  private UniqueAnnotations() {}
  private static final AtomicInteger nextUniqueValue = new AtomicInteger(1);

  /**
   * Returns an annotation instance that is not equal to any other annotation
   * instances, for use in creating distinct {@link com.google.inject.Key}s.
   */
  public static Annotation create() {
    return create(nextUniqueValue.getAndIncrement());
  }

  static Annotation create(final int value) {
    return new Internal() {
      public int value() {
        return value;
      }

      public Class<? extends Annotation> annotationType() {
        return Internal.class;
      }

      @Override public String toString() {
        return "@" + Internal.class.getName() + "(value=" + value + ")";
      }

      @Override public boolean equals(Object o) {
        return o instanceof Internal
            && ((Internal) o).value() == value();
      }

      @Override public int hashCode() {
        return (127 * "value".hashCode()) ^ value;
      }
    };
  }

  @Retention(RUNTIME) @BindingAnnotation
  @interface Internal {
    int value();
  }
}
