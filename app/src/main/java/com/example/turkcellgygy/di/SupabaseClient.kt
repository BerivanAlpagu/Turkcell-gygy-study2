package com.example.turkcellgygy.di

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {

    val supabaseClient = createSupabaseClient(
        supabaseUrl = "https://xegnbeidmtgtdzjkxjui.supabase.co",
        supabaseKey = "sb_publishable_m3o-Dfp9ILDioN7RR77g9w_Y5EFkILQ"
    ){
        install(plugin = Postgrest)
    }
}