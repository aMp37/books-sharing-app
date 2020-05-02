package com.example.bookshare.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UserTest {

    @Test
    fun set_wrong_email_return_blank_string() {
        val testedUser = User()

        testedUser.email = "12345653test"
        assertEquals("",testedUser.email)

        testedUser.email = "adam@"
        assertEquals("",testedUser.email)

        testedUser.email = "adam.com"
        assertEquals("",testedUser.email)

        testedUser.email = "user@.com"
        assertEquals("",testedUser.email)

        testedUser.email = "user.name@domain"
        assertEquals("",testedUser.email)

        testedUser.email = "user@domain."
        assertEquals("",testedUser.email)


    }

    @Test
    fun set_good_email_return_input_value() {
        val testedUser = User()

        testedUser.email = "user.name@domain.com"
        assertEquals("user.name@domain.com",testedUser.email)

        testedUser.email = "user@domain.com"
        assertEquals("user@domain.com",testedUser.email)
    }

    @Test
    fun set_wrong_password_return_blank_string() {
    }

    @Test
    fun set_good_password_return_input_value() {
        val testedUser = User()

        testedUser.password = "123@"
        assertEquals("123@",testedUser.password)
    }
}