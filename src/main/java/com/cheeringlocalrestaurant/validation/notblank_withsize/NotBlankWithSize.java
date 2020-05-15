package com.cheeringlocalrestaurant.validation.notblank_withsize;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Repeatable(NotBlankWithSize.List.class)
@Constraint(validatedBy = {NotBlankWithSizeValidator.class})
public @interface NotBlankWithSize {

    String message() default "{validation.NotBlankWithSize.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int min() default 1;

    int max() default Integer.MAX_VALUE;

    @Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotBlankWithSize[] value();
    }
}
