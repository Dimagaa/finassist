package com.app.finsassist.controller

import com.app.finsassist.dto.user.UserCreateRequest
import com.app.finsassist.dto.user.UserDto
import com.app.finsassist.util.BOB_USER
import com.app.finsassist.util.DELETE_USERS_SQL
import com.app.finsassist.util.INSERT_USERS_SQL
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.commons.lang3.builder.EqualsBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils

private const val BOB_EMAIL = "bob@test.test"
private const val PASSWORD = "password"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    @Autowired
    private lateinit var applicationContext: WebApplicationContext

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    lateinit var mockMvc: MockMvc


    @BeforeAll
    fun beforeAll() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    @Test
    @WithUserDetails(BOB_EMAIL)
    @Sql(
        scripts = [
            DELETE_USERS_SQL,
            INSERT_USERS_SQL
        ],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    fun get_authenticatedUser_shouldReturnUserDto() {
        //given
        val expected = UserDto(
            id = BOB_USER.id!!,
            email = BOB_USER.email!!,
            firstName = BOB_USER.firstName!!,
            lastName = BOB_USER.lastName!!
        )

        //when
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
            .response
            .let { objectMapper.readValue<UserDto>(it.contentAsByteArray) }

        //then
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun get_unauthorized_shouldReturn401() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun create_newUser_shouldReturnUserDto() {
        //given
        val expected = UserDto(
            id = BOB_USER.id!!,
            email = BOB_USER.email!!,
            firstName = BOB_USER.firstName!!,
            lastName = BOB_USER.lastName!!
        )
        val payload = UserCreateRequest(
            email = BOB_USER.email!!,
            firstName = BOB_USER.firstName!!,
            lastName = BOB_USER.lastName!!,
            password = PASSWORD,
            repeatedPassword = PASSWORD
        ).let { objectMapper.writeValueAsString(it) }

        //when
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()
            .response
            .let { objectMapper.readValue<UserDto>(it.contentAsByteArray) }

        //then
        EqualsBuilder.reflectionEquals(expected, actual, "id")
    }

    @Test
    @Sql(
        scripts = [
            DELETE_USERS_SQL,
            INSERT_USERS_SQL
        ],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    fun create_userAlreadyExists_shouldReturnBadRequestStatus() {
        //given
        val payload = UserCreateRequest(
            email = BOB_USER.email!!,
            firstName = BOB_USER.firstName!!,
            lastName = BOB_USER.lastName!!,
            password = PASSWORD,
            repeatedPassword = PASSWORD
        ).let { objectMapper.writeValueAsString(it) }

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun create_notValidData_shouldReturnBadRequestStatus() {
        //given
        val payload = UserCreateRequest(
            email = BOB_USER.email!!,
            firstName = BOB_USER.firstName!!,
            lastName = BOB_USER.lastName!!,
            password = PASSWORD,
            repeatedPassword = StringUtils.EMPTY
        ).let { objectMapper.writeValueAsString(it) }

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    @WithUserDetails(BOB_EMAIL)
    @Sql(
        scripts = [
            DELETE_USERS_SQL,
            INSERT_USERS_SQL
        ],
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    fun update_userAuthenticated_shouldReturnUpdatedUserDto() {
        //given
        val email = "modified@test.com"
        val firstName = "modifiedName"
        val lastName = "modifiedLastName"
        val expected = UserDto(
            id = BOB_USER.id!!,
            email = email,
            firstName = firstName,
            lastName = lastName
        )
        val payload = UserCreateRequest(
            email = email,
            firstName = firstName,
            lastName = lastName,
            password = PASSWORD,
            repeatedPassword = PASSWORD
        ).let { objectMapper.writeValueAsString(it) }

        //when
        val actual = mockMvc.perform(
            MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
            .response
            .let { objectMapper.readValue<UserDto>(it.contentAsByteArray) }

        //then
        Assertions.assertEquals(expected, actual)
    }
}
