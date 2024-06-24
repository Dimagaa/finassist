rootProject.name = "finassist"
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("mapstruct", "1.5.5.Final")
            version("jsonwebtoken", "0.12.5")
            library("springdoc-openapi","org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0" )
            library("liquibase-core", "org.liquibase:liquibase-core:4.28.0")
            library("jsonwebtoken-api", "io.jsonwebtoken", "jjwt-api").versionRef("jsonwebtoken")
            library("jsonwebtoken-impl", "io.jsonwebtoken", "jjwt-impl").versionRef("jsonwebtoken")
            library("jjwt-jackson", "io.jsonwebtoken", "jjwt-jackson").versionRef("jsonwebtoken")
            library("lombok", "org.projectlombok:lombok:1.18.32")
            library("mapstruct", "org.mapstruct", "mapstruct" ).versionRef("mapstruct")
            library("mapstruct-processor", "org.mapstruct", "mapstruct-processor").versionRef("mapstruct")
            library("lombok-mapstruct-binding", "org.projectlombok:lombok-mapstruct-binding:0.2.0")
            library("postgresql", "org.postgresql:postgresql:42.7.3")
            library("testcontainers-junit-jupiter", "org.testcontainers:junit-jupiter:1.19.8")
            library("testcontainers-postgresql", "org.testcontainers:postgresql:1.19.8")
            bundle("testcontainers", listOf("testcontainers-junit-jupiter", "testcontainers-postgresql"))
        }
    }
}
