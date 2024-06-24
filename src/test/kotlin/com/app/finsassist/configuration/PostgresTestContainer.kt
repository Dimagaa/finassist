package com.app.finsassist.configuration

import org.testcontainers.containers.PostgreSQLContainer


private const val POSTGRES_IMAGE = "postgres:latest"

class PostgresTestContainer : PostgreSQLContainer<PostgresTestContainer>(POSTGRES_IMAGE) {
    companion object {
        val instance = PostgresTestContainer()
    }

    override fun start() {
        super.start()
        System.setProperty("TEST_DB_URL", instance.jdbcUrl)
        System.setProperty("TEST_DB_USERNAME", instance.username)
        System.setProperty("TEST_DB_PASSWORD", instance.password)
    }
}
