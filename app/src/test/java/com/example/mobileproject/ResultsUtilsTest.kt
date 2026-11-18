package com.example.mobileproject

import com.example.mobileproject.ui.results.calculatePercent
import com.example.mobileproject.ui.results.getResultMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultsUtilsTest {

    @Test
    fun zeroScore_isZeroPercent() {
        assertEquals(0, calculatePercent(0, 3))
    }

    @Test
    fun partialScore_isRounded() {
        assertEquals(33, calculatePercent(1, 3))
        assertEquals(67, calculatePercent(2, 3))
    }

    @Test
    fun fullScore_isHundredPercent() {
        assertEquals(100, calculatePercent(3, 3))
    }

    @Test
    fun noQuestions_doesNotCrash() {
        assertEquals(0, calculatePercent(0, 0))
    }

    @Test
    fun message_dependsOnPercent() {
        assertEquals("Perfect! 🏆", getResultMessage(100))
        assertEquals("Great job!", getResultMessage(67))
        assertEquals("Not bad, keep practicing!", getResultMessage(33))
        assertEquals("Don't give up, try again!", getResultMessage(0))
    }
}