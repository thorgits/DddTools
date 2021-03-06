package annotation;


import casestudy.DefaultRoot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@AggregateRoot
public @interface AggregatePart {
    Class root() default DefaultRoot.class;
}