package com.app.finsassist.configuration

import org.mapstruct.InjectionStrategy
import org.mapstruct.MapperConfig
import org.mapstruct.NullValueCheckStrategy

@MapperConfig(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
class MapperConfiguration {
}