package com.znsd.oneself.validation;


import com.znsd.oneself.validation.constraints.EnumStrValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Title: EnumValidator.java
 * @Description: EnumStrValidate String类型的校验器实现类
 * @since 2021/1/27
 */
public class EnumStrValidator implements ConstraintValidator<EnumStrValidate, String> {

    private List<String> values = new ArrayList<>();

    private boolean isValidateNull;

    @Override
    public void initialize(EnumStrValidate annotation) {
        for (Enum<? extends EnumValidate<String>> e : annotation.value().getEnumConstants()){
            values.add(((EnumValidate<String>)e).getValue());
        }
        isValidateNull = annotation.isValidateNull();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        if (!isValidateNull && str == null){
            //空值不校验，直接过
            return true;
        }
        return values.contains(str);
    }

}
