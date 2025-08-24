package com.danimo.common.infrastructure.annotations;

import org.hibernate.annotations.SQLJoinTableRestriction;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface PersistenceAdapter {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
