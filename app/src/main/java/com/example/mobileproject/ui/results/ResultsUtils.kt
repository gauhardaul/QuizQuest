package com.example.mobileproject.ui.results

import kotlin.math.roundToInt


fun calculatePercent(score: Int, total: Int): Int {
    if (total <= 0) {
        return 0
    }
    return (score * 100.0 / total).roundToInt()
}


fun getResultMessage(percent: Int): String {
    return when {
        percent == 100 -> "Perfect! 🏆"
        percent >= 60 -> "Great job!"
        percent >= 30 -> "Not bad, keep practicing!"
        else -> "Don't give up, try again!"
    }
}