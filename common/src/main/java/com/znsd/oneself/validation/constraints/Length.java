package com.znsd.oneself.validation.constraints;


import com.znsd.oneself.validation.LengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 入参长度限制
 * 
 *
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { LengthValidator.class })
public @interface Length {

	String message() default "{e3plus.validation.constraints.Length.message}";

	// 作用参考@Validated和@Valid的区别
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 
	 * @return size the element.length must be lower or equal to
	 */
	int max();

}
