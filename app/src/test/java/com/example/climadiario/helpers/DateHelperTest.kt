package com.example.climadiario.helpers

import org.junit.Test

import org.junit.Assert.*

class DateHelperTest {

    @Test fun nextDay1deenero() {
        assertEquals(listOf(1, 1), DateHelper.nextDay(31, 12))
    }

    @Test fun nextDay1demarzo() {
        assertEquals(listOf(1, 3), DateHelper.nextDay(28, 2))
    }

    @Test fun nextDay1dejunio() {
        assertEquals(listOf(1, 6), DateHelper.nextDay(31, 5))
    }

    @Test fun nextDay1dediciembre() {
        assertEquals(listOf(1, 12), DateHelper.nextDay(30, 11))
    }

    @Test fun nextDayErrores(){
        assertNotEquals(listOf(32, 13), DateHelper.nextDay(31, 12))
        assertNotEquals(listOf(1, 2), DateHelper.nextDay(28, 2))
        assertNotEquals(listOf(31, 5), DateHelper.nextDay(30, 4))
        assertNotEquals(listOf(29, 2), DateHelper.nextDay(28, 2))
    }

    @Test fun nextDayDiasNormales(){
        assertEquals(listOf(4, 3), DateHelper.nextDay(3, 3))
        assertEquals(listOf(10, 8), DateHelper.nextDay(9, 8))
        assertEquals(listOf(14, 5), DateHelper.nextDay(13, 5))
        assertEquals(listOf(2, 1), DateHelper.nextDay(1, 1))
    }
}