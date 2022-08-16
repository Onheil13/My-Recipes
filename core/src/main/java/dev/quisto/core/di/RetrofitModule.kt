package dev.quisto.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.quisto.core.BuildConfig
import dev.quisto.core.domain.repository.RESTDataSource
import dev.quisto.core.util.DEFAULT_TIME_OUT
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {

    @Provides
    @Named("baseURL")
    fun provideBaseURl(): String = "https://api.spoonacular.com/recipes/"

    @Provides
    fun provideInterceptor(): Interceptor {
        return Interceptor {
            val original: Request = it.request()
            val originalHttpUrl: HttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(
                    "apiKey", BuildConfig.SUCCEED_API_KEY
                )
                .build()

            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()

            it.proceed(request)
        }
    }

    @Provides
    fun getOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
        .readTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
        .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    fun provideRetrofit(
        @Named("baseURL")
        baseUrl: String,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()
    }

    @Provides
    @Named("retrofitClient")
    fun getApiClient(retrofit: Retrofit): RESTDataSource {
        return retrofit.create(RESTDataSource::class.java)
    }


}

class NullOnEmptyConverterFactory : Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {

        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body -> if (body.contentLength() == 0L) null else delegate.convert(body) }
    }
}