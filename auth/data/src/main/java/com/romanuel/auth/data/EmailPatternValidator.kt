package com.romanuel.auth.data

import android.util.Patterns
import com.romanuel.auth.domain.PatternValidator

object EmailPatternValidator : PatternValidator {
    override fun matches(value: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(value).matches()
}