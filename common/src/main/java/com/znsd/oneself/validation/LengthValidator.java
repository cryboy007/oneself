package com.znsd.oneself.validation;


import com.znsd.oneself.util.StringUtil;
import com.znsd.oneself.validation.constraints.Length;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LengthValidator implements ConstraintValidator<Length, String> {

	private int max;

	@Override
	public void initialize(Length length) {
		this.max = length.max();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtil.isEmptyOrNull(value)) {
			return true;
		}
		return value.length() <= max;
	}

}
