package com.example.newsapp.view.activities

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class LoginActivityTest {
    @Test
    fun validateInput_emptyEmailname_returnFalse() {
        val result = validateNotEmpty(
            "",
            ""
        )
        assertThat(result, `is`(false))
    }

    @Test
    fun validateInput_emptyPassword_returnFalse() {
        val result = validateNotEmpty(
            "hi",
            ""
        )
        assertThat(result, `is`(false))
    }

    @Test
    fun validateInput_correctBoth_returnTrue() {
        val result = validateNotEmpty(
            "hi",
            "hi"
        )
        assertThat(result, `is`(true))
    }

    @Test
    fun validateInput_userExist_returnTrue() {
        val result = validateUserExist(
            "hi"
        )
        assertThat(result, `is`(true))
    }

    @Test
    fun validateInput_passwordValid_returnTrue() {
        val result = validatePassword(
            "hi",
            "hi"
        )
        assertThat(result, `is`(true))
    }
    @Test
    fun validateInput_passwordValid_returnFalse() {
        val result = validatePassword(
            "hi",
            "hooi"
        )
        assertThat(result, `is`(false))
    }
}