package com.lexx7.servicemessages.web.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqUsernameConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqUsername {
    String message() default "Логин уже содержится в базе, попробуйте ввести другой";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
