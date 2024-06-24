package com.app.finsassist.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.BeanWrapperImpl

class PropertiesMatchValidator : ConstraintValidator<PropertiesMatch, Any> {
    private lateinit var propertyNames: Array<String>

    override fun initialize(constraintAnnotation: PropertiesMatch) {
        propertyNames = constraintAnnotation.properties
    }

    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val obj = BeanWrapperImpl(value)
        return propertyNames.map {
            obj.getPropertyValue(it)
        }.allEqual()
    }

    private fun List<*>.allEqual(): Boolean = all { it == first() }
}
