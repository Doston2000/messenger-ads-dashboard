package uz.codingtech.messengerdashboard.utils

import kotlin.math.floor

fun calculateViews(cpm: String, budget: String): Int {
    val cpmValue = cpm.toDoubleOrNull()
    val budgetValue = budget.toDoubleOrNull()

    return if (cpmValue != null && cpmValue > 0 && budgetValue != null) {
        floor((budgetValue / cpmValue) * 1000).toInt()
    } else {
        0
    }
}
