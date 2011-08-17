package todo.data.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeIdValidator.class)
public @interface EmployeeId {

	String message() default "{Must be valid employee number}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
