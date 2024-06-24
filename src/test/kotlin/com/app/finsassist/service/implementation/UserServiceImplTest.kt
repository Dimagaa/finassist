package com.app.finsassist.service.implementation

import com.app.finsassist.dto.user.UserCreateRequest
import com.app.finsassist.dto.user.UserDto
import com.app.finsassist.exception.UserCreationException
import com.app.finsassist.mapper.UserMapper
import com.app.finsassist.repository.UserRepository
import com.app.finsassist.util.BOB_USER
import java.util.Optional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userMapper: UserMapper

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userServiceImpl: UserServiceImpl

    @Test
    fun create_ifUserDoesNotExist_successful() {
        //given
        val expected: UserDto = mock()
        val request = UserCreateRequest(
            email = BOB_USER.email!!,
            password = BOB_USER.password!!,
            repeatedPassword = BOB_USER.password!!,
            firstName = BOB_USER.firstName!!,
            lastName = BOB_USER.lastName!!
        )
        given(userRepository.getByEmail(BOB_USER.email!!)).willReturn(Optional.empty())
        given(userRepository.save(BOB_USER)).willReturn(BOB_USER)
        given(userMapper.toModel(request)).willReturn(BOB_USER)
        given(userMapper.toDto(BOB_USER)).willReturn(expected)
        given(passwordEncoder.encode(BOB_USER.password)).willReturn(BOB_USER.password)

        //when
        val actual = userServiceImpl.create(request)

        //then
        Assertions.assertEquals(expected, actual)
        Mockito.verify(passwordEncoder).encode(BOB_USER.password)
    }

    @Test
    fun create_ifUserExist_throwUserCreationException() {
        //given
        val request = UserCreateRequest(
            email = BOB_USER.email!!,
            password = BOB_USER.password!!,
            repeatedPassword = BOB_USER.password!!,
            firstName = BOB_USER.firstName!!,
            lastName = BOB_USER.lastName!!
        )
        given(userRepository.getByEmail(BOB_USER.email!!)).willReturn(Optional.of(BOB_USER))

        //when
        //then
        Assertions.assertThrows(UserCreationException::class.java) { userServiceImpl.create(request) }
    }
}
