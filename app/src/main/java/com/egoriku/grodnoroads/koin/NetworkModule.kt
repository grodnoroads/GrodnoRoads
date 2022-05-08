package com.egoriku.grodnoroads.koin

import com.egoriku.grodnoroads.BuildConfig
import com.egoriku.grodnoroads.data.api.FORM_REPORT_ACTION
import com.egoriku.grodnoroads.data.api.GrodnoRoadsApi
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val networkModule = module {
    single {
        val retrosheetInterceptor = RetrosheetInterceptor.Builder()
            .setLogging(BuildConfig.DEBUG)
            .addForm(
                FORM_REPORT_ACTION,
                "https://docs.google.com/forms/d/e/1FAIpQLScvuOIcHuVCdR5fZNbwLPFslheGX6olZkr8dpo1gcYmakkIlQ/viewform?usp=sf_link"
            )
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(retrosheetInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://docs.google.com/spreadsheets/d/1b-R3WMGRzz48QO-zznHQAY7O7DsPM18DmwoLyPoV7Y8/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
    }

    single { get<Retrofit>().create<GrodnoRoadsApi>() }
}