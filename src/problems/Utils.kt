package problems

import kotlin.math.pow

infix fun Long.exp(power: Int) = this.toFloat().pow(power)