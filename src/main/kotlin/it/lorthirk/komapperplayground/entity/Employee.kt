package it.lorthirk.komapperplayground.entity

import org.komapper.annotation.*
import java.time.LocalDateTime

@KomapperExperimentalAssociation
@KomapperEntity
@KomapperOneToMany(targetEntity = Address::class, navigator = "addresses")
data class Employee(
    @KomapperId @KomapperAutoIncrement
    val id: Int = 0,
    val name: String,
    @KomapperVersion
    val version: Int = 0,
    @KomapperCreatedAt
    val createdAt: LocalDateTime = LocalDateTime.MIN,
    @KomapperUpdatedAt
    val updatedAt: LocalDateTime = LocalDateTime.MIN,
)
