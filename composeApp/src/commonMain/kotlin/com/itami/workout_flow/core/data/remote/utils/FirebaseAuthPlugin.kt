package com.itami.workout_flow.core.data.remote.utils

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.auth.AuthProvider
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.util.AttributeKey

class FirebaseAuthProvider(private val firebaseAuth: FirebaseAuth): AuthProvider {
    suspend fun getIdToken(): String? {
        return firebaseAuth.currentUser?.getIdToken(false)
    }

    @Deprecated(
        message = "Please use sendWithoutRequest function instead",
        level = DeprecationLevel.ERROR,
        replaceWith = ReplaceWith("error(\"Deprecated\")")
    )
    override val sendWithoutRequest: Boolean
        get() = error("Deprecated")

    override suspend fun addRequestHeaders(
        request: HttpRequestBuilder,
        authHeader: HttpAuthHeader?
    ) {
        val token = Firebase.auth.currentUser?.getIdToken(false)
        request.headers {
            if (contains(HttpHeaders.Authorization)) {
                remove(HttpHeaders.Authorization)
            }
            append(HttpHeaders.Authorization,  "Bearer $token")
        }
    }

    override fun isApplicable(auth: HttpAuthHeader): Boolean {
        return auth is HttpAuthHeader.Parameterized && auth.authScheme == "Bearer"
    }
}

class FirebaseAuthConfig {
    lateinit var provider: FirebaseAuthProvider
}

class FirebaseAuthPlugin(config: FirebaseAuthConfig) {
    private val provider = config.provider

    class Config {
        var provider: FirebaseAuthProvider? = null
        fun build() = FirebaseAuthConfig().apply {
            provider = this@Config.provider ?: FirebaseAuthProvider(Firebase.auth)
        }
    }

    companion object Plugin : HttpClientPlugin<Config, FirebaseAuthPlugin> {
        override val key: AttributeKey<FirebaseAuthPlugin> = AttributeKey("FirebaseAuthPlugin")

        override fun prepare(block: Config.() -> Unit): FirebaseAuthPlugin {
            val config = Config().apply(block).build()
            return FirebaseAuthPlugin(config)
        }

        override fun install(plugin: FirebaseAuthPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                plugin.provider.getIdToken()?.let { token ->
                    context.headers.append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
        }
    }
}
