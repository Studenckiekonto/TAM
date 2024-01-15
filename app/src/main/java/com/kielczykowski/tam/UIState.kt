package com.kielczykowski.tam

import com.kielczykowski.tam.data.Cities
import com.kielczykowski.tam.data.population


data class UIState (
    val citiess :List<Cities>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class UIStateDetails (
    val citiessdetails :List<population>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)