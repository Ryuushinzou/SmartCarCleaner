package com.scc.auth_api.services

import com.scc.auth_api.requests.RegisterBody
import com.scc.auth_api.responses.RegisterResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * The Auth service that maps the requests to the related endpoint using the Retrofit library.
 *
 * @see <link>https://square.github.io/retrofit/</link> for more information regarding Retrofit
 */
interface AuthService {
    /**
     *
     */
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("userName") username: String,
        @Field("password") password: String
    ): Observable<String>

    /**
     *
     */
    @POST("users")
    fun register(@Body registerBody: RegisterBody): Observable<RegisterResponse>
}