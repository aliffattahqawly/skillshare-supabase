package com.yourssohail.learnsupabase.data.network

import com.yourssohail.learnsupabase.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.supabaseUrl ?: throw IllegalArgumentException("Supabase URL is missing"),
        supabaseKey = BuildConfig.supabaseKey ?: throw IllegalArgumentException("Supabase Key is missing")
    ) {
        install(GoTrue)
        install(Storage)
    }
}