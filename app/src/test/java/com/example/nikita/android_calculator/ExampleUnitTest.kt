package com.example.nikita.android_calculator

import org.junit.Test

import org.junit.Assert.*

import  org.mariuszgromada.math.mxparser.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun parser_correct() {
        val exp: Expression = Expression("1+2+3+4")
        assertEquals(exp.calculate(), 10.0, 0.01)
    }

    @Test
    fun parser_incorrect() {
        val exp: Expression = Expression("1+2*")
        assertEquals(exp.calculate(), Double.NaN, 0.01)

    }

    @Test
    fun parser_incorrect2() {
        val exp: Expression = Expression("1+2*asfsdfvfesd")
        assertEquals(exp.calculate(), Double.NaN, 0.01)
    }

    @Test
    fun parser_correct2() {
        val exp = Expression("2^3")
        assertEquals(exp.calculate(), 8.0, 0.01)
    }
    @Test
    fun parser_correct3() {
        val exp = Expression("sqrt(64)")
        assertEquals(exp.calculate(), 8.0, 0.01)
    }

    @Test
    fun parser_correct4() {
        val exp = Expression("sqrt(6dsfszvd)")
        assertEquals(true, (exp.calculate() as Number) == Double.NaN)
    }

}
