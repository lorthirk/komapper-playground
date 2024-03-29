package it.lorthirk.komapperplayground

import it.lorthirk.komapperplayground.entity.*
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.r2dbc.R2dbcDatabase

suspend fun main() {
    // (1) create a database instance
    val database = R2dbcDatabase("r2dbc:h2:mem:///quickstart;DB_CLOSE_DELAY=-1")

    try {
        // (2) start transaction
        database.withTransaction {

            // (3) get an entity metamodel
            val e = Meta.employee
            val a = Meta.address

            // (4) create schema
            database.runQuery {
                QueryDsl.create(e)
            }

            database.runQuery {
                QueryDsl.create(a)
            }

            // (5) insert multiple employees at once
            database.runQuery {
                QueryDsl.insert(e).multiple(Employee(name = "AAA"), Employee(name = "BBB"))
            }

            val employees = database.runQuery {
                QueryDsl.from(e).orderBy(e.id)
            }

            database.runQuery {
                QueryDsl.insert(a).multiple(Address(street = "Via San Francesco", employeeId = employees[0].id))
            }

            // (6) select all

            val addresses = database.runQuery {
                QueryDsl.from(a).orderBy(a.id)
            }

            val employeeWithAddresses = database.runQuery {
                QueryDsl.from(e)
                    .innerJoin(a) {
                        e.id eq a.employeeId
                    }.includeAll()
            }

            val employeeWithJoin = database.runQuery {
                QueryDsl.from(e)
                    .innerJoin(a) {
                        e.id eq a.employeeId
                    }.include(a)
            }

            // (7) print all results
            for ((i, employee) in employees.withIndex()) {
                println("RESULT $i: $employee")
            }

            for ((i, address) in addresses.withIndex()) {
                println("RESULT $i: $address")
            }

            println(employees[0].addresses(employeeWithAddresses))

            throw RuntimeException()
        }
    } catch (ex: RuntimeException){ println("rollback")}
}
