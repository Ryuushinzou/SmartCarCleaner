package com.scc.auth_api.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("userName") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phone: String? = null,
    @SerializedName("carIds") val carIds: List<Int> = emptyList()
)