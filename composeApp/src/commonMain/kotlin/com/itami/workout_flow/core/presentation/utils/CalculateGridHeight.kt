package com.itami.workout_flow.core.presentation.utils

import kotlin.math.ceil

fun calculateGridHeight(
    itemCount: Int,
    itemHeight: Int,
    gridColumnCount: Int,
    verticalSpacing: Int = 0,
    topPadding: Int = 0,
    bottomPadding: Int = 0
): Int {
    val rows = ceil(itemCount / gridColumnCount.toDouble()).toInt()
    return (rows * (itemHeight + verticalSpacing)) + topPadding + bottomPadding
}