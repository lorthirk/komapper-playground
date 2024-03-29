package it.lorthirk.komapperplayground.entity

import org.komapper.annotation.KomapperAutoIncrement
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId

@KomapperEntity
data class Address(
    @KomapperId @KomapperAutoIncrement
    val id: Int = 0,

    val employeeId: Int,

    val street: String,
)
