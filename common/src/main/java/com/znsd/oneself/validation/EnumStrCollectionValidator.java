package com.znsd.oneself.validation;

import com.znsd.oneself.validation.constraints.EnumStrValidate;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @version 1.0
 * @Title: EnumStrCollectionValidator.java
 * @Description: EnumStrValidate集合类型的校验器实现类
 * @since 2021/1/27
 */
public class EnumStrCollectionValidator implements ConstraintValidator<EnumStrValidate, Collection<String>> {

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
    public boolean isValid(Collection<String> collection, ConstraintValidatorContext context) {
        if (!isValidateNull && CollectionUtils.isEmpty(collection)){
            return true;
        }
        for (String str : collection){
            if (!isValidateNull && str == null){
                continue;
            }
            if (!values.contains(str)){
                return false;
            }
        }
        return true;
    }
}
