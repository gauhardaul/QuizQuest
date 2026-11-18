package com.example.mobileproject.ui.results

import kotlin.math.roundToInt


fun calculatePercent(score: Int, total: Int): Int {
    if (total <= 0) {
        return 0
    }
    return (score * 100.0 / total).roundToInt()
}