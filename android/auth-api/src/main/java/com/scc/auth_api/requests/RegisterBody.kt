package com.scc.auth_api.requests

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("userName") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phone: String? = null,
    @SerializedName("password") val password: String
)