package com.lexx7.servicemessages.web.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameNotCurrentConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameNotCurrent {
    String message() default "Пользователь уже добавлен или используется в текущий момент";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
