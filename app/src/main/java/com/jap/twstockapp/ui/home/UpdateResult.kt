package com.jap.twstockapp.ui.home

import java.io.Serializable

/**
 * Authentication result : success (user details) or error message.
 */
data class UpdateResult(
    val success: Int? = null,
    val error: Int? = null
) : Serializable
